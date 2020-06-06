package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EntitySQLTest {
    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new EntitySQL());
    }

    @Test
    public void testGetSelectAllSql() {
        assertThrows(UnsupportedOperationException.class, ()->new EntitySQL().getSelectAllSql());
    }

    @Test
    public void testGetSelectByIdSql() {
        assertThrows(UnsupportedOperationException.class, ()->new EntitySQL().getSelectByIdSql());
    }

    @Test
    public void testGetInsertSql() {
        assertThrows(UnsupportedOperationException.class, ()-> new EntitySQL().getInsertSql());
    }

    @Test
    public void testGetUpdateSql() {
        assertThrows(UnsupportedOperationException.class, ()->new EntitySQL().getUpdateSql());
    }
}
