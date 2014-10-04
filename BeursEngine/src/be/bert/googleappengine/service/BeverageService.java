package be.bert.googleappengine.service;

import java.util.List;

import be.bert.googleappengine.model.Beverage;

public interface BeverageService {

	public void createOrUpdate(Beverage drink);
	public Beverage get(String name);
	public void delete(String name);
	public List<Beverage> getAll();
}
