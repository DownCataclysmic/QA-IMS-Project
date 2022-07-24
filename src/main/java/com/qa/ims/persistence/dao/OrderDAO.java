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
		Long order_id = resultSet.getLong("orders_id");
		long customer_id = resultSet.getLong("fk_customers_id");
		Customer customer = customerDAO.read(customer_id);
		Double order_price = calculateOrderCost(order_id);
		List<Item> item_list = getItems(order_id);
		
		return new Order(order_id, customer, order_price, item_list);
	}
	
	public double calculateOrderCost(Long order_id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
	             PreparedStatement statement = connection
	                     .prepareStatement("SELECT * FROM order_items WHERE fk_orders_id = ?")) {
	            statement.setLong(1, order_id);
	            ResultSet resultSet = statement.executeQuery();
	            double totalOrderCost = 0;
	            while (resultSet.next()) {
	                totalOrderCost += itemDAO.read(resultSet.getLong("unit_price")).getPrice();
	            }
	            return totalOrderCost;
	        } catch (Exception e) {
	            LOGGER.debug(e);
	            LOGGER.error(e.getMessage());
	        }
	        return 0.0;
	 }
	
	
	public List<Item> getItems(Long order_id) {
		List<Item> ItemList = new ArrayList<>();
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT * FROM order_items WHERE fk_orders_id = ?")) {
            statement.setLong(1, order_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ItemList.add(itemDAO.read(resultSet.getLong("fk_item_id")));
            }
            return ItemList;
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return ItemList;
    }
	
	@Override
	public List<Order> readAll() {
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
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY orders_id DESC LIMIT 1");) {
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
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE orders_id = ?");) {
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
						.prepareStatement("INSERT INTO orders(fk_customers_id, orders_total_price) VALUES (?, 0.0)");) {
			statement.setLong(1, order.getCustomer_id().getId());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public Order updateAddToOrder(Long order_id, Long item_id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE orders (orders_id = ?, fk_items_id = ?) VALUES (?,?)");) {
			statement.setLong(1, order_id);
			statement.setLong(2, item_id);
			statement.executeUpdate();
			return read(order_id);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	public Order updateRemoveFromOrder(Long order_id, Long item_id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("DELETE FROM order_items WHERE fk_orders_id = " + order_id +" AND fk_items_id = " + item_id)) {
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
		try (Connection connection = DBUtils.getInstance().getConnection();
	            PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE orders_id = ?");) {
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
		// TODO Auto-generated method stub
		return null;
	}

}