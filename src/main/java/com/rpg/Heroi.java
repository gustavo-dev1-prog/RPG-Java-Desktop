package com.rpg;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Heroi {

    public Group grupo = new Group();
    public ImageView sprite;
    public ImageView espada;
    public Rectangle hitboxAtaque;
    public double x, y, vy;
    public boolean esquerda, direita, noChao;
    private static final double GRAVIDADE = 900;
    private static final double VELOCIDADE = 200;
    private boolean viradoDireita = true;
    private boolean andando = false;
    private double tempoAnim = 0;
    private double balanco = 0;
    private boolean atacando = false;
    private double tempoAtaque = 0;
    private double offsetAtaqueX = 0;
    private static final double DURACAO_ATAQUE = 0.25;
    private static final double MAO_DIREITA_X = 48;
    private static final double MAO_Y = 98;
    public static final boolean DEBUG = false;

    public Heroi() {
        sprite = new ImageView(new Image(new File("sprites/guerreiro.png").toURI().toString()));
        sprite.setFitWidth(100);
        sprite.setFitHeight(150);

        espada = new ImageView(new Image(new File("sprites/espada.png").toURI().toString()));
        espada.setFitWidth(40);
        espada.setPreserveRatio(true);

        hitboxAtaque = new Rectangle(30, 12);
        hitboxAtaque.setFill(Color.color(1, 0, 0, 0.35));
        hitboxAtaque.setVisible(DEBUG);

        grupo.getChildren().addAll(sprite, espada, hitboxAtaque);
    }

    public void setPosicao(double x, double chaoY) {
        this.x = x;
        this.y = chaoY;
        vy = 0;
        noChao = true;
        atualizarVisual();
    }

    public boolean isViradoDireita() {
        return viradoDireita;
    }

    public void atacar() {
        if (!atacando) {
            atacando = true;
            tempoAtaque = 0;
        }
    }

    public boolean estaAtacando() {
        return atacando && tempoAtaque > 0.05 && tempoAtaque < 0.22;
    }
    
    public boolean estaAnimandoAtaque() {
        return atacando;
    }

    public void atualizar(double delta, double chaoY) {

        andando = esquerda || direita;
        if (esquerda) {
            x -= VELOCIDADE * delta;
            viradoDireita = false;
        }
        if (direita) {
            x += VELOCIDADE * delta;
            viradoDireita = true;
        }

        if (!noChao) vy += GRAVIDADE * delta;
        y += vy * delta;

        if (y >= chaoY) {
            y = chaoY;
            vy = 0;
            noChao = true;
        }

        if (atacando) {
            tempoAtaque += delta;
            offsetAtaqueX = Math.sin((tempoAtaque / DURACAO_ATAQUE) * Math.PI) * 30;

            if (tempoAtaque >= DURACAO_ATAQUE) {
                atacando = false;
                offsetAtaqueX = 0;
            }
        }

        atualizarVisual();
    }
    private void atualizarVisual() {

        grupo.setLayoutX(x);
        grupo.setLayoutY(y - sprite.getFitHeight());

        sprite.setScaleX(viradoDireita ? 1 : -1);
        sprite.setTranslateY(0);
        sprite.setRotate(0);

        double larguraSprite = sprite.getFitWidth();

        double maoBase = MAO_DIREITA_X;

        double maoX = viradoDireita
                ? maoBase + offsetAtaqueX
                : larguraSprite - maoBase - espada.getFitWidth() - offsetAtaqueX;
        double espadaX = maoX;
        double espadaY = MAO_Y;

        espada.setScaleX(viradoDireita ? 1 : -1);
        espada.setRotate(atacando ? (viradoDireita ? 20 : -20) : 0);
        espada.setX(espadaX);
        espada.setY(espadaY);
        double larguraHitbox = 28;
        double alturaHitbox = 8;
        hitboxAtaque.setWidth(larguraHitbox);
        hitboxAtaque.setHeight(alturaHitbox);
        double ajusteDireita = 6;
        double ajusteEsquerda = - 30;
        double ajusteVertical = 6;
        if (viradoDireita) {
            hitboxAtaque.setX(espadaX + ajusteDireita);
        } else {
            hitboxAtaque.setX(espadaX - larguraHitbox - ajusteEsquerda);
        }

        hitboxAtaque.setY(espadaY + ajusteVertical);

        hitboxAtaque.setVisible(DEBUG);
        }
     }