# Ex1

## This project was made during my OOP course at Ariel University in the Department of Computer Science, 2020.

Project site: https://github.com/Lioo7/Ex0.git

The assignment is about modeling data structure and algorithms in a (unidirectional) weighted graph.
Particularly these methods:
* copy - This computes a deep copy of this graph.
* isConnected - This checks if the graph is connected or not.
* shortestPathDist - This returns the shortest distance between two given nodes.
* shortestPath - This returns the shortest path between two given nodes.
* save - This saves this weighted (undirected) graph to the given file name.
* load - This loads a graph to this graph algorithm.

The main data structures in the program are HashMap, all the nodes (vertices) store in a HashMap and all the edges also store in a HashMap which contains another inner HashMap inside, which contains the neighbors of each node (vertex) in the graph.
The reason behind it is the efficient complexity of this data structure, which allows accessing, inserting, and to delete from the hash table with great complexity of O(1) on average.

## Examples: 

### 1.
### input:

        WGraph_DS g1 = new WGraph_DS();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);
        
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

        System.out.println(ga.isConnected());
        System.out.println(ga.shortestPathDist(0, 3));
        System.out.println(ga.shortestPath(0, 3));
        
### illustration: ![alt text](https://i.ibb.co/wynXzwk/1.png)

        
### output:

       true
       9.4
       [{key=0}, {key=1}, {key=4}, {key=3}]
       

### 2.
### input:

        WGraph_DS g1 = new WGraph_DS();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);

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

        g1.removeEdge(0, 1);
        
### illustration: ![alt text](https://i.ibb.co/9wds5yp/2.png)
                

### output:

       false
       -1.0
       null

For further information on graphs:
https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)

@author Lioz Akirav.
