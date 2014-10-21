package be.bert.googleappengine.transaction;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class PMF {
	private static final EntityManagerFactory emfInstance = Persistence
		      .createEntityManagerFactory("transactions-optional");
		
	private PMF() {
	}
	
	public static EntityManagerFactory get() {
		return emfInstance;
	}
}