import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph g;
    //Creates a HashSet which contains all the visited nodes.
    private HashSet<node_info> visited = new HashSet<>();
    Comparator<node_info> comparator = new Comparator<node_info>() {
        @Override
        public int compare(node_info n1, node_info n2) {
            return (int) (n1.getTag() - n2.getTag());
        }
    };

    /**
     * Graph constructor.
     */
    public WGraph_Algo() {
        this.g = new WGraph_DS();
    }

    /**
     * Graph constructor which get a graph.
     *
     * @param other the graph.
     */
    public WGraph_Algo(weighted_graph other) {
        this.g = other;
    }

    @Override
    public void init(weighted_graph g) {
        this.g = g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.g;
    }

    @Override
    public weighted_graph copy() {
         /*
        The method creates a new graph, then copies all the nodes of the original graph to the new graph.
        Then iterates on all the neighbors of each node and makes a new edge in the new graph.
         */
        weighted_graph other = new WGraph_DS();
        Collection<node_info> getV = g.getV();

        for (node_info original : getV) {
            int key = original.getKey();
            WGraph_DS.NodeData temp = new WGraph_DS.NodeData(key);
            temp.setInfo(original.getInfo());
            temp.setTag(original.getTag());
            other.addNode(temp.getKey());
        }

        Iterator<node_info> v = getV.iterator();
        while (v.hasNext()) {
            WGraph_DS.NodeData original = (WGraph_DS.NodeData) v.next();

            for (node_info ni : original.getNi()) {
                if (g.hasEdge(original.getKey(), ni.getKey())) {
                    double wei = g.getEdge(original.getKey(), ni.getKey());
                    other.connect(original.getKey(), ni.getKey(), wei);
                }
            }
        }
        return other;
    }

    /**
     * The Breadth-first search (BFS) is an algorithm for traversing or searching
     * tree or graph data structures. It starts at the given node in the graph,
     * and explores all of the neighbor nodes at the present depth prior to moving on
     * to the nodes at the next depth level.
     * The BFS sets the info and the tag of each node in the given graph.
     *
     * @param g      the graph which the search will run.
     * @param source the node from which the search will start.
     */
    private void BFS(weighted_graph g, node_info source) {
        /*
        Clears the visited HashSet and initializes all to nodes in the graph to their default values,
        except the tag of the source, which gets the value 0.
         */
        visited.clear();

        /*
        Creates a queue which will contain the nodes that need to traverse (by their order).
         */
        Queue<node_info> queue = new LinkedList<node_info>();
        queue.add(source);

        /*
        While the queue is not empty, the algorithm takes the first node and traverses all its neighbors.
        If this neighbor is not yet visited, it adds to the queue, gets its default values and add marks as visited.
        After the algorithm finishes gaining with all the neighbors, it marks the current node as visited
        and continues to the next node in the queue.
         */
        while (!queue.isEmpty()) {
            WGraph_DS.NodeData current = (WGraph_DS.NodeData) queue.poll();
            for (node_info temp : current.getNi()) {
                if (!visited.contains(temp)) {
                    queue.add(temp);
                    visited.add(temp);
                }
            }
            visited.add(current);
        }
    }

    private void Dijkstra(weighted_graph g, node_info source) {
        /*
        Clears the visited HashSet and initializes all to nodes in the graph to their default values,
        except the tag of the source, which gets the value 0.
         */
        visited.clear();
        Collection<node_info> getV = g.getV();
        for (node_info node : getV) {
            node.setInfo(null);
            node.setTag(Double.POSITIVE_INFINITY);
        }
        source.setTag(0);

        /*
        Creates a queue which will contain the nodes that need to traverse (by their order).
         */
        PriorityQueue<node_info> pq = new PriorityQueue<node_info>(3, comparator);
        pq.add(source);

        /*
        While the queue is not empty, the algorithm takes the first node and traverses all its neighbors.
        If this neighbor is not yet visited, it adds to the queue, gets its default values and add marks as visited.
        After the algorithm finishes gaining with all the neighbors, it marks the current node as visited
        and continues to the next node in the queue.
         */
        while (!pq.isEmpty()) {
            WGraph_DS.NodeData current = (WGraph_DS.NodeData) pq.poll();
            if (!visited.contains(current)) {
                visited.add(current);
                for (node_info temp : current.getNi()) {
                    if (!visited.contains(temp)) {
                        double distance = current.getTag() + g.getEdge(current.getKey(), temp.getKey());
                        if (distance < temp.getTag()) {
                            temp.setTag(distance);
                            temp.setInfo("" + current.getKey());
                            pq.add(temp);
                        }
                    }
                }
            }

        }
    }

    @Override
    public boolean isConnected() {
       /*
        Checks if the graph is not empty, then runs the BFS algorithm on the first node in the graph.
        After that, iterates on all the nodes in the graph and checks if all of them have been visited,
        if not then return false.
         */
        if (!g.getV().isEmpty()) {
            node_info source = g.getV().iterator().next();
            BFS(g, source);
            if (g.nodeSize() != visited.size()) return false;
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
           /*
        Checks if the graph is not empty and if the two nodes are in the graph.
        then runs the BFS algorithm on the source node and returns the tag of the destination.
         */
        node_info source = g.getNode(src);
        node_info destination = g.getNode(dest);
        if (!g.getV().isEmpty() && g.getV().contains(source) && g.getV().contains(destination)) {
            Dijkstra(g, source);
            if (destination.getTag() < Double.POSITIVE_INFINITY) {
                return destination.getTag();
            }
        }
        return -1;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }

    public static void main(String args[]) {
        node_info n0 = new WGraph_DS.NodeData(0);
        node_info n1 = new WGraph_DS.NodeData(1);
        node_info n2 = new WGraph_DS.NodeData(2);
        WGraph_DS g1 = new WGraph_DS();
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.addNode(4);

        g1.connect(0, 1, 2);
        g1.connect(1, 2, 6.2);
        g1.connect(1, 4, 6.4);
        g1.connect(2, 3, 2);
        g1.connect(4, 3, 1);

        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(g1);
//        System.out.println(g2.isConnected());
//        System.out.println(g2.shortestPathDist(0, 3));
        weighted_graph_algorithms g3 = new WGraph_Algo();
        g3.init(g2.copy());
        System.out.println(g3.isConnected());
        System.out.println(g2.shortestPathDist(0, 3));
    }
}
