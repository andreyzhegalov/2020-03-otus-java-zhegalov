package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;

import hw09.jdbc.jdbc.mapper.testingclasses.ClassWithIdNotation;
import hw09.jdbc.jdbc.mapper.testingclasses.ClassWithManyIdNotations;
import hw09.jdbc.jdbc.mapper.testingclasses.EmptyClass;
import hw09.jdbc.jdbc.mapper.testingclasses.User;

public class UserEntityClassTest {
    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new EntityClass<Object>(new Object()));
    }

    @Test
    public void testGetName() {
        assertEquals("Object", new EntityClass<Object>(new Object()).getName());
    }

    @Test
    public void testGetConstructor() {
        assertThrows(UnsupportedOperationException.class, () -> new EntityClass<Object>(Object.class).getConstructor());
    }

    @Test
    public void testGetIdFieldFailed() {
        assertThrows(RuntimeException.class,
                () -> new EntityClass<EmptyClass>(new EmptyClass()).getIdField().getName());
    }

    @Test
    public void testGetIdFieldSuccesfull() {
        assertEquals("intField",
                new EntityClass<ClassWithIdNotation>(new ClassWithIdNotation()).getIdField().getName());
    }

    @Test
    public void testGetIdFieldForClassWithManyIdField() {
        assertThrows(RuntimeException.class,
                () -> new EntityClass<ClassWithManyIdNotations>(new ClassWithManyIdNotations()).getIdField().getName());
    }

    private boolean hasFieldWithName(List<Field> fields, String name) {
        return null != fields.stream().filter(field -> name.equals(field.getName())).findAny().orElse(null);
    }

    @Test
    public void testGetAllFields() {
        final var fields = new EntityClass<User>(new User()).getAllFields();
        assertEquals(3, fields.size());
        assertTrue(hasFieldWithName(fields, "id"));
        assertTrue(hasFieldWithName(fields, "name"));
        assertTrue(hasFieldWithName(fields, "age"));
    }

    @Test
    public void testGetFieldsWithoutId() {
        final var fields = new EntityClass<User>(new User()).getFieldsWithoutId();
        assertEquals(2, fields.size());
        assertTrue(hasFieldWithName(fields, "name"));
        assertTrue(hasFieldWithName(fields, "age"));
    }
}
