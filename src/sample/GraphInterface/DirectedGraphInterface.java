package sample.GraphInterface;

import java.util.Map;

public interface DirectedGraphInterface<T> {
    /*
        Task:Add new vertice with label
        @param:label
     */

    void addVertex(T vertexLabel);

    void addEdge(T begin, T end, double edgeWeight);

    void addEdge(T begin, T end);

    boolean hasEdge(T begin, T end);

    boolean isEmpty();

    int getNumberOfVertices();

    int getNumberOfEdges();

    void clear();

    void f(T vertexLabel);

<<<<<<< HEAD
    Map<T,VertexInterface<T>> getVerTex();

    double getEdgeWeight(T beginVertex,T endVertex);
=======
    public Map<T,VertexInterface<T>> getVerTex();

>>>>>>> 04f905a030f77f3b70f341cf59ec566a09af1b12

}
