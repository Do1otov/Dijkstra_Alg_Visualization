package model;

import java.util.*;
public class DijkstraAlgorithm1 extends DijkstraAlgorithm{
    private final Map<Vertex, Integer> forward_distances;
    private final Map<Vertex, Integer> backward_distances;

    private final Set<Vertex> forward_visited;
    private final Set<Vertex> backward_visited;

    private final PriorityQueue<Vertex> forward_queue;
    private final PriorityQueue<Vertex> backward_queue;

    private final List<String> steps;
    private final List<DijkstraState> states;

    public DijkstraAlgorithm1(DirectedGraph graph) {
        super(graph);
        this.forward_distances = new HashMap<>();
        this.forward_visited = new HashSet<>();
        this.forward_queue = new PriorityQueue<>(Comparator.comparingInt(forward_distances::get));


        this.backward_distances = new HashMap<>();
        this.backward_visited = new HashSet<>();
        this.backward_queue = new PriorityQueue<>(Comparator.comparingInt(forward_distances::get));

        this.steps = new ArrayList<>();
        this.states = new ArrayList<>();

        for (Vertex vertex : graph.getVertices()) {
            forward_distances.put(vertex, Integer.MAX_VALUE);
            backward_distances.put(vertex, Integer.MAX_VALUE);
        }
    }

