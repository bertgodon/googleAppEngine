package be.bert.googleappengine.mapper;

import java.util.Calendar;

import be.bert.googleappengine.model.OrderItem;
import be.bert.googleappengine.model.PriceHistory;

public class PriceHistoryMapper {

	public static PriceHistory fromOrderItem(OrderItem orderItem){
		PriceHistory priceHistory = new PriceHistory();
		priceHistory.setId(orderItem.getDrink().getId());
		priceHistory.setPrice(orderItem.getDrink().getPrice());
		priceHistory.setDate(Calendar.getInstance().getTime());
		return priceHistory;
	}
}
