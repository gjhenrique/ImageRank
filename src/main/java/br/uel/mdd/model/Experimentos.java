package br.uel.mdd.model;

import java.util.Arrays;

public class Experimentos {

    private Long id;

    private Integer nivel;

    private Integer[] coeficiente;

    private Byte[] imagem;

    private String nomeImagem;

    private Integer classeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer[] getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(Integer[] coeficiente) {
        this.coeficiente = coeficiente;
    }

    public Byte[] getImagem() {
        return imagem;
    }

    public void setImagem(Byte[] imagem) {
        this.imagem = imagem;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public Integer getClasseId() {
        return classeId;
    }

    public void setClasseId(Integer classeId) {
        this.classeId = classeId;
    }

    @Override
    public String toString() {
        return "Experimentos{" +
                "id=" + id +
                ", nivel=" + nivel +
                ", coeficiente=" + Arrays.toString(coeficiente) +
                ", imagem=" + Arrays.toString(imagem) +
                ", nomeImagem='" + nomeImagem + '\'' +
                ", classeId=" + classeId +
                '}';
    }
}
