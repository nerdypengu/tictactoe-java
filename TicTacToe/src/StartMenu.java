/* KELOMPOK 10
 * Eugenia Indrawan - 5026221020
 * Ashila Mahdiyyah - 5026221148
 * Razi Alvaro Arman - 5026221168
 * 
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame {

    public StartMenu() {
        setTitle("Game Start Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel titleLabel = new JLabel("Tic Tac Toe");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    ModeMenu modeMenu = new ModeMenu();
                    modeMenu.setVisible(true);
                    setVisible(false);
                });
            }
        });

        JButton aboutUsButton = new JButton("About Us");
        aboutUsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AboutMe().setVisible(true);

            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the "Exit" button click event
                System.exit(0); // Terminate the application
            }
        });

        // Create a panel to hold the components with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Set GridBagConstraints for center alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0); // Add space below the title
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        panel.add(startGameButton, gbc);

        gbc.gridy++;
        panel.add(aboutUsButton, gbc);

        gbc.gridy++;
        panel.add(exitButton, gbc);

        // Add the panel to the frame
        add(panel);

        // Set the preferred size and pack
        setPreferredSize(new Dimension(400, 300)); // Set the preferred width and height
        pack(); // Pack the components
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartMenu startMenu = new StartMenu();
            startMenu.setVisible(true);
        });
    }
}
