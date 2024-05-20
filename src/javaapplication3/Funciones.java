package javaapplication3;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;

public class Funciones extends JPanel {
    private final Graph graph;
    private static final int NODE_RADIUS = 10; // Radio de los nodos

    public Funciones(Graph graph) {
        this.graph = graph;
        this.setSize(800, 600);

        // Obtener y mostrar la matriz de adyacencia
        int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
        printAdjacencyMatrix(adjacencyMatrix);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Asegura que el fondo del panel se pinte
        drawGraph(g);
    }

    private void drawGraph(Graphics g) {
        int[][] positions = {
            {0, 0}, // dummy position for 0 (not used)
            // {x, y}
            {50, 150}, // position for node 1
            {150, 50}, // position for node 2
            {150, 250}, // position for node 3
            {250, 50}, // position for node 4
            {250, 250}, // position for node 5
            {350, 150}, // position for node 6
            {450, 50}, // position for node 7
            {450, 250}, // position for node 8
            {550, 150}  // position for node 9
        };

        // Set para almacenar aristas ya dibujadas
        Set<String> drawnEdges = new HashSet<>();

        // Dibujar aristas
        for (int from : graph.getAdjacencyList().keySet()) {
            for (Graph.Edge edge : graph.getEdges(from)) {
                int to = edge.to;
                int weight = edge.weight;

                // Ajustar las coordenadas para que las flechas no estén pegadas a los nodos
                int[] adjustedStart = adjustPosition(positions[from][0], positions[from][1], positions[to][0], positions[to][1], NODE_RADIUS);
                int[] adjustedEnd = adjustPosition(positions[to][0], positions[to][1], positions[from][0], positions[from][1], NODE_RADIUS);

                drawArrowLine(g, adjustedStart[0], adjustedStart[1], adjustedEnd[0], adjustedEnd[1], 5, 5);

                // Calcular la posición del texto
                int textX = (positions[from][0] + positions[to][0]) / 2;
                int textY = (positions[from][1] + positions[to][1]) / 2;

                // Comprobar si la arista inversa ya fue dibujada
                String edgeKey = from + "-" + to;
                String reverseEdgeKey = to + "-" + from;

                if (drawnEdges.contains(reverseEdgeKey)) {
                    // Ajustar la posición del texto para la arista inversa
                    textX += 10;
                    textY += 10;
                }

                g.drawString(Integer.toString(weight), textX, textY);
                drawnEdges.add(edgeKey);
            }
        }

        // Dibujar nodos
        for (int i = 1; i < positions.length; i++) {
            g.setColor(Color.YELLOW);
            g.fillOval(positions[i][0] - NODE_RADIUS, positions[i][1] - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
            g.setColor(Color.BLACK);
            g.drawOval(positions[i][0] - NODE_RADIUS, positions[i][1] - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
            g.drawString(Integer.toString(i), positions[i][0] - 5, positions[i][1] + 5);
        }
    }

    private int[] adjustPosition(int x1, int y1, int x2, int y2, int radius) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        double dist = Math.sqrt(dx * dx + dy * dy);
        double ratio = radius / dist;
        int newX = (int) (x1 + dx * ratio);
        int newY = (int) (y1 + dy * ratio);
        return new int[]{newX, newY};
    }

    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    private void printAdjacencyMatrix(int[][] matrix) {
        System.out.println("Matriz de adyacencia:");
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}