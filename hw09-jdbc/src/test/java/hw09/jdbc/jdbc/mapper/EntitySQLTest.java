package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hw09.jdbc.jdbc.mapper.testingclasses.CommonClass;

public class EntitySQLTest {
    final EntityClass<CommonClass> entityClass = new EntityClass<>(CommonClass.class);

    @Test
    public void testCtrWithNull() {
        assertThrows(MapperException.class, () -> new EntitySQL<Object>(null));
    }

    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new EntitySQL<CommonClass>(entityClass));
    }

    @Test
    public void testGetSelectAllSql() {
        final var entitySql = new EntitySQL<CommonClass>(entityClass);
        assertEquals("select int_id, str_field, int_field from common_class", entitySql.getSelectAllSql());
    }

    @Test
    public void testGetSelectByIdSql() {
        final var entitySql = new EntitySQL<CommonClass>(entityClass);
        assertEquals("select int_id, str_field, int_field from common_class where int_id = ?", entitySql.getSelectByIdSql());
    }

    @Test
    public void testGetInsertSql() {
        final var entitySql = new EntitySQL<CommonClass>(entityClass);
        assertEquals("insert into common_class (str_field, int_field) values (?, ?)", entitySql.getInsertSql());
    }

    @Test
    public void testGetUpdateSql() {
        final var entitySql = new EntitySQL<CommonClass>(entityClass);
        assertEquals("update common_class set str_field = ?, int_field = ? where int_id = ?", entitySql.getUpdateSql());
    }

}
