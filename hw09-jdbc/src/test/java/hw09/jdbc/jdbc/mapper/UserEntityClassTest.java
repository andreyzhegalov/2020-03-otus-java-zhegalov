package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;

import hw09.jdbc.jdbc.mapper.annotations.Id;
import hw09.jdbc.jdbc.mapper.testingclasses.ClassWithIdNotation;
import hw09.jdbc.jdbc.mapper.testingclasses.ClassWithManyIdNotations;

public class UserEntityClassTest {
    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new EntityClass<Object>(Object.class));
    }

    @Test
    public void testGetName() {
        assertEquals("Object", new EntityClass<Object>(Object.class).getName());
    }

    @Test
    public void testGetConstructor() {
        assertThrows(UnsupportedOperationException.class, () -> new EntityClass<Object>(Object.class).getConstructor());
    }

    @Test
    public void testGetIdFieldFailed() {
        assertThrows(RuntimeException.class,
                () -> new EntityClass<EmptyClass>(EmptyClass.class).getIdField().getName());
    }

    @Test
    public void testGetIdFieldSuccesfull() {
        assertEquals("intField",
                new EntityClass<ClassWithIdNotation>(ClassWithIdNotation.class).getIdField().getName());
    }

    @Test
    public void testGetIdFieldForClassWithManyIdField() {
        assertThrows(RuntimeException.class,
                () -> new EntityClass<ClassWithManyIdNotations>(ClassWithManyIdNotations.class).getIdField().getName());
    }

    private boolean hasFieldWithName(List<Field> fields, String name) {
        return null != fields.stream().filter(field -> name.equals(field.getName())).findAny().orElse(null);
    }

    @Test
    public void testGetAllFields() {
        final var fields = new EntityClass<User>(User.class).getAllFields();
        assertEquals(3, fields.size());
        assertTrue(hasFieldWithName(fields, "id"));
        assertTrue(hasFieldWithName(fields, "name"));
        assertTrue(hasFieldWithName(fields, "age"));
    }

    @Test
    public void testGetFieldsWithoutId() {
        final var fields = new EntityClass<User>(User.class).getFieldsWithoutId();
        assertEquals(2, fields.size());
        assertTrue(hasFieldWithName(fields, "name"));
        assertTrue(hasFieldWithName(fields, "age"));
    }
}
