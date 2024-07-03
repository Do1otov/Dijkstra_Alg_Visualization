package model;

import java.awt.*;
import java.util.UUID;

public class Vertex {
    private UUID id;
    private String label;
    private Integer x;
    private Integer y;
    private Color color;

    public Vertex(String label, Integer x, Integer y, Color color) {
        this.id = UUID.randomUUID();
        this.label = label;
        this.x = x;
        this.y = y;
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

    public Color getColor() {
        return color;
    }

    public String toJSON() {
        return String.format(
                "{\"id\":\"%s\",\"label\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%d\"}",
                id.toString(), label, x, y, color.getRGB()
        );
    }

    public static Vertex fromJSON(String json) {
        String[] fields = json.replaceAll("[{}\"]", "").split(",");
        UUID id = UUID.fromString(fields[0].split(":")[1]);
        String label = fields[1].split(":")[1];
        Integer x = Integer.parseInt(fields[2].split(":")[1]);
        Integer y = Integer.parseInt(fields[3].split(":")[1]);
        Color color = new Color(Integer.parseInt(fields[4].split(":")[1]));

        Vertex vertex = new Vertex(label, x, y, color);
        vertex.id = id;
        return vertex;
    }
}
