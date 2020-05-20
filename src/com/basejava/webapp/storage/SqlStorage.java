package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.ExceptionUtil;
import com.basejava.webapp.sql.SqlHelper;

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
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.execute();
            }
            writeContacts(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.process("" +
                " SELECT * FROM resume r " +
                "   LEFT JOIN contact c " +
                "       ON r.uuid = c.resume_uuid " +
                " WHERE r.uuid = ? ", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));
            do {
                readContact(resultSet, resume);
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
        return sqlHelper.process("" +
                "SELECT * FROM resume " +
                "LEFT JOIN contact c " +
                " ON resume.uuid = c.resume_uuid " +
                " ORDER BY resume.uuid", preparedStatement -> {
            Map<String, Resume> resumeHashMap = new LinkedHashMap<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Resume resume = resumeHashMap.computeIfAbsent(resultSet.getString("resume_uuid"), (k) -> {
                    try {
                        return new Resume(resultSet.getString("resume_uuid"), resultSet.getString("full_name"));
                    } catch (SQLException e) {
                        throw ExceptionUtil.convertException(e);
                    }
                });
                readContact(resultSet, resume);
                resumeHashMap.put(resume.getUuid(), resume);
            }
            return new ArrayList<>(resumeHashMap.values());
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

    private void readContact(ResultSet resultSet, Resume resume) throws SQLException {
        String type = resultSet.getString("type");
        if (type != null) {
            resume.addContact(ContactType.valueOf(type), resultSet.getString("value"));
        }
    }
}
