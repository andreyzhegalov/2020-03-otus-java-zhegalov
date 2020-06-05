package hw09.jdbc.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class EntityClass<T> implements EntityClassMetaData<T>{

	@Override
	public String getName() {
        throw new UnsupportedOperationException();
	}

	@Override
	public Constructor<T> getConstructor() {
        throw new UnsupportedOperationException();
	}

	@Override
	public Field getIdField() {
        throw new UnsupportedOperationException();
	}

	@Override
	public List<Field> getAllFields() {
        throw new UnsupportedOperationException();
	}

	@Override
	public List<Field> getFieldsWithoutId() {
        throw new UnsupportedOperationException();
	}
}

