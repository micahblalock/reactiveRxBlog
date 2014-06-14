package examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Component;

import rx.Observable;
import examples.domain.Movie;

@Component
public class MovieServices {

	ExecutorService executor = Executors.newFixedThreadPool(10);

	public Observable<Movie> getMovies() {
		return Observable.from(movies);
	}

	public Observable<String> getMovieDirector(Movie movie) {
		
        Observable.OnSubscribe<String> subscription = observer -> {
        	executor.execute(() -> {
	        	String director = (String)getMovieInfo(movie.getName()).get("Director");
	        	if(director != null){
	        		observer.onNext(director);
	        	} else {
	        		observer.onError(new Throwable("Movie not found: " + movie.getName()));
	        	}
	            observer.onCompleted();
        	});
        };
        return Observable.create(subscription);
	}
	
//	private void getMoviesAsync(final Subscriber<? super String> subscriber, final String movieName) {
//		executor.execute(new Runnable() {
//			@Override
//			public void run() {
//				getMovieInfo(movieName);
//			}
//		});
//	}
	
	private Map<String, Object> getMovieInfo(String movieName) {
		try {
			String queryURL = "http://www.omdbapi.com/?t="
					+ movieName.replaceAll(" ", "%20");
			final URL url = new URL(queryURL);
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(url.openStream()));

			JsonParser jsonParser = JsonParserFactory.getJsonParser();
			String content = "";
			String line = "";
			while ((line = reader.readLine()) != null) {
				content += line;
			}
			return jsonParser.parseMap(content);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private List<Movie> movies = Arrays.asList(new Movie("12 Years a Slave",
			"2013", 178375486, 18000000, "United Kingdom"), new Movie("Argo",
			"2012", 232325503, 44500000, "United States"), new Movie(
			"The Artist", "2011", 133432856, 15000000, "France"),
			new Movie("The King's Speech", "2010", 414211549, 15000000,
					"United Kingdom"), new Movie("The Hurt Locker", "2009",
					49230772, 15000000, "United States"), new Movie(
					"Slumdog Millionaire", "2008", 377910544, 15000000,
					"United Kingdom"), new Movie("No Country for Old Men",
					"2007", 171627166, 25000000, "United States"), new Movie(
					"The Departed", "2006", 289847354, 90000000,
					"United States"), new Movie("Crash", "2005", 98410061,
					6500000, "United States"), new Movie("Million Dollar Baby",
					"2004", 216763646, 30000000, "United States"), new Movie(
					"The Lord of the Rings: The Return of the King", "2003",
					1119929521, 94000000, "United Kingdom"), new Movie(
					"Chicago", "2002", 306776732, 45000000, "United States"),
			new Movie("A Beautiful Mind", "2001", 484284682, 58000000,
					"United States"), new Movie("Gladiator", "2000", 457640427,
					103000000, "United Kingdom"));
}
