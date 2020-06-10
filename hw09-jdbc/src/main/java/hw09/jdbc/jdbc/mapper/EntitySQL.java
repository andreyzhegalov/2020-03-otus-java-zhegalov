package hw09.jdbc.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.CaseFormat;

public class EntitySQL<T> implements EntitySQLMetaData {
    final EntityClassMetaData<T> entityClass;
    final T obj;

    public EntitySQL(EntityClassMetaData<T> entityClass, T obj) {
        if ((entityClass == null) || (obj == null)) {
            throw new MapperException("Argument must be not null.");
        }
        this.obj = obj;
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
        throw new UnsupportedOperationException();
    }

    private String prepareAllFieldsSql( List<Field> fields) {
        final String fieldsString = fields.stream().map((f) -> f.getName()).map((f) -> toLowerUnderScore(f))
                .collect(Collectors.joining(", "));
        return fieldsString;
    }

    private String prepareAllFieldsValueSql() {
        final List<String> values = Collections.nCopies(getValues().size(), "?");
        return values.stream().collect(Collectors.joining(", "));
    }

    public List<Object> getValues() {
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
        throw new UnsupportedOperationException();
    }

    private String toLowerUnderScore(String in) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, in);
    }
}
