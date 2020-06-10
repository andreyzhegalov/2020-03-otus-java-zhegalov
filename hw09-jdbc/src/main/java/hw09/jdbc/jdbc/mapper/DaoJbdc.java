package hw09.jdbc.jdbc.mapper;

import java.sql.Connection;

import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class DaoJbdc<T> implements JdbcMapper<T> {
    private final DbExecutorImpl<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;
    private final EntityClass<T> entityClass;
    private final EntitySQL<T> entitySQL;

    public DaoJbdc(DbExecutorImpl<T> dbExecutor, SessionManagerJdbc sessionManager, Class<T> entityType) {
        this.dbExecutor = dbExecutor;
        this.sessionManager = sessionManager;
        this.entityClass = new EntityClass<T>(entityType);
        this.entitySQL = new EntitySQL<T>(this.entityClass);
    }

    @Override
    public void insert(T objectData) {
        try {
            final long res = dbExecutor.executeInsert(getConnection(), this.entitySQL.getInsertSql(),
                    this.entitySQL.getValues(objectData));
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
