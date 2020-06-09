package hw09.jdbc.jdbc.mapper;

import java.sql.Connection;
import java.util.Collections;

import hw09.jdbc.jdbc.DbExecutor;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class DaoJbdc<T> implements JdbcMapper<T> {
    private final DbExecutor<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;

    public DaoJbdc(DbExecutor<T> dbExecutor, SessionManagerJdbc sessionManager) {
        this.dbExecutor = dbExecutor;
        this.sessionManager = sessionManager;
    }

    @Override
    public void insert(T objectData) {
        final EntityClass<T> entityClass = new EntityClass<T>(objectData);
        final EntitySQL<T> entitySql = new EntitySQL<T>(entityClass);
        try {
            return dbExecutor.executeInsert(getConnection(), entitySql.getInsertSql(),
                    Collections.singletonList(user.getName()));
        } catch (Exception e) {
            throw new MapperException(e);
        }
    }

    @Override
    public void update(T objectData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertOrUpdate(T objectData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T findById(long id, Class<T> clazz) {
        throw new UnsupportedOperationException();
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
