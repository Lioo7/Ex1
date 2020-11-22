import java.io.Serializable;
import java.util.*;

/**
 * The WGraph_DS class represents a unidirectional weighted graph.
 * Using a HashMap to store all the vertices in the graph.
 * and another HashMap inside another HashMap to store all the edges in the graph.
 *
 * @author Lioz Akirav.
 * @version 1.0, 22 Nov 2020.
 */

public class WGraph_DS implements weighted_graph, Serializable {

    private int key; //id
    private int mc; //Mode Count - for testing changes in the graph.
    private int countEdges; //The total number of edges in the graph,
    private HashMap<Integer, node_info> vertices; //An HashMap which contains all the vertices
    private HashMap<Integer, HashMap<Integer, Double>> edges; //An HashMap which contains all the edges


    public WGraph_DS() {
        this.mc = 0;
        this.countEdges = 0;
        this.vertices = new HashMap<>();
        this.edges = new HashMap<Integer, HashMap<Integer, Double>>();
    }

    // A subclass which implements node_info
    public static class NodeData implements node_info, Serializable {
        /**
         * The NodeData class represents the set of operations applicable on a
         * node (vertex) in an (unidirectional) weighted graph.
         * Using a HashMap to store the id and the neighbors of each vertex.
         * Each vertex contains three variables:
         * - A key of type int which represents the exclusive key of the vertex.
         * - An info of type String which represents the parent of the vertex, initializes as null by default.
         * - A tag of type int which represents the distance of the vertex from his source, initializes as infinity.
         *
         * @author Lioz Akirav.
         * @version 1.0, 22 Nov 2020.
         */

        private int key; //id
        private String info; //parent
        private double tag; //distance
        //Creates a collection using hash map
        private HashMap<Integer, node_info> nei;

        public NodeData(int key) {
            //Throwing an exception if gets a negative key
            if (key < 0) throw new RuntimeException("key must be a positive number!\nyou used: " + key);
            this.key = key;
            this.tag = Double.POSITIVE_INFINITY;
            this.info = null;
            this.nei = new HashMap<>();
        }

        /**
         * key getter method.
         *
         * @return the key (id) associated with this node.
         */
        @Override
        public int getKey() {
            return key;
        }


        /**
         * info getter method.
         *
         * @return the info (parent) associated with this node
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * info setter method allows changing the remark (metadata) associated with this node.
         *
         * @param s the node info (parent).
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * tag getter method.
         *
         * @return the tag (distance) associated with this node
         */
        @Override
        public double getTag() {
            return this.tag;
        }

        /**
         * tag setter method allows changing the remark (metadata) associated with this node.
         *
         * @param t - the node tag (distance).
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        /**
         * toString method.
         *
         * @return the key of the node.
         */
        public String toString() {
            return "{" + "key=" + key + '}';
        }
    }

    /**
     * Node getter method which returns the node_data by the node_id.
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        if (!vertices.containsKey(key)) {
            return null;
        }
        return vertices.get(key);
    }

    /**
     * The method checks if there is an edge between node1 and node2.
     * Complexity: O(1) - HashMap access complexity.
     *
     * @param node1 the key(id) of the first node.
     * @param node2 the key(id) of the second node.
     * @return true iff there is an edge between node1 and node2.
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        boolean flag = false;
        if (edges.get(node1) != null) { //Checks if the first node exist in edges
            if (edges.get(node1).containsKey(node2)) { //Checks if the second node exist in the inner HashMap
                flag = true;
            }
        }
        return flag;
    }

    /**
     * The method returns the weight of the edge if it exists.
     * Complexity: O(1) - HashMap access complexity.
     *
     * @param node1 the key(id) of the first node.
     * @param node2 the key(id) of the second node.
     * @return the weight of the edge if exists or -1 if not
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            return edges.get(node1).get(node2);
        } else {
            return -1;
        }
    }

    /**
     * The method adds a new node to the graph with the given node_data.
     * Complexity: O(1) - hash map add complexity.
     *
     * @param key the key of the node
     */
    @Override
    public void addNode(int key) {
        if (!vertices.containsKey(key)) { //Checks if the node does not exist already
            node_info temp = new NodeData(key);
            vertices.put(key, temp); //Adds the node
            //Creates a new inner HashMap inside edges that will contain the neighbors of this node
            edges.put(key, new HashMap<Integer, Double>());
            mc++;
        }
    }

