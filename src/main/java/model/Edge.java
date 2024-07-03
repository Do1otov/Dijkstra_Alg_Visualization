package model;

import java.awt.*;
import java.util.UUID;

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

    public String toJSON() {
        return String.format(
                "{\"from\":\"%s\",\"to\":\"%s\",\"weight\":%d,\"color\":\"%d\"}",
                fromV.getId().toString(), toV.getId().toString(), weight, color.getRGB()
        );
    }

    public static Edge fromJSON(String json, DirectedGraph graph) {
        String[] fields = json.replaceAll("[{}\"]", "").split(",");
        UUID fromId = UUID.fromString(fields[0].split(":")[1]);
        UUID toId = UUID.fromString(fields[1].split(":")[1]);
        Integer weight = Integer.parseInt(fields[2].split(":")[1]);
        Color color = new Color(Integer.parseInt(fields[3].split(":")[1]));

        Vertex fromV = graph.getVertexById(fromId);
        Vertex toV = graph.getVertexById(toId);

        Edge edge = new Edge(fromV, toV, color);
        edge.setWeight(weight);
        return edge;
    }
}
