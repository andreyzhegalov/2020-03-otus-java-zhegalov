package hw09.jdbc.jdbc.mapper;

import com.google.common.base.CaseFormat;

public class EntitySQL<T> implements EntitySQLMetaData {
    final EntityClassMetaData<T> entityClass;

    public EntitySQL(EntityClassMetaData<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String getSelectAllSql() {
        StringBuilder sb = new StringBuilder();
        final var fields = entityClass.getAllFields();
        if (fields.isEmpty()) {
            throw new RuntimeException("Can't create sql request for type " + entityClass.getName());
        }
        sb.append("select ");
        sb.append(prepareAllFieldsSql());
        sb.append(" from ");
        sb.append(toLowerUnderScore(entityClass.getName()));
        return sb.toString();
    }

    @Override
    public String getSelectByIdSql() {
        throw new UnsupportedOperationException();
    }

    public String prepareAllFieldsSql() {
        final var fields = entityClass.getAllFields();
        final var sb = new StringBuilder();
        for (final var field : fields) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(toLowerUnderScore(field.getName()));
        }
        return sb.toString();
    }

    public String prepareAllFieldsValueSql() {
        final var fields = entityClass.getAllFields();
        final var sb = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append('?');
        }
        return sb.toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder sb = new StringBuilder();
        final var fields = entityClass.getAllFields();
        if (fields.isEmpty()) {
            throw new RuntimeException("Can't create sql request for type " + entityClass.getName());
        }
        sb.append("insert into ");
        sb.append(toLowerUnderScore(entityClass.getName()));
        sb.append(" (");
        sb.append(prepareAllFieldsSql());
        sb.append(") values (");
        sb.append(prepareAllFieldsValueSql());
        sb.append(")");

        return sb.toString();
    }

    @Override
    public String getUpdateSql() {
        throw new UnsupportedOperationException();
    }

    private String toLowerUnderScore(String in) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, in);
    }
}
