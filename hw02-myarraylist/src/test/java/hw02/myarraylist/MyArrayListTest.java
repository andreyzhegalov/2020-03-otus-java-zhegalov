package hw02.myarraylist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyArrayListTest {
	@Test
	public void testAddAllFromCollections() {
		List<String> myArray = new MyArrayList<>();
		Assertions.assertTrue(Collections.addAll(myArray, "first"));
		Assertions.assertTrue(Collections.addAll(myArray, "first", "second"));
	}

	@Test
	public void testCopyFromCollections() {
		List<String> src = new MyArrayList<>();
		src.add("src");
		List<String> dest = new MyArrayList<>();
		dest.add("dest");
		Collections.copy(dest, src);
		Assertions.assertArrayEquals(src.toArray(), dest.toArray());
	}

	@Test
	public void testSortFromCollections() {
		MyArrayList<Integer> myArray = new MyArrayList<>();
		myArray.add(1);
		myArray.add(2);
		Collections.sort(myArray, Collections.reverseOrder());
		Assertions.assertArrayEquals(new Integer[] { 2, 1 }, myArray.toArray());
	}

	@Test
	public void testCtrMaxSizeArray() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			new MyArrayList<Object>(Integer.MAX_VALUE);
		});
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
		MyArrayList<String> myArray = new MyArrayList<>();
		myArray.add("1");
		Object[] array = myArray.toArray();
		Assertions.assertEquals(1, array.length);
		Assertions.assertEquals("1", array[0]);
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
	public void testGetOutOfBounds() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			new MyArrayList<Object>().get(0);
		});

		MyArrayList<String> myArray = new MyArrayList<>();
		myArray.add("1");
		myArray.add("2");
		Assertions.assertEquals("1", myArray.get(0));
		Assertions.assertEquals("2", myArray.get(1));
	}

	@Test
	public void testGetByIndex() {
		MyArrayList<String> myArray = new MyArrayList<>();
		myArray.add("1");
		myArray.add("2");
		Assertions.assertEquals("1", myArray.get(0));
		Assertions.assertEquals("2", myArray.get(1));
	}

	@Test
	public void testSetOutOfBounds() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			new MyArrayList<Object>().set(0, new Object());
		});
	}

	@Test
	public void testSetByIndex() {
		MyArrayList<String> myArray = new MyArrayList<>();
		myArray.add("1");
		Assertions.assertEquals("1", myArray.set(0, "2"));
		Assertions.assertEquals("2", myArray.get(0));
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
		Assertions.assertEquals(0, array.indexOf("1"));
		Assertions.assertEquals(1, array.indexOf("2"));
		Assertions.assertEquals(-1, array.indexOf("3"));
	}

	@Test
	public void testLastIndexOf() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().lastIndexOf(new Object());
		});
	}

	@Test
	public void testListIterator() {
		Assertions.assertDoesNotThrow(() -> {
			new MyArrayList<Object>().listIterator();
		});
	}

	@Test
	public void testListIteratorByIndexShouldException() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			new MyArrayList<Object>().listIterator(1);
		});
	}

	@Test
	public void testListIteratorByIndex() {
		Assertions.assertDoesNotThrow(() -> new MyArrayList<Object>().listIterator(0));
	}

	@Test
	public void testSubList() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().subList(0, 0);
		});
	}

	@Test
	public void testListIteratorHasNext() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).hasNext();
		});
	}

	@Test
	public void testListIteratorNextShouldException() {
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			new MyArrayList<Object>().listIterator(0).next();
		});
	}

	@Test
	public void testListIteratorNext() {
		MyArrayList<Object> myArray = new MyArrayList<>();
		myArray.add(new Object());
		myArray.add(new Object());

		ListIterator<Object> listIter = myArray.listIterator(0);
		listIter.next();
		listIter.next();
	}

	@Test
	public void testListIteratorHasPrevious() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).hasPrevious();
		});
	}

	@Test
	public void testListIteratorPrevious() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).previous();
		});
	}

	@Test
	public void testListIteratorNextIndex() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).nextIndex();
		});
	}

	@Test
	public void testListIteratorPreviousIndex() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).previousIndex();
		});
	}

	@Test
	public void testListIteratorRemove() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).remove();
		});
	}

	@Test
	public void testListIteratorSetShouldException() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			new MyArrayList<Object>().listIterator(0).set(new Object());
		});
	}

	@Test
	public void testListIteratorSet() {
		MyArrayList<Integer> myArray = new MyArrayList<>();
		myArray.add(1);
		myArray.add(2);
		Assertions.assertArrayEquals(new Integer[] { 1, 2 }, myArray.toArray());

		ListIterator<Integer> listIter = myArray.listIterator(0);
		listIter.next();
		listIter.set(11);
		listIter.next();
		listIter.set(22);
		Assertions.assertArrayEquals(new Integer[] { 11, 22 }, myArray.toArray());
	}

	@Test
	public void testListIteratorAdd() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).add(new Object());
		});
	}
}
