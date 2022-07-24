package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order> {
	public static final Logger LOGGER = LogManager.getLogger();

	private final CustomerDAO customerDAO;
	private final ItemDAO itemDAO;
	
	public OrderDAO(CustomerDAO customerDAO, ItemDAO itemDAO) {
		this.customerDAO = customerDAO;
		this.itemDAO = itemDAO;
	}
	
	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long order_id = resultSet.getLong("order_id");
		Long customer_id = resultSet.getLong("fk_customer_id");
		Customer customer = customerDAO.read(customer_id);
		Double order_price = resultSet.getDouble("order_price");
		List<Item> item_list = getItems(order_id); //Need to tweak this so I can add multiple items to an order.
		int quantity = resultSet.getInt("quantity");
		
		return new Order(order_id, customer, quantity, order_price, item_list);
	}
	
	//This shows the cost of one of the items.
	public Double getCost(Long item_list) {
		ItemDAO items = new ItemDAO();
		Double cost = items.read(item_list).getPrice();
		return cost;
	}
	
	public List<Item> getItems(Long order_id) {
		//Mish-mash of code to try and create a list of items in an order.Non-functioning so far.
		List<Item> item_list = new ArrayList<>();
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT * FROM order_items WHERE fk_order_id = ?");) {
            statement.setLong(1, order_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                item_list.add(itemDAO.read(resultSet.getLong("fk_item_id")));
            }
            return item_list;
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        
        return item_list;
    }
	
	@Override
	public List<Order> readAll() {
		//
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");) {
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()) {
				orders.add(modelFromResultSet(resultSet));
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	public Order readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY order_id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order read(Long order_id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE order_id = ?");) {
			statement.setLong(1, order_id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order create(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO orders(fk_customer_id, order_price) VALUES (?, 0.0)");) {
			statement.setLong(1, order.getCustomer_id().getId());
			//statement.setDouble(2, order.getOrder_price());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public Order updateAddToOrder(Long order_id, Long item_id, String item_name, List<Item> item_list) {
		//Updates the order by adding to the order.
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE order_items (fk_order_id = ?, fk_item_id = ?, quantity = ?) VALUES (?,?,?)");) {
			statement.setLong(1, order_id);
			statement.setLong(2, item_id);
			statement.executeUpdate();
			return read(order_id);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return read(order_id);
	}
	
	public Order updateRemoveFromOrder(Long order_id, Long item_id, String item_name, List<Item> item_list) {
		//Updates the order by removing from the order.
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("DELETE FROM order_items WHERE fk_order_id = " + order_id + " AND fk_item_id = " + item_id);) {
            statement.setLong(1, order_id);
            statement.setLong(2, item_id);
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return read(order_id);
    }
	
	@Override
	public int delete(Long order_id) {
		//When asked for an order id, the order associated with that order id is deleted.
		try (Connection connection = DBUtils.getInstance().getConnection();
	            PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE order_id = ?");) {
	    			statement.setLong(1, order_id);
	    			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
		
	}

	@Override
	public Order update(Order t) {
		//Thought the best way to approach this was by creating a method exclusively for adding items to an order
		//and another method for deleting items from an order.
		//This makes the "update" method redundant for orders.
		return null;
	}

}