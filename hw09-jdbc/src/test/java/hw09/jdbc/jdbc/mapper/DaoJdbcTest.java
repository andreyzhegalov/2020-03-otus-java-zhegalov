package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DaoJdbcTest {
    @Test
    public void testInsert() {
        assertThrows(UnsupportedOperationException.class, () -> new DaoJbdc<>().insert(null));
    }

    @Test
    public void testUpate() {
        assertThrows(UnsupportedOperationException.class, () -> new DaoJbdc<>().update(null));
    }

    @Test
    public void testInsertOrUpdate() {
        assertThrows(UnsupportedOperationException.class, () -> new DaoJbdc<>().insertOrUpdate(null));
    }

    @Test
    public void testFindById() {
        assertThrows(UnsupportedOperationException.class, () -> new DaoJbdc<>().findById(0, null));
    }
}
