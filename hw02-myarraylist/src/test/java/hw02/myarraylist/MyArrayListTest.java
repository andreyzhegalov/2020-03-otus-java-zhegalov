package hw02.myarraylist;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class MyArrayListTest {

	@Test
	public void testAddAllFromCollections() {
		final List<String> myArray = new MyArrayList<>();
		assertTrue(Collections.addAll(myArray, "first", "second"));
	}

	@Test
	public void testAddAllFromCollectionsMoreThan20() {
		final Integer[] array = new Integer[30];
		for( int i=0; i < array.length; i++)
		{
			array[i] = i;
		}
		final List<Integer> myArray = new MyArrayList<>();
		assertTrue(Collections.addAll(myArray, array));
	}

	@Test
	public void testCopyFromCollections() {
		final List<String> src = new MyArrayList<>();
		src.add("src");
		final List<String> dest = new MyArrayList<>();
		dest.add("dest");
		Collections.copy(dest, src);
		assertArrayEquals(src.toArray(), dest.toArray());
	}

	@Test
	public void testCopyFromCollectionsMoreThan20() {
		final int size = 30;
		final List<Integer> src = new MyArrayList<>();
		for( int i=0; i < size; i++)
		{
			src.add(i);
		}
		final List<Integer> dest = new MyArrayList<>();
		for( int i=0; i < size; i++)
		{
			dest.add(0);
		}
		Collections.copy(dest, src);
		assertArrayEquals(src.toArray(), dest.toArray());
	}

	@Test
	public void testSortFromCollections() {
		final MyArrayList<Integer> myArray = new MyArrayList<>();
		myArray.add(1);
		myArray.add(2);
		Collections.sort(myArray, Collections.reverseOrder());
		assertArrayEquals(new Integer[] { 2, 1 }, myArray.toArray());
	}


	@Test
	public void testSortFromCollectionsMoreThan20() {
		final int size = 30;
		final MyArrayList<Integer> myArray = new MyArrayList<>();
		for( int i=0; i < size; i++)
		{
			myArray.add(i);
		}

		Collections.sort(myArray, Collections.reverseOrder());

		int mustBe = size - 1;
		for( int i=0; i < size; i++ )
		{
			assertEquals( mustBe, myArray.get(i));
			mustBe--;
		}
	}

	@Test
	public void testCtrMaxSizeArray() {
		assertThrows(RuntimeException.class, () -> {
			new MyArrayList<Object>(Integer.MAX_VALUE);
		});
	}

	@Test
	public void testSize() {
		final MyArrayList<String> array = new MyArrayList<>();
		assertEquals(0, array.size());
	}

	@Test
	public void testIsEmpty() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().isEmpty();
		});
	}

	@Test
	public void testContainsTrue() {
		final MyArrayList<String> array = new MyArrayList<>();
		array.add("1");
		assertTrue(array.contains("1"));
	}

	@Test
	public void testContainsFalse() {
		final MyArrayList<String> array = new MyArrayList<>();
		array.add("1");
		assertFalse(array.contains("2"));
	}

	@Test
	public void testIterator() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().iterator();
		});
	}

	@Test
	public void testToArray() {
		final MyArrayList<String> myArray = new MyArrayList<>();
		myArray.add("1");
		final Object[] array = myArray.toArray();
		assertEquals("1", array[0]);
	}

	@Test
	public void testToArrayFromArray() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().toArray(new Object[10]);
		});
	}

	@Test
	public void testAdd() {
		final MyArrayList<String> array = new MyArrayList<>(1);
		assertTrue(array.add("1"));
	}

	@Test
	public void testRemove() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().remove(new Object());
		});
	}

	@Test
	public void testContainsAll() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().containsAll(new ArrayList<Object>());
		});
	}

	@Test 
	public void testAdddAll(){
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().addAll(new ArrayList<Object>());
		});

	}

	@Test
	public void testAddAllWithIndex() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().addAll(0, new ArrayList<Object>());
		});
	}

	@Test
	public void testRemoveAll() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().removeAll(new ArrayList<Object>());
		});
	}

	@Test
	public void testRetainAll() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().retainAll(new ArrayList<Object>());
		});
	}

	@Test
	public void testClear() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().clear();
		});
	}

	@Test
	public void testGetOutOfBoundsException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			new MyArrayList<Object>().get(0);
		});
	}

	@Test
	public void testGetByIndex() {
		final MyArrayList<String> myArray = new MyArrayList<>();
		myArray.add("1");
		assertEquals("1", myArray.get(0));
	}

	@Test
	public void testSetOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			new MyArrayList<Object>().set(0, new Object());
		});
	}

	@Test
	public void testSetByIndex() {
		final MyArrayList<String> myArray = new MyArrayList<>();
		myArray.add("1");
		assertEquals("1", myArray.set(0, "2"));
		assertEquals("2", myArray.get(0));
	}

	@Test
	public void testAddByIndex() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().add(0, new Object());
		});
	}

	@Test
	public void testRemoveByIndex() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().remove(0);
		});
	}

	@Test
	public void testIdexOfNotExistingElements() {
		final MyArrayList<String> array = new MyArrayList<>();
		assertEquals(-1, array.indexOf("1"));
	}

	@Test
	public void testIdexOf() {
		final MyArrayList<String> array = new MyArrayList<>();
		array.add("1");
		assertEquals(0, array.indexOf("1"));
	}

	@Test
	public void testIdexOfForNullObject() {
		final MyArrayList<String> array = new MyArrayList<>();
		array.add(null);
		assertEquals(0, array.indexOf(null));
	}

	@Test
	public void testIdexOfForNullObjectNotFounded() {
		final MyArrayList<String> array = new MyArrayList<>();
		array.add("1");
		assertEquals(-1, array.indexOf(null));
	}

	@Test
	public void testLastIndexOf() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().lastIndexOf(new Object());
		});
	}

	@Test
	public void testListIterator() {
		assertDoesNotThrow(() -> {
			new MyArrayList<Object>().listIterator();
		});
	}

	@Test
	public void testListIteratorByIndexShouldBoundException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			new MyArrayList<Object>().listIterator(1);
		});
	}

	@Test
	public void testListIteratorByIndexInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			new MyArrayList<Object>().listIterator(-1);
		});
	}

	@Test
	public void testListIteratorByIndex() {
		assertDoesNotThrow(() -> new MyArrayList<Object>().listIterator(0));
	}

	@Test
	public void testSubList() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().subList(0, 0);
		});
	}

	@Test
	public void testListIteratorHasNext() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).hasNext();
		});
	}

	@Test
	public void testListIteratorNextShouldException() {
		assertThrows(NoSuchElementException.class, () -> {
			new MyArrayList<Object>().listIterator(0).next();
		});
	}

	@Test
	public void testListIteratorNext() {
		final MyArrayList<Integer> myArray = new MyArrayList<>();
		myArray.add(1);

		final ListIterator<Integer> listIter = myArray.listIterator(0);
		assertEquals(1, listIter.next());
	}

	@Test
	public void testListIteratorHasPrevious() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).hasPrevious();
		});
	}

	@Test
	public void testListIteratorPrevious() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).previous();
		});
	}

	@Test
	public void testListIteratorNextIndex() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).nextIndex();
		});
	}

	@Test
	public void testListIteratorPreviousIndex() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).previousIndex();
		});
	}

	@Test
	public void testListIteratorRemove() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).remove();
		});
	}

	@Test
	public void testListIteratorSetShouldException() {
		assertThrows(IllegalStateException.class, () -> {
			new MyArrayList<Object>().listIterator(0).set(new Object());
		});
	}

	@Test
	public void testListIteratorSet() {
		final MyArrayList<Integer> myArray = new MyArrayList<>();
		myArray.add(1);
		myArray.add(2);

		final ListIterator<Integer> listIter = myArray.listIterator(0);
		listIter.next();
		listIter.set(11);
		listIter.next();
		listIter.set(22);
		assertArrayEquals(new Integer[] { 11, 22 }, myArray.toArray());
	}

	@Test
	public void testListIteratorAdd() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new MyArrayList<Object>().listIterator(0).add(new Object());
		});
	}
}
