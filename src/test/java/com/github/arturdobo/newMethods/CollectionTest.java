package com.github.arturdobo.newMethods;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class CollectionTest {

	private List<Integer> ints;

	@Before
	public void setUp() throws Exception {
		ints = new ArrayList<>();
		ints.addAll(Arrays.asList(3, 6, 1, 7, 21, 5, 11, 56, 13));
	}

	@Test
	public void removeFromCollection() throws Exception {
		ints.removeIf(i -> i < 10);

		assertThat(ints, hasSize(4));
		assertThat(ints, contains(21, 11, 56, 13));
	}
	
	@Test
	public void sortListWithGivenComparator() throws Exception {
		ints.sort(Collections.reverseOrder());

		assertThat(ints, contains(56, 21, 13, 11, 7, 6, 5, 3, 1));
	}
	
	@Test
	public void replaceAllElementsInList() throws Exception {
		ints.replaceAll(i -> i > 10 ? 10 : 0);

		assertThat(ints, contains(0, 0, 0, 0, 10, 0, 10, 10, 10));
	}
}
