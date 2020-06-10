package hw09.jdbc.jdbc.mapper;

import java.sql.Connection;

import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class DaoJbdc<T> implements JdbcMapper<T> {
    private final DbExecutorImpl<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;

    public DaoJbdc(DbExecutorImpl<T> dbExecutor, SessionManagerJdbc sessionManager) {
        this.dbExecutor = dbExecutor;
        this.sessionManager = sessionManager;
    }

    @Override
    public void insert(T objectData) {
        final EntityClass<T> entityClass = new EntityClass<T>(objectData);
        final EntitySQL<T> entitySql = new EntitySQL<T>(entityClass, objectData);
        try {
            final long res = dbExecutor.executeInsert(getConnection(), entitySql.getInsertSql(), entitySql.getValues());
            if (res == 0) {
                throw new MapperException("Record not insert");
            }
        } catch (Exception e) {
            throw new MapperException(e);
        }
    }

    @Override
    public void update(T objectData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertOrUpdate(T objec0tData) {
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
