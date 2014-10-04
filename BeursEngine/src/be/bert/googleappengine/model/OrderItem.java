package be.bert.googleappengine.model;

public class OrderItem {

	private int quantity;
	private Beverage drink;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Beverage getDrink() {
		return drink;
	}
	public void setDrink(Beverage drink) {
		this.drink = drink;
	}
}
