package com.qa.ims.persistence.domain;

public class Item {

	private Long itemId;
	private String itemName;
	private Long price;

	public Item(String itemName, Long price) {
		this.setItemName(itemName);
		this.setPrice(price);
	}

	public Item(Long itemId, String firstName, Long price) {
		this.setItemId(itemId);
		this.setItemName(itemName);
		this.setPrice(price);
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "item id:" + itemId + " item name:" + itemName + " Price: £" + price;
	}

}