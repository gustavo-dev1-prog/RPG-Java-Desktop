package com.rpg.view;

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.rpg.Heroi;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MundoFrame extends JFrame {

    private final JFXPanel fxPanel = new JFXPanel();
    private String heroiSelecionado;
    private Pane mundoPane;
    private Scene scene;
    private static final double CHAO_Y = 480;
    private long ultimoFrame = 0;
    private boolean bloqueioInput = false;
    private Heroi heroi;
    private ImageView espada;
    private boolean atacando;
    private double tempoAtaque = 0;

    public MundoFrame(String heroiSelecionado) {
        this.heroiSelecionado = heroiSelecionado;
        setTitle("Adventure World - " + heroiSelecionado);
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        add(fxPanel, BorderLayout.CENTER);
        Platform.runLater(() -> fxPanel.setScene(criarCena()));
        setVisible(true);
    }

    private Scene criarCena() {
        mundoPane = new Pane();
        mundoPane.setPrefSize(900, 700);
        mundoPane.setStyle("-fx-background-color: #87CEEB;");
        mundoPane.setFocusTraversable(true);

        criarFundo();
        criarHeroi();
        criarHUD();
        criarBotoes();
        scene = new Scene(mundoPane, 900, 700);  
        mundoPane.setFocusTraversable(true);
        Platform.runLater(() -> mundoPane.requestFocus());
        configurarTeclado(scene);
        iniciarGameLoop();
        Platform.runLater(() -> mundoPane.requestFocus());
        
        mundoPane.setOnMousePressed(e -> {
            System.out.println("Mouse clicado: " + e.getButton());

            if (e.getButton() == javafx.scene.input.MouseButton.SECONDARY) {
                iniciarAtaque();
            }
        });
        
        return scene;
    }


	private void iniciarAtaque() {
		System.out.println("ATAQUE");
		if(atacando) return;
		atacando = true;
		tempoAtaque = 0;
	}

	// 🌍 FUNDO DO MUNDO
    private void criarFundo() {
        Rectangle grama = new Rectangle();
        grama.setFill(Color.DARKGREEN);
        grama.setY(CHAO_Y);
        grama.widthProperty().bind(mundoPane.widthProperty());
        grama.heightProperty().bind(mundoPane.heightProperty().subtract(CHAO_Y));
        mundoPane.getChildren().add(grama);
    }

    // 🧙 CRIA HERÓI
    private void criarHeroi() {
        heroi = new Heroi(heroiSelecionado);
        heroi.x = 400;
        heroi.y = CHAO_Y - 150;
        heroi.noChao = true;
        heroi.sprite.setX(heroi.x);
        heroi.sprite.setY(heroi.y);
        File imgEspada = new File("sprites/espada.png");
        espada = new ImageView(new Image(imgEspada.toURI().toString(), 80, 80, true, true));
        espada.setX(400);
        espada.setY(300);
        mundoPane.getChildren().add(espada);
        mundoPane.getChildren().add(heroi.sprite);
       
        
    }
  

    // 🧠 HUD
    private void criarHUD() {
        Label hud = new Label( "Herói: " + heroiSelecionado + "\nVida: " + getVida() + "\nForça: " + getForca());
        hud.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        hud.setTextFill(Color.WHITE);
        hud.setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-padding: 10;");
        hud.setLayoutX(20);
        hud.setLayoutY(20);
        mundoPane.getChildren().add(hud);
    }

    // ⚙ BOTÃO MENU
    private void criarBotoes() {
        Button menu = new Button("⚙ Menu");
        menu.setLayoutX(780);
        menu.setLayoutY(20);
        menu.setOnAction(e -> abrirMenu());
        mundoPane.getChildren().add(menu);
    }

    // 🎮 TECLADO
    private void configurarTeclado(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (bloqueioInput) return;
            switch (e.getCode()) {
            case A, LEFT -> heroi.esquerda = true;
            case D, RIGHT -> heroi.direita = true;
            case SPACE -> {
            if (heroi.noChao) {
            heroi.vy = -450;
            heroi.noChao = false;
                    }
                }
            case TAB -> abrirInventario();
            case ESCAPE -> abrirMenu();
            }
        });

        scene.setOnKeyReleased(e -> {
        switch (e.getCode()) {
        case A, LEFT -> heroi.esquerda = false;
        case D, RIGHT -> heroi.direita = false;
            }
        });
    }

    // 🧍 MOVIMENTO
    private void atualizarMovimento(double delta) {
        if (!atacando) {
            if (heroi.esquerda) heroi.x -= 200 * delta;
            if (heroi.direita) heroi.x += 200 * delta;
        }
        heroi.vy += 900 * delta;
        heroi.y += heroi.vy * delta;

        if (heroi.y >= CHAO_Y - 150) {
            heroi.y = CHAO_Y - 150;
            heroi.vy = 0;
            heroi.noChao = true;
        }

        heroi.sprite.setX(heroi.x);
        heroi.sprite.setY(heroi.y);
        if (espada != null) {
            espada.setX(heroi.x + 60);
            espada.setY(heroi.y + 40);
        }
    }

    
    private void atualizarAtaque(double delta) {
        if (!atacando || espada == null) return;
        tempoAtaque += delta;
        double duracao = 0.25;
        double t = tempoAtaque / duracao;
        if (t >= 1) {
            atacando = false;
            tempoAtaque = 0;
            espada.setRotate(0);
            return;
        }
        double angulo = -100 + (200 * t);
        espada.setRotate(angulo);
    }
    

    // 🔁 GAME LOOP
    private void iniciarGameLoop() {
        new AnimationTimer() {
        @Override
        
     public void handle(long agora) {
       if (ultimoFrame == 0) {
       ultimoFrame = agora;
       return;
                }
       double delta = (agora - ultimoFrame) / 1_000_000_000.0;
       ultimoFrame = agora;
       atualizarMovimento(delta);
       atualizarAtaque(delta);
            }
        }.start();
    }

    // 🎒 INVENTÁRIO
    private void abrirInventario() {
        JOptionPane.showMessageDialog(this, "Inventário\n• Poção\n• Espada\n• Ouro", "Inventário",JOptionPane.INFORMATION_MESSAGE);
        Platform.runLater(() -> mundoPane.requestFocus());
    }

    // ⚙ MENU
    private void abrirMenu() {
        int op = JOptionPane.showConfirmDialog(this, "Deseja voltar ao menu?", "Menu", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
        dispose();
        SwingUtilities.invokeLater(SeleçãoHeróiFrame::new);
        } else {
        Platform.runLater(() -> mundoPane.requestFocus());
        }
    }

    // 📊 STATS
    private int getVida() {
        return switch (heroiSelecionado.toLowerCase()) {
            case "guerreiro" -> 150;
            default -> 100;
        };
    }

    private int getForca() {
        return switch (heroiSelecionado.toLowerCase()) {
            case "guerreiro" -> 25;
            default -> 15;
        };
    }
}