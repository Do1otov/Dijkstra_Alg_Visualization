package research;

public class FloydWarshall {
    public static int[][] solve(int[][] graph)
    {
        int n = graph.length;
        int[][] dist = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE
                            && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        //// Вывод результатов
        //for (int i = 0; i < n; i++) {
        //    for (int j = 0; j < n; j++) {
        //        if (dist[i][j] == Integer.MAX_VALUE) {
        //            System.out.print("INF ");
        //        } else {
        //            System.out.print(dist[i][j] + " ");
        //        }
        //    }
        //    System.out.println();
        //}
        return dist;
    }


    public static void main(String[] args) {
        int[][] graph = {
                {0,  Integer.MAX_VALUE , -2, Integer.MAX_VALUE},
                {4  , 0, 3, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 2},
                {Integer.MAX_VALUE, -1, Integer.MAX_VALUE, 0}
        };
        solve(graph);

    }
}