package de.heinemann.dojo.discount;

import static de.heinemann.dojo.discount.BookCreatorUtil.createBook;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookListPriceCalculatorTest {

	private BookListPriceCalculator sut = new BookListPriceCalculator();
	private ArrayList<Book> bookList = new ArrayList<Book>();
	
	// ##############################################################
	// Scenario 1: Empty Book List.
	// ##############################################################
	
	@Test
	public void returnsZeroForEmptyList() {
		// act
		BigDecimal overallPrice = sut.calcPriceWithDiscount(bookList);
		
		// assert
		assertEquals("overall price", BigDecimal.ZERO, overallPrice);  
	}
	
	// ##############################################################
	// Scenario 2: One Book in Book List.
	// ##############################################################

	@Test
	public void returnsBookPriceForSingleBook() {		
		// arrange
		String bookPrice = "19.99";
		bookList.add( createBook("A title", bookPrice) );
		
		// act
		BigDecimal overallPrice = sut.calcPriceWithDiscount(bookList);
		
		// assert
		assertEquals("overall price", new BigDecimal(bookPrice), overallPrice);
	} 

	// ##############################################################
	// Scenario 3: More than one books in book list, but no discount.
	// ##############################################################

	@Test
	public void returnsSumOfBookPricesForTwoBooks() {	
		// arrange
		bookList.add( createBook("A title", "9.98") );
		bookList.add( createBook("Another title", "14.99") );
		
		// act
		BigDecimal overallPrice = sut.calcPriceWithDiscount(bookList);
		
		// assert
		assertEquals("overall price", new BigDecimal("24.97"), overallPrice);
	} 
	

	@Test
	public void returnsSumOfBookPricesForTenBooksOfDefaultGenre() {	
		// arrange 10 books
		addBooksToList(10);
		
		// act
		BigDecimal overallPrice = sut.calcPriceWithDiscount(bookList);
		
		// assert
		assertEquals("overall price", new BigDecimal("10"), overallPrice);
	}

	@Test
	public void returnsDiscountedPriceForElevenBooksOfDefaultGenre() {	
		// arrange 11 books
		addBooksToList(11);
		
		// act
		BigDecimal overallPrice = sut.calcPriceWithDiscount(bookList);
		
		// assert
		BigDecimal expectedPrice = new BigDecimal("10.9");
		assertEquals("overall price", expectedPrice, overallPrice);
	}
	
	@Test
	public void returnsDiscountedPriceForElevenBooksOfTwoGenres() {
		addBooksToList(10);
		bookList.add(createBook("Different Genre", "1", Genre.HISTORY.name()));
		
		BigDecimal overallPrice = sut.calcPriceWithDiscount(bookList);
		
		BigDecimal expectedPrice = new BigDecimal("11");
		assertEquals("overall price", expectedPrice, overallPrice);
	}
	
	@Test
	public void returnsDiscountedPriceForElevenFantasyBooksAndElevenSciFiBooksAndOneMiscBook() {
		addBooksToList(11, Genre.FANTASY);
		addBooksToList(11, Genre.SCIFI);
		addBooksToList(1);
		
		BigDecimal overallPrice = sut.calcPriceWithDiscount(bookList);
		
		BigDecimal expectedPrice = new BigDecimal("22.8");
		
		assertEquals("overall price", expectedPrice, overallPrice);
	}
	
	// now: test case "books per series"
	
	@Test
	public void returnsDiscountedPriceForTwoBooksOfSameSeries() {
		addBooksToList(2, Genre.FANTASY, "testSeries");
		
		BigDecimal overallPrice = sut.calcPriceWithDiscount(bookList);
		
		BigDecimal expectedPrice = new BigDecimal("1.9");
		
		assertEquals("overall price", expectedPrice, overallPrice);
	}
	
	@Test
	public void returnsDiscountedPriceForTenBooksOfSameSeries() {
		addBooksToList(10, Genre.FANTASY, "testSeries");
		
		BigDecimal overallPrice = sut.calcPriceWithDiscount(bookList);
		
		BigDecimal expectedPrice = new BigDecimal("9.1");
		
		assertEquals("overall price", expectedPrice, overallPrice);
	}

	@Test
	public void returnsDiscountedPriceForElevenBooksOfSameSeries() {
		addBooksToList(11, Genre.FANTASY, "testSeries");
		
		BigDecimal overallPrice = sut.calcPriceWithDiscount(bookList);
		
		BigDecimal expectedPrice = new BigDecimal("9.9");
		
		assertEquals("overall price", expectedPrice, overallPrice);
	}

	private void addBooksToList(int number) {
		addBooksToList(number, Genre.MISC);
	}

	private void addBooksToList(int number, Genre genre) {
		addBooksToList(number, genre, null);
	}
	
	private void addBooksToList(int number, Genre genre, String series) {
		for (int i = 0; i < number; i++) {
			bookList.add( createBook("Another title "+ i, "1", genre.name(), series) );
		} 
	}
}