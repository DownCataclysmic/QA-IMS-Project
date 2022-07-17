package com.qa.ims.persistence.domain;

public class Item {

	private Long item_id;
	private String item_name;
	private Double item_price;

	public Item(String item_name, Double item_price) {
		this.setItemName(item_name);
		this.setPrice(item_price);
	}

	public Item(Long item_id, String item_name, Double item_price) {
		this.setItemId(item_id);
		this.setItemName(item_name);
		this.setPrice(item_price);
	}

	public Long getItemId() {
		return item_id;
	}

	public void setItemId(Long item_id) {
		this.item_id = item_id;
	}

	public String getItemName() {
		return item_name;
	}

	public void setItemName(String item_name) {
		this.item_name = item_name;
	}

	public Double getPrice() {
		return item_price;
	}

	public void setPrice(Double item_price) {
		this.item_price = item_price;
	}

	@Override
	public String toString() {
		return "Item ID: " + item_id + "  Item Name: " + item_name + "  Price: £" + item_price;
	}

}