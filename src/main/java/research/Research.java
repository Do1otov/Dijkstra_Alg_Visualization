package research;

import model.Vertex;
import model.DirectedGraph;
import model.DijkstraAlgorithm;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Research {

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter min_vertex_count: ");
        int min_v = scanner.nextInt();

        System.out.print("Enter min_max_count: ");
        int max_v = scanner.nextInt();

        System.out.print("Enter Dijkstra algorithm type \n 0 - full algo, else - matrix algo : ");
        int flag = scanner.nextInt();

        if(min_v<=0 || max_v <= min_v )
        {
            System.out.println("Неверные входные данные");
            return;
        }


        double[][] time_points = full_graph_tests(min_v, max_v);
        double[] time_Dijkstra = flag == 0  ? time_points[0] : time_points[2] ;
        double[] time_Floyd = time_points[1];

        double[] vertex_count = new double[max_v - min_v];
        for(int i = min_v; i < max_v; i++)
        {
            vertex_count[i-min_v] = i;
        }


        ArrayToFile.writeToFile("results.txt", vertex_count, time_Dijkstra);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GraphPlotter.plotGraph(vertex_count, time_Dijkstra ,time_Floyd);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public static long test_Dijkstra(DirectedGraph g, Vertex start_v , int n)
    {
        long totalTime;
        long startTime = System.nanoTime();

        for(int i = 0; i < n; i++)
        {
            DijkstraAlgorithm algo1 = new DijkstraAlgorithm(g);
            algo1.process(start_v);
        }

        long endTime = System.nanoTime();
        totalTime = endTime - startTime;
        return totalTime / n;
    }

    public static long test_DijkstraMatrix(int[][] matrix, int n)
    {
        long totalTime;
        long startTime = System.nanoTime();

        for(int i = 0; i < n; i++)
        {
            DijkstraAlgorithmMatrix.solve(matrix, 0);
        }

        long endTime = System.nanoTime();
        totalTime = endTime - startTime;
        return totalTime / n;
    }


    public static long test_Floyd(int[][] matrix, int n)
    {
        long totalTime;
        long startTime = System.nanoTime();

        for(int i = 0; i < n; i++)
        {
            FloydWarshall.solve(matrix);
        }

        long endTime = System.nanoTime();
        totalTime = endTime - startTime;
        return totalTime / n;
    }


    public static double[][] full_graph_tests(int min_v,int max_v)
    {
        DirectedGraph[] graphs = new DirectedGraph[max_v-min_v];
        int[][][] test_matrices = new int[max_v-min_v][][];
        for (int i = min_v; i < max_v; i++)
        {
            test_matrices[i - min_v] = MatrixGenerator.generateMatrix(i,i);
            graphs[i-min_v] = GraphGenerator.from_matrix(test_matrices[i - min_v]);
        }

        double[] time_Dijkstra = new double[max_v - min_v];
        double[] time_Floyd = new double[max_v - min_v];
        double[] time_DijkstraMatrix = new double[max_v - min_v];

        int current = 0;

        for(int i = 0; i < graphs.length; i++)
        {
            int start = 0;
            Vertex start_v = null;
            for(Vertex v : graphs[i].getVertices())
            {
                if (v.getLabel().equals(String.valueOf(start)))
                {
                    start_v = v;
                    break;
                }
            }
            double time1 = (double) test_Dijkstra(graphs[i], start_v, 10);
            time_Dijkstra[current] = time1 / 10;

            double time2 = (double) test_Floyd(test_matrices[i], 10);
            time_Floyd[current] = time2 / 10;
            double time3 = (double) test_Floyd(test_matrices[i], 10);
            time_DijkstraMatrix[current] = time3 / 10;

            current+=1;
            //System.out.println(current);
        }
    return new double[][]{time_Dijkstra, time_Floyd, time_DijkstraMatrix};
    }




}
