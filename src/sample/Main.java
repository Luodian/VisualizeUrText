package sample;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
//import guru.nidi.graphviz.model.Graph;
//import guru.nidi.graphviz.model.Node;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
//import org.apache.xpath.operations.String;
//import org.apache.xpath.operations.Bool;
import javafx.stage.StageStyle;
import sample.GraphInterface.DirectedGraph;
import sample.GraphInterface.DirectedGraphInterface;
import sample.boundary.*;
import sample.control.RandomWalk;
import sample.entity.TextFile;

import java.awt.*;
import java.io.*;
//import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

//import static guru.nidi.graphviz.model.Factory.*;


public class Main extends Application {

    private DirectedGraphInterface<String> dGraph = new DirectedGraph<>();  //有向图

    private int indexEnd=0;                                            //要切换的路线的最终word的id
    private String paths;                                               //最短路径的表示
    private Map<String, Stack<String>> parent= new HashMap<>();         //记录父节点，使用Stack，这样可以找多个路径
    private List<List<String>> totalList=new ArrayList<>();             //逆向路径总表

    private String picName;

    private Button middleButton = new Button();                   //中间按钮

    private Alert alert = new Alert(Alert.AlertType.WARNING,"请您先读取文件哦!");


    private RandomWalk randomWalk1;
    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        SvgImageLoaderFactory.install();
        Button btn = new Button("test");
        btn.setLayoutX(0);
        btn.setLayoutY(0);
        BorderPane border = new BorderPane();
        border.setLeft(addGridLeft(primaryStage));
        picName = "test.jpg";
        ImageView imageView = new ImageView("/sample/resources/images/"+picName);
        imageView.setFitHeight(600);
        imageView.setFitWidth(830);
        middleButton.setGraphic(imageView);
        middleButton.setId("middleButton");
        middleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File("out/production/" +
                            "VisualizeUrText/sample/resources/images/"+picName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        border.setRight(addGridRight());
        StackPane middleGrid = new StackPane();
        middleGrid.getChildren().add(middleButton);
        middleGrid.setPadding(new Insets(0,0,180,0));
        border.setCenter(middleGrid);

        Rectangle2D currentScreenBounds = Screen.getPrimary().getVisualBounds();

        double screenHeight = currentScreenBounds.getHeight();
        double screenWidth = currentScreenBounds.getWidth();

        Scene scene = new Scene(border,1700,1300);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());

        scene.setFill(null);

