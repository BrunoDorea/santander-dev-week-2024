package br.com.brunodorea.sdw2024;

import br.com.brunodorea.sdw2024.application.AskChampionUseCase;
import br.com.brunodorea.sdw2024.application.ListChampionsUseCase;
import br.com.brunodorea.sdw2024.domain.ports.ChampionsRepository;
import br.com.brunodorea.sdw2024.domain.ports.GenerativeAiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class Application {

	public static void main(String[] args) {SpringApplication.run(Application.class, args);}

	@Bean
	public ListChampionsUseCase provideListChampionsUseCase(ChampionsRepository repository) {
		return new ListChampionsUseCase(repository);
	}

	@Bean
	public AskChampionUseCase provideAskChampionsUseCase(ChampionsRepository repository, GenerativeAiService genAiService) {
		return new AskChampionUseCase(repository, genAiService);
	}
}
