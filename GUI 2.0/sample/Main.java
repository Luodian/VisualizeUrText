package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.*;
import java.util.logging.Level;

public class Main extends Application {

    private final Desktop desktop = Desktop.getDesktop();
    private String processLine="";                              //处理后的文本
    private String originLine="";                               //处理前的文本
    private  ImageView digraphImageView= new ImageView();      //中间的有向图
//    private

    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane border=new BorderPane();
        border.setTop(addHBoxTop());
        border.setLeft(addGridLeft(primaryStage));
        border.setCenter(addCenter());
        border.setRight(addGridRight());
        Scene scene = new Scene(border,1320,650);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*添加顶部栏*/
    public HBox addHBoxTop(){
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15,12,15,12));
        hbox.setStyle("-fx-background-color: #336699");

        return hbox;
    }

    /*添加左侧布局*/
    public GridPane addGridLeft(Stage primaryStage){
        GridPane gridLeft = new GridPane();
        gridLeft.setHgap(10);
        gridLeft.setVgap(70);
        gridLeft.setPadding(new Insets(200,20,200,50));

        final Button openButton = new Button("选择文件");
        final Button graphButton = new Button("绘制图形");
        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                FileChooser fileChooser  = new FileChooser();
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("TXT files (*.txt)",
                        "*.txt");
                fileChooser.getExtensionFilters().add(filter);
                File file = fileChooser.showOpenDialog(primaryStage);
                openWindow(file).show();
            }
        });
        graphButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //调用画图函数

                digraphImageView.setImage(new Image(Main.class.getResourceAsStream("03.jpg")));
        }
        });
