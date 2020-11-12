import java.util.Collection;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph {

    private int key; //id
    private double weight;
    private int mc; //Mode Count - for testing changes in the graph.
    private int countEdges ; //The total number of edges in the graph,
    public HashMap<Integer, NodeData> vertices;
    private HashMap<NodeData, NodeData> verNei = new HashMap<>();
    private HashMap<NodeData, Double> edges = new HashMap<>();

    public WGraph_DS() {
        this.mc = 0;
        this.countEdges  = 0;
        this.vertices = new HashMap<Integer, NodeData>();
    }

    public static class NodeData implements node_info{
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

    @Override
    public NodeData getNode(int key) {
        if (!vertices.containsKey(key)) {
            return null;
        }
        return vertices.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        /*
        The method checks if both of the edges exist in the graph,
        then it checks if node1 is a neighbor of node2 and vice versa.
         */
        boolean flag = false;
        NodeData a = getNode(node1);
        NodeData b = getNode(node2);
        if (a == null || b == null) {
            return false;
        }
        if (a.hasNi(b.getKey()) && b.hasNi(a.getKey())) {
            flag = true;
        }
        return flag;
    }

    @Override
    public double getEdge(int node1, int node2) {
        NodeData n1 = getNode(node1);
        NodeData n2 = getNode(node2);

        if(n1.hasNi(node2)){
            if(edges.get(n1) != null){
                return edges.get(n1);
            }
            else {
                return edges.get(n2);
            }
        }

        return -1;
    }

    @Override
    public void addNode(int key) {
        if(!vertices.containsKey(key)){
            NodeData temp = new NodeData(key);
            vertices.put(key, temp);
            mc++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 != node2) {
            NodeData a = getNode(node1);
            NodeData b = getNode(node2);
            if(!hasEdge(node1, node2)) {
                a.addNi(b);
                b.addNi(a);
                verNei.put(a, b);
                edges.put(b, w);
                mc++;
                countEdges += 1;
            }
            else{
                edges.replace(b, w); //MC OR NOT???
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        return vertices.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        return null;
    }

    @Override
    public node_info removeNode(int key) {
        return null;
    }

    @Override
    public void removeEdge(int node1, int node2) {

    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }

    public static void main(String args[]){
        NodeData n0 = new NodeData(0);
        NodeData n1 = new NodeData(1);
        NodeData n2 = new NodeData(2);
        WGraph_DS g1 = new WGraph_DS();
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.connect(0, 1, 10);

        System.out.println("n0: "+ g1.getNode(0));
        System.out.println("n0: "+ n0.getKey());
        System.out.println("n0 and n1 has edge: "+ g1.hasEdge(0, 1));
        System.out.println("n0 and n1 has edge: "+ g1.hasEdge(0, 1));
        System.out.println("n0 and n1 get edge: "+ g1.getEdge(0, 1));
        g1.connect(0, 1, 15.5);
        System.out.println("n0 and n1 get edge(2): "+ g1.getEdge(0, 1));

    }
}
