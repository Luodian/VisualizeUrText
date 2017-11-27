package sample.boundary;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import sample.GraphInterface.DirectedGraph;
import sample.GraphInterface.DirectedGraphInterface;
import sample.control.DisplayGraph;

public class GraphDisplayForm {
    public DirectedGraphInterface<String> dGraph = new DirectedGraph<>();  //有向图
    private Button middleButton;

    public GraphDisplayForm(DirectedGraphInterface dGraph, Button middleButton){
        this.dGraph = dGraph;
        this.middleButton = middleButton;
    }
    public void show() {
        String picName = "pic.png";
        DisplayGraph displayGraph = new DisplayGraph(dGraph);
        displayGraph.generatePure("pic");
        ImageView imageView = new ImageView("/sample/resources/images/"+picName);
        imageView.setFitHeight(600);
        imageView.setFitWidth(830);
        middleButton.setGraphic(imageView);
    }
    public void showFromShortestPath(String picName){
        ImageView imageView = new ImageView("/sample/resources/images/"+picName);
        imageView.setFitHeight(600);
        imageView.setFitWidth(830);
        middleButton.setGraphic(imageView);
    }
}
