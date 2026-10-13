package br.edu.fiap.banco.entity;

/**
 * Classificação de uma conta bancária, como CORRENTE ou POUPANCA.
 * O domínio permanece um record simples: JDBC cuida da tabela e o resource cuida do HTTP.
 */
public record TipoConta(Long id, String nome) {

    /** Preserva o ID e passa novamente pela normalização do construtor. */
    public void atualizarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (nome.length() < 3 || nome.length() > 40) {
            throw new IllegalArgumentException("Nome deve ter entre 3 e 40 letras");
        }

        nome = nome.toUpperCase().trim();
    }

    /** Cria um tipo ainda sem a chave que será gerada pelo banco. */
    public static TipoConta novo(String nome) {
        return new TipoConta(null, nome);
    }

    /** Valida e normaliza o nome em toda construção da entidade. */
    public TipoConta {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do tipo de conta é obrigatório.");
        }
        nome = nome.trim().toUpperCase();
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String nome() {
        return nome;
    }

    /** Devolve outra instância com o ID retornado pelo INSERT. */
    public TipoConta comId(Long novoId) {
        return new TipoConta(novoId, nome);
    }
}
