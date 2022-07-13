package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO orderDAO;
	private Utils utils;

	public OrderController(OrderDAO OrderDAO, Utils utils) {
		super();
		this.orderDAO = OrderDAO;
		this.utils = utils;
	}

	/**
	 * Reads all customers to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		for (Order order : orders) {
			LOGGER.info(order);
		}
		return orders;
	}

	@Override
	public Order create() {
		LOGGER.info("Please enter the customer ID:");
		Long customer_id = utils.getLong();
		LOGGER.info("Please enter the item ID:");
		Long item_id = utils.getLong();
		Order order = orderDAO.create(new Order(customer_id, item_id));
		LOGGER.info("Order created");
		return order;
	}

	@Override
	public Order update() {
		for (Order element : orderDAO.readAll()) {
		    System.out.println(element);
		}
		LOGGER.info("Please enter the order ID:");
		Long order_id = utils.getLong();
		LOGGER.info("Please enter a new customer ID:");
		Long customer_id = utils.getLong();
		LOGGER.info("Please enter a new item ID:");
		Long item_id = utils.getLong();
		Order order = orderDAO.update(new Order(order_id, customer_id, item_id));
		LOGGER.info("Customer Updated");
		return order;
	}

	@Override
	public int delete() {
		for (Order element : orderDAO.readAll()) {
		    System.out.println(element);
		}
		LOGGER.info("Please enter the ID of the order you would like to delete:");
		Long order_id = utils.getLong();
		return orderDAO.delete(order_id);
	}

}