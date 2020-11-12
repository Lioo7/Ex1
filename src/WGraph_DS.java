import java.util.Collection;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph {

    private int mc; //Mode Count - for testing changes in the graph.
    private int countEdges ; //The total number of edges in the graph,
    private HashMap<Integer, NodeData> vertices;

    public WGraph_DS() {
        this.mc = 0;
        this.countEdges  = 0;
        this.vertices = new HashMap<>();
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

    public static class Edge {
        private int key; //id
        private double len; //length
        //Creates a collection using hash map
        private HashMap<Integer, Double> edges;

        public Edge(int ver1, int ver2, double len) {
            String str = String.valueOf(ver1) + String.valueOf(ver2);
            this.key = Integer.parseInt(str);
            this.len = len;
            this.edges = new HashMap<>();
        }

        /**
         * key getter method.
         *
         * @return the key (id) associated with this edge.
         */
        public int getKey() {
            return key;
        }

        public int getKey(int ver1, int ver2) {
            int key = Integer.parseInt(String.valueOf(ver2) + String.valueOf(ver1));
            if(edges.containsKey(key)){
                return key;
            }

            int key2 = Integer.parseInt(String.valueOf(ver2) + String.valueOf(ver1));
            if(edges.containsKey(key)){
                return key2;
            }
             // need to add throw exception
            return 0;
        }

        /**
         * length getter method.
         *
         * @return the length associated with this edge.
         */
        public double getLen() {
            return len;
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
        if(hasEdge(node1, node2)){
            grt
            return temp.getLen();
        }
        return 0; //throw exception
    }

    @Override
    public void addNode(int key) {

    }

    @Override
    public void connect(int node1, int node2, double w) {

    }

    @Override
    public Collection<node_info> getV() {
        return null;
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
        n0.addNi(n1);

        System.out.println("n0: "+ n0.getKey());
        System.out.println("n0 nei: "+ n0.getNi());

        Edge e0 = new Edge(1, 0, 5.5);
        System.out.println("e0: "+ e0.getKey());
        System.out.println("e0 len: "+ e0.getLen());
    }
}
