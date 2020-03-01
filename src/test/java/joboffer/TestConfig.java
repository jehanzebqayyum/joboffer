package joboffer;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.GenericApplicationContext;


@Configuration
public class TestConfig {
	@Bean
	@Primary
	GenericApplicationContext genericApplicationContext(final GenericApplicationContext gac) {
		return Mockito.spy(gac);
	}
}
