package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;

import hw09.jdbc.jdbc.mapper.testingclasses.ClassWithIdNotation;
import hw09.jdbc.jdbc.mapper.testingclasses.ClassWithManyIdNotations;
import hw09.jdbc.jdbc.mapper.testingclasses.CommonClass;
import hw09.jdbc.jdbc.mapper.testingclasses.EmptyClass;
import hw09.jdbc.jdbc.mapper.testingclasses.User;

public class UserEntityClassTest {
    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new EntityClass<User>(User.class));
    }

    @Test
    public void testGetName() {
        assertEquals("User", new EntityClass<User>(User.class).getName());
    }

    @Test
    public void testGetConstructor() {
        final var entityClass = new EntityClass<CommonClass>(CommonClass.class);
        assertNotNull(entityClass.getConstructor());
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

    @Test
    public void testSetFieldShouldThrowException() {
        final var userClassEntity = new EntityClass<User>(User.class);
        final User user = new User();
        assertThrows(MapperException.class, () -> userClassEntity.setFieldValue(user, "???", null));
    }

    @Test
    public void testSetField() {
        final var userClassEntity = new EntityClass<User>(User.class);
        final User user = new User();
        userClassEntity.setFieldValue(user, "id", 1);
        assertEquals(1, user.getId());
    }

    @Test
    public void testGetField() {
        final var userClassEntity = new EntityClass<User>(User.class);
        final int age = 30;
        final User user = new User(0, "Name", age);
        assertEquals(age, userClassEntity.getFieldValue(user, "age"));
    }
}
