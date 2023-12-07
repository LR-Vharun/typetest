


import java.awt.*;
import javax.swing.*;

public class Frame1 {

    public Frame1() {

        JFrame frame = new JFrame("Instructions");

        frame.setSize(600, 206);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel label1 = new JLabel("Click on the start button below", SwingConstants.CENTER);
        label1.setFont(font);
        JLabel label2 = new JLabel("A passage will appear", SwingConstants.CENTER);
        label2.setFont(font);
        JLabel label3 = new JLabel("Start typing exactly as the passage says", SwingConstants.CENTER);
        label3.setFont(font);
        JLabel label4 = new JLabel("The characters will turn green if you type correctly", SwingConstants.CENTER);
        label4.setFont(font);
        JLabel label5 = new JLabel("If you type wrong characters there wont be any response", SwingConstants.CENTER);
        label5.setFont(font);
        JLabel label7 = new JLabel("After completing the passage press any key for your results",
                SwingConstants.CENTER);
        label7.setFont(font);
        JLabel label6 = new JLabel("ALL THE BEST! ☺☺☺ ", SwingConstants.CENTER);
        label6.setFont(font);

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);
        panel.add(label7);
        panel.add(label6);

        frame.add(panel, BorderLayout.BEFORE_FIRST_LINE);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);
    }
}
