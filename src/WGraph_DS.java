import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class WGraph_DS implements weighted_graph {

    private int key; //id
    private double weight;
    private int mc; //Mode Count - for testing changes in the graph.
    private int countEdges; //The total number of edges in the graph,
    private HashMap<Integer, node_info> vertices;
    private HashMap<Integer, Double> edges = new HashMap<>();

    public WGraph_DS() {
        this.mc = 0;
        this.countEdges = 0;
        this.vertices = new HashMap<Integer, node_info>();
    }

    public static class NodeData implements node_info {
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
            this.key = key;
            this.tag = -1;
            this.info = null;
            this.nei = new HashMap<>();
        }

        /**
         * This (private) method returns the hash map.
         *
         * @return the hash map.
         */
        private HashMap<Integer, node_info> getMap() {
            return this.nei;
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
         * Key setter method, allows changing the remark (meta data) associated with this node.
         *
         * @param key the node id.
         */
        public void setKey(int key) {
            this.key = key;
        }

        /**
         * The method returns a collection with all the neighbor nodes of this node_data.
         *
         * @return the neighbors of this node.
         */
        public Collection<node_info> getNi() {
            return this.nei.values();
        }

        /**
         * The method checks if two nodes are adjacent.
         *
         * @param key the node id.
         * @return true iff this<==>key are adjacent.
         */
        public boolean hasNi(int key) {
            for (node_info ni : this.nei.values()) {
                if (ni.getKey() == key) return true;
            }
            return false;
        }

        /**
         * The method adds a node to be a new neighbor of this.
         *
         * @param t the node_data that will adds to this node_data.
         */
        public void addNi(node_info t) {
            //Adds t to this nei
            this.nei.put(t.getKey(), t);
            NodeData temp = (NodeData) t;
            //Adds this to temp(t) nei
            temp.getMap().put(this.getKey(), this);
        }

        /**
         * The method removes the node from this-collection.
         *
         * @param node the node the will be deleted.
         */
        public void removeNode(node_info node) {
            this.nei.remove(node.getKey(), node);
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
    }

    private int generateEdgeKey(int node1, int node2) {
        String temp = String.valueOf(node1) + String.valueOf(node2);
        int edgeKey = Integer.parseInt(temp);
        return edgeKey;
    }

    private int getEdgeKey(int node1, int node2) {
        int edgeKey;
        edgeKey = generateEdgeKey(node1, node2);
        if (edges.containsKey(edgeKey)) {
            return edgeKey;
        }
        edgeKey = generateEdgeKey(node2, node1);
        if (edges.containsKey(edgeKey)) {
            return edgeKey;
        }
        return -1;
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
        int edgeKey = getEdgeKey(node1, node2);
        if(edgeKey >= 0) flag=true;

        return flag;
    }

    @Override
    public double getEdge(int node1, int node2) {
        node_info n1 = getNode(node1);
        node_info n2 = getNode(node2);
        int edgeKey = getEdgeKey(node1, node2);

        if (hasEdge(node1, node2)) {
            return edges.get(edgeKey);
        }

        return -1;
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
            node_info a = getNode(node1);
            node_info b = getNode(node2);
            NodeData v1 = (NodeData) a;
            NodeData v2 = (NodeData) b;
            int edgeKey;
            edgeKey = generateEdgeKey(node1, node2);

            if (!hasEdge(node1, node2)) {
                v1.addNi(v2);
                v2.addNi(v1);
                edges.put(edgeKey, w);
                mc++;
                countEdges += 1;
            }
            else {
                    edgeKey = getEdgeKey(node1, node2);
                    edges.replace(edgeKey, w);
                mc++;
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        return vertices.values();
    }

    private Collection<Double> getE() {
        return edges.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        node_info a = getNode(node_id);
        NodeData v = (NodeData) a;
        return v.getNi();
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
            node_info a = getNode(key);
            NodeData b = (NodeData) a;
            int edgeKey;
            Collection<node_info> getV = b.getNi();
            Iterator<node_info> v = getV.iterator();
            while (v.hasNext()) {
                NodeData t = (NodeData) v.next();
//                removeEdge(key, t.getKey());
                v.remove();
                b.removeNode(t);
                t.removeNode(b);
                edgeKey = getEdgeKey(b.getKey(), t.getKey());
                edges.remove(edgeKey);
                countEdges--;
                mc++;
            }
            this.vertices.remove(key, a);
            mc++;
            return a;
        }
        return null;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if (((node1 != node2)) && (hasEdge(node1, node2))) {
            node_info a = getNode(node1);
            node_info b = getNode(node2);
            NodeData v1 = (NodeData) a;
            NodeData v2 = (NodeData) b;
            int edgeKey;
            edgeKey = generateEdgeKey(node1, node2);
            v1.removeNode(v2);
            v2.removeNode(v1);
            if (edges.containsKey(edgeKey)) {
                edges.remove(edgeKey);
            } else {
                edgeKey = generateEdgeKey(node2, node1);
                edges.remove(edgeKey);
            }
            countEdges -= 1;
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
        WGraph_DS g1 = new WGraph_DS();
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.connect(0, 1, 10);
        g1.connect(0, 2, 15);
        g1.connect(1, 2, 20);


        System.out.println("E: " + g1.getE());
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
        System.out.println("E: " + g1.getE());
//        g1.removeNode(0);
        g1.removeNode(1);
//        g1.removeNode(2);
        System.out.println("n1 and n0 has edge: " + g1.hasEdge(1, 0));
        System.out.println("V: " + g1.getV());
        System.out.println("E: " + g1.getE());
    }
}
