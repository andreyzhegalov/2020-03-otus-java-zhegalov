package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hw09.jdbc.jdbc.mapper.testingclasses.CommonClass;
import hw09.jdbc.jdbc.mapper.testingclasses.EmptyClass;

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
    public void testGetSelectAllSqlForEmptyType() {
        final var emptyEntityClass = new EntityClass<EmptyClass>(EmptyClass.class);
        assertThrows(RuntimeException.class, () -> new EntitySQL<EmptyClass>(emptyEntityClass).getSelectAllSql());
    }

    @Test
    public void testGetSelectAllSql() {
        final var entitySql = new EntitySQL<CommonClass>(entityClass);
        assertEquals("select id, str_field, int_field from common_class", entitySql.getSelectAllSql());
    }

    @Test
    public void testGetSelectByIdSql() {
        final var entitySql = new EntitySQL<CommonClass>(entityClass);
        assertEquals("select id, str_field, int_field from common_class where id = ?", entitySql.getSelectByIdSql());
    }

    @Test
    public void testGetInsertSql() {
        final var entitySql = new EntitySQL<CommonClass>(entityClass);
        assertEquals("insert into common_class (str_field, int_field) values (?, ?)", entitySql.getInsertSql());
    }

    @Test
    public void testGetUpdateSql() {
        assertThrows(UnsupportedOperationException.class, () -> new EntitySQL<CommonClass>(entityClass).getUpdateSql());
    }

}
