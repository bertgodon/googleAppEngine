package be.bert.googleappengine.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Beverage {

	@PrimaryKey
	@Persistent	private String name;
	@Persistent	private float price;
	@Persistent private float miniumPrice;
	@Persistent private int weight;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getMiniumPrice() {
		return miniumPrice;
	}
	public void setMiniumPrice(float miniumPrice) {
		this.miniumPrice = miniumPrice;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
}