package hw09.jdbc.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.CaseFormat;

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
        sb.append(prepareAllFieldsSql(fields));
        sb.append(" from ");
        sb.append(toLowerUnderScore(entityClass.getName()));
        return sb.toString();
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSelectAllSql());
        sb.append(" where id = ?");
        return sb.toString();
    }

    private String prepareAllFieldsSql(List<Field> fields) {
        final String fieldsString = fields.stream().map((f) -> f.getName()).map((f) -> toLowerUnderScore(f))
                .collect(Collectors.joining(", "));
        return fieldsString;
    }

    private String prepareAllFieldsValueSql() {
        final List<String> values = Collections.nCopies(entityClass.getFieldsWithoutId().size(), "?");
        return values.stream().collect(Collectors.joining(", "));
    }


    @Override
    public String getInsertSql() {
        StringBuilder sb = new StringBuilder();
        final var fields = entityClass.getFieldsWithoutId();
        if (fields.isEmpty()) {
            throw new RuntimeException("Can't create sql request for type " + entityClass.getName());
        }
        sb.append("insert into ");
        sb.append(toLowerUnderScore(entityClass.getName()));
        sb.append(" (");
        sb.append(prepareAllFieldsSql(fields));
        sb.append(") values (");
        sb.append(prepareAllFieldsValueSql());
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String getUpdateSql() {
        final var fields = entityClass.getFieldsWithoutId();
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(toLowerUnderScore(entityClass.getName()));
        sb.append(" set ");
        final String sqlPart = fields.stream().map((f) -> f.getName()).map((f) -> toLowerUnderScore(f) + " = ?")
                .collect(Collectors.joining(", "));
        sb.append(sqlPart);
        sb.append(" where id = ?");
        return sb.toString();
    }

    public List<Object> getValues(T obj) {
        final var fields = entityClass.getFieldsWithoutId();
        final List<Object> res = new ArrayList<>();
        for (Field field : fields) {
            try {
                res.add(field.get(obj));
            } catch (Exception e) {
                throw new MapperException(e);
            }
        }
        return res;
    }


    private String toLowerUnderScore(String in) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, in);
    }
}
