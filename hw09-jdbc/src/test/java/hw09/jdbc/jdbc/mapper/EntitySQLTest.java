package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hw09.jdbc.jdbc.mapper.testingclasses.CommonClass;
import hw09.jdbc.jdbc.mapper.testingclasses.EmptyClass;

public class EntitySQLTest {
    final EntityClass<CommonClass> entityClass = new EntityClass<>(new CommonClass());

    @Test
    public void testCtrWithNull() {
        assertThrows(MapperException.class, () -> new EntitySQL<Object>(null, new Object()));
    }

    @Test
    public void testCtrWithNullEntity() {
        assertThrows(MapperException.class, () -> new EntitySQL<CommonClass>(entityClass, null));
    }

    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new EntitySQL<CommonClass>(entityClass, new CommonClass()));
    }

    @Test
    public void testGetSelectAllSqlForEmptyType() {
        final var emptyEntityClass = new EntityClass<EmptyClass>(new EmptyClass());
        assertThrows(RuntimeException.class,
                () -> new EntitySQL<EmptyClass>(emptyEntityClass, new EmptyClass()).getSelectAllSql());
    }

    @Test
    public void testGetSelectAllSql() {
        final var entitySql = new EntitySQL<CommonClass>(entityClass, new CommonClass());
        assertEquals("select id, str_field, int_field from common_class", entitySql.getSelectAllSql());
    }

    @Test
    public void testGetSelectByIdSql() {
        assertThrows(UnsupportedOperationException.class,
                () -> new EntitySQL<CommonClass>(entityClass, new CommonClass()).getSelectByIdSql());
    }

    @Test
    public void testGetInsertSql() {
        final var entitySql = new EntitySQL<CommonClass>(entityClass, new CommonClass());
        assertEquals("insert into common_class (str_field, int_field) values (?, ?)", entitySql.getInsertSql());
    }

    @Test
    public void testGetUpdateSql() {
        assertThrows(UnsupportedOperationException.class,
                () -> new EntitySQL<CommonClass>(entityClass, new CommonClass()).getUpdateSql());
    }

}
