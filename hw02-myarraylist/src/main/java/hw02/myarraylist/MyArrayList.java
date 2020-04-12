package hw02.myarraylist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class MyArrayList<T>  implements List<T>{
    private static final int DEFAULT_CAPACITY = 10;
    public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;

	private Object[] innerStorage;
	private int arraySize;
	private int dataIndex = 0;

	public MyArrayList() {
		innerStorage = new Object[DEFAULT_CAPACITY];
		this.arraySize = DEFAULT_CAPACITY;
	}

	public MyArrayList(int arraySize) {
		innerStorage = new Object[arraySize];
		this.arraySize = arraySize;
	}

	@Override
	public int size() {
		return dataIndex;
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	@Override
	public Iterator<T> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	private boolean increaseCapacity()
	{
		int newArraySize = arraySize << 1;
		if (newArraySize < 0 ) {
			return false;
		}
		innerStorage = Arrays.copyOf(innerStorage, newArraySize);
		arraySize = newArraySize;
		return true;
	}

	@Override
	public boolean add(T e) {
		if (dataIndex == arraySize ){
			if (!increaseCapacity()) return false;
		}
		innerStorage[dataIndex] = e;
		dataIndex++;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public T get(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T set(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(Object o) {
		int start = 0;
		int end = dataIndex+1;
        Object[] es = innerStorage;
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}
}




