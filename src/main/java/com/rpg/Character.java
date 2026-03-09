package com.rpg;

public abstract class Character {

    protected String nome;
    protected int vidaAtual;
    protected int vidaMaxima;
    protected int forca;

    public boolean estaVivo() {
        return vidaAtual > 0;
    }

    public void receberDano(int dano) {
        vidaAtual -= dano;
        if (vidaAtual < 0) vidaAtual = 0;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getForca() {
        return forca;
    }

    public String getNome() {
        return nome;
    }
}
