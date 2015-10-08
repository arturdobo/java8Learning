package com.github.arturdobo.streams;

import org.junit.Test;

import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.Assert.assertThat;

public class BuildingStreamTest {
	@Test
	public void infiniteStream() throws Exception {
		List<Integer> ints = Stream.iterate(0, i -> ++i)
		                           .limit(10)
		                           .collect(toList());

		assertThat(ints, contains(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		assertThat(ints, iterableWithSize(10));
	}

	@Test
	public void fibonacciSequence() throws Exception {
		List<int[]> tabSequence = Stream.iterate(new int[] { 0, 1 },
		                                         ints -> new int[] { ints[1], ints[0] + ints[1] })
		                                .limit(5)
		                                .collect(toList());

		assertThat(tabSequence,
		           contains(new int[] { 0, 1 },
		                    new int[] { 1, 1 },
		                    new int[] { 1, 2 },
		                    new int[] { 2, 3 },
		                    new int[] { 3, 5 }));

		List<Integer> sequence = tabSequence.stream()
		                                    .map(ints -> ints[0])
		                                    .collect(toList());

		assertThat(sequence, contains(0, 1, 1, 2, 3));
	}
	
	@Test
	public void fibonacciSequenceFromSupplier() throws Exception {
		List<Integer> sequence = IntStream.generate(new FibonacciSequence())
		                                  .limit(5)
		                                  .boxed()
		                                  .collect(toList());

		assertThat(sequence, contains(0, 1, 1, 2, 3));
	}
	
	@Test
	public void concatStreams() throws Exception {
		List<Integer> ints = Stream.concat(Stream.iterate(0, i -> i + 2)
		                                         .limit(3), Stream.iterate(1, i -> i + 2))
		                           .limit(6)
		                           .sorted()
		                           .collect(toList());

		assertThat(ints, contains(0, 1, 2, 3, 4, 5));
	}
	
	private class FibonacciSequence implements IntSupplier {
		private int previous = 0;
		private int current = 1;

		@Override
		public int getAsInt() {
			int old = previous;
			previous = current;
			current = current + old;

			return old;
		}
	}

}
