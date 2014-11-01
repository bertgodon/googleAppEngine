package be.bert.googleappengine.service;

import be.bert.googleappengine.model.Beverage;
import be.bert.googleappengine.model.Order;
import be.bert.googleappengine.model.OrderItem;

public class BeursPricesCalculater {

	private static final int GLOBAL_WEIGHT_FACTOR = 100;

	public static void calculate(Order order){
		int drinksNotSoldOut = countActiveDrinks(order);
		
		for(OrderItem orderItem : order.getOrderItems()){
			float price = orderItem.getDrink().getPrice();
			if(!orderItem.getDrink().isSoldOut()){
				for(OrderItem otherItem : order.getOrderItems()){
					if(otherItem.getDrink().getId().equals(orderItem.getDrink().getId())){
						price = price + otherItem.getQuantity() * orderItem.getDrink().getWeight() / GLOBAL_WEIGHT_FACTOR ;
					}
					else{
						price = price - otherItem.getQuantity() * orderItem.getDrink().getWeight() / (GLOBAL_WEIGHT_FACTOR * drinksNotSoldOut) ;
					}
				}
				if(price < orderItem.getDrink().getMiniumPrice()){
					orderItem.getDrink().setPrice(orderItem.getDrink().getMiniumPrice());;
				}
				else{
					orderItem.getDrink().setPrice(price);
				}
			}
		}
	}
	
	private static int countActiveDrinks(Order order){
		int count = 0;
		for(OrderItem item : order.getOrderItems()){
			if(!item.getDrink().isSoldOut()){
				count ++;
			}
		}
		return count;
	}
	
	public static void calculateAlternative(Order order){
		float aangekochteWaarde = calculateWeightedBoughtValue(order);
		int totalWeight =  calculateTotalWeight(order);
		int totalBeveragesCount = calcalateTotalCount(order);
		int differentOrderdBeverages = calcalateBeverageOrderlCount(order);
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

	private static int calcalateTotalCount(Order order) {
		int count = 0;
		for(OrderItem orderItem : order.getOrderItems()){
			count += orderItem.getQuantity();
		}
		return count;
	}
	
	private static int calcalateBeverageOrderlCount(Order order) {
		int count = 0;
		for(OrderItem orderItem : order.getOrderItems()){
			if(orderItem.getQuantity()>0){
				count++;
			}
		}
		return count;
	}

	private static float caclculateNewPrice(OrderItem orderItem, float totalWeight, float aangekochteWaarde, float totalQuantity, float countNotSelledBeverages){
		float newPrice = 0;
		float oldPrice = orderItem.getDrink().getPrice();
		int weight = orderItem.getDrink().getWeight();
		if(orderItem.getQuantity() > 0){
			newPrice = oldPrice + (weight * aangekochteWaarde/totalWeight * orderItem.getQuantity()/totalQuantity);
			System.out.println(newPrice +" = " +oldPrice +" + " +weight + " * " +aangekochteWaarde +" / " +totalWeight +" * " +orderItem.getQuantity()+ " / " +totalQuantity);
		} else{
			newPrice = oldPrice - (1/countNotSelledBeverages * weight * aangekochteWaarde/totalWeight);
			System.out.println(newPrice + " = " +oldPrice + " - " +" 1" +" / " +countNotSelledBeverages +" * " +weight + " * " +aangekochteWaarde + " / " +totalWeight);
		}
		
		return newPrice;
	}
	
	private static int calculateTotalWeight(Order order) {
		int weight = 0;
		for(OrderItem orderItem : order.getOrderItems()){
			if(!orderItem.getDrink().isSoldOut()){
				Beverage beverage = orderItem.getDrink();
				weight += beverage.getWeight();
			}
		}
		return weight;
	}
	
	private static float calculateWeightedBoughtValue(Order order) {
		float aangekochteWaarde = 0;
		for(OrderItem orderItem : order.getOrderItems()){
			Beverage beverage = orderItem.getDrink();
			aangekochteWaarde += orderItem.getQuantity() * beverage.getWeight() * beverage.getPrice();
		}
		return aangekochteWaarde;
	}
}