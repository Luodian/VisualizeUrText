package sample.boundary;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.GraphInterface.DirectedGraph;
import sample.GraphInterface.DirectedGraphInterface;
import sample.Main;
import sample.control.ChooseFile;

import java.io.File;

public class ChooseFileDialogForm {

    private String processLine="";                                    //处理后的文本
    private String originLine="";                                     //处理前的文本

    public DirectedGraphInterface<String> dGraph = new DirectedGraph<>();  //有向图

    public void openDialog(Stage primaryStage) {
        FileChooser fileChooser  = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("TXT files (*.txt)",
                "*.txt");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(primaryStage);

        ChooseFile chooseFile = new ChooseFile(file);
        chooseFile.choose();
        dGraph = chooseFile.dGraph;
        originLine = chooseFile.getOriginLine();
        processLine = chooseFile.getProcessLine();

    }
    public void translateWindow() {
        TextArea originText = new TextArea(originLine);
        originText.setEditable(true);
        originText.setWrapText(true);
//        originText.setMaxWidth(330);
        originText.setMaxHeight(280);

        Image image = new Image(Main.class.getResourceAsStream("/sample/resources/" +
                "images/arrow.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);

        TextArea processText = new TextArea(processLine);
        processText.setEditable(false);
        processText.setWrapText(true);
//        processText.setMaxWidth(330);
        processText.setMaxHeight(280);

        GridPane openGrid = new GridPane();
        openGrid.setHgap(15);
        openGrid.setVgap(15);
        openGrid.setPadding(new Insets(15,10,15,10));
        openGrid.add(originText,0,0);
        openGrid.add(imageView,1,0);
        openGrid.add(processText,2,0);

        Stage openStage = new Stage();
        openStage.setTitle("打开文件");
        Scene scene = new Scene(openGrid,650,340);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        openStage.setScene(scene);
        openStage.show();
    }
}
