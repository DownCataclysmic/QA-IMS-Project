package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
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
		Long fk_customer_id = utils.getLong();
		CustomerDAO customerDao = new CustomerDAO();
        Customer createdCustomer = customerDao.read(fk_customer_id);
        Order order = orderDAO.create(new Order(createdCustomer));
        LOGGER.info("New Order Successfully Created");
		return order;
	}

	@Override	
	public Order update() {
        Order order = new Order();
        for (Order element : orderDAO.readAll()) {
		    System.out.println(element);
		}
        LOGGER.info("Please enter the Order ID that you want to update");
        Long orders_id = utils.getLong();
        LOGGER.info("Would you like to add an item to the order or remove an item from the order?");
        String decision = utils.getString();
        LOGGER.info("Enter the item ID");
        Long items_id = utils.getLong();
        List<Item> item_list = new ArrayList<Item>();
        if (decision.equals("Add")) {
            order = orderDAO.updateAddToOrder(orders_id, items_id, item_list);
            LOGGER.info("The item has been successfully added to the order");
        }
        if (decision.equals("Remove")) {
            order = orderDAO.updateRemoveFromOrder(orders_id, items_id, item_list);
            LOGGER.info("The item has been successfully removed from order");
        }
        LOGGER.info("Order Successfully Updated");
        return order;
    }

	@Override
	public int delete() {
		for (Order element : orderDAO.readAll()) {
		    System.out.println(element);
		}
		LOGGER.info("Please enter the ID of the order you would like to delete:");
		Long order_id = utils.getLong();
		LOGGER.info("Order deleted.");
		return orderDAO.delete(order_id);
	}

}