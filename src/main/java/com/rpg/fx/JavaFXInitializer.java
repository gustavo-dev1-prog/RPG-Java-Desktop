package com.rpg.fx;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public class JavaFXInitializer {
	
	private static boolean iniciado = false;
	
	public static void init() {
		if(!iniciado) {
			new JFXPanel();
			Platform.setImplicitExit(false);
			iniciado = true;
		}
	}
}
