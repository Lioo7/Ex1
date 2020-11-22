import org.junit.jupiter.api.Test;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest_L {
    node_info n0 = new WGraph_DS.NodeData(0);
    node_info n1 = new WGraph_DS.NodeData(1);
    node_info n2 = new WGraph_DS.NodeData(2);
    node_info n3 = new WGraph_DS.NodeData(3);
    WGraph_DS g1 = new WGraph_DS();

    @Test
        // Tests a node
    void getNode1() {
        g1.addNode(0);
        node_info actual = g1.getNode(0);
        node_info expected = g1.getNode(0);
        assertEquals(expected, actual);
    }

    @Test
        // Tests a node that does not exist
    void getNode2() {
        node_info actual = null;
        node_info expected = null;
        assertEquals(expected, actual);
    }

    @Test
        // Tests the first direction
    void hasEdge1() {
        g1.addNode(0);
        g1.addNode(1);
        g1.connect(0, 1, 5.5);
        assertTrue(g1.hasEdge(0, 1));
    }

    @Test
        // Tests the second direction
    void hasEdge2() {
        g1.addNode(0);
        g1.addNode(1);
        g1.connect(0, 1, 5.5);
        assertTrue(g1.hasEdge(1, 0));

    }

    @Test
        // Tests an edge that does not exist
    void hasEdge3() {
        g1.addNode(0);
        g1.addNode(1);
        g1.connect(0, 1, 5.5);
        assertFalse(g1.hasEdge(2, 1));
    }

    @Test
        // Tests the value of an edge
    void getEdge1() {
        g1.addNode(0);
        g1.addNode(1);
        g1.connect(0, 1, 5.5);
        double expected = 5.5;
        double actual = g1.getEdge(0, 1);
        assertEquals(expected, actual);
    }

    @Test
        // Tests the value of an edge after an update
    void getEdge2() {
        g1.addNode(0);
        g1.addNode(1);
        g1.connect(0, 1, 5.5);
        g1.connect(0, 1, 12.3);
        double expected = 12.3;
        double actual = g1.getEdge(0, 1);
        assertEquals(expected, actual);
    }

    @Test
        // Tests the value of an edge that does not exist
    void getEdge3() {
        g1.addNode(0);
        g1.addNode(1);
        double expected = -1;
        double actual = g1.getEdge(0, 1);
        assertEquals(expected, actual);
    }

    @Test
    void addNode1() {
        g1.addNode(0);
        int expected = 0;
        int actual = g1.getNode(0).getKey();
        assertEquals(expected, actual);
    }

    @Test
        // Creates a graph with 100K nodes
    void addNode2() {
        int v = 100000, e = v * 0;
        weighted_graph g = graph_creator(v, e);
        int actual = g.nodeSize();
        assertEquals(v, actual);
    }

    @Test
        //Tests the connect method between two existing  nodes
    void connect1() {
        g1.addNode(0);
        g1.addNode(1);
        g1.connect(0, 1, 10.5);
        assertTrue(g1.hasEdge(0, 1));
    }

    @Test
        //Tests the connect method when one of the nodes does not exist
    void connect2() {
        g1.addNode(0);
        g1.connect(0, 1, 10.5);
        assertFalse(g1.hasEdge(0, 1));
    }

    @Test
        // Creates a graph with 100K nodes and million random edges
    void connect3() {
        int v = 100000, e = v * 10;
        weighted_graph g = graph_creator(v, e);
        int actual = g.edgeSize();
        assertEquals(e, actual);

    }

    @Test
        //Tests the getV method when there are two nodes in the graph
    void getV1() {
        g1.addNode(0);
        g1.addNode(1);
        String expected = "[{key=0}, {key=1}]";
        String actual = g1.getV().toString();
        assertEquals(expected, actual);
    }

    @Test
        //Tests the getV method when the graph is empty
    void getV2() {
        String expected = "[]";
        String actual = g1.getV().toString();
        assertEquals(expected, actual);
    }

    @Test
        //Tests the getV method when there are two neighbors to the given node
    void testGetV1() {
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.connect(0, 1, 01);
        g1.connect(0, 2, 02);
        g1.connect(1, 2, 12);

        String actual = g1.getV(0).toString();
        String expected = "[{key=1}, {key=2}]";
        assertEquals(expected, actual);
    }

    @Test
        //Tests the getV method when the graph is empty
    void testGetV2() {

        String actual = g1.getV(0).toString();
        String expected = "[]";
        assertEquals(expected, actual);
    }

    @Test
        //Tests the getV method when there are not any neighbors to the given node
    void testGetV3() {
        g1.addNode(0);

        String actual = g1.getV(0).toString();
        String expected = "[]";
        assertEquals(expected, actual);
    }

    @Test
        //Basic test
    void removeNode1() {
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.removeNode(2);
        String expected = "[{key=0}, {key=1}]";
        String actual = g1.getV().toString();
        assertEquals(expected, actual);
    }

    @Test
        //Tests the removeNode method when the graph is empty
    void removeNode2() {
        g1.removeNode(2);
        String expected = "[]";
        String actual = g1.getV().toString();
        assertEquals(expected, actual);
    }

    @Test
    void removeEdge() {
        g1.addNode(0);
        g1.addNode(1);
        g1.connect(0, 1, 10);
        g1.removeEdge(0, 1);
        assertTrue(!g1.hasEdge(0, 1));
    }

    @Test
        // Basic test
    void nodeSize1() {
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        int expected = 3;
        int actual = g1.nodeSize();
        assertEquals(expected, actual);
    }

    @Test
        // Tests the nodeSize method when the graph is empty
    void nodeSize2() {
        int expected = 0;
        int actual = g1.nodeSize();
        assertEquals(expected, actual);
    }

    @Test
        // Basic test
    void edgeSize1() {
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.connect(0, 1, 10);
        g1.connect(1, 2, 30);
        int expected = 2;
        int actual = g1.edgeSize();
        assertEquals(expected, actual);
    }

    @Test
        // Tests the nodeSize method when the graph is empty
    void edgeSize2() {
        int expected = 0;
        int actual = g1.edgeSize();
        assertEquals(expected, actual);
    }

    @Test
        // Tests the getMc function after made some cahnges in the graph
    void getMC() {
        g1.addNode(0); //1
        g1.addNode(1); //2
        g1.addNode(2); //3
        g1.connect(0, 1, 10); //4
        g1.connect(1, 2, 30); //5
        g1.connect(2, 1, 30.5); //6
        g1.removeEdge(1, 0); //7
        g1.removeNode(1); //8 + 9
        int expected = 9;
        int actual = g1.getMC();
        assertEquals(expected, actual);
    }

    /**
     * This function gets num if V and E and creates a random graph
     * @param v_size number of vertices
     * @param e_size number of edges
     * @return a random graph
     */
    public static weighted_graph graph_creator(int v_size, int e_size) {
        // Creating the nodes in the range from 0 to the given v_size and adding them to the graph
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < v_size; i++) {
            g.addNode(i);
        }

        // Keep running while the amount of edges in the graph is smaller than e_size
        while (g.edgeSize() < e_size) {
            // Generates a random integer in the rang of 0 to v_size
            int v1 = ThreadLocalRandom.current().nextInt(0, v_size);
            int v2 = ThreadLocalRandom.current().nextInt(0, v_size);
            // Generates a random double in the rang of 0 to v_size
            double w = ThreadLocalRandom.current().nextDouble(0, v_size);
            g.connect(v1, v2, w);
        }
        return g;
    }
}