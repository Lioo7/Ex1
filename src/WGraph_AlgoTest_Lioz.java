import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest_Lioz {

    node_info n0 = new WGraph_DS.NodeData(0);
    node_info n1 = new WGraph_DS.NodeData(1);
    node_info n2 = new WGraph_DS.NodeData(2);
    weighted_graph g1 = new WGraph_DS();

    @Test
        // Basic test
    void copy() {
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
        weighted_graph g = new WGraph_DS();
        g = g2.copy();
        assertEquals(g, g1);
    }

    @Test
        // Basic test
    void isConnected1() {
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
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);
        assertTrue(ga.isConnected());
    }

    @Test
        // Basic test, the graph is connected
    void isConnected() {
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
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);
        assertTrue(ga.isConnected());
    }

    @Test
        // Basic test, the graph is not connected
    void isConnected2() {
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.addNode(4);
        g1.connect(1, 2, 6.2);
        g1.connect(1, 4, 6.4);
        g1.connect(2, 3, 2);
        g1.connect(4, 3, 1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);
        assertFalse(ga.isConnected());
    }

    @Test
        // Tests an empty graph
    void isConnected3() {
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);
        assertTrue(ga.isConnected());
    }

    @Test
        // Basic test
    void shortestPathDist1() {
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
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);

        double expected = 9.4;
        double actual = ga.shortestPathDist(0, 3);

        assertEquals(expected, actual);
    }

    @Test
        // Made a change in one of the edges in the graph
    void shortestPathDist2() {
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.addNode(4);
        g1.connect(0, 1, 2);
        g1.connect(1, 2, 6.2);
        g1.connect(1, 4, 6.4);
        g1.connect(2, 3, 2);
        g1.connect(4, 3, 3);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);

        double expected = 9.4;
        double actual = ga.shortestPathDist(0, 3);

        assertNotEquals(expected, actual);
    }

    @Test
        // Tests an empty graph
    void shortestPathDist3() {
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);

        double expected = -1;
        double actual = ga.shortestPathDist(0, 3);

        assertEquals(expected, actual);
    }


    @Test
        // Basic test
    void shortestPath1() {
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

        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);

        String expected = "[{key=0}, {key=1}, {key=4}, {key=3}]";
        String actual = ga.shortestPath(0, 3).toString();

        assertEquals(expected, actual);
    }

    @Test
        // Made a change in one of the edges in the graph
    void shortestPath2() {
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.addNode(4);
        g1.connect(0, 1, 2);
        g1.connect(1, 2, 6.2);
        g1.connect(1, 4, 6.4);
        g1.connect(2, 3, 2);
        g1.connect(4, 3, 3);

        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);

        String expected = "[{key=0}, {key=1}, {key=2}, {key=3}]";
        String actual = ga.shortestPath(0, 3).toString();

        assertEquals(expected, actual);
    }

    @Test
        // Tests an empty graph
    void shortestPath3() {
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);

        assertEquals(null, ga.shortestPath(0, 3));
    }

    @Test
        // Basic test
    void saveAndLoad() throws IOException {
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
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);
        String file_name = "tester.obj";
        ga.save(file_name);
        weighted_graph g2 = new WGraph_DS();
        weighted_graph_algorithms gb = new WGraph_Algo();
        gb.init(g2);
        gb.load(file_name);

        assertEquals(ga.copy(), gb.copy());
    }
}