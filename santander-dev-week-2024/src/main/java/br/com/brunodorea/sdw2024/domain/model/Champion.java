package br.com.brunodorea.sdw2024.domain.model;

public record Champion(Long id, String name, String role, String lore, String imageUrl) {
    public String generateContextByQuestion(String question) {
        return """
        Pergunta: %s
        Nome do campeão: %s
        Função: %s
        História (Lore): %s
        """.formatted(question, this.name, this.role, this.lore);
    }
}