        alert.setTitle("请先读取文件哦！");
        Button alertBtn = new Button();
        alertBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.showAndWait();
            }
        });

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    /*添加左侧布局*/
    private GridPane addGridLeft(Stage primaryStage){
        GridPane gridLeft = new GridPane();
        gridLeft.setHgap(10);
        gridLeft.setVgap(70);
        gridLeft.setPadding(new Insets(450,0,200,190));

        final Button openButton = new Button("选择文件");
        final Button graphButton = new Button("绘制图形");
        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                ChooseFileDialogForm chooseFileDialogForm = new ChooseFileDialogForm();
                chooseFileDialogForm.openDialog(primaryStage);
                chooseFileDialogForm.translateWindow();
                dGraph = chooseFileDialogForm.dGraph;
            }
        });
        graphButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                if (dGraph.isEmpty()){
                    alert.showAndWait();
                    return;
                }
                GraphDisplayForm graphDisplayForm = new GraphDisplayForm(dGraph,middleButton);
                graphDisplayForm.show();

            }
        });

        gridLeft.add(openButton,1,0);
        gridLeft.add(graphButton,0,1);
        Label tipLabel=new Label("\t\t提示\n\t  点中央图片\n\t  可看高清图");
        tipLabel.setId("tiplabel");
        gridLeft.add(tipLabel,0,4,3,1);
        return gridLeft;
    }

   /*添加右侧布局*/
   private GridPane addGridRight()
   {
       Button bridgeWord = new Button("查询桥接词");
       Button generateNewText = new Button("生成新文本");
       Button shortestPath = new Button("最短路径");
       Button randomWalk = new Button("随机游走");

       Button start = new Button("开始");
       Button nextStep = new Button("下一步");
       Button stop = new Button("终止");
       Button openText= new Button("打开文本");
       openText.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               try {
                   Desktop.getDesktop().open(new File("随机游走路径.txt"));
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       });
       TextArea text = new TextArea();
       text.setMaxSize(180,130);
       text.setEditable(false);

       start.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               text.setText(randomWalk1.randomWalk()+"\n");
               picName = randomWalk1.getRandomWalkList().get(0)+"to"+randomWalk1.getRandomWalkList().get(randomWalk1.getRandomWalkList().size()-1)+ ".png";
               ImageView imageView = new ImageView("/sample/resources/images/"+picName);
               imageView.setFitHeight(600);
               imageView.setFitWidth(830);
               middleButton.setGraphic(imageView);
           }
       });
       nextStep.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               if (randomWalk1.getStopFlag()){
                   try {
                       TextFile textFile = new TextFile();
                       textFile.writeToRandomText(text.getText());
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   text.setText(text.getText()+"已经停止了\n");
               } else {
                   text.setText(text.getText()+randomWalk1.randomWalk()+"\n");
               }
               picName = randomWalk1.getRandomWalkList().get(0)+"to"+randomWalk1.getRandomWalkList().get(randomWalk1.getRandomWalkList().size()-1)+ ".png";
               ImageView imageView = new ImageView("/sample/resources/images/"+picName);
               imageView.setFitHeight(600);
               imageView.setFitWidth(830);
               middleButton.setGraphic(imageView);
           }
       });
       stop.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               randomWalk1.setStopFlag(true);
               try {
                   new TextFile().writeToRandomText(text.getText());
               } catch (IOException e) {
                   e.printStackTrace();
               }
               text.setText(text.getText()+"已经停止了\n");
           }
       });

       GridPane gridRight = new GridPane();
//       gridRight.setMaxSize(40,40);
       gridRight.setVgap(10);
       gridRight.setHgap(1);
       gridRight.setPadding(new Insets(500,70,30,0));
       gridRight.add(bridgeWord,1,0);
       gridRight.add(generateNewText,2,1);
       gridRight.add(shortestPath,0,1);
       gridRight.add(randomWalk,1,2);

       GridPane subGrid = new GridPane();
       subGrid.setVgap(20);
       subGrid.setVisible(false);
       subGrid.add(start,0,3);
       subGrid.add(nextStep,0,4);
       subGrid.add(stop,0,5);
       subGrid.add(openText,0,6);
       subGrid.add(text,3,3,2,4);

       gridRight.add(subGrid,0,3,3,4);
       Button  exitWindow = new Button("退出程序");
       exitWindow.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               System.exit(0);
           }
       });
       gridRight.add(exitWindow,0,8);


       bridgeWord.setOnAction(new EventHandler<ActionEvent>()
       {
           @Override
           public void handle(ActionEvent event) {
               if (dGraph.isEmpty()){
                   alert.showAndWait();
                   return;
               }
               new CheckBridgeWordForm(dGraph).show();
           }
       });
       generateNewText.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               if (dGraph.isEmpty()){
                   alert.showAndWait();
                   return;
               }
               GenerateNewTextForm generateNewTextForm = new GenerateNewTextForm(dGraph);
               generateNewTextForm.show();
           }
       });
       shortestPath.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               if (dGraph.isEmpty()){
                   alert.showAndWait();
                   return;
               }
               SearcheShortestPathForm searcheShortestPathForm = new SearcheShortestPathForm(dGraph,new GraphDisplayForm(dGraph,middleButton));
               searcheShortestPathForm.show();
           }
       });
       randomWalk.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               if (dGraph.isEmpty()){
                   alert.showAndWait();
                   return;
               }
               if(subGrid.isVisible()){
                   subGrid.setVisible(false);
                   randomWalk.setText("随机游走");
               }
               else {
                   randomWalk1 = new RandomWalk(dGraph,new GraphDisplayForm(dGraph,middleButton));
                   subGrid.setVisible(true);
                   randomWalk.setText("关闭");
               }
           }
       });
       return gridRight;
   }

    public static void main(String[] args) throws IOException
    {
        launch(args);
    }

}


