package br.uel.mdd.model;

public class Classes {

    private Long id;

    private String nome;

    private String tipoImagem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoImagem() {
        return tipoImagem;
    }

    public void setTipoImagem(String tipoImagem) {
        this.tipoImagem = tipoImagem;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipoImagem='" + tipoImagem + '\'' +
                '}';
    }
}
