package hw08.mygson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectHandler {
    final private Object obj;
    final private Class<?> clazz;
    final private Field[] fields;

    public ObjectHandler(Object obj) {
        this.obj = obj;
        this.clazz = this.obj.getClass();
        this.fields = clazz.getDeclaredFields();
    }

    public List<FieldHandler> getFields() {
        List<FieldHandler> res = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            final var field = fields[i];
            final var filedHandler = new FieldHandler(field, this.obj);
            if (filedHandler.isSynthetic()) {
                continue;
            }
            res.add(filedHandler);
        }
        return res;
    }
}