    /**
     * The method creates an edge between node1 and node2.
     * Complexity: O(1) - hash map access/add complexity.
     * @param node1 the key(id) of the first node.
     * @param node2 the key(id) of the second node.
     * @param w     the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 != node2 && w >= 0 && vertices.containsKey(node1) && vertices.containsKey(node2)) {
            /*
            If not exists an edge between the nodes,
            creates one by adding node2 to the inner HashMap (list of neighbors) of node1 and vice versa
             */
            if (!hasEdge(node1, node2)) {
                edges.get(node1).put(node2, w);
                edges.get(node2).put(node1, w);

                mc++;
                countEdges++;
            }
            /*
            If already exists an edge between the nodes and its weight is different from the current weight,
            then update the wight in the inner HashMap of node1 and node2
             */
            else if (edges.get(node1).get(node2) != w) {
                edges.get(node1).replace(node2, w);
                edges.get(node2).replace(node1, w);

                mc++;
            }
        }
    }

    /**
     * The method returns a pointer (shallow copy) for the collection representing all the nodes in the graph.
     * Complexity: O(1) - hash map access complexity is O(1).
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return vertices.values();
    }

    /**
     * The method returns a collection containing all the nodes connected to node_id.
     * Complexity: O(k) - hash map access complexity is O(1) *  k which represents the degree of node_id.
     *
     * @param node_id the key(id) of the node.
     * @return Collection<node_data>.
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        /*
        The function checks if exists any edge which associated to the given node_id
        If yes, then iterates all this edges and adds the neighbors of this node to
        a new ArrayList, then return that list which contains all the neighbors of the node
         */
        ArrayList<node_info> neighborsArray = new ArrayList<node_info>();
        if (edges.containsKey(node_id)) {
            for (Integer key : edges.get(node_id).keySet()) {
                neighborsArray.add(getNode(key));
            }
        }
        return neighborsArray;
    }

    /**
     * The method deletes the node (with the given ID) from the graph -
     * and removes all edges which start or end at this node.
     * Complexity: O(n) - HashMap access complexity is O(1) * n nodes.
     *
     * @param key the key(id) of the node.
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
      /*
        The method checks if the node exists in the graph,
        if yes: the method will remove all edges which starts or ends at this node,
        and then will remove the node from the hash map.
        else: the method will return null.
         */
        if ((getNode(key) != null) && (vertices.containsKey(key))) {
            if (edges.containsKey(key)) {
                Iterator<node_info> v = getV(key).iterator();
                while (v.hasNext()) {
                    node_info t = v.next();
                    if (hasEdge(t.getKey(), key)) {
                        removeEdge(t.getKey(), key);
                    }
                }
            }
            node_info removed = getNode(key);
            this.vertices.remove(key);
            mc++;
            return removed;
        }
        return null;
    }

    /**
     * The method deletes the edge between node1 and node2 from the graph.
     * Complexity: O(1) - hash map delete complexity.
     *
     * @param node1 the key(id) of the first node which connects to the edge.
     * @param node2 the key(id) of the second node which connects to the edge.
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (((node1 != node2)) && (hasEdge(node1, node2))) {
            // Removes the edge from the HashMap
            edges.get(node1).remove(node2);
            edges.get(node2).remove(node1);
            countEdges--;
            mc++;
        }
    }

    /**
     * nodeSize getter method.
     * Complexity: O(1) - hash map access complexity.
     * @return the method returns the number of vertices (nodes) in the graph.
     */
    @Override
    public int nodeSize() {
        return vertices.size();
    }

    /**
     * edgeSize getter method.
     * Complexity: O(1) - hash map access complexity.
     *
     * @return the method returns the number of edges in the graph.
     */
    @Override
    public int edgeSize() {
        return countEdges;
    }

    /**
     * getMc getter method.
     *
     * @return the method returns the Mode Count - for testing changes in the graph.
     */
    @Override
    public int getMC() {
        return mc;
    }

    /**
     * This Method checks if the given graph is equals to the current graph
     * @param obj a graph
     * @return true if both of the graphs are equals or false if not
     */
    @Override
    public boolean equals(Object obj) {
        /*
        This comparison checks if both if the objects have the same size of nodes and edges
         */
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WGraph_DS copy = (WGraph_DS) obj;

        if (this.nodeSize() == copy.nodeSize() && this.edgeSize() == copy.edgeSize()) {
            return true;
        }
        return false;
    }

    public static void main(String args[]) {
        node_info n0 = new NodeData(0);
        node_info n1 = new NodeData(1);
        node_info n2 = new NodeData(2);
        node_info n12 = new NodeData(12);
        node_info n20 = new NodeData(20);
        WGraph_DS g1 = new WGraph_DS();
        g1.addNode(0);
        System.out.println("V0: " + g1.getV(0));
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(12);
        g1.addNode(20);
        g1.connect(0, 1, 10);
        g1.connect(0, 1, 10.1);
        g1.connect(0, 2, 20);
        g1.connect(1, 2, 21);
        g1.connect(12, 0, 120);
        g1.connect(1, 20, 201);

        System.out.println("n0: " + g1.getNode(0));
        System.out.println("n0: " + n0.getKey());
        System.out.println("n0 and n1 has edge: " + g1.hasEdge(0, 1));
        System.out.println("n1 and n0 has edge: " + g1.hasEdge(1, 0));
        System.out.println("n0 and n1 get edge(1): " + g1.getEdge(0, 1));
        g1.connect(1, 0, 15.5);
        System.out.println("n0 and n1 get edge(2): " + g1.getEdge(0, 1));
        g1.connect(2, 1, 20);
        System.out.println("n1 and n2 get edge(3): " + g1.getEdge(2, 1));
        g1.connect(1, 2, 30);
        System.out.println("n1 and n2 get edge(4): " + g1.getEdge(1, 2));
        System.out.println("n1 and n2 get edge(3): " + g1.getEdge(2, 1));
        System.out.println("n0 neighbors: " + g1.getV(0));
        g1.removeEdge(2, 1);
        System.out.println("n1 and n2 get edge(4): " + g1.getEdge(1, 2));
        System.out.println("n1 and n2 get edge(3): " + g1.getEdge(2, 1));
        System.out.println("V: " + g1.getV());
//        g1.removeNode(0);
        g1.removeNode(1);
//        g1.removeNode(2);
        System.out.println("n1 and n0 has edge: " + g1.hasEdge(1, 0));
        System.out.println("V: " + g1.getV());
    }
}
