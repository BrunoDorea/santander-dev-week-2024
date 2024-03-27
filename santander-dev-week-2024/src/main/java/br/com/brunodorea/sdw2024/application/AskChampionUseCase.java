package br.com.brunodorea.sdw2024.application;

import br.com.brunodorea.sdw2024.domain.exception.ChampionNotFoundException;
import br.com.brunodorea.sdw2024.domain.model.Champion;
import br.com.brunodorea.sdw2024.domain.ports.ChampionsRepository;

public record AskChampionUseCase(ChampionsRepository repository) {
    public  String askChampion(Long championId, String question) {
        Champion champion = repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        // TODO: Evoluir a lógica de negócio para considerar a integração com
        //  IAs generativas para retorno

        String championContext = champion.generateContextByQuestion(question);
        return championContext;
    }
}
