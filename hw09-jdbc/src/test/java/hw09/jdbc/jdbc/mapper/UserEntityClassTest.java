package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UserEntityClassTest {
    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new EntityClass<User>());
    }

    @Test
    public void testGetName() {
        assertThrows(UnsupportedOperationException.class, () -> new EntityClass<User>().getName());
    }

    @Test
    public void testGetConstructor() {
        assertThrows(UnsupportedOperationException.class, () -> new EntityClass<User>().getConstructor());
    }

    @Test
    public void testGetIsField() {
        assertThrows(UnsupportedOperationException.class, () -> new EntityClass<User>().getIdField());
    }

    @Test
    public void testGetAllFields() {
        assertThrows(UnsupportedOperationException.class, () -> new EntityClass<User>().getAllFields());
    }

    @Test
    public void testGetFieldsWithoutId() {
        assertThrows(UnsupportedOperationException.class, () -> new EntityClass<User>().getFieldsWithoutId());
    }
}
