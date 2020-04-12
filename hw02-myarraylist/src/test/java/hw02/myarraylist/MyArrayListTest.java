package hw02.myarraylist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MyArrayListTest {
	@Test
	public void testSize() {
		Assertions.assertThrows(UnsupportedOperationException.class, ()->{
			new MyArrayList<Integer>().size();
		});
	}
}
