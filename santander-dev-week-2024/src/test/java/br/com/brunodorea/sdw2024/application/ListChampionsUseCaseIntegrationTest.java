package br.com.brunodorea.sdw2024.application;

import br.com.brunodorea.sdw2024.domain.model.Champion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ListChampionsUseCaseIntegrationTest {
    @Autowired
    private ListChampionsUseCase listChampionsUseCase;

    @Test
    public void testListChampions() {
        List<Champion> champions = listChampionsUseCase.findAll();

        Assertions.assertEquals(12, champions.size());
    }
}
