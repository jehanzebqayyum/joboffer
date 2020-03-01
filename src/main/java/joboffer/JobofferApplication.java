package joboffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JobofferApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobofferApplication.class, args);
	}

}
