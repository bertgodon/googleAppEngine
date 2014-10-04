package be.bert.googleappengine.service;

import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;

import be.bert.googleappengine.model.Beverage;
import be.bert.googleappengine.transaction.PMF;

@Named
public class BeverageDataStoreService implements BeverageService {
	
	PersistenceManager persistanceManager;
	
	public BeverageDataStoreService() {
		persistanceManager = PMF.get().getPersistenceManager();
	}
	
	@Override
	public Beverage get(String name) {
		return persistanceManager.getObjectById(Beverage.class, name);
	}

	@Override
	public void delete(String name) {
		Beverage beverage = persistanceManager.getObjectById(Beverage.class, name);
		persistanceManager.deletePersistent(beverage);
	}

	@Override
	public List<Beverage> getAll() {
		return (List<Beverage>) persistanceManager.getManagedObjects();
	}

	@Override
	public void createOrUpdate(Beverage drink) {
		persistanceManager.makePersistent(drink);
	}
}
