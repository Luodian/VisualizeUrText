package com.company;

import javax.naming.OperationNotSupportedException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/*
    "Implement the Serializable interface
    when you want to be able to convert
    an instance of a class into a series
    of bytes or when you think that a
    Serializable object might reference
    an instance of your class.
    Serializable classes are useful when you want to
    persist instances of them or send them over a wire."
                                                         -"Effective Java"
 */

class Vertex<T> implements VertextInterface<T>, java.io.Serializable
{

    //vertex data field
    private T label;
    private List<Edge> edgeList;
    private boolean visited;
    private VertexInterface<T> previousVertex;
    private double cost;

    /*
        construct function
     */

    public Vertex(T _label)
    {
        label = _label;
        edgeList = new LinkedList<Edge>();
        visited = false;
        previousVertex = null;
        cost = 0;
    }

    @Override
    public T getLable()
    {
        return label;
    }

    @Override
    public void visit()
    {
        this.visited = true;
    }

    @Override
    public void unvisit()
    {
        this.visited = false;
    }

    @Override
    public boolean isVisited()
    {
        return this.visited;
    }

    /*
        add edges between current vertex to endVertex

        @param:edgeWeight can be zero, but java didn't support default param, we need use polymorphism functions.
        @return:return false when found duplicate edges.

     */

    @Override
    public boolean addEdge(VertexInterface<T> endVertex,double edgeWeight = 0)
    {
        boolean result = false;
        if (!this.equals(endVertex))
        {
            Iterator<VertexInterface<T>> neighbors_Iterator = this.NeighborIterator;
            boolean duplicate_Edges = false;

            // find whether their exists duplicated edges

            while(!duplicate_Edges && neighbors_Iterator.hasNext())
            {
                VertexInterface<T> neighbor_Vertex = neighbors_Iterator.next();

                // we need to override equals method for VertexInterface

                if (endVertex.equals(neighbor_Vertex))
                {
                    duplicate_Edges = true;
                    break;
                }
            }
            if (!duplicate_Edges)
            {
                edgeList.add(new Edge(endVertex,edgeWeight));
                result = true;
            }
        }
        return result;
    }

    /*
        polymorphism for weight equals to 0;
     */

    @Override
    public boolean connect(VertexInterface<T> endVertex)
    {
        return connect(endVertex,0);
    }

    @Override
    public Iterator<VertexInterface<T>> getNeighborIterator()
    {
        return new NeighborIterator();
    }

    @Override
    public Iterator getWeightIterator()
    {
        return new WeightIterator();
    }

    @Override
    public boolean hasNeighbor()
    {
        return !(edgeList.isEmpty());
    }

    @Override
    public VertexInterface<T> getUnvisitedNeighbor()
    {
        VertexInterface<T> result = null;
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while(neighbors.hasNext() && result == null)
        {
            VertexInterface<T> nextNeighbor = neighbors.next();
            // if the next vertex haven't been visited
            if (!nextNeighbor.isVisited())
            {
                result = nextNeighbor;
            }
        }
        return result;
    }

    @Override
    public void setPredecessor(VertexInterface<T> predecessor)
    {
        this.previousVertex = predecessor;
    }

    @Override
    public VertexInterface<T> getPredecessor()
    {
        return this.previousVertex;
    }

    @Override
    public boolean hasPredecessor()
    {
        return this.previousVertex != null;
    }

    @Override
    public void setCost(double _cost)
    {
        cost = _cost;
    }

    @Override
    public double getCost()
    {
        return cost;
    }

    //we need to rewrite equals method to make sure we can compare two Vertexs.
    @Override
    public boolean equals(Object other)
    {
        boolean result;
        if ((other == null) || (getClass() != other.getClass()))
        {
            result = false;
        }
        else
        {
            Vertex<T> otherVertex = (Vertex<T>) other;
            // using label to judge whether it's equal.
            result = label.equals(otherVertex.label);
        }
        return result;
    }

    /**Task: 遍历该顶点邻接点的迭代器--为 getNeighborInterator()方法 提供迭代器
    * 由于顶点的邻接点以边的形式存储在java.util.List中,因此借助List的迭代器来实现
    * 由于顶点的邻接点由Edge类封装起来了--见Edge.java的定义的第一个属性
    * 因此，首先获得遍历Edge对象的迭代器,再根据获得的Edge对象解析出邻接点对象
    */

    private class NeighborIterator implements Iterator<VertexInterface<T>>
    {
        Iterator<Edge> EdgesIterator;

        private NeighborIterator()
        {
            EdgesIterator = edgeList.iterator();
        }

        @Override
        public boolean hasNext() {
            return EdgesIterator.hasNext();
        }

        @Override
        public VertexInterface<T> next() {
            VertexInterface<T> nextNeighbor = null;
            if (EdgesIterator.hasNext())
            {
                Edge edgeToNextNeighbor = EdgesIterator.next();
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            }
            else
            {
                throw new NoSuchElementException();
            }
            return nextNeighbor;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    private class WeightIterator implements Iterator
    {
        private Iterator<Edge> edgesIterator;
        private WeightIterator()
        {
            edgesIterator = edgeList.iterator();
        }

        @Override
        public boolean hasNext() {
            return edgesIterator.hasNext();
        }

        @Override
        public Double next() {
            Double result;
            if (edgesIterator.hasNext())
            {
                Edge edge = edgesIterator.next();
                result = edge.getWeight();
            }
            else
            {
                throw new NoSuchElementException();
            }
            return new Double(result);
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

}


