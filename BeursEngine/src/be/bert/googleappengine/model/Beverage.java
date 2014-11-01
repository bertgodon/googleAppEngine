package be.bert.googleappengine.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Beverage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private float price;
	private float miniumPrice;
	private float recommendedPrice;
	private boolean soldOut;
	private int weight;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public float getRecommendedPrice() {
		return recommendedPrice;
	}
	public void setRecommendedPrice(float recommendedPrice) {
		this.recommendedPrice = recommendedPrice;
	}
	public boolean isSoldOut() {
		return soldOut;
	}
	public void setSoldOut(boolean soldOut) {
		this.soldOut = soldOut;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
}