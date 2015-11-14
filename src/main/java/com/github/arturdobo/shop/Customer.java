package com.github.arturdobo.shop;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
public class Customer {
	@NonNull
	private String name;

	private CreditCard creditCard;

	private List<Thing> basket;

	private Optional<DiscountCard> discountCard = Optional.empty();

	public void addToBasket(String name, int price) {
		if (basket == null)
			basket = new ArrayList<>();

		basket.add(new Thing(name, price));
	}
}
