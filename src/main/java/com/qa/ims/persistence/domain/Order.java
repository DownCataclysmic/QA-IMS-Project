package com.qa.ims.persistence.domain;

import java.util.List;

public class Order {

	private Long order_id;
	private Customer customer_id;
	private Long item_id;
	private Double order_price;
	private List<Item> item_list;
	
	public Order(Long order_id) {
		this.order_id = order_id;
	}
	
	public Order(Customer customer_id) {
		this.customer_id = customer_id;
	}
	
	public Order(Long order_id, Long item_id) {
		this.order_id = order_id;
		this.item_id = item_id;
	}

	public Order(Long order_id, Customer customer_id, Double order_price) {
		this.order_id = order_id;
		this.customer_id = customer_id;
		this.order_price = order_price;
	}
	
	public Order(Long order_id, Customer customer_id, Double order_price, List<Item> item_list) {
		this.order_id = order_id;
		this.customer_id = customer_id;
		this.order_price = order_price;
		this.item_list = item_list;
	}
	
	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public Customer getCustomer_id() {
		return customer_id;
	}
	
	public void setCustomer_id(Customer customer_id) {
		this.customer_id = customer_id;
	}

	public Double getOrder_price() {
		return order_price;
	}
	
	public void setOrder_price(Double order_price) {
		this.order_price = order_price;
	}
	
	public List<Item> getItems() {
		return item_list;
	}
	
	public void setItems(List<Item> item_list) {
		this.item_list = item_list;
	}
	
	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}
	
	
	@Override
	public String toString() {
		return "Order ID: " + order_id + "  Customer ID: " + customer_id.getId() + "  Price: " + order_price + " Items: " + item_list;
	}

}
