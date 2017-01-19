package de.heinemann.dojo.discount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class DiscountStrategy {

	private List<BigDecimal> discounts = new ArrayList<>();
	
	protected void addNextDiscounts(int number, BigDecimal discount) {
		for (int i = 0; i < number; i++) {
			discounts.add(discount);
		}
	}
	
	private BigDecimal getDiscount(int index) {
		return index < discounts.size() ? discounts.get(index) : discounts.get(discounts.size() - 1);
	}
	
	public void assignDiscounts(List<DiscountBook> discountBooks) {
		for (int i = 0; i < discountBooks.size(); i++) {
			DiscountBook discountBook = discountBooks.get(i);
			BigDecimal discount = getDiscount(i);
			discountBook.setDiscountIfGreater(discount);
		}
	}

}
