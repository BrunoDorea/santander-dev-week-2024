package br.com.brunodorea.sdw2024.application;

import br.com.brunodorea.sdw2024.domain.exception.ChampionNotFoundException;
import br.com.brunodorea.sdw2024.domain.model.Champion;
import br.com.brunodorea.sdw2024.domain.ports.ChampionsRepository;
import br.com.brunodorea.sdw2024.domain.ports.GenerativeAiService;

public record AskChampionUseCase(ChampionsRepository repository,
                                 GenerativeAiService genAiService) {
    public  String askChampion(Long championId, String question) {
        Champion champion = repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        String context = champion.generateContextByQuestion(question);
        String objective = """
                Atue como um assistente com a habilidade de se comportar como os campeões do League of Legends (LOL).
                Responda perguntas incorporando a personalidade e estilo de um determinado campeão.
                Segue a pergunta, nome do campeão e a sua história:
                
                """;

        return genAiService.generateContent(objective, context);
    }
}
