package com.qa.ims.persistence.domain;

import java.util.List;

public class Item {

	private Long item_id;
	private String item_name;
	private Double item_price;
	private List<Item> item_list;

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

	public List<Item> getItem_list() {
		return item_list;
	}

	public void setItem_list(List<Item> item_list) {
		this.item_list = item_list;
	}

	//I have absolutely no clue what the below code does:
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item_name == null) ? 0 : item_name.hashCode());
		result = prime * result + ((item_id == null) ? 0 : item_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (getItemName() == null) {
			if (other.getItemName() != null)
				return false;
		} else if (!getItemName().equals(other.getItemName()))
			return false;
		if (item_id == null) {
			if (other.item_id != null)
				return false;
		} else if (!item_id.equals(other.item_id))
			return false;
		return true;
	}

}