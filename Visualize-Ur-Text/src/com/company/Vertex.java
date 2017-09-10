package com.company;

import javax.naming.OperationNotSupportedException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class Vertex<T> implements VertextInterface<T>, java.io.Serializable
{

    //vertex data field
    private T lable;
    private List<Edge> EdgeList;
    private boolean visited;
    private VertexInterface<T> previousVertex;
    private double Vertex_cost;

    //vertex method field

    /*
        label : 用来标识顶点，如图中的 V0,V1,V2,V3
        在实际代码中，V0...V3 以字符串的形式表示，就可以用来标识不同的顶点了。
        因此，需要在Vertex类中添加获得顶点标识的方法---getLabel()
     */

    public T getLable()
    {
        return lable;
    }

    /*
        EdgeList : 存放与该顶点关联的边。
        从上面Edge.java中可以看到，Edge的实质是“顶点”，
        因为，Edge类除去weight属性，就只剩表示顶点的vertex属性了。
        借助EdgeList，当给定一个顶点时，就可以访问该顶点的所有邻接点。
        因此，Vertex.java中就需要实现根据EdgeList中存放的边来遍历
        某条边的终点(也即相应顶点的各个邻接点) 的迭代器了。
     */
    public Iterator<VertexInterface<T>> getNeighborIterator
    {
        return new NeighborIterator;
    }

    /*
        遍历该顶点邻接点的迭代器--为 getNeighborInterator()方法 提供迭代器
        由于顶点的邻接点以边的形式存储在java.util.List中,因此借助List的迭代器来实现
        由于顶点的邻接点由Edge类封装起来了--见Edge.java的定义的第一个属性
        因此，首先获得遍历Edge对象的迭代器,再根据获得的Edge对象解析出邻接点对象
     */


    private class NeighborIterator implements Iterator<VertexInterface<T>>
    {
        Iterator<Edge> EdgesIterator;

        private NeighborIterator()
        {
            EdgesIterator = EdgeList.iterator();
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
        }

        @Override
        public remove()
        {
            throw new OperationNotSupportedException();
        }
    }

}


