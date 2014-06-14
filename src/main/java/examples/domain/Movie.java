package examples.domain;

public class Movie {

	String name;
	int year;
	int boxOffice;
	int budget;
	String country;
	
	public Movie(String name, String year, int boxOffice, int budget, String country) {
		this.name = name;
		this.year = Integer.parseInt(year);
		this.boxOffice = boxOffice;
		this.budget = budget;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public int getYear() {
		return year;
	}

	public int getBoxOffice() {
		return boxOffice;
	}

	public int getBudget() {
		return budget;
	}

	public String getCountry() {
		return country;
	}

}
