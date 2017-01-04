package br.com.guaxinim.java8;

class Usuario {
    private String nome;
    private int pontos;
    private boolean moderador;

    public Usuario(String nome, int pontos) {
        this.pontos = pontos;
        this.nome = nome;
    }

    public Usuario(String nome) {
        this.nome = nome;
    }

    public boolean isModerador() {
        return moderador;
    }

    public void tornaModerador() {
        this.moderador = true;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", pontos=" + pontos +
                '}';
    }
}
