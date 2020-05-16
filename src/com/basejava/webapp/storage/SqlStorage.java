package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.ConnectionFactory;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper = new SqlHelper();


    public SqlStorage(String dbUrl, String dbUser, String bdPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, bdPassword);
    }

    @Override
    public int size() {
        AtomicInteger result = new AtomicInteger();
        sqlHelper.process(connectionFactory, "SELECT COUNT(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result.set(resultSet.getInt(1));
            } else throw new StorageException("Base reading error","");
        });
        return result.get();
    }

    @Override
    public void clear() {
        sqlHelper.process(connectionFactory, "DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.process(connectionFactory, "SELECT * FROM resume r WHERE r.uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, resume.getUuid());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                throw new ExistStorageException(resume.getUuid());
            }
            sqlHelper.process(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?,?)", preparedStatement1 -> {
                preparedStatement1.setString(1, resume.getUuid());
                preparedStatement1.setString(2, resume.getFullName());
                preparedStatement1.execute();
            });
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.process(connectionFactory, "SELECT * FROM resume r WHERE r.uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, resume.getUuid());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(resume.getUuid());
            }
            sqlHelper.process(connectionFactory, "UPDATE resume SET full_name = ? WHERE uuid = ?", preparedStatement1 -> {
                preparedStatement1.setString(1, resume.getFullName());
                preparedStatement1.setString(2, resume.getUuid());
                preparedStatement1.execute();
            });
        });
    }

    @Override
    public Resume get(String uuid) {
        AtomicReference<Resume> resume = new AtomicReference<>();
        sqlHelper.process(connectionFactory, "SELECT * FROM resume r WHERE r.uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            resume.set(new Resume(uuid, resultSet.getString("full_name")));
        });
        return resume.get();
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.process(connectionFactory, "SELECT * FROM resume r WHERE r.uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            sqlHelper.process(connectionFactory, "DELETE FROM resume WHERE uuid = ?", preparedStatement1 -> {
                preparedStatement1.setString(1, uuid);
                preparedStatement1.execute();
            });
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> resumes = new ArrayList<>();
        ArrayList<String> uuids = new ArrayList<>();
        sqlHelper.process(connectionFactory, "SELECT uuid FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                uuids.add(resultSet.getString(1));
            }
        });
        for (String uuid : uuids) {
            resumes.add(get(uuid));
        }
        Collections.sort(resumes);
        return resumes;
    }
}
