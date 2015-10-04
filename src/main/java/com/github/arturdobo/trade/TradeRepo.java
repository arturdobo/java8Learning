package com.github.arturdobo.trade;

import java.util.ArrayList;
import java.util.List;

public abstract class TradeRepo {
	private static List<Trade> trades;

	static {
		trades = new ArrayList<>();

		trades.add(new Trade(Trade.Order.BUY, 2.97772, 5.70532, 16.98884547));
		trades.add(new Trade(Trade.Order.BUY, 2.97772, 0.009757, 0.02905361));
		trades.add(new Trade(Trade.Order.BUY, 2.97582, 0.091743, 0.27301065));
		trades.add(new Trade(Trade.Order.BUY, 2.97582, 0.106, 0.31543692));
		trades.add(new Trade(Trade.Order.BUY, 2.97582, 0.1003, 0.29847474));
		trades.add(new Trade(Trade.Order.BUY, 2.97772, 0.1054, 0.31385168));
		trades.add(new Trade(Trade.Order.SELL, 2.97202, 0.1, 0.297202));
		trades.add(new Trade(Trade.Order.BUY, 2.97145, 0.236018, 0.70131568));
		trades.add(new Trade(Trade.Order.SELL, 2.9703, 290.996, 864.3454188));
		trades.add(new Trade(Trade.Order.BUY, 2.97202, 0.1101, 0.3272194));
		trades.add(new Trade(Trade.Order.SELL, 2.97202, 0.287852, 0.8555019));
		trades.add(new Trade(Trade.Order.BUY, 2.97467, 2.30604, 6.859708));
		trades.add(new Trade(Trade.Order.SELL, 2.97467, 0.472018, 1.40409778));
		trades.add(new Trade(Trade.Order.SELL, 2.97609, 0.287852, 0.85667345));
		trades.add(new Trade(Trade.Order.BUY, 2.97772, 0.1057, 0.314745));
		trades.add(new Trade(Trade.Order.BUY, 2.97772, 0.1014, 0.3019408));
		trades.add(new Trade(Trade.Order.SELL, 2.97772, 5.02614, 14.9664376));
		trades.add(new Trade(Trade.Order.SELL, 2.97772, 0.472018, 1.40553743));
		trades.add(new Trade(Trade.Order.SELL, 2.97845, 0.92043, 2.74145473));
		trades.add(new Trade(Trade.Order.BUY, 2.97989, 0.1, 0.297989));
		trades.add(new Trade(Trade.Order.BUY, 2.98098, 0.1, 0.298098));
		trades.add(new Trade(Trade.Order.SELL, 2.97056, 2.25656, 6.70324687));
		trades.add(new Trade(Trade.Order.BUY, 2.97485, 334.062, 993.7843407));
		trades.add(new Trade(Trade.Order.SELL, 2.97485, 0.287852, 0.85631652));
		trades.add(new Trade(Trade.Order.BUY, 2.98343, 0.039482, 0.11779178));
		trades.add(new Trade(Trade.Order.BUY, 2.97914, 0.060518, 0.18029159));
		trades.add(new Trade(Trade.Order.BUY, 2.97914, 0.1077, 0.32085337));
		trades.add(new Trade(Trade.Order.BUY, 2.97914, 0.1178, 0.35094269));
		trades.add(new Trade(Trade.Order.BUY, 2.98343, 0.713982, 2.13011531));
		trades.add(new Trade(Trade.Order.BUY, 2.98322, 0.286018, 0.85325461));
		trades.add(new Trade(Trade.Order.SELL, 2.9706, 6.8737, 20.41901322));
		trades.add(new Trade(Trade.Order.SELL, 2.97144, 0.287852, 0.85533494));
		trades.add(new Trade(Trade.Order.SELL, 2.97144, 0.604723, 1.79689811));
		trades.add(new Trade(Trade.Order.SELL, 2.97471, 0.287852, 0.85627622));
		trades.add(new Trade(Trade.Order.SELL, 2.97536, 0.287852, 0.85646332));
		trades.add(new Trade(Trade.Order.SELL, 2.98061, 0.472018, 1.40690157));
		trades.add(new Trade(Trade.Order.SELL, 2.98249, 0.713982, 2.12944417));
		trades.add(new Trade(Trade.Order.SELL, 2.9828, 0.472018, 1.40793529));
		trades.add(new Trade(Trade.Order.BUY, 2.98343, 0.1, 0.298343));
		trades.add(new Trade(Trade.Order.BUY, 2.98249, 0.286018, 0.85304582));

	}

	public static List<Trade> getAll() {
		return trades;
	}
}
