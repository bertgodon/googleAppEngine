package be.bert.googleappengine.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private List<OrderItem> drinks;
	private float totalAmount;
	private float previousAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
