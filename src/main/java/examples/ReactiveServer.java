package examples;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import rx.Observable;
import rx.functions.Action1;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ReactiveServer {

	public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ReactiveServer.class, args);
        List<String> stooges = Arrays.asList("Larry","Moe","Curly");
        Observable.from(stooges).subscribe(new Action1<String>() {

                @Override
                public void call(String s) {
                    System.out.println("Hello " + s + "!");
                }

            });

	}

}
