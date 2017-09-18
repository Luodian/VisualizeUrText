package sample.GraphInterface;

import java.util.Iterator;

public interface VertexInterface<T> {
    /*
        Task:get id for each vertex
        @param:get instance for each object
     */

    public T getLabel();

    public void visit();

    public void unvisit();

    public boolean isVisited();

    public void addEdge(VertexInterface<T> endVertex, double edgeWeight);

    public void addEdge(VertexInterface<T> endVertex);

    /*
        Task:make a iterator to visit out_edge from this vertex
        @return:an iterator pointed to this out_edge
     */

    public Iterator<Vertex<T>.Edge> getNeighborIterator();

    public boolean hasNeighbor();

    public void setPredecessor(VertexInterface<T> predecessor);

    public VertexInterface<T> getPredecessor();

    public boolean hasPredecessor();

    public void setCost(double _cost);

    public double getCost();

    public boolean equals(Object other);
}
