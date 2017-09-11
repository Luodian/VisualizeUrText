package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.awt.*;
import java.io.*;

public class Main extends Application {

    private final Desktop desktop = Desktop.getDesktop();
    private String pureline="";

    @Override
    public void start(Stage primaryStage) throws Exception{

       /*添加左侧布局*/
        GridPane gridLeft = new GridPane();
        gridLeft.setHgap(10);
        gridLeft.setVgap(10);
        gridLeft.setPadding(new Insets(0,10,0,10));

        final Button openButton = new Button("选择文件");
        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                FileChooser fileChooser  = new FileChooser();
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("TXT files (*.txt)",
                        "*.txt");
                fileChooser.getExtensionFilters().add(filter);
                File file = fileChooser.showOpenDialog(primaryStage);
                System.out.println(file);

                InputStreamReader reader= null;
                try {
                    reader = new InputStreamReader(new FileInputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BufferedReader br = new BufferedReader(reader);
                String line = "";

                try {
                    line = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while(line!=null){
                    pureline+=' ';
                    for (char chr:line.toCharArray()) {
                        if(chr==','||chr=='.'||chr=='?'||chr=='!'||chr==34||chr==39){
                            pureline+=' ';
                        }
                        else if((chr>='A'&&chr<='Z')||(chr>='a'&&chr<='z')){
                            pureline+=chr;
                        }
                    }
                    try {
                        line = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(pureline);
            }
        });
        gridLeft.add(openButton,0,0);

        TextArea originText = new TextArea(pureline);
        originText.setEditable(false);
        originText.setWrapText(true);

        originText.setMaxWidth(200);
        originText.setMaxHeight(150);;
//        GridPane.setVgrow(originText, Priority.ALWAYS);
        gridLeft.add(originText,0,1);




        /*添加右侧布局*/
        GridPane gridRight = new GridPane();
        gridRight.setHgap(10);
        gridRight.setVgap(10);
        gridRight.setPadding(new Insets(0,10,0,10));

//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        StackPane root = new StackPane();
//        root.getChildren().add(openButton);

        BorderPane border=new BorderPane();
        border.setTop(addHBox());
        border.setLeft(gridLeft);
        Scene scene = new Scene(border,300,275);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public HBox addHBox(){
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15,12,15,12));
        hbox.setStyle("-fx-background-color: #336699");

        return hbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
