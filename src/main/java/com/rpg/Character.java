package com.rpg;

public abstract class Character {

    protected String nome;
    protected int vida;
    protected int forca;

    public boolean estaVivo() {
        return vida > 0;
    }

    public void receberDano(int dano) {
        if (dano < 0) return;

        vida -= dano;
        if (vida < 0) {
            vida = 0;
        }
    }

    public void atacar(Character alvo) {
        if (alvo != null && estaVivo()) {
            alvo.receberDano(forca);
        }
    }

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getForca() {
        return forca;
    }
}
