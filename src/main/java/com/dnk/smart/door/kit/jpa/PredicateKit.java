package com.dnk.smart.door.kit.jpa;

import lombok.Getter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PredicateKit {

	@Getter
	private final List<Predicate> predicates = new ArrayList<>();

	private PredicateKit() {
	}

	public static PredicateKit instance() {
		return new PredicateKit();
	}

	public static Predicate[] get(Collection<Predicate> predicates) {
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	public PredicateKit append(Predicate predicate) {
		predicates.add(predicate);
		return this;
	}

	public Predicate[] get() {
		return get(predicates);
	}
}
