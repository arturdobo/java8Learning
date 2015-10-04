package com.github.arturdobo.streams;

import com.github.arturdobo.trade.Trade;
import com.github.arturdobo.trade.TradeRepo;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.*;
import static org.junit.Assert.assertThat;

public class MappingTest {
	@Test
	public void onlyOrderEnums() throws Exception {
		Set<Trade.Order> orders = TradeRepo.getAll()
		                                   .stream()
		                                   .map(Trade::getOrder)
		                                   .collect(toSet());

		assertThat(orders, hasSize(2));
	}
	
	@Test
	public void trimmedStringsLength() throws Exception {
		List<Integer> strings = Arrays.asList("   Aaaa   ", "  Bbbb  ", " Cccc    ")
		                              .stream()
		                              .map(String::trim)
		                              .map(String::length)
		                              .collect(toList());

		assertThat(strings, everyItem(equalTo(4)));
	}
	
	@Test
	public void distinctLowercaseCharactersFromWords() throws Exception {
		List<String> strings = Arrays.asList("Qwerty", "Wertyu")
		                             .stream()
		                             .map(String::toLowerCase)
		                             .map(s -> s.split(""))
		                             .flatMap(Arrays::stream)
		                             .distinct()
		                             .collect(toList());

		assertThat(strings, hasItems("q", "w", "e", "r", "t", "y", "u"));
	}
	
	@Test
	public void pairOfEvenNumbers() throws Exception {
		List<Integer> first = Arrays.asList(1, 2, 3);
		List<Integer> second = Collections.singletonList(4);

		List<int[]> pairs = first.stream()
		                         .flatMap(i -> second.stream()
		                                             .filter(j -> i % 2 == 0 && j % 2 == 0)
		                                             .map(j -> new int[] { i, j }))
		                         .collect(toList());

		assertThat(pairs, hasItems(new int[] { 2, 4 }));
	}

}
