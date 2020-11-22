import java.io.*;
import java.util.*;

/**
 * The WGraph_Algo class represents an Undirected (positive) Weighted Graph Theory algorithms including:
 * * 0. clone(); (copy)
 * * 1. init(graph);
 * * 2. isConnected();
 * * 3. double shortestPathDist(int src, int dest);
 * * 4. List<node_data> shortestPath(int src, int dest);
 * * 5. Save(file);
 * * 6. Load(file);
 *
 * @author Lioz Akirav.
 * @version 1.0, 22 Nov 2020.
 */

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph g;
    //Creates a HashSet which contains all the visited nodes.
    private HashSet<node_info> visited = new HashSet<>();

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

    /**
     * The method inits the graph on which this set of algorithms operates on.
     *
     * @param g the graph.
     */
    @Override
    public void init(weighted_graph g) {
        this.g = g;
    }

    /**
     * This method init the graph on which this set of algorithms operates on.
     * @return the graph
     */
    @Override
    public weighted_graph getGraph() {
        return this.g;
    }

    /**
     * The method computes a deep copy of this graph.
     *
     * @return copy of the graph.
     */
    @Override
    public weighted_graph copy() {
         /*
        The method creates a new graph, then copies all the nodes of the original graph to the new graph.
        Then iterates on all the neighbors of each node and creates a new edge in the new graph.
         */
        weighted_graph copy = new WGraph_DS();
        Collection<node_info> getV = g.getV();

        for (node_info original : getV) {
            int key = original.getKey();
            WGraph_DS.NodeData temp = new WGraph_DS.NodeData(key);
            temp.setInfo(original.getInfo());
            temp.setTag(original.getTag());
            copy.addNode(temp.getKey());
        }

        Iterator<node_info> v = getV.iterator();
        while (v.hasNext()) {
            WGraph_DS.NodeData original = (WGraph_DS.NodeData) v.next();
            for (node_info ni : g.getV(original.getKey())) {
                if (g.hasEdge(original.getKey(), ni.getKey()) && !copy.hasEdge(original.getKey(), ni.getKey())) {
                    double weight = g.getEdge(original.getKey(), ni.getKey());
                    copy.connect(original.getKey(), ni.getKey(), weight);
//                    System.out.println("Weight: " + weight);
                }
            }
        }
        return copy;
    }

    /**
     * The Breadth-first search (BFS) is an algorithm for traversing or searching
     * tree or graph data structures. It starts at the given node in the graph,
     * and explores all of the neighbor nodes at the present depth prior to moving on
     * to the nodes at the next depth level.
     *
     * @param g      the graph which the search will run.
     * @param source the node from which the search will start.
     */
    private void BFS(weighted_graph g, node_info source) {
        //Clears the visited HashSet.
        visited.clear();
        /*
        Creates a queue which will contain the nodes that need to traverse (by their order).
         */
        Queue<node_info> queue = new LinkedList<node_info>();
        queue.add(source);

        /*
        While the queue is not empty, the algorithm takes the first node and traverses all its neighbors.
        If this neighbor is not yet visited, it adds to the queue and marks as visited.
        After the algorithm finishes gaining with all the neighbors, it marks the current node as visited
        and continues to the next node in the queue.
         */
        while (!queue.isEmpty()) {
            WGraph_DS.NodeData current = (WGraph_DS.NodeData) queue.poll();
            for (node_info temp : g.getV(current.getKey())) {
                if (!visited.contains(temp)) {
                    queue.add(temp);
                    visited.add(temp);
                }
            }
            visited.add(current);
        }
    }

    /**
     * The Dijkstra's algorithm is an algorithm for finding the shortest paths between nodes in a graph.
     * For a given source node in the graph, the algorithm finds the shortest path between that node and every other.
     *
     * @param g the graph
     * @param source the source node
     */
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
        Creates a priority queue which will contain the nodes that need to traverse.
        The priority queue ranks the nodes by their tag values from the greater to the lesser.
         */
        int initialCapacity = g.nodeSize();
        PriorityQueue<node_info> pq = new PriorityQueue<node_info>(initialCapacity, comparator);
        pq.add(source);

        /*
        While the p.queue is not empty, the algorithm takes the first node (if is not visited yet)
        marks it and traverses all its neighbors. If this neighbor is not yet visited,
        it adds to the p.queue and calculates its distance from the source.
        If its distance is smaller than its tag value, then sets its tag to be distance
        and set its info to contain the path from the source till this node. Then adds this node to the p.queue.
        After the algorithm finishes gaining with all the neighbors, it continues to the next node in the p.queue.
         */
        while (!pq.isEmpty()) {
            WGraph_DS.NodeData current = (WGraph_DS.NodeData) pq.poll();
            if (!visited.contains(current)) {
                visited.add(current);
                for (node_info temp : g.getV(current.getKey())) {
                    if (!visited.contains(temp)) {
                        double distance = current.getTag() + g.getEdge(current.getKey(), temp.getKey());
                        if (distance < temp.getTag()) {
                            temp.setTag(distance);
                            temp.setInfo(current.getInfo() + "-" + current.getKey() + "-");
                            pq.add(temp);
                        }
                    }
                }
            }

        }
    }

    /**
     * The method checks if the graph is connected or not.
     *
     * @return true if and only if (iff) there is a valid path from every node to each other node.
     */
    @Override
    public boolean isConnected() {
       /*
        Checks if the graph is not empty, then runs the BFS algorithm on the first node in the graph.
        After that, checks if all of the nodes have been visited by comparing the number of nodes in
        the graph to the number of the nodes that have been marked as visited.
        If they are not equals then return false.
         */
        if (!g.getV().isEmpty()) {
            node_info source = g.getV().iterator().next();
            BFS(g, source);
            if (g.nodeSize() != visited.size()) return false;
        }
        return true;
    }

    /**
     * The method returns the shortest distance between two given nodes.
     *
     * @param src  - start node.
     * @param dest - end (target) node.
     * @return the length of the shortest path between src to dest, if no such path --> returns -1.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
           /*
        Checks if the graph is not empty and if the two nodes are in the graph.
        then runs the Dijkstra algorithm on the source node and returns the tag of the destination
        only if its distance is lower than infinity.
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

    /**
     * The method gets a string and checks if its contains a number
     *
     * @param str a string
     * @return true id the string contains a number
     */
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * The method returns the shortest distance between two given nodes.
     *
     * @param src  - start node.
     * @param dest - end (target) node.
     * @return the length of the shortest path between src to dest, if no such path --> returns -1.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        //Creates an ArrayList which is used to contain the path.
        List<node_info> path = new ArrayList<node_info>();

        //Return the source if both of the src and the dest are equals.
        if (src == dest) {
            path.add(g.getNode(src));
            return path;
        }

        /*
        Calls the Dijkstra method to check if there exists a pathway between both of the given nodes.
        If the Dijkstra function returned a positive number, then adds all the numbers in the info of
        the destination node to the array (by calling isNumeric method).
        Then adds the destination node to the list and returns the path.
         */
        if (shortestPathDist(src, dest) > -1) {
            node_info destination = g.getNode(dest);
            String str = destination.getInfo();
            String arr[] = str.split("-");
            for (String temp : arr) {
                if (isNumeric(temp)) {
                    int key = Integer.valueOf(temp);
                    path.add(g.getNode(key));
                }
            }
            path.add(destination);
            return path;
        }
        return null;
    }

    /**
     * Saves this weighted (undirected) graph to the given file name.
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        boolean ans = false;
        try {
            File f = new File(file);
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.g);
            fileOutputStream.close();
            objectOutputStream.close();
            ans = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * This method load a graph to this graph algorithm.
     * If the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        boolean ans = false;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            weighted_graph loadGraph = (weighted_graph) objectInputStream.readObject();
            this.g = loadGraph;
            fileInputStream.close();
            objectInputStream.close();
            ans = true;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    /*
    A comparator which compares between two nodes, base on their tag values.
     */
    Comparator<node_info> comparator = new Comparator<node_info>() {
        @Override
        public int compare(node_info n1, node_info n2) {
            return (int) (n1.getTag() - n2.getTag());
        }
    };
}
