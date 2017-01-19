package de.heinemann.dojo.discount;

import java.math.BigDecimal;

/**
 * Helper to create test data objects.
 * 
 * @author IKS Dojo Team
 */
public class BookCreatorUtil {
	

	/**
	 * Creates a book instance with the given attributes.
	 * 
	 * @param attributes Assumed order: title, price, genre, series
	 * @return Book
	 */
	public static Book createBook(String... attributes) {
		if (attributes.length < 0) 
			throw new RuntimeException("At least 2 attibutes expected!");
		
		if (attributes.length == 2)
			return new Book(attributes[0], new BigDecimal(attributes[1]), 
					        null, null);

		if (attributes.length == 3)
			return new Book(attributes[0], new BigDecimal(attributes[1]), 
					        null, Genre.valueOf(attributes[2]));
		
		return new Book(attributes[0], new BigDecimal(attributes[1]),  
				        attributes[3], Genre.valueOf(attributes[2]));
	}

}
