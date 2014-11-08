package be.bert.googleappengine.service;

import be.bert.googleappengine.model.Beverage;
import be.bert.googleappengine.model.Order;
import be.bert.googleappengine.model.OrderItem;

public class BeursPricesCalculater {

	private static final float GLOBAL_WEIGHT_FACTOR = 10;
	
	public static void calculateAlternative(Order order){
		float aangekochteWaarde = 0;
		int totalWeight = 0;
		int totalBeveragesCount = 0;
		int differentOrderdBeverages = 0;
		
		for(OrderItem orderItem : order.getOrderItems()){
			if(!orderItem.getDrink().isSoldOut()){
				Beverage beverage = orderItem.getDrink();
				totalWeight += beverage.getWeight();
				totalBeveragesCount += orderItem.getQuantity();
				aangekochteWaarde += orderItem.getQuantity() * beverage.getWeight() * beverage.getPrice();
				if(orderItem.getQuantity()>0){
					differentOrderdBeverages++;
				}
			}
		}
		int countNotSelledBeverages = order.getOrderItems().size() - differentOrderdBeverages; 
		
		for(OrderItem orderItem : order.getOrderItems()){
			float newPrice = caclculateNewPrice(orderItem, totalWeight, aangekochteWaarde, totalBeveragesCount, countNotSelledBeverages);
			if(newPrice < orderItem.getDrink().getMiniumPrice()){
				newPrice = orderItem.getDrink().getMiniumPrice();
			}
			orderItem.getDrink().setPrice(newPrice);
			cleanOrderInformation(orderItem);
		}
	}
	
	private static void cleanOrderInformation(OrderItem orderItem) {
		orderItem.setQuantity(0);
		orderItem.setTotal(0);
	}

	private static float caclculateNewPrice(OrderItem orderItem, float totalWeight, float aangekochteWaarde, float totalQuantity, float countNotSelledBeverages){
		float newPrice = 0;
		float oldPrice = orderItem.getDrink().getPrice();
		int weight = orderItem.getDrink().getWeight();
		if(orderItem.getQuantity() > 0){
			newPrice = oldPrice + (weight * aangekochteWaarde/totalWeight * orderItem.getQuantity()/totalQuantity/GLOBAL_WEIGHT_FACTOR);
//			System.out.println(newPrice +" = " +oldPrice +" + " +weight + " * " +aangekochteWaarde +" / " +totalWeight +" * " +orderItem.getQuantity()+ " / " +totalQuantity);
		} else{
			newPrice = oldPrice - (1/countNotSelledBeverages * weight * aangekochteWaarde/totalWeight)/GLOBAL_WEIGHT_FACTOR * 2;
//			System.out.println(newPrice + " = " +oldPrice + " - " +" 1" +" / " +countNotSelledBeverages +" * " +weight + " * " +aangekochteWaarde + " / " +totalWeight);
		}
		
		return newPrice;
	}
}