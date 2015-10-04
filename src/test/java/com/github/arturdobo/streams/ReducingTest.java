package com.github.arturdobo.streams;

import com.github.arturdobo.trade.Trade;
import com.github.arturdobo.trade.TradeRepo;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class ReducingTest {
	@Test
	public void sumInts() throws Exception {
		Integer sum = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)
		                    .stream()
		                    .reduce(0, Integer::sum);

		assertThat(sum, equalTo(45));
	}
	
	@Test
	public void sumFilteredWhenNoneAndLackOfInitValueIsAbsent() throws Exception {
		Optional<Integer> sum = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)
		                              .stream()
		                              .filter(i -> i > 10)
		                              .reduce(Integer::sum);

		assertThat(sum.isPresent(), is(false));
	}
	
	@Test
	public void maxVolume() throws Exception {
		Optional<Double> max = TradeRepo.getAll()
		                                   .stream()
		                                   .map(Trade::getVolume)
		                                   .reduce(Double::max);

		assertThat(max.isPresent(), is(true));
		assertThat(max.get(), closeTo(334.062, 0.001));
	}
	
	@Test
	public void minPrice() throws Exception {
		Optional<Double> min = TradeRepo.getAll()
		                                   .stream()
		                                   .map(Trade::getPrice)
		                                   .reduce(Double::min);

		assertThat(min.isPresent(), is(true));
		assertThat(min.get(), closeTo(2.9703, 0.0001));
	}
}
