import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutMe extends JFrame {
    public AboutMe() {
        setSize(800, 650);
        setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon("fotogrup2.jpg");

        JLabel label = new JLabel(
                "<html><br><center>Tic Tac Toe</center><br><center>Developed by Group 10:</center><br>"
                        + "<center>5026221020 - Eugenia Indrawan</center>"
                        + "<center>5026221148 - Ashila Mahdiyyah</center>"
                        + "<center>5026221168 - Razi Alvaro Arman</center><br>"
                        + "<center>Final Project ASD A 2023/2024</center></html>");
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setIconTextGap(10);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        JButton exitButton = new JButton("Back");
        exitButton.setBounds(0, 0, 50, 30);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    StartMenu startMenu = new StartMenu();
                    startMenu.setVisible(true);
                    dispose();
                });
            }
        });
        // Create a panel to hold the label and button
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(exitButton, BorderLayout.SOUTH);

        // Add an empty border to the panel to create a margin
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(panel);
        setTitle("About");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
