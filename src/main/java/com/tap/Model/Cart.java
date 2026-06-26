package com.tap.Model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<Integer, CartItem> items;

	public Cart() {
		this.items = new HashMap<Integer, CartItem>();
	}

	public Map<Integer, CartItem> getItems() {
		return items;
	}

	public void addItem(CartItem cartItem) {
		int menuId = cartItem.getMenuId();
		if (items.containsKey(menuId)) {
			CartItem existingItem = items.get(menuId);
			existingItem.setQty(existingItem.getQty() + cartItem.getQty());
		} else {
			items.put(menuId, cartItem);
		}
	}

	public void updateItem(int menuId, int qty) {
		if (items.containsKey(menuId)) {
			if (qty <= 0) {
				items.remove(menuId);
			} else {
				items.get(menuId).setQty(qty);
			}
		}
	}

	public void removeItem(int menuId) {
		items.remove(menuId);
	}

	public void clear() {
		items.clear();
	}
}
