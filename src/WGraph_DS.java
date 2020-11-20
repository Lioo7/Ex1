import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {

    private int key; //id
    private int mc; //Mode Count - for testing changes in the graph.
    private int countEdges; //The total number of edges in the graph,
    private HashMap<Integer, node_info> vertices;
    private HashMap<Integer, HashMap<Integer, Double>> edges;

    public WGraph_DS() {
        this.mc = 0;
        this.countEdges = 0;
        this.vertices = new HashMap<>();
        this.edges = new HashMap<Integer, HashMap<Integer, Double>>();
    }

    public static class NodeData implements node_info, Serializable {
        /**
         * The NodeData class represents the set of operations applicable on a
         * node (vertex) in an (unidirectional) unweighted graph.
         * Using a HashMap to store the id and the neighbors of each vertex.
         * Each vertex contains three variables:
         * - A key of type int which represents the exclusive key of the vertex, initializes automatically.
         * - An info of type String which represents the parent of the vertex, initializes as null by default.
         * - A tag of type int which represents the distance of the vertex form his source, initializes as -1 by default.
         * Each of these three variables has getters and setters methods.
         *
         * @author Lioz Akirav.
         * @version 1.0, 12 Nov 2020.
         */

        private int key; //id
        private String info; //parent
        private double tag; //distance
        //Creates a collection using hash map
        private HashMap<Integer, node_info> nei;

        public NodeData(int key) {
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
         * info setter method, allows changing the remark (meta data) associated with this node.
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
         * tag setter method, allows changing the remark (meta data) associated with this node.
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
         * @return the neighbours of the node.
         */
        public String toString() {
            return "{" + "key=" + key + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeData nodeData = (NodeData) o;
            return key == nodeData.key &&
                    Double.compare(nodeData.tag, tag) == 0 &&
                    Objects.equals(info, nodeData.info);
//                    && Objects.equals(nei, nodeData.nei);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, info, tag, nei);
        }
    }

    @Override
    public node_info getNode(int key) {
        if (!vertices.containsKey(key)) {
            return null;
        }
        return vertices.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        boolean flag = false;
        if (edges.get(node1) != null) {
            if (edges.get(node1).containsKey(node2)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            return edges.get(node1).get(node2);
        } else {
            return -1;
        }
    }

    @Override
    public void addNode(int key) {
        if (!vertices.containsKey(key)) {
            node_info temp = new NodeData(key);
            vertices.put(key, temp);
            mc++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 != node2) {
            if (!hasEdge(node1, node2)) {
                if (edges.get(node1)==null && edges.get(node2)==null) {
                    HashMap<Integer, Double> value1 = new HashMap<>();
                    value1.put(node2, w);
                    edges.put(node1, value1);

                    HashMap<Integer, Double> value2 = new HashMap<>();
                    value2.put(node1, w);
                    edges.put(node2, value2);

                    mc++;
                    countEdges++;
                }

                else if(edges.get(node1)==null && edges.get(node2)!=null) {
                    HashMap<Integer, Double> value1 = new HashMap<>();
                    value1.put(node2, w);
                    edges.put(node1, value1);

                    edges.get(node2).put(node1, w);

                    mc++;
                    countEdges++;
                }

                else if(edges.get(node1)!=null && edges.get(node2)==null) {
                    edges.get(node1).put(node2, w);

                    HashMap<Integer, Double> value2 = new HashMap<>();
                    value2.put(node1, w);
                    edges.put(node2, value2);

                    mc++;
                    countEdges++;
                }

                else if (edges.get(node1)!=null && edges.get(node2)!=null) {
                    edges.get(node1).put(node2, w);
                    edges.get(node2).put(node1, w);

                    mc++;
                    countEdges++;
                }
            }
            else {
                if (edges.containsKey(node1) && edges.get(node1).containsKey(node2) && edges.get(node1).get(node2) != w) {
                    HashMap<Integer, Double> value1 = edges.get(node1);
                    value1.put(node2, w);
                    edges.replace(node1, value1);

                    HashMap<Integer, Double> value2 = edges.get(node2);
                    value2.put(node1, w);
                    edges.replace(node2, value2);

                    mc++;
                }
            }
        }
    }


    @Override
    public Collection<node_info> getV() {
        return vertices.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        ArrayList<node_info> neighborsArray = new ArrayList<node_info>();
        if (edges.containsKey(node_id)) {
            for (Integer key : edges.get(node_id).keySet()) {
                neighborsArray.add(getNode(key));
            }
        }
        return neighborsArray;
    }

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

    @Override
    public void removeEdge(int node1, int node2) {
        if (((node1 != node2)) && (hasEdge(node1, node2))) {
            edges.get(node1).remove(node2);
            edges.get(node2).remove(node1);
            countEdges--;
            mc++;
        }
    }

    @Override
    public int nodeSize() {
        return vertices.size();
    }

    @Override
    public int edgeSize() {
        return countEdges;
    }

    @Override
    public int getMC() {
        return mc;
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
