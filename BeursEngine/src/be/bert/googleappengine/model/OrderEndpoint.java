package be.bert.googleappengine.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import be.bert.googleappengine.channel.ChannelKeys;
import be.bert.googleappengine.service.BeursPricesCalculater;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gson.Gson;

@Api(name = "orderendpoint", namespace = @ApiNamespace(ownerDomain = "bert.be", ownerName = "bert.be", packagePath = "googleappengine.model"))
public class OrderEndpoint {

	@ApiMethod(name = "getInitialOrder")
	public Order getInitialOrder(){
		EntityManager em = getEntityManager();
		Order order = new Order();
    	try{
    		Query q = em.createQuery("select m from Beverage m");
    		List<Beverage> beverages = q.getResultList();
    		List<OrderItem> orderItems = new ArrayList<OrderItem>();
    		for(Beverage beverage : beverages){
    			OrderItem orderItem = new OrderItem();
    			orderItem.setDrink(beverage);
    			orderItems.add(orderItem);
    		}
    		order.setOrderItems(orderItems);
    	}
    	finally{
    		em.close();
    	}
		return order;
	}
	
	
	@ApiMethod(name = "updateOrder")
	public Order updateOrder(Order order) {
		System.out.println("updating order");
		BeursPricesCalculater.calculateAlternative(order);
		order.setPreviousAmount(order.getTotalAmount());
		order.setTotalAmount(0);
		//persit
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		Gson gson = new Gson();
		channelService.sendMessage(new ChannelMessage(ChannelKeys.OVERVIEW_KEY, gson.toJson(order)));
		return order;
	}


	private boolean containsOrder(Order order) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Order item = mgr.find(Order.class, order.getId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}
}
