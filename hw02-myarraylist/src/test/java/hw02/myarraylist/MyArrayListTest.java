package hw02.myarraylist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyArrayListTest {
	@Test
	public void testCollectionsAddAll() {
		List<String> myArray = new MyArrayList<>();
		Assertions.assertTrue(Collections.addAll(myArray, "first"));
		Assertions.assertTrue(Collections.addAll(myArray, "first", "second"));
	}

	@Test
	public void testCollectionsAddAllForOtherCollection() {
		List<String> myArray = new MyArrayList<>();
		Assertions.assertTrue(Collections.addAll(myArray, "first"));
	}

	@Test
	public void testSize() {
		MyArrayList<String> array = new MyArrayList<>();
		Assertions.assertEquals(0, array.size());
	}

	@Test
	public void testIsEmpty() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().isEmpty();
		});
	}

	@Test
	public void testContains() {
		MyArrayList<String> array = new MyArrayList<>();
		array.add("1");
		Assertions.assertTrue(array.contains("1"));
		Assertions.assertFalse(array.contains("2"));
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
		MyArrayList<String> array = new MyArrayList<>(1);
		Assertions.assertTrue(array.add("1"));
		Assertions.assertEquals(1, array.size());
		Assertions.assertTrue(array.add("2"));
		Assertions.assertEquals(2, array.size());
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
		MyArrayList<String> array = new MyArrayList<>();
		array.add("1");
		array.add("2");
		Assertions.assertEquals( 0, array.indexOf("1"));
		Assertions.assertEquals( 1, array.indexOf("2"));
		Assertions.assertEquals( -1, array.indexOf("3"));
	}

	@Test
	public void testLastIndexOf() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().lastIndexOf(new Object());
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
