package hw09.jdbc.jdbc.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private final DbExecutorImpl<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;
    private final EntityClass<T> entityClass;
    private final EntitySQL<T> entitySQL;

    public JdbcMapperImpl(DbExecutorImpl<T> dbExecutor, SessionManagerJdbc sessionManager, Class<T> entityType) {
        this.dbExecutor = dbExecutor;
        this.sessionManager = sessionManager;
        this.entityClass = new EntityClass<>(entityType);
        this.entitySQL = new EntitySQL<>(this.entityClass);
    }

    private List<Object> getFieldsValue(T objectData) {
        return entityClass.getFieldsWithoutId().stream().map((f) -> entityClass.getFieldValue(objectData, f.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void insert(T objectData) {
        try {
            final long id = dbExecutor.executeInsert(getConnection(), this.entitySQL.getInsertSql(),
                    getFieldsValue(objectData));
            entityClass.setFieldValue(objectData, entityClass.getIdField().getName(), id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MapperException(e);
        }
    }

    @Override
    public void update(T objectData) {
        try {
            final long id = entityClass.getIdValue(objectData);
            final var object = findById(id);
            if (object == null) {
                throw new MapperException("Failed update object data. No item with id " + id);
            }
            dbExecutor.executeUpdate(getConnection(), entitySQL.getUpdateSql(), id, getFieldsValue(objectData));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MapperException(e);
        }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        if (entityClass.getIdValue(objectData) > 0) {
            update(objectData);
        }
        insert(objectData);
    }

    @Override
    public T findById(long id) {
        try {
            return dbExecutor.executeSelect(getConnection(), entitySQL.getSelectByIdSql(), id, rs -> {
                try {
                    if (rs.next()) {
                        final var ctr = entityClass.getConstructor();
                        final T objectData = ctr.newInstance();
                        for (final var field : entityClass.getAllFields()) {
                            final var dbFieldName = NameConverterHelper.toLowerUnderScore(field.getName());
                            final var value = rs.getObject(dbFieldName);
                            entityClass.setFieldValue(objectData, field.getName(), value);
                        }
                        return objectData;
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
}
