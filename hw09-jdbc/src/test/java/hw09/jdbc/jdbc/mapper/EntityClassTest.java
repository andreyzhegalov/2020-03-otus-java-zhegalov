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
import hw09.jdbc.jdbc.mapper.testingclasses.ClassWithoutNotation;
import hw09.jdbc.jdbc.mapper.testingclasses.CommonClass;
import hw09.jdbc.jdbc.mapper.testingclasses.User;

public class EntityClassTest {
    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new EntityClassMetaDataImpl<User>(User.class));
    }

    @Test
    public void testGetName() {
        assertEquals("User", new EntityClassMetaDataImpl<User>(User.class).getName());
    }

    @Test
    public void testGetConstructor() {
        final var entityClass = new EntityClassMetaDataImpl<CommonClass>(CommonClass.class);
        assertNotNull(entityClass.getConstructor());
    }

    @Test
    public void testGetIdFieldFailed() {
        assertThrows(MapperException.class,
                () -> new EntityClassMetaDataImpl<ClassWithoutNotation>(ClassWithoutNotation.class).getIdField().getName());
    }

    @Test
    public void testGetIdFieldSuccesfull() {
        assertEquals("intField",
                new EntityClassMetaDataImpl<ClassWithIdNotation>(ClassWithIdNotation.class).getIdField().getName());
    }

    @Test
    public void testGetIdFieldForClassWithManyIdField() {
        assertThrows(MapperException.class,
                () -> new EntityClassMetaDataImpl<ClassWithManyIdNotations>(ClassWithManyIdNotations.class).getIdField().getName());
    }

    private boolean hasFieldWithName(List<Field> fields, String name) {
        return fields.stream().anyMatch(field -> name.equals(field.getName()));
    }

    @Test
    public void testGetAllFields() {
        final var fields = new EntityClassMetaDataImpl<User>(User.class).getAllFields();
        assertEquals(3, fields.size());
        assertTrue(hasFieldWithName(fields, "userId"));
        assertTrue(hasFieldWithName(fields, "userName"));
        assertTrue(hasFieldWithName(fields, "userAge"));
    }

    @Test
    public void testGetFieldsWithoutId() {
        final var fields = new EntityClassMetaDataImpl<User>(User.class).getFieldsWithoutId();
        assertEquals(2, fields.size());
        assertTrue(hasFieldWithName(fields, "userName"));
        assertTrue(hasFieldWithName(fields, "userAge"));
    }

    @Test
    public void testSetFieldShouldThrowException() {
        final var userClassEntity = new EntityClassMetaDataImpl<User>(User.class);
        final User user = new User();
        assertThrows(MapperException.class, () -> userClassEntity.setFieldValue(user, "???", null));
    }

    @Test
    public void testSetField() {
        final var userClassEntity = new EntityClassMetaDataImpl<User>(User.class);
        final User user = new User();
        userClassEntity.setFieldValue(user, "userId", 1);
        assertEquals(1, user.getUserId());
    }

    @Test
    public void testGetField() {
        final var userClassEntity = new EntityClassMetaDataImpl<User>(User.class);
        final int age = 30;
        final User user = new User(0, "Name", age);
        assertEquals(age, userClassEntity.getFieldValue(user, "userAge"));
    }

    @Test
    public void testGetId() {
        final var userClassEntity = new EntityClassMetaDataImpl<User>(User.class);
        final long userId = 1L;
        final User user = new User(userId, "Name", 30);
        assertEquals(userId, userClassEntity.getIdValue(user));
    }
}
