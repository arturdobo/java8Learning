package com.github.arturdobo.streams;

import com.github.arturdobo.trade.Trade;
import com.github.arturdobo.trade.TradeRepo;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.assertThat;

public class MixingIntermediateAndTerminalOperationsTest {
	@Test
	public void sortedDistinctInsBetween4And9() throws Exception {
		List<Integer> ints = Arrays.asList(3, 1, 0, 7, 9, 11, 7, 9, 5, 2, 9, 23, 10)
		                           .stream()
		                           .filter(i -> i > 4)
		                           .filter(i -> i < 9)
		                           .distinct()
		                           .sorted(comparing(Integer::byteValue))
		                           .collect(toList());

		assertThat(ints, contains(5, 7));
	}
	
	@Test
	public void concatUppercaseSortedStrings() throws Exception {
		String str = Arrays.asList("oo", "dd", "yy", "aa")
		                   .stream()
		                   .map(String::toUpperCase)
		                   .sorted()
		                   .collect(joining());

		assertThat(str, equalTo("AADDOOYY"));
	}
	
	@Test
	public void theSmallestPrice() throws Exception {
		Optional<Trade> trade = TradeRepo.getAll()
		                                 .stream()
		                                 .reduce((trade1, trade2) -> trade1.getPrice() < trade2.getPrice()
		                                                             ? trade1 : trade2);

		Optional<Double> price = TradeRepo.getAll()
		                                  .stream()
		                                  .map(Trade::getPrice)
		                                  .reduce(Double::min);

		assertThat(trade.isPresent(), is(true));
		assertThat(price.isPresent(), is(true));
		assertThat(trade.get()
		                .getPrice(), equalTo(price.get()));
	}
	
	@Test
	public void theSmallestPriceAgain() throws Exception {
		Optional<Trade> trade = TradeRepo.getAll()
		                                 .stream()
		                                 .min(comparing(Trade::getPrice));

		assertThat(trade.isPresent(), is(true));
		assertThat(trade.get()
		                .getPrice(), equalTo(2.9703));
	}
}
