package com.qa.ims.persistence.domain;

public class Order {

	private Long order_id;
	private Long customer_id;
	private Long item_id;
	
	public Order(long customer_id, long item_id) {
		super();
		this.customer_id = customer_id;
		this.item_id = item_id;
	}

	public Order(long order_id, long customer_id, long item_id) {
		super();
		this.order_id = order_id;
		this.customer_id = customer_id;
		this.item_id = item_id;
	}
	
	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

}
