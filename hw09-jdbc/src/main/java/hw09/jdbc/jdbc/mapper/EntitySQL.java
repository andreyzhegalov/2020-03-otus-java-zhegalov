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
        sb.append("select ");

        final var fields = entityClass.getAllFields();
        if (fields.isEmpty()) {
            throw new RuntimeException("Can't create sql request for type " + entityClass.getName());
        }
        final var iter = fields.iterator();
        while (iter.hasNext()) {
            sb.append(toLowerUnderScore( iter.next().getName()));
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(" from ");
        sb.append(toLowerUnderScore(entityClass.getName()));
        return sb.toString();
    }

    @Override
    public String getSelectByIdSql() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInsertSql() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUpdateSql() {
        throw new UnsupportedOperationException();
    }

    private String toLowerUnderScore(String in){
        final var res = new String();
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, in);
    }
}
