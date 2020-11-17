import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
    // Basic tests

    node_info n0 = new WGraph_DS.NodeData(0);
    node_info n1 = new WGraph_DS.NodeData(1);
    node_info n2 = new WGraph_DS.NodeData(2);
    WGraph_DS g1 = new WGraph_DS();
    weighted_graph_algorithms ga = new WGraph_Algo();

    @Test
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
    }

    @Test
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

        ga.init(g1);

        assertTrue(ga.isConnected());
    }

    @Test
    void shortestPathDist() {
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

        ga.init(g1);
        double expected = 9.4;
        double actual = ga.shortestPathDist(0, 3);

        assertEquals(expected, actual);
    }

    @Test
    void shortestPath() {
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

        ga.init(g1);
        String expected = "[{key=0}, {key=1}, {key=4}, {key=3}]";
        String actual = ga.shortestPath(0, 3).toString();

        assertEquals(expected, actual);
    }

    @Test
    void save() throws IOException {
        String file_name = "tester";
        ga.init(g1);
        ga.save(file_name);
        weighted_graph_algorithms gb = new WGraph_Algo();
        gb.load(file_name);


        assertEquals(ga, gb);
    }

    @Test
    void load() {
    }
}