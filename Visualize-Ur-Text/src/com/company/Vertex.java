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
class Vertex<T> implements VertexInterface<T>, java.io.Serializable
{
    /**
    *  Edge类封装了出边，内部包含一个weight属性以及出边的下一个结点。
    *  把protected class似乎可以当成是C++的类内部结构体来使用。
    */
    protected class Edge implements java.io.Serializable
    {
        private VertexInterface<T> vertex;
        public Double weight;

        protected Edge(VertexInterface<T> _v, double _w)
        {
            vertex = _v;
            weight = _w;
        }

        protected VertexInterface<T> getEndVertex()
        {
            return vertex;
        }

        protected double getWeight()
        {
            return weight;
        }
    }
    /**Task:
    *   用来做edgeList的迭代指针
    */
    private class NeighborIterator implements Iterator<VertexInterface<T>>
    {

        // data field
        Iterator<Edge> edgesIterator;

        // construct function
        private NeighborIterator()
        {
            edgesIterator = edgeList.iterator();
        }

        @Override
        public boolean hasNext() {
            return edgesIterator.hasNext();
        }

        @Override
        public VertexInterface<T> next() {
            VertexInterface<T> nextNeighbor = null;
            if (edgesIterator.hasNext())
            {
                Edge edgeToNextNeighbor = edgesIterator.next();
                nextNeighbor = (VertexInterface<T>) edgeToNextNeighbor.getEndVertex();
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
    /**Task: 生成一个遍历该顶点所有邻接边的权值的迭代器
     * 权值是Edge类的属性,因此先获得一个遍历Edge对象的迭代器,取得Edge对象,再获得权值
     * @param 权值的类型
     */
    private class WeightIterator implements Iterator
    {
        private Iterator<Edge> edgesIterator;
        private WeightIterator()
        {
            edgesIterator = edgeList.iterator();
        }

        @Override
        public boolean hasNext()
        {
            return edgesIterator.hasNext();
        }

        @Override
        public Double next()
        {
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
    public T getLabel()
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
    public boolean addEdge(VertexInterface<T> endVertex,double edgeWeight)
    {
        boolean result = false;
        if (!this.equals(endVertex))
        {
            Iterator< VertexInterface<T> > neighbors_Iterator;
            neighbors_Iterator = this.getNeighborIterator();
            boolean duplicate_Edges = false;

            // find whether their exists duplicated edges

            while(!duplicate_Edges && neighbors_Iterator.hasNext())
            {
                // 使用.next()方法获取边迭代器所指的终点。
                
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
    public boolean addEdge(VertexInterface<T> endVertex)
    {
        return addEdge(endVertex,0);
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
}


