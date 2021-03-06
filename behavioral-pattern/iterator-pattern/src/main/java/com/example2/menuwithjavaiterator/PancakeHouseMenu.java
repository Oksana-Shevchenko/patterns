package com.example2.menuwithjavaiterator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
public class PancakeHouseMenu implements Menu {
	private List<MenuItem> menuItems = new ArrayList<>();

	public PancakeHouseMenu(){
		addItem("K&B's Pancake Breakfast","Pancakes with scrambled eggs, and toast", true, 2.99);
		addItem("Regular Pancake Breakfast","Pancakes with fried eggs, sausage", false, 2.99);
		addItem("Blueberry Pancakes","Pancakes made with fresh blueberries", true, 3.49);
		addItem("Waffles","Waffles, with your choice of blueberries or strawberries", true, 3.59);
	}

	public void addItem(String name, String description, boolean vegetarian, double price) {
		MenuItem item = new MenuItem(name,description, vegetarian, price);
		menuItems.add(item);
	}

	public Iterator createIterator() {
		return menuItems.iterator();
	}
}
