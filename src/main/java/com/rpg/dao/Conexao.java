package com.rpg.dao;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    private static final String URL;
    private static final String USUARIO;
    private static final String SENHA;

    static {
        try (InputStream input = Conexao.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("Arquivo db.properties não encontrado");
            }

            Properties props = new Properties();
            props.load(input);

            URL = props.getProperty("db.url");
            USUARIO = props.getProperty("db.user");
            SENHA = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar configurações do banco", e);
        }
    }

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
