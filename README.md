
This is a sample rest service app for the blog: **Virtuous Developers Guide to Reactive Programming**

*To build and run this app you must have a Java 8 jdk and gradle installed.*

To build the app use the command below. This builds a single executable JAR file that contains all the necessary dependencies, classes, and resources.
```bash
gradle build
```

Then you can run the app:
```bash
java -jar build/libs/rx-spring-boot-0.1.0.jar
```

You can call the service with either of the following:

```html
http://localhost:8080/movies
http://localhost:8080/movies?gross=25000000
```


