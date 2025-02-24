package bst.bobsoolting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration.class
})
public class BobsooltingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BobsooltingApplication.class, args);
	}

}
