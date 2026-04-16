RPG Game Java - Desktop 2D com JavaFX + Swing + MySQL
Jogo desktop estilo medieval desenvolvido em Java para aplicar Orientação a Objetos, integração com banco de dados e arquitetura em camadas.

Status: Em desenvolvimento.

🎮 Demo

markdown   ![Demo do RPG](rpg_demo.gif)

⚔️ Features Implementadas

-Autenticação de usuário: Tela de login em Swing com validação no MySQL via JDBC;

-Arquitetura Híbrida: Interface de login/seleção em Swing + Game loop renderizado em JavaFX;

-POO Aplicada: Hierarquia Character → Enemy → Slime usando classe abstrata, herança e polimorfismo;

-Sistema de Combate: Movimentação com gravidade, pulo, ataque com hitbox, knockback e IA básica de inimigo;

-Persistência: DAO usuarioDAO conecta com MySQL usando db.properties externo para credenciais;

-UI Dinâmica: Barras de vida, inventário e animações com AnimationTimer e Timeline do JavaFX;

-Boas Práticas: Separação em pacotes com.rpg, com.rpg.dao, com.rpg.view, com.rpg.view.components.

🛠️ Stack Técnica Real do Projeto

-Linguagem: Java;

-Interface: JavaFX para game loop/gráficos + Swing para telas de Login e Seleção;

-Banco de Dados: MySQL com JDBC, PreparedStatement para evitar SQL Injection;

-Game Dev: AnimationTimer, ImageView, colisão com Rectangle, física simples;

-Ferramentas: Git, GitHub, Eclipse, MySQL Workbench.

📚 Aprendizados Técnicos Aplicados

-Herança e Polimorfismo: Character abstrata → Enemy/Heroi sobrescrevem comportamentos;

-DAO Pattern: Separação de lógica de negócio e acesso a dados com usuarioDAO;

-Integração Swing/JavaFX: Uso de JFXPanel e Platform.runLater para UI híbrida;

-Game Loop: AnimationTimer para update 60fps + física de gravidade manual.
