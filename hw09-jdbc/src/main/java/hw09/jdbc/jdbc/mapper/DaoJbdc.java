package hw09.jdbc.jdbc.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class DaoJbdc<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(DaoJbdc.class);

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
    public long insert(T objectData) {
        try {
            return dbExecutor.executeInsert(getConnection(), this.entitySQL.getInsertSql(),
                    this.entitySQL.getValues(objectData));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
        try {
            return dbExecutor.executeSelect(getConnection(), "select id, name, age from user where id  = ?", id, rs -> {
                try {
                    if (rs.next()) {
                        final var ctr = entityClass.getConstructor();
                        final T entity = ctr.newInstance();
                        for (final var field : entityClass.getAllFields()) {
                            entityClass.setField(entity, field.getName(), rs.getObject(field.getName()));
                        }
                        return entity;
                    }
                    return null;
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                } catch (InstantiationException e) {
                    logger.error(e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                } catch (IllegalArgumentException e) {
                    logger.error(e.getMessage(), e);
                } catch (InvocationTargetException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            }).get();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    // public static Class<?>[] toClasses(Object[] args) {
    // return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    // }

}
