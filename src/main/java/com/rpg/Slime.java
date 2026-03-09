package com.rpg;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Slime extends Enemy {
    private double baseX;
    private double y;
    private double tempo;
    private Rectangle vidaBg;
    private Rectangle vida;
    private Rectangle hitbox;
    private double knockback = 0;
    private boolean morto = false;
    private double tempoDano = 0;
    private boolean piscando = false;
    public static final double ALTURA_SPRITE = 50;

    public Slime() {
        nome = "Slime";
        vidaMaxima = 30;
        vidaAtual = 30;
        sprite = new ImageView(
        new Image(new File("sprites/slime.png").toURI().toString())
        );
        sprite.setFitWidth(60);
        sprite.setPreserveRatio(true);
        vidaBg = new Rectangle(60, 6, Color.DARKRED);
        vida = new Rectangle(60, 6, Color.LIMEGREEN);
        hitbox = new Rectangle(45, 35);
        hitbox.setFill(Color.TRANSPARENT);
        hitbox.setStroke(Color.RED);
        hitbox.setVisible(false);
    }
    public void setPosicao(double x, double chaoY) {
        this.baseX = x;
        double alturaReal = sprite.getBoundsInLocal().getHeight();
        this.y = chaoY - alturaReal + -14;
        sprite.setX(baseX);
        sprite.setY(y);
        atualizarHitbox();
        atualizarVida();
    }
    public void atualizar(double delta) {
        if (morto) return;
        tempo += delta;
        if (Math.abs(knockback) > 0.5) {
            sprite.setX(sprite.getX() + knockback);
            knockback *= 0.85;
            baseX = sprite.getX();
        } else {
            knockback = 0;
            sprite.setX(baseX + Math.sin(tempo * 2) * 20);
        }
        
        double larguraTela = sprite.getScene().getWidth();
        double larguraSlime = sprite.getFitWidth();

        if (sprite.getX() < 0) {
            sprite.setX(0);
        }

        if (sprite.getX() + larguraSlime > larguraTela) {
            sprite.setX(larguraTela - larguraSlime);
        }
        
        if (piscando) {
            tempoDano += delta;
            sprite.setOpacity(Math.sin(tempoDano * 40) > 0 ? 0.3 : 1);
            if (tempoDano > 0.3) {
                piscando = false;
                tempoDano = 0;
                sprite.setOpacity(1);
            }
        }
        atualizarHitbox();
        atualizarVida();
    }
    public void tomarDano(int dano) {
        if (morto) return;

        vidaAtual -= dano;
        if (vidaAtual < 0) vidaAtual = 0;

        piscando = true;
        tempoDano = 0;

        if (vidaAtual == 0) {
            morrer();
        }
    }
    private void morrer() {
        morto = true;
        sprite.setVisible(false);
        vida.setVisible(false);
        vidaBg.setVisible(false);
        hitbox.setVisible(false);
    }
    public void aplicarKnockback(boolean direita) {
        knockback = direita ? 15 : -15;
    }
    private void atualizarVida() {
        double proporcao = (double) vidaAtual / vidaMaxima;
        vida.setWidth(vidaBg.getWidth() * proporcao);
        double x = sprite.getX();
        double y = sprite.getY() - 10;
        vidaBg.setX(x);
        vida.setX(x);
        vidaBg.setY(y);
        vida.setY(y);
    }
    private void atualizarHitbox() {
        hitbox.setX(sprite.getX() + 7);
        hitbox.setY(sprite.getY() + 10);
    }
    public ImageView getSprite() {
        return sprite;
    }
    public Rectangle getVidaBg() {
        return vidaBg;
    }
    public Rectangle getVida() {
        return vida;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public boolean estaMorto() {
        return morto;
    }
}