package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class UserEntityClassTest {
    @Test
    public void testCtr() {
        class Test{
        }
        assertDoesNotThrow(() -> new EntityClass<Test>(Test.class));
    }

    @Test
    public void testGetName() {
        class Test{
        }
        assertEquals("Test", new EntityClass<Test>(Test.class).getName());
    }

    @Test
    public void testGetConstructor() {
        class Test{
        }
        assertThrows(UnsupportedOperationException.class, () -> new EntityClass<Test>(Test.class).getConstructor());
    }

    @Test
    public void testGetIdFieldFailed() {
        class Test {
        }
        assertThrows(RuntimeException.class, () -> new EntityClass<Test>(Test.class).getIdField().getName());
    }

    @Test
    public void testGetIdFieldSuccesfull() {
        class Test {
            @Id
            public int id;
        }
        assertEquals("id", new EntityClass<Test>(Test.class).getIdField().getName());
    }

    @Test
    public void testGetIdFieldForClassWithManyIdField() {
        class Test {
            @Id
            private int id;
            @Id
            private int otherId;
        }
        assertThrows(RuntimeException.class, () -> new EntityClass<Test>(Test.class).getIdField().getName());
    }

    private boolean hasFieldWithName(List<Field> fields, String name) {
        return null != fields.stream().filter(field -> name.equals(field.getName())).findAny().orElse(null);
    }

    @Test
    public void testGetAllFields() {
        class Test {
            @Id
            private int id;
            private int field1;
        }
        final var fields = new EntityClass<Test>(Test.class).getAllFields();
        assertEquals(2, fields.size());
        assertTrue(hasFieldWithName(fields, "id"));
        assertTrue(hasFieldWithName(fields, "field1"));
    }

    @Test
    public void testGetFieldsWithoutId() {
        class Test {
            @Id
            private int id;
            private int field1;
        }
        final var fields = new EntityClass<Test>(Test.class).getFieldsWithoutId();
        assertEquals(1, fields.size());
        assertTrue(hasFieldWithName(fields, "field1"));
    }
}
