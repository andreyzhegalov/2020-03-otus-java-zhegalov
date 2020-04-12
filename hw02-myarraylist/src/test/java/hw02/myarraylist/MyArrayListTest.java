package hw02.myarraylist;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;

public class MyArrayListTest {
	@Test
	public void testSize() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().size();
		});
	}

	@Test
	public void testIsEmpty() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().isEmpty();
		});
	}

	@Test
	public void testContains() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().contains(null);
		});
	}

	@Test
	public void testIterator() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().iterator();
		});
	}

	@Test
	public void testToArray() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().toArray();
		});
	}

	@Test
	public void testToArrayFromArray() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().toArray(new Object[10]);
		});
	}

	@Test
	public void testAdd() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().add(new Object());
		});
	}

	@Test
	public void testRemove() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().remove(new Object());
		});
	}

	@Test
	public void testContainsAll() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().containsAll(new ArrayList<Object>());
		});
	}

	@Test
	public void testAddAll() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().addAll(1, new ArrayList<Object>());
		});
	}

	@Test
	public void testRemoveAll() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().removeAll(new ArrayList<Object>());
		});
	}

	@Test
	public void testRetainAll() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().retainAll(new ArrayList<Object>());
		});
	}

	@Test
	public void testClear() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().clear();
		});
	}

	@Test
	public void testGet() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().get(0);
		});
	}

	@Test
	public void testSet() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().set(0, new Object());
		});
	}

	@Test
	public void testAddByIndex() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().add(0, new Object());
		});
	}

	@Test
	public void testRemoveByIndex() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().remove(0);
		});
	}

	@Test
	public void testIdexOf() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().indexOf(new Object());
		});
	}

	@Test
	public void testLastIndexOf() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().indexOf(new Object());
		});
	}

	@Test
	public void testListIterator() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator();
		});
	}

	@Test
	public void testListIteratorByIndex() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0);
		});
	}

	@Test
	public void testSubList() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().subList(0, 0);
		});
	}
}
