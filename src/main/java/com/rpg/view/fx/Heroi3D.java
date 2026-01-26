package com.rpg.view.fx;

import javafx.scene.Group;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Heroi3D extends Group {

    private String classe;

    public Heroi3D(String classe) {
        this.classe = classe;
        criarModelo();
    }

    private void criarModelo() {
        Box corpo = new Box(100, 180, 60);

        PhongMaterial material = new PhongMaterial();

        switch (classe) {
            case "Guerreiro":
                material.setDiffuseColor(Color.DARKRED);
                break;
            case "Mago":
                material.setDiffuseColor(Color.DARKBLUE);
                break;
            case "Arqueiro":
                material.setDiffuseColor(Color.DARKGREEN);
                break;
            default:
                material.setDiffuseColor(Color.GRAY);
        }

        corpo.setMaterial(material);
        this.getChildren().add(corpo);
    }
}