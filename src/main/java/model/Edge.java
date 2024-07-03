package model;

import java.awt.*;

public class Edge {
    private final Vertex fromV;
    private final Vertex toV;
    private Integer weight;
    private Color color;

    public Edge(Vertex fromV, Vertex toV, Color color) {
        this.fromV = fromV;
        this.toV = toV;
        this.weight = 0;
        this.color = color;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vertex getFromV() {
        return fromV;
    }

    public Vertex getToV() {
        return toV;
    }

    public Integer getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }
}
