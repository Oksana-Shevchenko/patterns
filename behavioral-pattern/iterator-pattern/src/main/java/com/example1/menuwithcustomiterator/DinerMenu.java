package com.example1.menuwithcustomiterator;

import lombok.Getter;

@Getter
public class DinerMenu {
	private static final int MAX_ITEMS = 6;
	private int numberOfItems = 0;
	private MenuItem[] menuItems;

	public DinerMenu() {
		menuItems = new MenuItem[MAX_ITEMS];

		addItem("Vegetarian BLT", "(Fakin') Bacon with lettuce & tomato on whole wheat", true, 2.99);
		addItem("BLT", "Bacon with lettuce & tomato on whole wheat", false, 2.99);
		addItem("Soup of the day", "Soup of the day, with a side of potato salad", false, 3.29);
		addItem("Hotdog", "A hot dog, with saurkraut, relish, onions, topped with cheese", false, 3.05);
	}

	private void addItem(String name, String description, boolean vegetarian, double price) {
		MenuItem item = new MenuItem(name, description, vegetarian, price);
		if (numberOfItems >= MAX_ITEMS) {
			System.err.println("Sorry, menu with custom iterator is full! Can't add item to menu with custom iterator");
		} else {
			menuItems[numberOfItems] = item;
			numberOfItems = numberOfItems + 1;
		}
	}

	public CustomIterator createIterator() {
		return new DinerMenuIterator(menuItems);
	}
}
