package com.github.arturdobo.streams;

import com.github.arturdobo.trade.Trade;
import com.github.arturdobo.trade.TradeRepo;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.assertThat;

public class FindingAndMatchingTest {
	@Test
	public void hasAtLeastOneSell() throws Exception {
		assertThat(TradeRepo.getAll()
		                    .stream()
		                    .anyMatch(Trade::isSell), is(true));
	}
	
	@Test
	public void eachTradesVolumeIsGreaterThan0() throws Exception {
		assertThat(TradeRepo.getAll()
		                    .stream()
		                    .allMatch(trade -> trade.getVolume() > 0), is(true));
	}

	@Test
	public void nonePriceIsLessThan2Point9() throws Exception {
		assertThat(TradeRepo.getAll()
		                    .stream()
		                    .noneMatch(trade -> trade.getPrice() < 2.9), is(true));
	}

	@Test
	public void anyStringThatLengthIsGreaterThan5() throws Exception {
		Optional<String> string = Arrays.asList("Aaaaaa", "Bbbbbbb", "Ccc")
		                                .stream()
		                                .filter(s -> s.length() > 5)
		                                .findAny();

		assertThat(string.isPresent(), is(true));
		assertThat(string.get(), equalTo("Aaaaaa"));
	}

}
