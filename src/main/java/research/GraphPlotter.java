package research;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphPlotter extends JFrame {
    public static void plotGraph(double[] xData, double[] yData1, double[] yData2) throws IOException {
        double xMin = findMin(xData);
        double xMax = findMax(xData);
        double y1Min = findMin(yData1);
        double y1Max = findMax(yData1);
        double y2Min = findMin(yData2);
        double y2Max = findMax(yData2);
        double yMin = Math.min(y1Min, y2Min);
        double yMax = Math.max(y1Max, y2Max);

        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        JFrame frame = new JFrame("Point plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g, xData, yData1, yData2, xMin, xMax, yMin, yMax);
            }
        };

        frame.getContentPane().add(graphPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        graphPanel.paint(g2d);
        String f = "plot.png";
        ImageIO.write(image, "png", new File(f));
        System.out.println("Graph saved to " + f);
    }

    private static void drawGraph(Graphics g, double[] xData, double[] yData1, double[] yData2, double xMin, double xMax, double yMin, double yMax) {
        int width = g.getClipBounds().width;
        int height = g.getClipBounds().height;

        g.setColor(Color.GRAY);
        g.drawLine(50, height - 80, width - 50, height - 80); // Ось X
        g.drawLine(50, height - 80, 50, 80); // Ось Y

        Font font = new Font("Arial", Font.PLAIN, 12);
        g.setFont(font);
        for (int i = 0; i <= 10; i++) {
            int x = 50 + i * (width - 100) / 10;
            int y = height - 80;
            g.drawString(String.format("%.1f", xMin + i * (xMax - xMin) / 10), x - 20, y + 20);
            g.drawLine(x, y, x, y + 5);
        }
        for (int i = 0; i <= 10; i++) {
            int x = 50;
            int y = height - 80 - i * (height - 160) / 10;
            g.drawString(String.format("%.1f", yMin + i * (yMax - yMin) / 10), x - 40, y + 5);
            g.drawLine(x - 5, y, x, y);
        }

        g.drawString("vertex", width - 40, height - 40);
        g.drawString("time", 20, 50);

        g.setColor(Color.BLUE);
        for (int i = 0; i < xData.length; i++) {
            int x = (int) ((xData[i] - xMin) * (width - 100) / (xMax - xMin)) + 50;
            int y = (int) (height - 80 - (height - 160) * (yData1[i] - yMin) / (yMax - yMin));
            g.fillOval(x - 5, y - 5, 10, 10);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < xData.length; i++) {
            int x = (int) ((xData[i] - xMin) * (width - 100) / (xMax - xMin)) + 50;
            int y = (int) (height - 80 - (height - 160) * (yData2[i] - yMin) / (yMax - yMin));
            g.fillOval(x - 5, y - 5, 10, 10);
        }
    }

    private static double findMin(double[] data) {
        double min = data[0];
        for (int i = 1; i < data.length; i++) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        return min;
    }

    private static double findMax(double[] data) {
        double max = data[0];
        for (int i = 1; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        double[] xData = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        double[] yData1 = {2, 1, 1, 8, 1, 8, 6, 4, 1, 0, -2, -4};
        double[] yData2 = {4, 6, 8, 10, 12, 10, 8, 6, 4, 2, 0, -2};
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    plotGraph(xData, yData1, yData2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}