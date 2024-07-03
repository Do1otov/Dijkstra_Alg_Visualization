package gui;

import model.DijkstraAlgorithm;
import model.Edge;
import model.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GraphFieldManager {
    private final App app;
    private final JPanel graphField;

    private Vertex selectedVertex;
    private Vertex firstVertex;
    private Point initClick;

    public GraphFieldManager(App app) {
        this.app = app;
        this.graphField = new GraphPainter();

        graphField.setBackground(GUISettings.GRAPH_FIELD_COLOR);
        graphField.addMouseListener(new MouseListener());
        graphField.addMouseMotionListener(new MouseMotionListener());

        GridBagConstraints gbc = app.getGBC();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.75;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        app.add(graphField, gbc);
    }

    public JPanel getGraphField() {
        return graphField;
    }

    public Vertex getFirstVertex() {
        return firstVertex;
    }

    private class GraphPainter extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (Vertex vertex : app.getGraph().getVertices()) {
                int x = vertex.getX() - GUISettings.VERTEX_RADIUS;
                int y = vertex.getY() - GUISettings.VERTEX_RADIUS;
                g2.setColor(vertex.getColor());
                g2.fillOval(x, y, GUISettings.VERTEX_RADIUS * 2, GUISettings.VERTEX_RADIUS * 2);
                g2.setColor(GUISettings.TITLE_COLOR);
                g2.drawString(vertex.getLabel(), vertex.getX() - GUISettings.VERTEX_RADIUS / 2, vertex.getY() - (GUISettings.VERTEX_RADIUS + GUISettings.VERTEX_RADIUS / 2));

                if (vertex.equals(app.getGraphFieldManager().getFirstVertex())) {
                    g2.setColor(GUISettings.OUTLINE_SELECTED_VERTEX_COLOR);
                    g2.drawOval(x, y, GUISettings.VERTEX_RADIUS * 2, GUISettings.VERTEX_RADIUS * 2);
                }
            }

            for (Vertex vertex : app.getGraph().getVertices()) {
                for (Edge edge : app.getGraph().getEdgesFrom(vertex)) {
                    Point from = new Point(edge.getFromV().getX(), edge.getFromV().getY());
                    Point to = new Point(edge.getToV().getX(), edge.getToV().getY());
                    Point intersection = getIntersection(from, to);
                    g2.setColor(edge.getColor());
                    g2.drawLine(from.x, from.y, intersection.x, intersection.y);
                    if (app.graphIsDirected()) {
                        drawArrow(g2, from.x, from.y, intersection.x, intersection.y);
                    }
                    int midX = (from.x + to.x) / 2;
                    int midY = (from.y + to.y) / 2;
                    g2.setColor(GUISettings.TITLE_COLOR);
                    g2.drawString(edge.getWeight().toString(), midX, midY);
                }
            }

            if (app.isRunDijkstra()) {
                Vertex startVertex = app.getGraphFieldManager().getFirstVertex();
                if (startVertex != null) {
                    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(app.getGraph());
                    dijkstra.process(startVertex);

                    for (Vertex vertex : app.getGraph().getVertices()) {
                        Integer distance = dijkstra.getDistanceTo(vertex);
                        if (distance != null && distance < Integer.MAX_VALUE) {
                            g2.setColor(Color.RED);
                            g2.drawString(String.valueOf(distance), vertex.getX() - GUISettings.VERTEX_RADIUS / 2, vertex.getY() + GUISettings.VERTEX_RADIUS + 10);
                        }
                    }
                }
                app.resetRunDijkstra();
            }

            g2.dispose();
        }

        private Point getIntersection(Point from, Point to) {
            int radius = GUISettings.VERTEX_RADIUS;
            double dx = to.x - from.x;
            double dy = to.y - from.y;
            double dist = Math.sqrt(dx * dx + dy * dy);
            double newX = to.x - dx * radius / dist;
            double newY = to.y - dy * radius / dist;
            return new Point((int) newX, (int) newY);
        }

        private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {
            int arrowSize = GUISettings.ARROW_SIZE;
            double angle = Math.atan2(y2 - y1, x2 - x1);
            int x = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
            int y = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
            int x3 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
            int y3 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));
            int[] xPoints = {x2, x, x3};
            int[] yPoints = {y2, y, y3};
            g2.fillPolygon(xPoints, yPoints, 3);
        }
    }

    private void addVertex(Point point) {
        Vertex vertex = new Vertex("", point.x, point.y, GUISettings.VERTEX_COLOR);
        app.getGraph().addVertex(vertex);
        graphField.repaint();
    }

    private void addEdge(Vertex from, Vertex to) {
        Edge edge = new Edge(from, to, GUISettings.EDGE_COLOR);
        app.getGraph().addEdge(edge);
        graphField.repaint();
    }

    private void removeVertex(Vertex vertex) {
        app.getGraph().removeVertex(vertex);
        graphField.repaint();
    }

    private void removeEdge(Edge edge) {
        app.getGraph().removeEdge(edge);
        graphField.repaint();
    }

    private Vertex getVertexAt(Point point) {
        for (Vertex vertex : app.getGraph().getVertices()) {
            if (Math.pow(point.x - vertex.getX(), 2) + Math.pow(point.y - vertex.getY(), 2) <= Math.pow(10, 2)) {
                return vertex;
            }
        }
        return null;
    }

    private Edge getEdgeAt(Point point) {
        for (Vertex vertex : app.getGraph().getVertices()) {
            for (Edge edge : app.getGraph().getEdgesFrom(vertex)) {
                Point from = new Point(edge.getFromV().getX(), edge.getFromV().getY());
                Point to = new Point(edge.getToV().getX(), edge.getToV().getY());
                if (isPointOnLine(point, from, to)) {
                    return edge;
                }
            }
        }
        return null;
    }

    private boolean isPointOnLine(Point point, Point from, Point to) {
        double distance = point.distance(from) + point.distance(to);
        double lineLength = from.distance(to);
        return Math.abs(distance - lineLength) < 3;
    }

    private class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (app.getControlPanelsManager().getEditButton().isSelected()) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    addVertex(e.getPoint());
                } else if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    if (firstVertex == null) {
                        firstVertex = getVertexAt(e.getPoint());
                        if (firstVertex != null) {
                            graphField.repaint();
                        }
                    } else {
                        Vertex secondVertex = getVertexAt(e.getPoint());
                        if (secondVertex != null && !secondVertex.equals(firstVertex)) {
                            addEdge(firstVertex, secondVertex);
                        }
                        firstVertex = null;
                        graphField.repaint();
                    }
                } else if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
                    Edge edge = getEdgeAt(e.getPoint());
                    if (edge != null) {
                        String weightStr = CustomDialog.showInputDialog(graphField, "Edge Weight", "Enter Non-Negative Edge Weight:");
                        if (weightStr != null) {
                            try {
                                int weight = Integer.parseInt(weightStr);
                                if (weight < 0) {
                                    CustomMessageDialog.showMessageDialog(graphField, "Error", "Edge weight must be non-negative.");
                                } else {
                                    app.getGraph().setEdgeWeight(edge, weight);
                                    graphField.repaint();
                                }
                            } catch (NumberFormatException ex) {
                                CustomMessageDialog.showMessageDialog(graphField, "Error", "Invalid input. Please enter a number.");
                            }
                        }
                    }
                } else if (SwingUtilities.isLeftMouseButton(e) && firstVertex != null) {
                    firstVertex = null;
                    graphField.repaint();
                }
            } else if (app.getControlPanelsManager().getDeleteButton().isSelected()) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    Vertex vertex = getVertexAt(e.getPoint());
                    if (vertex != null) {
                        removeVertex(vertex);
                    } else {
                        Edge edge = getEdgeAt(e.getPoint());
                        if (edge != null) {
                            removeEdge(edge);
                        }
                    }
                }
            }
            graphField.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (app.getControlPanelsManager().getEditButton().isSelected() && SwingUtilities.isLeftMouseButton(e)) {
                selectedVertex = getVertexAt(e.getPoint());
                initClick = e.getPoint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            selectedVertex = null;
            initClick = null;
        }
    }

    private class MouseMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (app.getControlPanelsManager().getEditButton().isSelected() && selectedVertex != null) {
                int deltaX = e.getX() - initClick.x;
                int deltaY = e.getY() - initClick.y;
                selectedVertex.setX(selectedVertex.getX() + deltaX);
                selectedVertex.setY(selectedVertex.getY() + deltaY);
                initClick = e.getPoint();
                graphField.repaint();
            }
        }
    }
}
