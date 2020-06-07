package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EntitySQLTest {
    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new EntitySQL<Object>(null));
    }

    @Test
    public void testGetSelectAllSqlForEmptyType() {
        class Test {
        }
        assertThrows(RuntimeException.class,
                () -> new EntitySQL<Test>(new EntityClass<Test>(Test.class)).getSelectAllSql());
    }

    @Test
    public void testGetSelectAllSql() {
        class ClassForTesting {
            final int id = 0;
            final String strField = new String();
        }
        assertEquals("select id, str_field from class_for_testing",
                new EntitySQL<ClassForTesting>(new EntityClass<ClassForTesting>(ClassForTesting.class)).getSelectAllSql());
    }

    @Test
    public void testGetSelectByIdSql() {
        assertThrows(UnsupportedOperationException.class, () -> new EntitySQL<Object>(null).getSelectByIdSql());
    }

    @Test
    public void testGetInsertSql() {
        class ClassForTesting {
            final int id = 0;
            final String strField = new String();
            final int intFiled = 1;
        }
        assertEquals("insert into classfortesting(name) values (?)", new EntitySQL<ClassForTesting>(new EntityClass<ClassForTesting>(ClassForTesting.class)) );
    }

    @Test
    public void testGetUpdateSql() {
        assertThrows(UnsupportedOperationException.class, () -> new EntitySQL<Object>(null).getUpdateSql());
    }

}
