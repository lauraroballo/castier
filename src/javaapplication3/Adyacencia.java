package javaapplication3;

import java.awt.*;
import javax.swing.*;

public class Adyacencia extends JPanel {
    private final int[][] matrix;
    private final int[][] positions = {
        {0, 0}, // dummy position for 0 (not used)
        {50, 150}, {150, 50}, {150, 250}, {250, 50}, {250, 250},
        {350, 150}, {450, 50}, {450, 250}, {550, 150}
    };

    public Adyacencia(int[][] matrix) {
        this.matrix = matrix;
        this.setPreferredSize(new Dimension(200, 200));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la matriz
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix.length; j++) {
                g.drawString(Integer.toString(matrix[i][j]), positions[j][0], positions[i][1]);
            }
        }

        // Dibujar las etiquetas de las coordenadas
        for (int i = 1; i < positions.length; i++) {
            g.drawString(Integer.toString(i), positions[i][0] - 5, positions[i][1] + 5);
        }
    }
}