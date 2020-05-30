package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.Section;
import com.basejava.webapp.model.SectionType;
import com.basejava.webapp.sql.SqlHelper;
import com.basejava.webapp.util.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;


    public SqlStorage(String dbUrl, String dbUser, String bdPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, bdPassword);
    }

    @Override
    public int size() {
        return sqlHelper.process("SELECT COUNT(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else throw new StorageException("Base reading error", "");
        });
    }

    @Override
    public void clear() {
        sqlHelper.process("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }
            writeContacts(resume, connection);
            writeSections(resume, connection);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DELETE FROM contact WHERE resume_uuid = ?; " +
                    "DELETE FROM section WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getUuid());
                preparedStatement.execute();
            }
            writeContacts(resume, connection);
            writeSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.process("" +
                " SELECT * FROM resume r " +
                "   LEFT JOIN contact c " +
                "       ON r.uuid = c.resume_uuid " +
                "LEFT JOIN section s ON r.uuid = s.resume_uuid" +
                " WHERE r.uuid = ? ", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));
            do {
                readContact(resultSet, resume);
                readSections(resultSet, resume);
            } while (resultSet.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.process("DELETE FROM resume WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume ORDER BY uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    String name = resultSet.getString("full_name");
                    resumeMap.put(uuid, new Resume(uuid, name));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("resume_uuid");
                    Resume resume = resumeMap.get(uuid);
                    readContact(resultSet, resume);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("resume_uuid");
                    Resume resume = resumeMap.get(uuid);
                    readSections(resultSet, resume);
                }
            }
            return new ArrayList<>(resumeMap.values());
        });

    }

    private void writeContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, entry.getKey().name());
                preparedStatement.setString(3, entry.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void writeSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO section (resume_uuid, section_type, section_value) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, sectionType.name());
                Section section = entry.getValue();
                preparedStatement.setString(3, JsonParser.write(section, Section.class));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }

    }

    private void readContact(ResultSet resultSet, Resume resume) throws SQLException {
        String type = resultSet.getString("type");
        if (type != null) {
            resume.addContact(ContactType.valueOf(type), resultSet.getString("value"));
        }
    }

    private void readSections(ResultSet resultSet, Resume resume) throws SQLException {
        String type = resultSet.getString("section_type");
        if (type != null) {
            SectionType sectionType = SectionType.valueOf(type);
            Section section = JsonParser.read(resultSet.getString("section_value"), Section.class);
            resume.addSection(sectionType, section);
        }
    }
}
