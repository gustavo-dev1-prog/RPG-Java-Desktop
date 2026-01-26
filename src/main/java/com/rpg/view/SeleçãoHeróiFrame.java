package com.rpg.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.SnapshotParameters;

public class SeleçãoHeróiFrame extends JFrame {
    
    private JFXPanel fxPanel = new JFXPanel();
    private String heroiSelecionado;
    private AnimationTimer animacaoRotacao;
    private static final int LARGURA_JANELA = 1200;
    private static final int ALTURA_JANELA = 700;
    private static final int LARGURA_IMAGEM_HEROI = 220;
    private static final int ALTURA_IMAGEM_HEROI = 280;
    private static final int LARGURA_HEROI_BOX = 350;
    private static final int ALTURA_HEROI_BOX = 500;
    private static final double FATOR_ZOOM = 2.0; 
    
    public SeleçãoHeróiFrame() { 
        setTitle("Seleção de Herói - Adventure World");
        
        setSize(LARGURA_JANELA, ALTURA_JANELA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        fxPanel.setPreferredSize(new Dimension(LARGURA_JANELA, ALTURA_JANELA));
        add(fxPanel, BorderLayout.CENTER);
        
        iniciarJavaFX();
        setVisible(true);
    }
    
    private void iniciarJavaFX() {
        Platform.runLater(() -> {
            fxPanel.setScene(criarCena());
        });
    }
    
    private Scene criarCena() {
        System.out.println("=== INICIANDO TELA DE SELEÇÃO DE HERÓIS ===");
        System.out.println("Procurando imagens na pasta 'sprites/'...");
        
        verificarImagens();
        
        BorderPane borderPrincipal = new BorderPane();
        borderPrincipal.setStyle("-fx-background-color: white;");
        
        Label titulo = new Label("ESCOLHA SEU HERÓI");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titulo.setTextFill(Color.BLACK);
        
        VBox topoBox = new VBox(titulo);
        topoBox.setAlignment(Pos.CENTER);
        topoBox.setPadding(new javafx.geometry.Insets(20, 0, 30, 0));
        borderPrincipal.setTop(topoBox);
        
        HBox containerHerois = new HBox(40);
        containerHerois.setAlignment(Pos.CENTER);
        
        VBox guerreiroBox = criarHeroiBox("Guerreiro", Color.CRIMSON, 150, 25);
        guerreiroBox.setPrefSize(LARGURA_HEROI_BOX, ALTURA_HEROI_BOX);
        containerHerois.getChildren().addAll(guerreiroBox);
        borderPrincipal.setCenter(containerHerois);
        
        Button btnIniciar = new Button("INICIAR JOGO");
        btnIniciar.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        btnIniciar.setStyle("-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                          "-fx-text-fill: white; " +
                          "-fx-background-radius: 8; " +
                          "-fx-padding: 12 30; " +
                          "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 8, 0, 0, 3);");
        btnIniciar.setMinSize(250, 60);
        btnIniciar.setOnAction(e -> iniciarJogo());
        
        VBox bottomBox = new VBox(btnIniciar);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new javafx.geometry.Insets(0, 0, 30, 0));
        borderPrincipal.setBottom(bottomBox);
        
        Scene scene = new Scene(borderPrincipal, LARGURA_JANELA, ALTURA_JANELA);
        
        iniciarAnimacaoSuave(guerreiroBox);
        
        return scene;
    }
    
    private void verificarImagens() {
        String[] classes = {"guerreiro"};
        String[] extensoes = {".png", ".jpg", ".jpeg", ".gif"};
        
        for (String classe : classes) {
            boolean encontrada = false;
            
            for (String extensao : extensoes) {
                File file = new File("sprites/" + classe + extensao);
                if (file.exists()) {
                    System.out.println("✓ Imagem encontrada: " + file.getAbsolutePath());
                    System.out.println("  Tamanho do arquivo: " + file.length() + " bytes");
                    encontrada = true;
                    break;
                }
            }
            
            if (!encontrada) {
                System.out.println("✗ Imagem NÃO encontrada: sprites/" + classe + ".png");
                System.out.println("  Usando fallback gráfico");
            }
        }
        System.out.println("==========================================");
    }
    
