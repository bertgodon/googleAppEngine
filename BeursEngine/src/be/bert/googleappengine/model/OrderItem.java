package be.bert.googleappengine.model;

public class OrderItem {

	private float total;
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
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
}
