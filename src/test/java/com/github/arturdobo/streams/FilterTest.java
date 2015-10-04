package com.github.arturdobo.streams;

import com.github.arturdobo.trade.Trade;
import com.github.arturdobo.trade.TradeRepo;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.assertThat;

public class FilterTest {
	@Test
	public void sellOnly() throws Exception {
		List<Trade> trades = TradeRepo.getAll()
		                              .stream()
		                              .filter(trade -> trade.getOrder() == Trade.Order.SELL)
		                              .collect(toList());
		
		assertThat(trades, everyItem(hasProperty("order", not(Trade.Order.BUY))));
	}
	
	@Test
	public void buyOnlyWithMethodReference() throws Exception {
		List<Trade> trades = TradeRepo.getAll()
		                              .stream()
		                              .filter(Trade::isBuy)
		                              .collect(toList());
		
		assertThat(trades, everyItem(hasProperty("order", not(Trade.Order.SELL))));
	}
	
	@Test
	public void sellOnlyWithVolumeLessThan0Point5() throws Exception {
		Predicate<Trade> sellOnly = Trade::isSell;
		Predicate<Trade> volLessThan0Point5 = trade -> trade.getVolume() < 0.5;
		
		List<Trade> trades = TradeRepo.getAll()
		                              .stream()
		                              .filter(sellOnly.and(volLessThan0Point5))
		                              .collect(toList());
		
		assertThat(trades,
		           allOf(everyItem(hasProperty("order", is(Trade.Order.SELL))),
		                 everyItem(hasProperty("volume", not(greaterThan(0.5))))));
	}
	
	@Test
	public void only5firstSell() throws Exception {
		List<Trade> trades = TradeRepo.getAll()
		                              .stream()
		                              .filter(Trade::isSell)
		                              .limit(5)
		                              .collect(toList());

		assertThat(trades, everyItem(hasProperty("order", is(Trade.Order.SELL))));
		assertThat(trades, hasSize(5));
	}
	
	@Test
	public void distinctEvenNumbers() throws Exception {
		List<Integer> ints = Arrays.asList(1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9, 0)
		                           .stream()
		                           .filter(i -> i % 2 == 0)
		                           .distinct()
		                           .collect(toList());

		assertThat(ints, contains(2, 4, 6, 8, 0));
	}
	
	@Test
	public void skipFirst2Odd() throws Exception {
		List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6)
		                           .stream()
		                           .filter(i -> i % 2 != 0)
		                           .skip(2)
		                           .collect(toList());

		assertThat(ints, contains(5));
	}
	
	@Test
	public void listShouldBeEmptyWhenLimitNumberIsGreaterThanListSize() throws Exception {
		List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5)
		                           .stream()
		                           .skip(6)
		                           .collect(toList());

		assertThat(ints, IsEmptyCollection.<Integer>empty());
	}
}
