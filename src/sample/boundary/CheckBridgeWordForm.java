package sample.boundary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.GraphInterface.DirectedGraphInterface;
import sample.Main;
import sample.control.CheckBridgeWord;

public class CheckBridgeWordForm {

    public DirectedGraphInterface<String> dGraph;  //有向图

    public CheckBridgeWordForm(DirectedGraphInterface dGraph) {
        this.dGraph = dGraph;
    }
    public void show(){
        Label word1Label = new Label("Word 1");
        Label word2Label = new Label("Word 2");
        Label outputLabel = new Label("Output");

        TextField word1 = new TextField();
        word1.setEditable(true);
        TextField word2 = new TextField();
        word2.setEditable(true);
        TextArea output = new TextArea();
        output.setWrapText(true);
        output.setEditable(false);
        output.setMaxSize(500,200);

        Button button = new Button("查询");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (word1.getText().equals("")||word2.getText().equals("")){
                    output.setText("Please input two words!");
                }else {
                    CheckBridgeWord checkBridgeWord = new CheckBridgeWord(dGraph);
                    output.setText(checkBridgeWord.queryBridgeWords(word1.getText(), word2.getText()));
                }
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.add(word1Label,0,0);
        gridPane.add(word2Label,0,1);
        gridPane.add(outputLabel,0,2);
        gridPane.add(word1,1,0);
        gridPane.add(word2,1,1);
        gridPane.add(output,1,2,2,1);
        gridPane.add(button,1,3);

        Stage bridgeStage = new Stage();
        bridgeStage.setTitle("查询桥接词");
        Scene scene = new Scene(gridPane,630,340);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        bridgeStage.setScene(scene);
        bridgeStage.show();
    }
}
