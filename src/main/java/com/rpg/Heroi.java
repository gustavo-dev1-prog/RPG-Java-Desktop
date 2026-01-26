package com.rpg;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class Heroi {

    public ImageView sprite;

    public double x, y;
    public double vx, vy;
    public boolean esquerda, direita, noChao;

    public Heroi(String nome) {
        try {
            File img = new File("sprites/" + nome.toLowerCase() + ".png");
            sprite = new ImageView(new Image(img.toURI().toString(), 195, 175, false, true));
        } catch (Exception e) {
            sprite = new ImageView();
        }
    }
}


