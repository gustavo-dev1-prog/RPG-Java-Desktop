package com.rpg.view;

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.rpg.Heroi;
import com.rpg.Slime;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MundoFrame extends JFrame {

    private final JFXPanel fxPanel = new JFXPanel();
    private Pane mundoPane;
    private Scene scene;
    private double CHAO_Y;
    private static final double OFFSET_CHAO = 16;
    private long ultimoFrame;
    private Heroi heroi;
    private Slime slime;
    private boolean ataqueAplicado = false;

    public MundoFrame(String heroiSelecionado) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(fxPanel, BorderLayout.CENTER);
        Platform.runLater(() -> fxPanel.setScene(criarCena(heroiSelecionado)));

        setVisible(true);
    }

    private Scene criarCena(String heroiSelecionado) {
        mundoPane = new Pane();
        scene = new Scene(mundoPane);

        criarFundo();

        scene.heightProperty().addListener((o, a, n) -> definirChao());

        Platform.runLater(() -> {
            definirChao();
            criarHeroi(heroiSelecionado); 
            criarSlime();
            criarBotoes();
            configurarTeclado();
            configurarMouse();
            iniciarGameLoop();
            mundoPane.requestFocus();
        });

        return scene;
    }

    private void criarFundo() {
        Rectangle grama = new Rectangle();
        grama.setFill(Color.DARKGREEN);
        grama.widthProperty().bind(mundoPane.widthProperty());
        grama.heightProperty().bind(mundoPane.heightProperty().multiply(0.25));
        grama.yProperty().bind(mundoPane.heightProperty().multiply(0.75));
        mundoPane.getChildren().add(grama);
    }

    private void criarHeroi(String nome) {
        heroi = new Heroi();
        heroi.setPosicao(400,  CHAO_Y);
        mundoPane.getChildren().add(heroi.grupo);
    }

    private void criarSlime() {
        slime = new Slime();
        slime.getVidaBg().setWidth(60);
        slime.getVida().setWidth(60);
        slime.getVidaBg().setHeight(6);
        slime.getVida().setHeight(6);

        mundoPane.getChildren().addAll(
            slime.getSprite(),
            slime.getVidaBg(),
            slime.getVida(),
            slime.getHitbox()
        );
    }

    private void verificarAtaque() {
        if (!heroi.estaAnimandoAtaque()) {
            ataqueAplicado = false;
            return;
        }

        if (ataqueAplicado) return;

        var hitHeroi = heroi.hitboxAtaque.localToScene(
                heroi.hitboxAtaque.getBoundsInLocal()
        );

        var hitSlime = slime.getHitbox().localToScene(
                slime.getHitbox().getBoundsInLocal()
        );

        if (hitHeroi.intersects(hitSlime)) {
            slime.tomarDano(10);
            slime.aplicarKnockback(heroi.isViradoDireita());
            ataqueAplicado = true;
        }
    }

    private void definirChao() {
        CHAO_Y = scene.getHeight() * 0.75 + OFFSET_CHAO;

        if (heroi != null) {
            heroi.setPosicao(100, CHAO_Y); 
        }

        if (slime != null) {
            double slimeX = scene.getWidth() - 200; 
            slime.setPosicao(slimeX, CHAO_Y);
        }
    }
    
    private void configurarTeclado() {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A -> heroi.esquerda = true;
                case D -> heroi.direita = true;
                case SPACE -> {
                    if (heroi.noChao) {
                        heroi.vy = -450;
                        heroi.noChao = false;
                    }
                }
                case ESCAPE -> abrirMenu();
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.A) heroi.esquerda = false;
            if (e.getCode() == javafx.scene.input.KeyCode.D) heroi.direita = false;
        });
    }

    private void configurarMouse() {
        mundoPane.setOnMousePressed(e -> {
            if (e.getButton() == javafx.scene.input.MouseButton.SECONDARY) {
                heroi.atacar();
            }
        });
    }

    private void iniciarGameLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long agora) {
                if (ultimoFrame == 0) {
                    ultimoFrame = agora;
                    return;
                }

                double delta = (agora - ultimoFrame) / 1e9;
                ultimoFrame = agora;
                heroi.atualizar(delta, CHAO_Y);
                if (!slime.estaMorto()) {
                    slime.atualizar(delta);
                    atualizarBarraSlime();
                    verificarAtaque();
                }
            }
        }.start();
    }

    private void atualizarBarraSlime() {
    	double barraX = slime.getSprite().getX() + (slime.getSprite().getFitWidth() - slime.getVidaBg().getWidth()) / 2;
    	double barraY = slime.getSprite().getY() - 18;
    	slime.getVidaBg().setX(barraX);
    	slime.getVidaBg().setY(barraY);
    	slime.getVida().setX(barraX);
    	slime.getVida().setY(barraY);
    }

    private void criarBotoes() {
        Button menu = new Button("Menu");
        menu.setLayoutX(20);
        menu.setLayoutY(20);
        menu.setFocusTraversable(false);
        menu.setOnAction(e -> abrirMenu());
        mundoPane.getChildren().add(menu);
    }

    private void abrirMenu() {
       String[] opcoes = {
    		   "Continuar",
    		   "Selecionar Herói",
    		   "Sair do Jogo",
             };
       int escolha = JOptionPane.showOptionDialog( this, "Meu do Jogo", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
       

       switch (escolha) {

           case 0 -> {
           }
           case 1 -> {
               voltarSelecaoHeroi();
           }
           case 2 -> {
               System.exit(0);
           }
       }
   }

    private void voltarSelecaoHeroi() {
    	dispose();
    	new SeleçãoHeróiFrame();
    }
}

        
        