    private VBox criarHeroiBox(String classe, Color corDestaque, int vida, int forca) {
        System.out.println("Criando box para: " + classe);
        
        ImageView heroiImageView = carregarImagemHeroi(classe);
        
        StackPane imagemContainer = new StackPane(heroiImageView);
        imagemContainer.setMinSize(LARGURA_IMAGEM_HEROI, ALTURA_IMAGEM_HEROI);
        imagemContainer.setMaxSize(LARGURA_IMAGEM_HEROI, ALTURA_IMAGEM_HEROI);
        imagemContainer.setStyle(
            "-fx-background-color: #FAFAFA; " +
            "-fx-border-color: " + toHex(corDestaque) + "; " +
            "-fx-border-width: 3; " +
            "-fx-border-radius: 10; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 3);"
        );
        
        VBox statusBox = new VBox(8);
        statusBox.setAlignment(Pos.CENTER);
        
        Label labelVida = new Label("❤ VIDA: " + vida);
        labelVida.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        labelVida.setTextFill(Color.DARKRED);
        
        Label labelForca = new Label("⚔ FORÇA: " + forca);
        labelForca.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        labelForca.setTextFill(Color.DARKBLUE);
        
        statusBox.getChildren().addAll(labelVida, labelForca);
        
        Label nomeClasse = new Label(classe.toUpperCase());
        nomeClasse.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        nomeClasse.setTextFill(corDestaque);
        
        Button btnSelecionar = new Button("SELECIONAR");
        btnSelecionar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnSelecionar.setMinSize(140, 40);
        
        String corBotao = getCorBotao(classe);
        
        btnSelecionar.setStyle("-fx-background-color: " + corBotao + "; " +
                              "-fx-text-fill: white; " +
                              "-fx-background-radius: 6; " +
                              "-fx-padding: 8 20; " +
                              "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        btnSelecionar.setOnAction(e -> selecionarHeroi(classe, corDestaque));
        
        VBox heroiBox = new VBox(15);
        heroiBox.setAlignment(Pos.CENTER);
        heroiBox.getChildren().addAll(
            nomeClasse,
            statusBox,
            imagemContainer,
            btnSelecionar
        );
        
        return heroiBox;
    }
    
    private ImageView carregarImagemHeroi(String classe) {
        ImageView imageView = new ImageView();
        
        try {
            String[] extensoes = {".png", ".jpg", ".jpeg", ".gif"};
            File arquivoImagem = null;
            
            for (String extensao : extensoes) {
                File file = new File("sprites/" + classe.toLowerCase() + extensao);
                if (file.exists()) {
                    arquivoImagem = file;
                    break;
                }
            }
            
            if (arquivoImagem != null && arquivoImagem.exists()) {
                System.out.println("Carregando imagem: " + arquivoImagem.getAbsolutePath());
                
                Image imagem = new Image(
                    arquivoImagem.toURI().toString(),
                    LARGURA_IMAGEM_HEROI * FATOR_ZOOM,   
                    ALTURA_IMAGEM_HEROI * FATOR_ZOOM,    
                    true,    
                    true,   
                    false    
                );
                
                imageView.setImage(imagem);
                imageView.setFitWidth(LARGURA_IMAGEM_HEROI);
                imageView.setFitHeight(ALTURA_IMAGEM_HEROI);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true); 
                
                imageView.translateXProperty().addListener((obs, oldVal, newVal) -> {
                    
                });
                
                System.out.println("✓ Imagem carregada com sucesso!");
                
            } else {
                System.out.println("Imagem não encontrada, criando fallback para: " + classe);
                imageView = criarImagemFallback(classe);
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem de " + classe + ": " + e.getMessage());
            imageView = criarImagemFallback(classe);
        }
        
        return imageView;
    }
    
    private ImageView criarImagemFallback(String classe) {
        Canvas canvas = new Canvas(LARGURA_IMAGEM_HEROI, ALTURA_IMAGEM_HEROI);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Color corFundo = Color.LIGHTGRAY;
        Color corPersonagem = getCorClasse(classe);
        
        gc.setFill(corFundo);
        gc.fillRect(0, 0, LARGURA_IMAGEM_HEROI, ALTURA_IMAGEM_HEROI);
        
        int centroX = LARGURA_IMAGEM_HEROI / 2;
        int centroY = ALTURA_IMAGEM_HEROI / 2;
        
        gc.setFill(corPersonagem);
        gc.fillOval(centroX - 30, centroY - 80, 60, 60);
        gc.fillRect(centroX - 25, centroY - 20, 50, 70);
        
        gc.fillRect(centroX - 50, centroY - 20, 25, 40);
        gc.fillRect(centroX + 25, centroY - 20, 25, 40); 
        gc.fillRect(centroX - 30, centroY + 50, 25, 50); 
        gc.fillRect(centroX + 5, centroY + 50, 25, 50);  
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gc.fillText(classe.toUpperCase(), centroX - 40, centroY - 100);
        
        String icone = getIconeClasse(classe);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        gc.fillText(icone, centroX - 15, centroY + 10);
        
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(2);
        gc.strokeRect(1, 1, LARGURA_IMAGEM_HEROI - 2, ALTURA_IMAGEM_HEROI - 2);
        
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image imagem = canvas.snapshot(params, null);
        
        ImageView imageView = new ImageView(imagem);
        imageView.setFitWidth(LARGURA_IMAGEM_HEROI);
        imageView.setFitHeight(ALTURA_IMAGEM_HEROI);
        
        return imageView;
    }
    
    private Color getCorClasse(String classe) {
        switch(classe.toLowerCase()) {
            case "guerreiro": return Color.CRIMSON;
            default: return Color.GRAY;
        }
    }
    
    private String getCorBotao(String classe) {
        switch(classe.toLowerCase()) {
            case "guerreiro": return "#C62828";
            default: return "#666666";
        }
    }
    
    private String getIconeClasse(String classe) {
        switch(classe.toLowerCase()) {
            case "guerreiro": return "⚔";
            default: return "?";
        }
    }
    
    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
            (int)(color.getRed() * 255),
            (int)(color.getGreen() * 255),
            (int)(color.getBlue() * 255));
    }
    
    private void selecionarHeroi(String classe, Color cor) {
        heroiSelecionado = classe;
        
        Platform.runLater(() -> {
            JOptionPane.showMessageDialog(this,
                "✅ Herói selecionado: " + classe.toUpperCase() + "\n\n" +
                "• Vida: " + getVida(classe) + "\n" +
                "• Força: " + getForca(classe) + "\n\n" +
                "Clique em 'INICIAR JOGO' para começar sua aventura!",
                "Herói Selecionado",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        System.out.println("Herói selecionado: " + classe);
    }
    
    private int getVida(String classe) {
        switch(classe.toLowerCase()) {
            case "guerreiro": return 150;
            default: return 100;
        }
    }
    
    private int getForca(String classe) {
        switch(classe.toLowerCase()) {
            case "guerreiro": return 25;
            default: return 15;
        }
    }
    
    private void iniciarAnimacaoSuave(VBox... heroisBoxes) {
        animacaoRotacao = new AnimationTimer() {
            private long ultimoTempo = 0;
            private double tempo = 0;
            
            @Override
            public void handle(long agora) {
                if (ultimoTempo == 0) {
                    ultimoTempo = agora;
                    return;
                }
                
                double deltaTempo = (agora - ultimoTempo) / 1_000_000_000.0;
                ultimoTempo = agora;
                
                tempo += deltaTempo;
                
                for (VBox box : heroisBoxes) {
                    double flutuacao = Math.sin(tempo * 1.5) * 5;
                    box.setTranslateY(flutuacao);
                    
                    for (javafx.scene.Node node : box.getChildren()) {
                        if (node instanceof Button) {
                            Button btn = (Button) node;
                            double brilho = 0.1 + (Math.sin(tempo * 2) * 0.05);
                            String estiloAtual = btn.getStyle();
                            if (estiloAtual.contains("-fx-background-color:")) {
                            }
                        }
                    }
                }
            }
        };
        
        animacaoRotacao.start();
    }
    
    private void iniciarJogo() {
        if (heroiSelecionado == null) {
            Platform.runLater(() -> {
                JOptionPane.showMessageDialog(this,
                    "⚠️ ATENÇÃO!\n\n" +
                    "Por favor, selecione um herói antes de iniciar o jogo.\n" +
                    "Clique em 'SELECIONAR' abaixo do herói desejado.",
                    "Seleção Necessária",
                    JOptionPane.WARNING_MESSAGE);
            });
            return;
        }
        
        if (animacaoRotacao != null) {
            animacaoRotacao.stop();
        }
        
        System.out.println("=== INICIANDO JOGO ===");
        System.out.println("Herói: " + heroiSelecionado);
        System.out.println("Vida: " + getVida(heroiSelecionado));
        System.out.println("Força: " + getForca(heroiSelecionado));
        
        Platform.runLater(() -> {
            int resposta = JOptionPane.showConfirmDialog(this,
                "🎮 INICIAR JOGO 🎮\n\n" +
                "Herói: " + heroiSelecionado.toUpperCase() + "\n" +
                "Vida: " + getVida(heroiSelecionado) + "\n" +
                "Força: " + getForca(heroiSelecionado) + "\n\n" +
                "Deseja começar sua aventura?",
                "Confirmar Início",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (resposta == JOptionPane.YES_OPTION) {
                dispose();
                new MundoFrame(heroiSelecionado);
       
                System.out.println("Jogo iniciado! Próxima tela em desenvolvimento...");
                
                JOptionPane.showMessageDialog(null,
                    "🎉 JOGO INICIADO! 🎉\n\n" +
                    "Bem-vindo ao mundo de RPG!\n" +
                    "Herói: " + heroiSelecionado.toUpperCase() + "\n\n" +
                    "Implemente a próxima tela do jogo aqui.",
                    "Aventura Iniciada",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    public static void main(String[] args) {
        File pastaSprites = new File("sprites");
        if (!pastaSprites.exists()) {
            System.out.println("AVISO: Pasta 'sprites/' não encontrada!");
            System.out.println("Criando pasta 'sprites/'...");
            pastaSprites.mkdir();
            System.out.println("Coloque suas imagens na pasta 'sprites/' com os nomes:");
            System.out.println("  - guerreiro.png");
        }
        
        SwingUtilities.invokeLater(() -> {
            new SeleçãoHeróiFrame();
        });
    }
}