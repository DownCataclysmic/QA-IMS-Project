package com.qa.ims.persistence.domain;

public class Item {

	private Long itemId;
	private String item_name;
	private Long item_price;

	public Item(String item_name, Long item_price) {
		this.setItemName(item_name);
		this.setPrice(item_price);
	}

	public Item(Long itemId, String item_name, Long item_price) {
		this.setItemId(itemId);
		this.setItemName(item_name);
		this.setPrice(item_price);
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return item_name;
	}

	public void setItemName(String item_name) {
		this.item_name = item_name;
	}

	public Long getPrice() {
		return item_price;
	}

	public void setPrice(Long item_price) {
		this.item_price = item_price;
	}

	@Override
	public String toString() {
		return "Item ID: " + itemId + "  Item Name: " + item_name + "  Price: £" + item_price;
	}

}