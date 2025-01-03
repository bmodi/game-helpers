package ca.svarb.game_helpers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ca.svarb.whelper", "ca.svarb.whelper.rest"})
public class GameHelpersApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameHelpersApplication.class, args);
	}

}
