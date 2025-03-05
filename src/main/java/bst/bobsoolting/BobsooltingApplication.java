package bst.bobsoolting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration.class
})
@MapperScan("bst.bobsoolting.post.command.domain.repository")
//PostRepository를 정상작동을 위한 추가.. - 승민
public class BobsooltingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BobsooltingApplication.class, args);
	}

}
