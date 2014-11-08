package be.bert.googleappengine.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import be.bert.googleappengine.channel.ChannelKeys;
import be.bert.googleappengine.mapper.PriceHistoryMapper;
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
		BeursPricesCalculater.calculateAlternative(order);

		resetOrder(order);
		sendChannelUpdate(order);
		update(order);
		
		return order;
	}

	private void resetOrder(Order order) {
		order.setPreviousAmount(order.getTotalAmount());
		order.setTotalAmount(0);
	}

	private void sendChannelUpdate(Order order) {
		Gson gson = new Gson();
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage(ChannelKeys.OVERVIEW_KEY, gson.toJson(order)));
	}

	private void updateHistory(OrderItem orderItem) {
		PriceHistory history = PriceHistoryMapper.fromOrderItem(orderItem);
		EntityManager em = getEntityManager();
		try{
			em.persist(history);
		} finally{
			em.close();
		}
	}

	private void updateBeverage(OrderItem orderItem){
		EntityManager em = getEntityManager();
		try{
			if (!containsBeverage(orderItem.getDrink())) {
				throw new EntityNotFoundException("Object does not exist");
			}
			em.persist(orderItem.getDrink());
		} finally{
			em.close();
		}
	}
	
	private void update(Order order) {
		for(OrderItem orderItem : order.getOrderItems()){
			updateBeverage(orderItem);
			updateHistory(orderItem);
		}
	}

	private boolean containsBeverage(Beverage beverage) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Beverage item = mgr.find(Beverage.class, beverage.getId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
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
