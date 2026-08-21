package com.trade.app.upstock.lib.dto;

import com.upstox.marketdatafeederv3udapi.rpc.proto.Feed;
import com.upstox.marketdatafeederv3udapi.rpc.proto.FeedResponse;
import com.upstox.marketdatafeederv3udapi.rpc.proto.MarketFullFeed;

public record MarketTickDTO(

		String instrumentKey,

		double ltp,

		long volume,

		double openInterest,

		long timestamp) {

	public static MarketTickDTO map(

			String instrumentKey,

			FeedResponse response,

			Feed feed) {

		MarketFullFeed marketFF = feed.getFullFeed().getMarketFF();

		return new MarketTickDTO(

				instrumentKey,

				marketFF.getLtpc().getLtp(),

				marketFF.getVtt(),

				marketFF.getOi(),

				response.getCurrentTs());
	}
}
