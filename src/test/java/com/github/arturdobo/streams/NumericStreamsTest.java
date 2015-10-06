package com.github.arturdobo.streams;

import com.github.arturdobo.trade.Trade;
import com.github.arturdobo.trade.TradeRepo;
import org.junit.Test;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsCollectionContaining.*;
import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class NumericStreamsTest {
	@Test
	public void averagePrice() throws Exception {
		OptionalDouble average = TradeRepo.getAll()
		                                  .stream()
		                                  .mapToDouble(Trade::getPrice) // Without boxing
		                                  .average();

		assertThat(average.isPresent(), is(true));
		assertThat(average.getAsDouble(), closeTo(2.97, 0.01));
	}
	
	@Test
	public void rangeOfNumbersSum() throws Exception {
		assertThat(IntStream.range(1, 10)
		                    .sum(),
		           equalTo(IntStream.rangeClosed(1, 9)
		                            .sum()));
	}
	
	@Test
	public void pythagoreanTriple() throws Exception {
		List<int[]> triples = IntStream.rangeClosed(1, 10)
		                               .boxed()
		                               .flatMap(a -> IntStream.rangeClosed(a, 10)
		                                                      .boxed()
		                                                      .filter(b -> Math.sqrt(a * a + b * b) % 1.0 == 0)
		                                                      .map(b -> new int[] { a, b,
		                                                                            (int) Math.sqrt( a * a + b * b) }))
		                               .collect(toList());

		assertThat(triples, hasSize(2));
		assertThat(triples, hasItems(new int[] { 3, 4, 5 }, new int[] { 6, 8, 10 }));
	}
}