package de.heinemann.dojo.discount;

import java.math.BigDecimal;

public class DiscountBook {
	
	private Book book;
	
	// percentage (100% == 1; 1% == 0.01) to be subtracted from  price 
	private BigDecimal discount;
	
	DiscountBook(Book book, BigDecimal discount) {
		this.book = book;
		this.discount = discount;
	}

	public BigDecimal calculateNetPrice() {
		return BigDecimal.ONE.subtract(discount).multiply(book.getPrice());
	}
	
	public void setDiscountIfGreater(BigDecimal discount) {
		if (discount.compareTo(this.discount) > 0) {
			setDiscount(discount);
		}
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
}
