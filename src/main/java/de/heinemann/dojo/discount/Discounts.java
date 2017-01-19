package de.heinemann.dojo.discount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Discounts {

	private List<BigDecimal> discounts = new ArrayList<>();
	
	public Discounts addNextDiscounts(int number, BigDecimal discount) {
		for (int i = 0; i < number; i++) {
			discounts.add(discount);
		}
		return this;
	}
	
	public BigDecimal getDiscount(int index) {
		return index < discounts.size() ? discounts.get(index) : discounts.get(discounts.size() - 1);
	}
	
}
