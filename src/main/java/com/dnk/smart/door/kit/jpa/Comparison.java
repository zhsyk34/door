package com.dnk.smart.door.kit.jpa;

import lombok.Getter;

/**
 * Less Than	<	<
 * Greater Than	>	>
 * Less Than or Equal To	<=	<=
 * Greater Than or Equal To	>=	>=
 * Equal	=	==
 * Not Equal	!=	<>
 * <p>
 * http://www.objectdb.com/java/jpa/query/jpql/comparison
 */
//TODO
@Getter
public enum Comparison {
	LT("<", "Less Than"),
	GT(">", "Greater Than"),
	LE("<=", "Less Than or Equal To"),
	GE("<", "Greater Than or Equal To"),
	EQ("=", "Equal"),
	NE("<>", "Not Equal"),
	LK("like", "Like"),
	BT("between", "BETWEEN AND");

	private final String symbol;
	private final String sense;

	Comparison(String symbol, String sense) {
		this.symbol = symbol;
		this.sense = sense;
	}

	/*public static Expression expression() {
		CriteriaBuilder builder = null;
		builder.like().not().;

		Path path = null;
		builder.sum(path, 2);
	}*/

}
