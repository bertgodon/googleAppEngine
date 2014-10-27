//package be.bert.googleappengine.model;
//
//import be.bert.googleappengine.model.EMF;
//
//import com.google.api.server.spi.config.Api;
//import com.google.api.server.spi.config.ApiMethod;
//import com.google.api.server.spi.config.ApiNamespace;
//import com.google.api.server.spi.response.CollectionResponse;
//import com.google.appengine.api.datastore.Cursor;
//import com.google.appengine.datanucleus.query.JPACursorHelper;
//
//import java.util.List;
//
//import javax.annotation.Nullable;
//import javax.inject.Named;
//import javax.persistence.EntityExistsException;
//import javax.persistence.EntityNotFoundException;
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//
//@Api(name = "pricehistoryendpoint", namespace = @ApiNamespace(ownerDomain = "bert.be", ownerName = "bert.be", packagePath = "googleappengine.model"))
//public class PriceHistoryEndpoint {
//
//	/**
//	 * This method lists all the entities inserted in datastore.
//	 * It uses HTTP GET method and paging support.
//	 *
//	 * @return A CollectionResponse class containing the list of all entities
//	 * persisted and a cursor to the next page.
//	 */
//	@SuppressWarnings({ "unchecked", "unused" })
//	@ApiMethod(name = "listPriceHistory")
//	public CollectionResponse<PriceHistory> listPriceHistory(
//			@Nullable @Named("cursor") String cursorString,
//			@Nullable @Named("limit") Integer limit) {
//
//		EntityManager mgr = null;
//		Cursor cursor = null;
//		List<PriceHistory> execute = null;
//
//		try {
//			mgr = getEntityManager();
//			Query query = mgr
//					.createQuery("select from PriceHistory as PriceHistory");
//			if (cursorString != null && cursorString != "") {
//				cursor = Cursor.fromWebSafeString(cursorString);
//				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
//			}
//
//			if (limit != null) {
//				query.setFirstResult(0);
//				query.setMaxResults(limit);
//			}
//
//			execute = (List<PriceHistory>) query.getResultList();
//			cursor = JPACursorHelper.getCursor(execute);
//			if (cursor != null)
//				cursorString = cursor.toWebSafeString();
//
//			// Tight loop for fetching all entities from datastore and accomodate
//			// for lazy fetch.
//			for (PriceHistory obj : execute)
//				;
//		} finally {
//			mgr.close();
//		}
//
//		return CollectionResponse.<PriceHistory> builder().setItems(execute)
//				.setNextPageToken(cursorString).build();
//	}
//
//	/**
//	 * This method gets the entity having primary key id. It uses HTTP GET method.
//	 *
//	 * @param id the primary key of the java bean.
//	 * @return The entity with primary key id.
//	 */
//	@ApiMethod(name = "getPriceHistory")
//	public PriceHistory getPriceHistory(@Named("id") Long id) {
//		EntityManager mgr = getEntityManager();
//		PriceHistory pricehistory = null;
//		try {
//			pricehistory = mgr.find(PriceHistory.class, id);
//		} finally {
//			mgr.close();
//		}
//		return pricehistory;
//	}
//
//	/**
//	 * This inserts a new entity into App Engine datastore. If the entity already
//	 * exists in the datastore, an exception is thrown.
//	 * It uses HTTP POST method.
//	 *
//	 * @param pricehistory the entity to be inserted.
//	 * @return The inserted entity.
//	 */
//	@ApiMethod(name = "insertPriceHistory")
//	public PriceHistory insertPriceHistory(PriceHistory pricehistory) {
//		EntityManager mgr = getEntityManager();
//		try {
//			if (containsPriceHistory(pricehistory)) {
//				throw new EntityExistsException("Object already exists");
//			}
//			mgr.persist(pricehistory);
//		} finally {
//			mgr.close();
//		}
//		return pricehistory;
//	}
//
//	/**
//	 * This method is used for updating an existing entity. If the entity does not
//	 * exist in the datastore, an exception is thrown.
//	 * It uses HTTP PUT method.
//	 *
//	 * @param pricehistory the entity to be updated.
//	 * @return The updated entity.
//	 */
//	@ApiMethod(name = "updatePriceHistory")
//	public PriceHistory updatePriceHistory(PriceHistory pricehistory) {
//		EntityManager mgr = getEntityManager();
//		try {
//			if (!containsPriceHistory(pricehistory)) {
//				throw new EntityNotFoundException("Object does not exist");
//			}
//			mgr.persist(pricehistory);
//		} finally {
//			mgr.close();
//		}
//		return pricehistory;
//	}
//
//	/**
//	 * This method removes the entity with primary key id.
//	 * It uses HTTP DELETE method.
//	 *
//	 * @param id the primary key of the entity to be deleted.
//	 */
//	@ApiMethod(name = "removePriceHistory")
//	public void removePriceHistory(@Named("id") Long id) {
//		EntityManager mgr = getEntityManager();
//		try {
//			PriceHistory pricehistory = mgr.find(PriceHistory.class, id);
//			mgr.remove(pricehistory);
//		} finally {
//			mgr.close();
//		}
//	}
//
//	private boolean containsPriceHistory(PriceHistory pricehistory) {
//		EntityManager mgr = getEntityManager();
//		boolean contains = true;
//		try {
//			PriceHistory item = mgr.find(PriceHistory.class,
//					pricehistory.getId());
//			if (item == null) {
//				contains = false;
//			}
//		} finally {
//			mgr.close();
//		}
//		return contains;
//	}
//
//	private static EntityManager getEntityManager() {
//		return EMF.get().createEntityManager();
//	}
//
//}
