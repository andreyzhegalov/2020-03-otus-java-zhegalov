package hw08.mygson.object;

import java.lang.reflect.Modifier;
import java.lang.reflect.Field;

import hw08.mygson.MyGsonException;

public class FieldHandler {
    private Field field;
    private Object obj;

    public FieldHandler(Field field, Object obj) {
        if (field == null) {
            throw new MyGsonException("Field is null");
        }
        if (obj == null) {
            throw new MyGsonException("Obj is null");
        }
        field.setAccessible(true);
        this.field = field;
        this.obj = obj;
    }

    public boolean isSynthetic() {
        return field.isSynthetic();
    }

    public boolean isStatic() {
        return (field.getModifiers() & Modifier.STATIC) > 0;
    }

    public String getName() {
        return field.getName();
    }

    public Object getObject() {
        Object obj = null;
        try {
            obj = field.get(this.obj);
        } catch (IllegalArgumentException e) {
            throw new MyGsonException("Error get value from field " + getName());
        } catch (IllegalAccessException e) {
            throw new MyGsonException("Error get value from field " + getName());
        }
        return obj;
    }

    public Class<?> getType() {
        return field.getType();
    }

}
