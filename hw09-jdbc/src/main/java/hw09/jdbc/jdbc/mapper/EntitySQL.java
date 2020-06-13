package hw09.jdbc.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQL<T> implements EntitySQLMetaData {
    final EntityClassMetaData<T> entityClass;

    public EntitySQL(EntityClassMetaData<T> entityClass) {
        if (entityClass == null) {
            throw new MapperException("Argument must be not null.");
        }
        this.entityClass = entityClass;
    }

    @Override
    public String getSelectAllSql() {
        StringBuilder sb = new StringBuilder();
        final var fields = entityClass.getAllFields();
        if (fields.isEmpty()) {
            throw new MapperException("Can't create sql request for type " + entityClass.getName());
        }
        sb.append("select ");
        sb.append(prepareSqlFields(fields));
        sb.append(" from ");
        sb.append(NameConverterHelper.toLowerUnderScore(entityClass.getName()));
        return sb.toString();
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSelectAllSql());
        sb.append(prepareWhereIdPart());
        return sb.toString();
    }

    private String prepareSqlFields(List<Field> fields) {
        final String fieldsString = fields.stream().map((f) -> f.getName())
                .map((f) -> NameConverterHelper.toLowerUnderScore(f)).collect(Collectors.joining(", "));
        return fieldsString;
    }

    private String prepareSqlValues() {
        final List<String> values = Collections.nCopies(entityClass.getFieldsWithoutId().size(), "?");
        return values.stream().collect(Collectors.joining(", "));
    }

    @Override
    public String getInsertSql() {
        StringBuilder sb = new StringBuilder();
        final var fields = entityClass.getFieldsWithoutId();
        if (fields.isEmpty()) {
            throw new MapperException("Can't create sql request for type " + entityClass.getName());
        }
        sb.append("insert into ");
        sb.append(NameConverterHelper.toLowerUnderScore(entityClass.getName()));
        sb.append(" (");
        sb.append(prepareSqlFields(fields));
        sb.append(") values (");
        sb.append(prepareSqlValues());
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String getUpdateSql() {
        final var fields = entityClass.getFieldsWithoutId();
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(NameConverterHelper.toLowerUnderScore(entityClass.getName()));
        sb.append(" set ");
        final String sqlPart = fields.stream().map((f) -> f.getName())
                .map((f) -> NameConverterHelper.toLowerUnderScore(f) + " = ?").collect(Collectors.joining(", "));
        sb.append(sqlPart);
        sb.append(prepareWhereIdPart());
        return sb.toString();
    }

    private String prepareWhereIdPart() {
        StringBuilder sb = new StringBuilder();
        sb.append(" where ");
        sb.append(NameConverterHelper.toLowerUnderScore(entityClass.getIdField().getName()));
        sb.append(" = ?");
        return sb.toString();
    }

}
