package com.company;

import sun.security.provider.certpath.Vertex;

/*
    Edge类封装了出边，内部包含一个weight属性以及出边的下一个结点。
    把protected class似乎可以当成是C++的类内部结构体来使用。
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

