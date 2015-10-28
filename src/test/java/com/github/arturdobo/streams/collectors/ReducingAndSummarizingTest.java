package com.github.arturdobo.streams.collectors;

import com.github.arturdobo.trade.Trade;
import com.github.arturdobo.trade.TradeRepo;
import org.junit.Test;

import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.summarizingDouble;
import static java.util.stream.Collectors.summingDouble;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.assertThat;

public class ReducingAndSummarizingTest {
	@Test
	public void volumeStatistics() throws Exception {
		DoubleSummaryStatistics stats = TradeRepo.getAll()
		                                         .stream()
		                                         .collect(summarizingDouble(Trade::getVolume));

		Optional<Double> min = TradeRepo.getAll()
		                                .stream()
		                                .map(Trade::getVolume)
		                                .collect(minBy(Double::compareTo));

		assertThat(stats.getMin(), equalTo(min.get()));

		Optional<Double> max = TradeRepo.getAll()
		                                .stream()
		                                .map(Trade::getVolume)
		                                .collect(maxBy(Double::compareTo));

		assertThat(stats.getMax(), equalTo(max.get()));

		Long count = TradeRepo.getAll()
		                      .stream()
		                      .map(Trade::getVolume)
		                      .collect(counting());

		assertThat(stats.getCount(), equalTo(count));

		Double sum = TradeRepo.getAll()
		                      .stream()
		                      .map(Trade::getVolume)
		                      .collect(summingDouble(Double::doubleValue));

		assertThat(stats.getSum(), equalTo(sum));
	}

	@Test
	public void stringsConcat() throws Exception {
		assertThat(Stream.of("A", "B", "C")
		                 .collect(joining(", ")), equalTo("A, B, C"));
	}
	
}
