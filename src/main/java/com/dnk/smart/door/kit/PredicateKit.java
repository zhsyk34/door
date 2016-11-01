package com.dnk.smart.door.kit;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PredicateKit {

	private final List<Predicate> predicateList;

	private PredicateKit() {
		predicateList = new ArrayList<>();
	}

	public static PredicateKit instance() {
		return new PredicateKit();
	}

	public PredicateKit append(Predicate predicate) {
		predicateList.add(predicate);
		return this;
	}

	public Predicate[] get() {
		return predicateList.toArray(new Predicate[predicateList.size()]);
	}
}