//        graphButton.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//            }
//        });
        gridLeft.add(openButton,1,0);
        gridLeft.add(graphButton,0,1);
        return gridLeft;
    }

    /*添加中间布局*/
   public StackPane addCenter(){
       StackPane middleGraph = new StackPane();
       Image digraphImage = new Image(Main.class.getResourceAsStream("有向图.jpg"));
       digraphImageView.setImage(digraphImage);
       digraphImageView.setFitWidth(750);
       digraphImageView.setFitHeight(550);
       digraphImageView.setSmooth(true);
       middleGraph.setPadding(new Insets(40,0,40,0));
       middleGraph.getChildren().add(digraphImageView);
       return middleGraph;
   }

   /*添加右侧布局*/
   public GridPane addGridRight(){
       Button bridgeWord = new Button("查询桥接词");
       Button generateNewText = new Button("生成新文本");
       Button shortestPath = new Button("最短路径");
       Button randomWalk = new Button("随机游走");

       bridgeWord.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               bridgeWindow().show();
           }
       });
       generateNewText.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               newTextWindow().show();
           }
       });
       shortestPath.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               shortestPathWindow().show();
           }
       });
       randomWalk.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               randomWalkWindow().show();
           }
       });
       GridPane gridRight = new GridPane();
       gridRight.setVgap(40);
       gridRight.setHgap(8);
       gridRight.setPadding(new Insets(130,30,30,10));
       gridRight.add(bridgeWord,1,0);
       gridRight.add(generateNewText,2,1);
       gridRight.add(shortestPath,0,1);
       gridRight.add(randomWalk,1,2);
       return gridRight;
   }

    /*原始文档转换窗口*/
    public Stage openWindow(File file){
        
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
            processLine+=' ';
            originLine+=line;
            for (char chr:line.toCharArray()) {
                if(chr==','||chr=='.'||chr=='?'||chr=='!'||chr==34||chr==39){
                    processLine+=' ';
                }
                else if((chr>='A'&&chr<='Z')||(chr>='a'&&chr<='z')){
                    processLine+=chr;
                }
            }
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(processLine);

        TextArea originText = new TextArea(originLine);
        originText.setEditable(true);
        originText.setWrapText(true);
        originText.setMaxWidth(150);
        originText.setMaxHeight(150);

        Image image = new Image("http://2.pic.9ht.com/up/2015-9/201591815454.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);

        TextArea processText = new TextArea(processLine);
        processText.setEditable(false);
        processText.setWrapText(true);
        processText.setMaxWidth(150);
        processText.setMaxHeight(150);
        
        GridPane openGrid = new GridPane();
        openGrid.setHgap(15);
        openGrid.setVgap(15);
        openGrid.setPadding(new Insets(15,10,15,10));
        openGrid.add(originText,0,0);
        openGrid.add(imageView,1,0);
        openGrid.add(processText,2,0);

        Stage openStage = new Stage();
        openStage.setTitle("打开文件");
        Scene scene = new Scene(openGrid,380,220);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        openStage.setScene(scene);

        return openStage;
    }

    /*查询桥接词窗口*/
    public Stage bridgeWindow(){
        Label word1Label = new Label("Word 1");
        Label word2Label = new Label("Word 2");
        Label outputLabel = new Label("Output");

        TextField word1 = new TextField();
        word1.setEditable(true);
        TextField word2 = new TextField();
        word2.setEditable(true);
        TextArea output = new TextArea();
        output.setEditable(false);
        output.setMaxSize(300,50);

        Button button = new Button("查询");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                output.setText("这里是桥接词啦啦啦啦绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿绿" +
                        "绿绿绿绿绿绿绿绿绿");                        //参数为桥接词,回头补上
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
        gridPane.add(output,1,2);
        gridPane.add(button,1,3);

        Stage bridgeStage = new Stage();
        bridgeStage.setTitle("查询桥接词");
        Scene scene = new Scene(gridPane,380,220);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        bridgeStage.setScene(scene);

        return bridgeStage;
    }

    /*生成新文本窗口*/
    public Stage newTextWindow(){
        Label oldTextLabel = new Label("请输入要转换的文本");
        Label newTextLabel = new Label("新文本");
        TextArea oldText = new TextArea();
        oldText.setEditable(true);
        TextArea newText = new TextArea();
        newText.setEditable(false);
        Button button = new Button("转换");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newText.setText("这是转换后的文字");
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
        newTextStage.setTitle("查询桥接词");
        Scene scene = new Scene(gridPane,380,220);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        newTextStage.setScene(scene);

        return newTextStage;

    }

    /*最短路径窗口*/
    public Stage shortestPathWindow(){
        Label word1Label = new Label("Word 1");
        Label word2Label = new Label("Word 2");
        Label pathLabel = new Label("路径");
        TextField word1 = new TextField();
        word1.setEditable(true);
        TextField word2 = new TextField();
        word2.setEditable(true);
        TextArea path = new TextArea();
        path.setEditable(false);
        path.setMaxSize(300,50);

        Button button = new Button("查询");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                path.setText("这就是查询到的最短路径");
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.add(word1Label,0,0);
        gridPane.add(word2Label,0,1);
        gridPane.add(pathLabel,0,2);
        gridPane.add(word1,1,0);
        gridPane.add(word2,1,1);
        gridPane.add(path,1,2);
        gridPane.add(button,1,3);

        Stage shortestPathStage = new Stage();
        shortestPathStage.setTitle("查询最短路径");
        Scene scene = new Scene(gridPane,380,220);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        shortestPathStage.setScene(scene);

        return shortestPathStage;
    }

    /*随机游走窗口 */
    public Stage randomWalkWindow(){
        Button start = new Button("开始");
        Button nextStep = new Button("下一步");
        Button stop = new Button("终止");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setPadding(new Insets(30,10,10,150));
        gridPane.add(start,0,0);
        gridPane.add(nextStep,0,1);
        gridPane.add(stop,0,2);

        Stage randomStage = new Stage();
        randomStage.setTitle("随机游走");
        Scene scene = new Scene(gridPane,380,220);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        randomStage.setScene(scene);

        return randomStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
