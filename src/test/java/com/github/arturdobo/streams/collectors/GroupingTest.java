package com.github.arturdobo.streams.collectors;

import com.github.arturdobo.trade.Trade;
import com.github.arturdobo.trade.TradeRepo;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class GroupingTest {
	@Test
	public void tradesGroupedByOrder() throws Exception {
		Map<Trade.Order, List<Trade>> trades = TradeRepo.getAll()
		                                                .stream()
		                                                .collect(groupingBy(Trade::getOrder));

		assertThat(trades, hasKey(Trade.Order.BUY));
		assertThat(trades, hasKey(Trade.Order.SELL));

		List<Trade> buys = trades.get(Trade.Order.BUY);
		assertThat(buys, not(empty()));
		assertThat(buys, everyItem(hasProperty("order", equalTo(Trade.Order.BUY))));

		List<Trade> sells = trades.get(Trade.Order.SELL);
		assertThat(sells, not(empty()));
		assertThat(sells, everyItem(hasProperty("order", equalTo(Trade.Order.SELL))));
	}
	
	@Test
	public void multiLevelOrderGrouping() throws Exception {
		Map<Trade.Order, Map<Amount, List<Trade>>> trades =
				TradeRepo.getAll()
				         .stream()
				         .collect(groupingBy(Trade::getOrder,
				                             groupingBy(trade -> trade.getVolume() < 1 ? Amount.SMALL
				                                                                       : Amount.BIG)));

		Map<Amount, List<Trade>> buys = trades.get(Trade.Order.BUY);
		Map<Amount, List<Trade>> sells = trades.get(Trade.Order.SELL);

		assertThat(buys, hasKey(Amount.SMALL));
		assertThat(buys, hasKey(Amount.BIG));
		assertThat(sells, hasKey(Amount.SMALL));
		assertThat(sells, hasKey(Amount.BIG));

		assertThat(buys.get(Amount.BIG), hasSize(3));
		assertThat(buys.get(Amount.SMALL), hasSize(19));
		assertThat(sells.get(Amount.BIG), hasSize(4));
		assertThat(sells.get(Amount.SMALL), hasSize(14));
	}
	
	@Test
	public void numberOfOrders() throws Exception {
		Map<Trade.Order, Long> trades = TradeRepo.getAll()
		                                         .stream()
		                                         .collect(groupingBy(Trade::getOrder, counting()));

		assertThat(trades.get(Trade.Order.SELL), equalTo(18L));
		assertThat(trades.get(Trade.Order.BUY), equalTo(22L));
	}
	
	@Test
	public void highestSellAndBuyVolume() throws Exception {
		Map<Trade.Order, Optional<Trade>> trades =
				TradeRepo.getAll()
				         .stream()
				         .collect(groupingBy(Trade::getOrder,
				                             maxBy(comparingDouble
						                                   (Trade::getVolume))));

		Optional<Trade> buy = trades.get(Trade.Order.BUY);
		Optional<Trade> sell = trades.get(Trade.Order.SELL);

		assertThat(buy.isPresent(), is(true));
		assertThat(buy.get().getVolume(), closeTo(334.06, 0.01));
		assertThat(sell.isPresent(), is(true));
		assertThat(sell.get().getVolume(), closeTo(290.99, 0.01));
	}
	
	@Test
	public void omittingUnnecessaryOptional() throws Exception {
		Map<Integer, String> strings = Stream.<String>of("A", "B", "AA", "BB", "AAA", "BBB", "CCC")
				.collect(groupingBy(String::length,
				                    collectingAndThen(maxBy(String::compareTo), Optional::get)));

		assertThat(strings, hasEntry(1, "B"));
		assertThat(strings, hasEntry(2, "BB"));
		assertThat(strings, hasEntry(3, "CCC"));
	}
	
	private enum Amount {
		SMALL, BIG
	}

}
