package com.company;

public interface DirectedGraphInterface<T>
{
    /*
        Task:Add new vertice with label
        @param:label
     */

    public void addVertex(T vertexLabel);
    public void addEdge(T begin,T end,double edgeWeight);
    public void addEdge(T begin,T end);
    public boolean hasEdge(T begin,T end);
    public boolean isEmpty();
    public int getNumberOfVertices();
    public int getNumberOfEdges();
    public void clear();
}
