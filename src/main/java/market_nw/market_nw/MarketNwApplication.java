package market_nw.market_nw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MarketNwApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketNwApplication.class, args);
	}

}
