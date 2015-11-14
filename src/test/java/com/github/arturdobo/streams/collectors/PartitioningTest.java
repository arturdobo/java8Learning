package com.github.arturdobo.streams.collectors;

import com.github.arturdobo.trade.Trade;
import com.github.arturdobo.trade.TradeRepo;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class PartitioningTest {
	@Test
	public void numbersGreaterThanFive() throws Exception {
		int[] ints = { 1, 5, 6, 8, 0 };

		List<Integer> fromFilter = IntStream.of(ints)
		                                    .filter(value -> value > 0)
		                                    .boxed()
		                                    .collect(toList());

		List<Integer> fromPartitioning = IntStream.of(ints)
		                                          .boxed()
		                                          .collect(partitioningBy(value -> value > 0))
		                                          .get(true);

		assertThat(fromPartitioning, equalTo(fromFilter));
	}
	
	@Test
	public void chargeFreeVolumeAndGroupedByOrder() throws Exception {
		Map<Boolean, Map<Trade.Order, List<Trade>>> trades =
				TradeRepo.getAll()
				         .stream()
				         .collect(partitioningBy(Trade::isChargeFree,
				                                 groupingBy(Trade::getOrder)));

		Map<Trade.Order, List<Trade>> charged = trades.get(false);
		Map<Trade.Order, List<Trade>> chargeFree = trades.get(true);

		Matcher<Iterable<Trade>> everyItemValueGreaterThan1 =
				CoreMatchers.everyItem(hasProperty("volume", Matchers.greaterThan(1.0)));

		assertThat(charged.get(Trade.Order.BUY), everyItemValueGreaterThan1);
		assertThat(charged.get(Trade.Order.SELL), everyItemValueGreaterThan1);

		assertThat(chargeFree.get(Trade.Order.BUY), not(everyItemValueGreaterThan1));
		assertThat(chargeFree.get(Trade.Order.SELL), not(everyItemValueGreaterThan1));

	}
	
	@Test
	public void highestChargedAndChargeFreeVolume() throws Exception {
		Map<Boolean, Trade> trades =
				TradeRepo.getAll()
				         .stream()
				         .collect(partitioningBy(Trade::isChargeFree,
				                                 collectingAndThen(maxBy(comparingDouble(Trade::getVolume)),
				                                                   Optional::get)));

		assertThat(trades.get(true)
		                 .getVolume(), closeTo(0.90, 0.05));
		assertThat(trades.get(false)
		                 .getVolume(), closeTo(334.05, 0.05));
	}
}
