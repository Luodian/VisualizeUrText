package sample.boundary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.GraphInterface.DirectedGraphInterface;
import sample.Main;
import sample.control.GenerateNewText;

public class GenerateNewTextForm {
    public DirectedGraphInterface<String> dGraph;  //有向图

    public GenerateNewTextForm(DirectedGraphInterface dGraph) {
        this.dGraph = dGraph;
    }
    public void show() {
        Label oldTextLabel = new Label("请输入要转换的文本");
        Label newTextLabel = new Label("新文本");
        TextArea oldText = new TextArea();
        oldText.setEditable(true);
        oldText.setMaxWidth(280);
        oldText.setWrapText(true);
        TextArea newText = new TextArea();
        newText.setEditable(false);
        newText.setMaxWidth(280);
        newText.setWrapText(true);
        Button button = new Button("转换");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GenerateNewText generateNewText = new GenerateNewText(dGraph);
                newText.setText(generateNewText.generateNewText(oldText.getText()));
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.add(oldTextLabel,0,0);
        gridPane.add(newTextLabel,2,0);
        gridPane.add(oldText,0,1);
        gridPane.add(newText,2,1);
        gridPane.add(button,1,1);

        Stage newTextStage = new Stage();
        newTextStage.setTitle("生成新文本");
        Scene scene = new Scene(gridPane,680,340);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        newTextStage.setScene(scene);
        newTextStage.show();
    }
}
