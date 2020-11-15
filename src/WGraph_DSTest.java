import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    /*
    Basic tests for WGraph class
     */
    node_info n0 = new WGraph_DS.NodeData(0);
    node_info n1 = new WGraph_DS.NodeData(1);
    node_info n2 = new WGraph_DS.NodeData(2);
    WGraph_DS g1 = new WGraph_DS();

    @Test
    void getNode() {
        g1.addNode(0);
        node_info actual = n0;
        node_info expected = g1.getNode(0);
        assertEquals(expected, actual);
    }

    @Test
    void hasEdge() {
        g1.addNode(0);
        g1.addNode(1);
        g1.connect(0, 1, 5.5);
        assertTrue(g1.hasEdge(0, 1));
    }

    @Test
    void getEdge() {
        g1.addNode(0);
        g1.addNode(1);
        g1.connect(0, 1, 5.5);
        double expected = 5.5;
        double actual = g1.getEdge(0, 1);
        assertEquals(expected, actual);
    }

    @Test
    void addNode() { //Is it a good test?
        g1.addNode(0);
        int expected = 0;
        int actual = g1.getNode(0).getKey();
        assertEquals(expected, actual);
    }

    @Test
    void connect() {
        g1.addNode(0);
        g1.addNode(1);
        g1.connect(0, 1, 10.5);
        assertTrue(g1.hasEdge(0, 1));
    }

    @Test
    void getV() {
        g1.addNode(0);
        g1.addNode(1);
        String expected = "[{key=0}, {key=1}]";
        String actual = g1.getV().toString();
        assertEquals(expected, actual);
    }

    @Test
    void testGetV() {
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.connect(0, 1, 10);
        g1.connect(0, 2, 20);
        g1.connect(1, 2, 30);

        String expected = "[{key=1}, {key=2}]";
        String actual = g1.getV(0).toString();
        assertEquals(expected, actual);
    }

    @Test
    void removeNode() {
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.removeNode(2);
        String expected = "[{key=0}, {key=1}]";
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
    void nodeSize() {
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        int expected = 3;
        int actual = g1.nodeSize();
        assertEquals(expected, actual);
    }

    @Test
    void edgeSize() {
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
}