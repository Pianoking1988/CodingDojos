package de.heinemann.dojo.discount;

import java.math.BigDecimal;

/**
 * JavaBean Domain object for the BookListPriceCalculator.
 * 
 * @author IKS Dojo Team
 */
public class Book {

	private String title;
	private BigDecimal price;
	private String series;
	private Genre genre;

	public Book(String aTitle, 
			    BigDecimal aPrice, 
			    String aSeries,
			    Genre aGenre) 
	{
		if (aTitle == null) throwValidationExceptionFor("title");
		if (aPrice == null) throwValidationExceptionFor("price");
		if (aGenre == null) aGenre = Genre.MISC;
		
		this.title = aTitle;
		this.price = aPrice;
		this.series = aSeries;
		this.genre = aGenre;
	}

	private void throwValidationExceptionFor(String attribute) {
		throw new RuntimeException("Error creating book: " 
	                                + attribute + " must be provided!");
	}

	public String getTitle() {
		return title;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public String getSeries() {
		return series;
	}
	
	public Genre getGenre() {
		return genre;
	}

	
	@Override
	public String toString() {
		return "Book [title=" + title + ", price=" + price + ", series="
				+ series + ", genre=" + genre + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((series == null) ? 0 : series.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (genre != other.genre)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (series == null) {
			if (other.series != null)
				return false;
		} else if (!series.equals(other.series))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
