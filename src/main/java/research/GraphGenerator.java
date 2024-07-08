package research;

import model.DirectedGraph;
import model.Edge;
import model.Vertex;

import java.awt.*;
import java.util.List;
import research.MatrixGenerator;

public class GraphGenerator {

    public static void main(String[] args)
    {
        int[][] m = MatrixGenerator.generateMatrix(5,5);
        DirectedGraph g = from_matrix(m);
        String s = g.toJSON();
        System.out.println(s);
    }

    public static DirectedGraph generate_random_full_graph(int size)
    {
        int[][] m = MatrixGenerator.generateMatrix(size,size);
        return from_matrix(m);
    }

    public static DirectedGraph from_matrix(int[][] matrix)
    {
        DirectedGraph graph = new DirectedGraph();
        for(int i = 0; i < matrix.length; i++)
        {
            Vertex v = new Vertex(String.valueOf(i), 0,0, Color.RED);
            graph.addVertex(v);
        }

        List<Vertex> vertices = graph.getVertices();

        for(int i = 0; i < vertices.size(); i++)
        {
            for(int j = 0; j < vertices.size(); j++)
            {
                Edge e = new Edge(vertices.get(i), vertices.get(j), Color.RED, matrix[i][j]);
                graph.addEdge(e);
            }
        }
        return graph;
    }

}
