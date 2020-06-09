package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hw09.jdbc.jdbc.mapper.testingclasses.CommonClass;

public class EntitySQLTest {
    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new EntitySQL<Object>(null));
    }

    @Test
    public void testGetSelectAllSqlForEmptyType() {
        assertThrows(RuntimeException.class,
                () -> new EntitySQL<Object>(new EntityClass<Object>(Object.class)).getSelectAllSql());
    }

    @Test
    public void testGetSelectAllSql() {
        final var entityClass = new EntityClass<CommonClass>(CommonClass.class);
        final var entitySql = new EntitySQL<CommonClass>(entityClass);
        assertEquals("select id, str_field, int_field from common_class",
                entitySql.getSelectAllSql());
    }

    @Test
    public void testGetSelectByIdSql() {
        assertThrows(UnsupportedOperationException.class, () -> new EntitySQL<Object>(null).getSelectByIdSql());
    }

    @Test
    public void testGetInsertSql() {
        final var entityClass = new EntityClass<CommonClass>(CommonClass.class);
        final var entitySql = new EntitySQL<CommonClass>(entityClass);
        assertEquals("insert into common_class (id, str_field, int_field) values (?, ?, ?)",  entitySql.getInsertSql());
    }

    @Test
    public void testGetUpdateSql() {
        assertThrows(UnsupportedOperationException.class, () -> new EntitySQL<Object>(null).getUpdateSql());
    }

}
