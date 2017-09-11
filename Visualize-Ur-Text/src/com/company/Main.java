package com.company;

public class Main {

    public static void main(String[] args)
    {
	    // write your code here
        DirectedGraphInterface<String>  graph = new DirectedGraph<>();
        System.out.println("Graph is Empty? " + graph.isEmpty());

        //要注意先加点再连边。
        graph.addVertex("Visualize");
        graph.addVertex("Ur");
        graph.addVertex("Text");

        //
        graph.addEdge("Visualize","Ur",1);
        graph.addEdge("Ur","Text",1);
        graph.addEdge("Ur","Text",1);

        System.out.println(graph.hasEdge("Ur","Text"));
    }
}
