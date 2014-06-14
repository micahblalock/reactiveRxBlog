package examples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.gson.Gson;

import rx.Observable;

@RestController
public class Movies {
	
	private Gson gson = new Gson();

	@Autowired
	MovieServices movieService;
	
	@RequestMapping(value = "/movies", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DeferredResult<String> getMovies(@RequestParam(value="gross", required=false, defaultValue="0") String gross) {
		final DeferredResult<String> deferredResult = new DeferredResult<>();
    	Observable<List<Object>> movies = movieService.getMovies()
    	  .filter(movie-> movie.getBoxOffice() > Long.parseLong(gross))
		  .flatMap(movie -> {
			  Observable<Map<String, String>> m = Observable.from(movie.getName()).map(movieName -> { return getMap("movieName", movieName);});
			  Observable<Map<String, String>> d = movieService.getMovieDirector(movie).map(director -> {
				  return getMap("director", director); 
			  });
			  return Observable.zip(m, d, (movieInfo, directorInfo) -> {
				  ((Map<String,String>)movieInfo).putAll((Map<String,String>)directorInfo);    				  
				  return (movieInfo);    				  
			  });
		  })
		  .toList();
    	movies.subscribe(movieInfo -> {
    			System.out.println(movieInfo);
    			deferredResult.setResult(gson.toJson(movieInfo));
    			System.out.println("result contains: " + deferredResult);
    		}, throwable -> {throwable.printStackTrace();});
    	System.out.println("result: " + deferredResult);
    	return deferredResult;
    }
	
	private Map<String,String> getMap(String key, String value) {
    	Map<String, String> m = new HashMap<String, String>();
    	m.put(key,value);
    	return m;
    }


    
}
