package com.dnk.smart.door.kit.jpa;

import lombok.Getter;

import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderKit {
	@Getter
	private final List<Order> orders = new ArrayList<>();

	private OrderKit() {
	}

	public static OrderKit instance() {
		return new OrderKit();
	}

	public static Order[] get(Collection<Order> orders) {
		return orders.toArray(new Order[orders.size()]);
	}

	public OrderKit append(Order order) {
		orders.add(order);
		return this;
	}

	public Order[] get() {
		return get(orders);
	}
}