    public void process(Vertex start, Vertex end) {

        steps.add("Step 0:\n  The start of Dijkstra`s algorithm.\n");
        saveState(null, null, null, null);

        if(end==null)
        {
            String l = graph.getVertices().getFirst().getLabel();
            for (Vertex v : graph.getVertices())
            {
                if(v.getLabel().compareTo(l) >= 0)
                {
                    l = v.getLabel();
                    end = v;
                }
            }

            System.out.println("Label_end = " + end.getLabel());
        }

        forward_distances.put(start, 0);
        forward_queue.add(start);

        backward_distances.put(end, 0);
        backward_queue.add(end);

        int count = 1;
        boolean f = false;
        Vertex middle = null;

        while (!forward_queue.isEmpty() && !backward_queue.isEmpty() && !f) {

            Vertex forward_v = forward_queue.peek();
            Vertex backward_v = backward_queue.peek();

            //if (forward_visited.contains(backward_v)) {
                //    forward_queue.poll();
                //    continue;
                //}
            //
            //if (backward_visited.contains(forward_v)) {
                //    backward_queue.poll();
                //    continue;
                //}


            //if (forward_v == backward_v)
            //{
//
            //    System.out.println("END");
            //    break;
            //}

            int forward_dist = forward_distances.get(forward_v);
            int backward_dist = backward_distances.get(backward_v);


            if(forward_dist <= backward_dist)
            {   steps.add(String.format("\nStep %d:\n  Processing vertex (%s).\n", count, forward_v.getLabel()));
                saveState(forward_v, null, null, null);

                forward_queue.poll();
                forward_visited.add(forward_v);

                for (Edge edge : graph.getEdgesFrom(forward_v)) {
                    Vertex neighbor = edge.getToV();
                    if (!forward_visited.contains(neighbor)) {
                        steps.add(String.format("  Processing edge (%s -> %s).\n", forward_v.getLabel(), neighbor.getLabel()));
                        saveState(forward_v, edge, neighbor, null);

                        int newDist = forward_distances.get(forward_v) + edge.getWeight();
                        String stringSign = newDist < forward_distances.get(neighbor) ? " < " : " > ";
                        String stringDist = forward_distances.get(neighbor) < Integer.MAX_VALUE ? forward_distances.get(neighbor).toString() : "∞";
                        String inequality = forward_distances.get(forward_v) + " + " + edge.getWeight() + stringSign + stringDist;

                        steps.add(String.format("  Distance inequality: %s.\n", inequality));
                        saveState(forward_v, edge, neighbor, inequality);
                        if (newDist < forward_distances.get(neighbor)) {
                            forward_distances.put(neighbor, newDist);
                            forward_queue.add(neighbor);
                            steps.add(String.format("  Relaxation of edge (%s -> %s) and updating the distance to vertex (%s): New distance = %d.\n", forward_v.getLabel(), neighbor.getLabel(), neighbor.getLabel(), newDist));
                            saveState(forward_v, edge, neighbor, null);

                        }
                    }
                    if (backward_visited.contains(forward_v))
                    {
                        f = true;
                        middle = forward_v;
                    }


                }
                count++;
            }
            else
            {   steps.add(String.format("\nStep %d:\n  Processing vertex (%s).\n", count, backward_v.getLabel()));
                saveState(backward_v, null, null, null);

                backward_queue.poll();
                backward_visited.add(backward_v);

                for (Edge edge : graph.getEdgesTo(backward_v)) {

                    Vertex neighbor = edge.getFromV();
                    if (!backward_visited.contains(neighbor)) {

                        steps.add(String.format("  Processing edge (%s <- %s).\n", backward_v.getLabel(), neighbor.getLabel()));
                        saveState(backward_v, edge, neighbor, null);

                        int newDist = backward_distances.get(backward_v) + edge.getWeight();
                        String stringSign = newDist < backward_distances.get(neighbor) ? " < " : " > ";
                        String stringDist = backward_distances.get(neighbor) < Integer.MAX_VALUE ? backward_distances.get(neighbor).toString() : "∞";
                        String inequality = backward_distances.get(backward_v) + " + " + edge.getWeight() + stringSign + stringDist;

                        steps.add(String.format("  Distance inequality: %s.\n", inequality));
                        saveState(backward_v, edge, neighbor, inequality);

                        if (newDist < backward_distances.get(neighbor)) {
                            backward_distances.put(neighbor, newDist);
                            backward_queue.add(neighbor);

                            steps.add(String.format("  Relaxation of edge (%s <- %s) and updating the distance to vertex (%s): New distance = %d.\n", backward_v.getLabel(), neighbor.getLabel(), neighbor.getLabel(), newDist));
                            saveState(backward_v, edge, neighbor, null);
                        }
                    }

                    if (forward_visited.contains(backward_v))
                    {
                        f = true;
                        middle = backward_v;
                    }
                }
                count++;
            }

        }

        //post_process
        if (middle==null)
        {
            System.out.println("Path not found");
            return;
        }
        int min_len = forward_distances.get(middle) + backward_distances.get(middle);

        for (Map.Entry<Vertex, Integer> entry : forward_distances.entrySet())
        {
            for(Edge e : graph.getEdgesFrom(entry.getKey()))
            {
                if (backward_distances.get(e.getToV()) != Integer.MAX_VALUE && forward_distances.get(e.getFromV()) != Integer.MAX_VALUE)
                {
                    int current_dist = forward_distances.get(e.getFromV()) + backward_distances.get(e.getToV()) + e.getWeight();
                    if (current_dist < min_len) {
                        System.out.println("More optimal solution found -->> " + current_dist);
                        min_len = current_dist;
                    }
                }
            }
        }

        System.out.println("Answer = " +  min_len);

    }

    protected void saveState(Vertex vertex, Edge edge, Vertex neighbor, String inequality) {
        Map<Vertex, Integer> d = new HashMap<>(forward_distances);

        d.forEach((k, v) -> {
            if (backward_distances.containsKey(k) && backward_distances.get(k) != Integer.MAX_VALUE) {
                d.put(k, backward_distances.get(k));
            }
        });

        Set<Vertex> v = new HashSet<>(forward_visited);
        v.addAll(backward_visited);

        states.add(new DijkstraState(d, v, new ArrayList<>(steps), vertex, edge, neighbor, inequality));
    }

    public DijkstraState getState(Integer index) {
        return states.get(index);
    }

    public Integer getNumberStates() {
        return states.size();
    }
}
