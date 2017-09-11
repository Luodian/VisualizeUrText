package com.company;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class DirectedGraph<T> implements DirectedGraphInterface<T>,java.io.Serializable
{
    /*
        data field;
     */
    private Map<T,VertexInterface<T>> VerticesMap_ObjToIter;
    private int edgeCount;

    public DirectedGraph()
    {
        VerticesMap_ObjToIter = new LinkedHashMap<>();
    }

    @Override
    public void addVertex(T vertexLabel)
    {
        VerticesMap_ObjToIter.put(vertexLabel,new Vertex<T>(vertexLabel));
    }

    @Override
    public boolean addEdge(T begin,T end,double edgeWeight)
    {
        boolean result = false;
        VertexInterface<T> beginVertex = VerticesMap_ObjToIter.get(begin);
        VertexInterface<T> endVertex = VerticesMap_ObjToIter.get(end);

        if (beginVertex != null && endVertex != null)
        {
            result = beginVertex.addEdge(endVertex,edgeWeight);
        }
        if (result)
        {
            edgeCount ++;
        }
        return result;
    }

    @Override
    public boolean addEdge(T begin,T end)
    {
        return addEdge(begin,end,0);
    }

    @Override
    public boolean hasEdge(T begin,T end)
    {
        boolean found = false;
        VertexInterface<T> beginVertex = VerticesMap_ObjToIter.get(begin);
        VertexInterface<T> endVertex = VerticesMap_ObjToIter.get(end);

        if (beginVertex == null || endVertex == null || beginVertex.hasNeighbor() == false)
        {
            return found;
        }
        else
        {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext())
            {
                VertexInterface<T> neighbor = neighbors.next();
                if (endVertex.equals(neighbor))
                {
                    found = true;
                }
            }
        }
        return found;
    }

    @Override
    public boolean isEmpty()
    {
        return VerticesMap_ObjToIter.isEmpty();
    }

    @Override
    public int getNumberOfVertices()
    {
        return VerticesMap_ObjToIter.size();
    }

    @Override
    public int getNumberOfEdges()
    {
        return edgeCount;
    }

    @Override
    public void clear()
    {
        VerticesMap_ObjToIter.clear();
    }
}
