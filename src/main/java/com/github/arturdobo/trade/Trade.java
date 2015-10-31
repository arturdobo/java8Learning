package com.github.arturdobo.trade;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Trade {
	private Order order;
	private double price; //sufficient
	private double volume; //sufficient
	private double total; //sufficient

	public boolean isSell() {
		return order == Order.SELL;
	}

	public boolean isBuy() {
		return order == Order.BUY;
	}

	public boolean isChargeFree() {
		return volume <= 1;
	}

	public enum Order {
		BUY, SELL
	}
}
