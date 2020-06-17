package hw09.jdbc.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClass;
    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClass) {
        if (entityClass == null) {
            throw new MapperException("Argument must be not null.");
        }
        this.entityClass = entityClass;
        this.selectAllSql = prepareGetSelectAllSql();
        this.selectByIdSql = prepareSelectByIdSlq();
        this.insertSql = prepareInsertSql();
        this.updateSql = prepareUpdateSql();
    }

    @Override
    public String getSelectAllSql() {
        return this.selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return this.selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return this.insertSql;
    }

    @Override
    public String getUpdateSql() {
        return this.updateSql;
    }

    private String prepareGetSelectAllSql() {
        final var fields = entityClass.getAllFields();
        if (fields.isEmpty()) {
            throw new MapperException("Can't create sql request for type " + entityClass.getName());
        }
        return String.format("select %s from %s", prepareSqlFields(fields),
                NameConverterHelper.toLowerUnderScore(entityClass.getName()));
    }

    private String prepareSelectByIdSlq() {
        return String.format("%s%s", getSelectAllSql(), prepareWhereIdPart());
    }

    private String prepareInsertSql() {
        final var fields = entityClass.getFieldsWithoutId();
        if (fields.isEmpty()) {
            throw new MapperException("Can't create sql request for type " + entityClass.getName());
        }
        return String.format("insert into %s (%s) values (%s)",
                NameConverterHelper.toLowerUnderScore(entityClass.getName()), prepareSqlFields(fields),
                prepareSqlValues());
    }

    private String prepareUpdateSql() {
        final var fields = entityClass.getFieldsWithoutId();
        final String sqlPart = fields.stream().map(Field::getName)
                .map((f) -> NameConverterHelper.toLowerUnderScore(f) + " = ?").collect(Collectors.joining(", "));
        return String.format("update %s set %s%s", NameConverterHelper.toLowerUnderScore(entityClass.getName()),
                sqlPart, prepareWhereIdPart());
    }

    private String prepareSqlFields(List<Field> fields) {
        return fields.stream().map(Field::getName)
                .map(NameConverterHelper::toLowerUnderScore).collect(Collectors.joining(", "));
    }

    private String prepareSqlValues() {
        final List<String> values = Collections.nCopies(entityClass.getFieldsWithoutId().size(), "?");
        return String.join(", ", values);
    }

    private String prepareWhereIdPart() {
        return String.format(" where %s = ?",
                NameConverterHelper.toLowerUnderScore(entityClass.getIdField().getName()));
    }
}
