package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

public class ItemController implements CrudController<Item> {

	public static final Logger LOGGER = LogManager.getLogger();

	private ItemDAO itemDAO;
	private Utils utils;

	public ItemController(ItemDAO itemDAO, Utils utils) {
		super();
		this.itemDAO = itemDAO;
		this.utils = utils;
	}

	/**
	 * Reads all customers to the logger
	 */
	@Override
	public List<Item> readAll() {
		List<Item> items = itemDAO.readAll();
		for (Item item : items) {
			LOGGER.info(item);
		}
		return items;
	}

	@Override
	public Item create() {
		LOGGER.info("Please enter the item name:");
		String item_name = utils.getString();
		LOGGER.info("Please enter the price");
		Double item_price = utils.getDouble();
		Item item = itemDAO.create(new Item(item_name, item_price));
		LOGGER.info("item created");
		return item;
	}

	@Override
	public Item update() {
		for (Item element : itemDAO.readAll()) {
		    System.out.println(element);
		}
		LOGGER.info("Please enter the ID of the item you would like to update:");
		Long item_id = utils.getLong();
		LOGGER.info("Please enter a new item name:");
		String item_name = utils.getString();
		LOGGER.info("Please enter a new price:");
		Double item_price = utils.getDouble();
		Item item = itemDAO.update(new Item(item_id, item_name, item_price));
		LOGGER.info("Customer Updated");
		return item;
	}

	@Override
	public int delete() {
		for (Item element : itemDAO.readAll()) {
		    System.out.println(element);
		}
		LOGGER.info("Please enter the ID of the item you would like to delete:");
		Long item_id = utils.getLong();
		return itemDAO.delete(item_id);
	}
}
