package sample.control;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import sample.GraphInterface.DirectedGraph;
import sample.GraphInterface.DirectedGraphInterface;
import sample.Main;
import sample.entity.Picture;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DisplayGraph {

    public DirectedGraphInterface<String> dGraph = new DirectedGraph<>();  //有向图
    public DisplayGraph(DirectedGraphInterface dGraph){
        this.dGraph  = dGraph;
    }
    public void generatePure(String picName) {
        Picture picture = new Picture();
        picture.dGraph=dGraph;
        picture.showDirectedPicture(picName);
    }
}
