package br.edu.fiap.banco.entity;

public record TipoConta(Long id, String nome) {

    public void atualizarNome(String nome) {
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (nome.length() < 3 ||  nome.length() > 40) {
            throw new IllegalArgumentException("Nome deve ter entre 3 e 40 letras");
        }

        nome = nome.toUpperCase().trim();
    }
}
