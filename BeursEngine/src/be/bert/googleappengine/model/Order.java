package be.bert.googleappengine.model;

import java.util.List;

public class Order {

	private List<OrderItem> drinks;
	private float totalAmount;
	private float previousAmount;

	public List<OrderItem> getDrinks() {
		return drinks;
	}

	public void setDrinks(List<OrderItem> drinks) {
		this.drinks = drinks;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public float getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(float previousAmount) {
		this.previousAmount = previousAmount;
	}
}
