package gui;

import model.*;
import static gui.Settings.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GraphFieldManager {
    private final App app;
    private final JPanel graphField;

    private Vertex selectedVertex;

    private Vertex LastVertex;
    private Vertex firstVertex;

    private Point initClick;

    public GraphFieldManager(App app) {
        this.app = app;
        this.graphField = new GraphPainter();
        reset();

        graphField.setBackground(GRAPH_FIELD_COLOR);
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

    public void setFirstVertex(Vertex vertex) {
        this.firstVertex = vertex;
    }

    public Vertex getFirstVertex() {
        return firstVertex;
    }

    public void setLastVertex(Vertex vertex) {
        this.LastVertex = vertex;
    }

    public Vertex getLastVertex() {
        return LastVertex;
    }

    public void reset() {
        this.selectedVertex = null;
        this.firstVertex = null;
        this.LastVertex = null;
        this.initClick = null;
    }

    private void addVertex(Point point) {
        Vertex vertex = new Vertex("", point.x, point.y, VERTEX_COLOR);
        app.getGraph().addVertex(vertex);
        app.getAlgorithmManager().reset();
        graphField.repaint();
    }

    private void addEdge(Vertex from, Vertex to) {
        Edge edge = new Edge(from, to, EDGE_COLOR);
        app.getGraph().addEdge(edge);
        app.getAlgorithmManager().reset();
        graphField.repaint();
    }

    private void removeVertex(Vertex vertex) {
        app.getGraph().removeVertex(vertex);
        app.getAlgorithmManager().reset();
        graphField.repaint();
    }

    private void removeEdge(Edge edge) {
        app.getGraph().removeEdge(edge);
        app.getAlgorithmManager().reset();
        graphField.repaint();
    }

    private Vertex getVertexAt(Point point) {
        for (Vertex vertex : app.getGraph().getVertices()) {
            if (Math.pow(point.x - vertex.getX(), 2) + Math.pow(point.y - vertex.getY(), 2) <= Math.pow(VERTEX_RADIUS, 2)) {
                return vertex;
            }
        }
        app.getAlgorithmManager().reset();
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

    private class GraphPainter extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (app.getAlgorithmManager().isRun()) {
                DijkstraState state = app.getAlgorithmManager().getState();
                for (Vertex vertex : app.getGraph().getVertices()) {
                    vertex.setColor(VERTEX_COLOR);
                    for (Edge edge : app.getGraph().getEdgesFrom(vertex)) {
                        edge.setColor(EDGE_COLOR);
                    }
                }

                for (Vertex vertex : state.getDistances().keySet()) {
                    if (state.getVisited().contains(vertex)) {
                        vertex.setColor(VISITED_VERTEX_COLOR);
                    }
                }
                if (state.getCurrentVertex() != null) {
                    state.getCurrentVertex().setColor(CURRENT_VERTEX_COLOR);
                }
                if (state.getNeighborVertex() != null) {
                    state.getNeighborVertex().setColor(NEIGHBOR_VERTEX_COLOR);
                }
                if (state.getCurrentEdge() != null) {
                    state.getCurrentEdge().setColor(PROCESSED_EDGE_COLOR);
                }
            }

            for (Vertex vertex : app.getGraph().getVertices()) {
                for (Edge edge : app.getGraph().getEdgesFrom(vertex)) {
                    Point from = new Point(edge.getFromV().getX(), edge.getFromV().getY());
                    Point to = new Point(edge.getToV().getX(), edge.getToV().getY());
                    Point intersection = getIntersection(from, to);
                    g2.setColor(edge.getColor());
                    g2.drawLine(from.x, from.y, intersection.x, intersection.y);
                    if (app.getGraph().isDirected()) {
                        drawArrow(g2, from.x, from.y, intersection.x, intersection.y);
                    }

                    int midX = (from.x + to.x) / 2;
                    int midY = (from.y + to.y) / 2;
                    g2.setColor(TITLE_COLOR);
                    g2.drawString(edge.getWeight().toString(), midX, midY);
                }
            }

            for (Vertex vertex : app.getGraph().getVertices()) {
                int x = vertex.getX() - VERTEX_RADIUS;
                int y = vertex.getY() - VERTEX_RADIUS;
                g2.setColor(vertex.getColor());
                g2.fillOval(x, y, VERTEX_RADIUS * 2, VERTEX_RADIUS * 2);
                g2.setColor(TITLE_COLOR);
                g2.drawString(vertex.getLabel(), vertex.getX() - VERTEX_RADIUS / 2, vertex.getY() - (VERTEX_RADIUS + VERTEX_RADIUS / 2));

                if (vertex.equals(firstVertex)) {
                    g2.setColor(OUTLINE_SELECTED_VERTEX_COLOR);
                    g2.drawOval(x, y, VERTEX_RADIUS * 2, VERTEX_RADIUS * 2);
                }

                if (vertex.equals(LastVertex)) {
                    g2.setColor(OUTLINE_SELECTED_SECOND_VERTEX_COLOR);
                    g2.drawOval(x, y, VERTEX_RADIUS * 2, VERTEX_RADIUS * 2);
                }
            }

            if (app.getAlgorithmManager().isRun()) {
                DijkstraState state = app.getAlgorithmManager().getState();
                for (Vertex vertex : state.getDistances().keySet()) {
                    g2.setColor(DISTANCE_COLOR);
                    g2.drawString(state.getDistances().get(vertex) < Integer.MAX_VALUE ? String.valueOf(state.getDistances().get(vertex)) : "∞", vertex.getX() - VERTEX_RADIUS / 2, vertex.getY() + 2 * VERTEX_RADIUS);
                }

                if (state.getInequality() != null) {
                    g2.setColor(INEQUALITY_COLOR);
                    g2.drawString(state.getInequality(), state.getNeighborVertex().getX() - VERTEX_RADIUS / 2 - 16, state.getNeighborVertex().getY() + 3 * VERTEX_RADIUS);
                }
            }
            g2.dispose();
        }

        private Point getIntersection(Point from, Point to) {
            int radius = VERTEX_RADIUS;
            double dx = to.x - from.x;
            double dy = to.y - from.y;
            double dist = Math.sqrt(dx * dx + dy * dy);
            double newX = to.x - dx * radius / dist;
            double newY = to.y - dy * radius / dist;
            return new Point((int) newX, (int) newY);
        }

        private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {
            int arrowSize = ARROW_SIZE;
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

    private class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (app.getControlPanelsManager().getEditButton().isSelected()) {
                if (SwingUtilities.isMiddleMouseButton(e) && e.getClickCount() == 1) {
                    LastVertex = getVertexAt(e.getPoint());
                    graphField.repaint();
                }
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    if (firstVertex == null) {
                        firstVertex = getVertexAt(e.getPoint());
                        if (firstVertex == null) {
                            addVertex(e.getPoint());
                        } else {
                            graphField.repaint();
                        }
                    } else {
                        Vertex secondVertex = getVertexAt(e.getPoint());
                        if (secondVertex != null && !secondVertex.equals(firstVertex)) {
                            addEdge(firstVertex, secondVertex);
                            firstVertex = null;
                            graphField.repaint();
                        } else if (secondVertex == null) {
                            firstVertex = null;
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
                    Edge edge = getEdgeAt(e.getPoint());
                    if (edge != null) {
                        String weightStr = CustomDialog.showInputDialog(graphField, "Edge Weight", "Enter Non-Negative Edge Weight:", 250, 125);
                        if (weightStr != null && !weightStr.trim().isEmpty()) {
                            try {
                                int weight = Integer.parseInt(weightStr);
                                if (weight < 0) {
                                    CustomMessageDialog.showMessageDialog(graphField, "Error", "Edge weight must be non-negative.", 250, 100);
                                } else {
                                    app.getGraph().setEdgeWeight(edge, weight);
                                    app.getAlgorithmManager().reset();
                                    graphField.repaint();
                                }
                            } catch (NumberFormatException ex) {
                                CustomMessageDialog.showMessageDialog(graphField, "Error", "Invalid input. Please enter a number.", 250, 100);
                            }
                        }
                    }
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
        }
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
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
            if (selectedVertex != null && (app.getControlPanelsManager().getEditButton().isSelected() || app.getControlPanelsManager().getDeleteButton().isSelected())) {
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
