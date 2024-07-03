package model;

import java.awt.*;
import java.util.UUID;

public class Vertex {
    private final UUID id;
    private String label;
    private Integer x;
    private Integer y;
    private Integer weight;
    private Color color;

    public Vertex(String label, Integer x, Integer y, Color color) {
        this.id = UUID.randomUUID();
        this.label = label;
        this.x = x;
        this.y = y;
        this.weight = Integer.MAX_VALUE;
        this.color = color;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }
}
