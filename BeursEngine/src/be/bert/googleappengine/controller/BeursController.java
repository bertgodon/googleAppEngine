package be.bert.googleappengine.controller;

import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import be.bert.googleappengine.model.Beverage;
import be.bert.googleappengine.transaction.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;


@Api(name = "drinkService",
	version = "v1")
@Named
public class BeursController {
	
    @ApiMethod(name = "getAllDrinks", httpMethod="GET")
	public List<Beverage> getAll(){
        EntityManager em = PMF.get().createEntityManager();
        try{
        	Query q = em.createQuery("select m from Beverage m");
        	return q.getResultList();
        }
        finally{
        	em.close();
        }
	}
    
    @ApiMethod(name = "add", httpMethod="POST")
   	public void addDrink(Beverage beverage){
        EntityManager em = PMF.get().createEntityManager();
    	try{
    		em.getTransaction().begin();
    		em.persist(beverage);
    		em.getTransaction().commit();
    	} finally{
			em.close();
    	}
   	}
    
    @ApiMethod(name = "deleteById", httpMethod="POST")
   	public void deleteById(@Named("id") Long id){
        EntityManager em = PMF.get().createEntityManager();
    	try{
    		Beverage beverage = em.find(Beverage.class,id);
    		em.getTransaction().begin();
    		em.remove(beverage);
    		em.getTransaction().commit();
    	} finally{
			em.close();
    	}
   	}
}