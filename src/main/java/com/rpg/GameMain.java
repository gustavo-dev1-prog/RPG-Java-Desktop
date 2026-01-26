
package com.rpg;
import com.rpg.view.LoginFrame;
import javax.swing.SwingUtilities;

public class GameMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	new LoginFrame().setVisible(true);
        });
     }
}