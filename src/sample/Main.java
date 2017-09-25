package sample;

//import com.kitfox.svg.Text;
//import guru.nidi.codeassert.config.In;
//import guru.nidi.graphviz.attribute.Color;
//import guru.nidi.graphviz.attribute.Shape;
//import guru.nidi.graphviz.attribute.Style;
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
//import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
//import guru.nidi.graphviz.model.Node;
import guru.nidi.graphviz.parse.Parser;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
//import org.apache.xpath.operations.String;
//import org.apache.xpath.operations.Bool;
import javafx.stage.StageStyle;
import sample.GraphInterface.DirectedGraph;
import sample.GraphInterface.DirectedGraphInterface;
import sample.GraphInterface.VertexInterface;

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

    private List<String> dotLines = new ArrayList<>();                 //dot命令

    private String processLine="";                                    //处理后的文本
    private String originLine="";                                     //处理前的文本

//    private  ImageView digraphImageView= new ImageView();            //窗口中间的图片

    private Map<String, Stack<String>> parent= new HashMap<>();         //记录父节点，使用Stack，这样可以找多个路径
    private List<List<String>> totalList=new ArrayList<>();             //逆向路径总表

    private String picName;

    private Button middleButton = new Button();                   //中间按钮
    private List<String> randomWalkList = new ArrayList<>();        //随机游走记录路径的链表
    private boolean stopFlag=true;                                  //停止随机游走的标志

    private Alert alert = new Alert(Alert.AlertType.WARNING,"请您先读取文件哦!");


    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        SvgImageLoaderFactory.install();
        Button btn = new Button("test");
        btn.setLayoutX(0);
        btn.setLayoutY(0);
        BorderPane border = new BorderPane();
//        border.setTop(addHBoxTop());
        border.setLeft(addGridLeft(primaryStage));
//        border.setCenter(addCenter());
        picName = "test.jpg";
        ImageView imageView = new ImageView("/sample/resources/images/"+picName);
        imageView.setFitHeight(600);
        imageView.setFitWidth(830);
        middleButton.setGraphic(imageView);
        middleButton.setId("middleButton");
//        middleButton.setMaxSize(100,50);
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

//        Scene scene = new Scene(border, screenWidth - 200, screenHeight - 100);
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

   /*添加顶部栏*/
//   private HBox addHBoxTop(){
//        HBox hbox = new HBox();
//        hbox.setPadding(new Insets(15,12,15,12));
//        hbox.setStyle("-fx-background-color: #336699");
//
//        return hbox;
//    }

    /*添加左侧布局*/
    private GridPane addGridLeft(Stage primaryStage){
        GridPane gridLeft = new GridPane();
//        gridLeft.setMaxSize(40,40);
        gridLeft.setHgap(10);
        gridLeft.setVgap(70);
        gridLeft.setPadding(new Insets(450,0,200,190));

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
            public void handle(ActionEvent event)
            {
                if (dGraph.isEmpty()){
                    alert.showAndWait();
                    return;
                }
                //调用整体画图函数
                picName = "pic.png";
                showDirectedPicture("pic");
                ImageView imageView = new ImageView("/sample/resources/images/"+picName);
                imageView.setFitHeight(600);
                imageView.setFitWidth(830);
                middleButton.setGraphic(imageView);
//                digraphImageView.setImage(new Image(Main.class.getResourceAsStream("/sample/resources/" +
//                        "images/"+"pic"+".png")));
            }
        });

        gridLeft.add(openButton,1,0);
        gridLeft.add(graphButton,0,1);
        Label tipLabel=new Label("\t\t提示\n\t  点中央图片\n\t  可看高清图");
        tipLabel.setId("tiplabel");
        gridLeft.add(tipLabel,0,4,3,1);
        return gridLeft;
    }

    /*添加中间布局*/
//    private StackPane addCenter(){
//       StackPane middleGraph = new StackPane();
//       Image digraphImage = new Image(Main.class.getResourceAsStream("resources/images/test.jpg"));
//       digraphImageView.setImage(digraphImage);
//       digraphImageView.setFitWidth(820);
//       digraphImageView.setFitHeight(550);
//       digraphImageView.setSmooth(true);
//       middleGraph.setPadding(new Insets(40,0,210,0));
//       middleGraph.getChildren().add(digraphImageView);
//       return middleGraph;
//   }

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
               stopFlag=false;
               randomWalkList.clear();
               text.setText(randomWalk()+"\n");
               picName = randomWalkList.get(0)+"to"+randomWalkList.get(randomWalkList.size()-1)+ ".png";
               ImageView imageView = new ImageView("/sample/resources/images/"+picName);
               imageView.setFitHeight(600);
               imageView.setFitWidth(830);
               middleButton.setGraphic(imageView);
//               digraphImageView.setImage(new Image(Main.class.getResourceAsStream("/sample/resources/" +
//                       "images/"+randomWalkList.get(0)+"to"+randomWalkList.get(randomWalkList.size()-1)+
//                       ".png")));
           }
       });
       nextStep.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               if (stopFlag){
                   try {
                       writeToRandomText(text.getText());
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   text.setText(text.getText()+"已经停止了\n");
               } else {
                   text.setText(text.getText()+randomWalk()+"\n");
               }
               picName = randomWalkList.get(0)+"to"+randomWalkList.get(randomWalkList.size()-1)+ ".png";
               ImageView imageView = new ImageView("/sample/resources/images/"+picName);
               imageView.setFitHeight(600);
               imageView.setFitWidth(830);
               middleButton.setGraphic(imageView);

//               digraphImageView.setImage(new Image(Main.class.getResourceAsStream("/sample/resources/" +
//                       "images/"+randomWalkList.get(0)+"to"+randomWalkList.get(randomWalkList.size()-1)+
//                       ".png")));
           }
       });
       stop.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               stopFlag=true;
               try {
                   writeToRandomText(text.getText());
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
               bridgeWindow().show();
           }
       });
       generateNewText.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               if (dGraph.isEmpty()){
                   alert.showAndWait();
                   return;
               }newTextWindow().show();
           }
       });
       shortestPath.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               if (dGraph.isEmpty()){
                   alert.showAndWait();
                   return;
               }
               shortestPathWindow().show();
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
                   subGrid.setVisible(true);
                   randomWalk.setText("关闭");
               }
           }
       });
       return gridRight;
   }

    /*原始文档转换窗口*/
    private Stage openWindow(File file) {

        dGraph.clear();
        indexEnd=0;
        paths = "";
        dotLines.clear();
        processLine="";
        originLine="";
        parent.clear();
        totalList.clear();
        randomWalkList.clear();
        stopFlag=true;

        InputStreamReader reader = null;
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
        String tmpStr = "";
        while (line != null) {
            originLine += line+"\n";
            char[] chars = line.toCharArray();
            tmpStr += ' ';
            for (char chr : chars) {
                if (chr >= 'A' && chr <= 'Z') {
                    chr += ('a' - 'A');
                } else if (chr == ',' || chr == '.' || chr == '?' || chr == '!' || chr == 34 || chr == 39 || chr == ' ') {
                    chr = ' ';
                } else if (chr < 'a' || chr > 'z') {
                    continue;
                }
                tmpStr += chr;
            }
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringTokenizer st = new StringTokenizer(tmpStr);
        while (st.hasMoreTokens()) {
            String tmpWord = st.nextToken();
            processLine += tmpWord;
            processLine += ' ';
        }
        processLine = processLine.trim();
        wordsToken(processLine);            //抽取单词

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

        return openStage;
    }

    /*查询桥接词窗口*/
    private Stage bridgeWindow(){
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
                    output.setText(queryBridgeWords(word1.getText(), word2.getText()));
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

        return bridgeStage;
    }

    /*生成新文本窗口*/
    private Stage newTextWindow(){
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
                    newText.setText(generateNewText(oldText.getText()));
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

        return newTextStage;

    }

    /*最短路径窗口*/
    private Stage shortestPathWindow(){
        Label word1Label = new Label("Word 1");
        Label word2Label = new Label("Word 2");
        Label pathLabel = new Label("路径");
        TextField word1 = new TextField();
        word1.setEditable(true);
        TextField word2 = new TextField();
        word2.setEditable(true);
        TextArea path = new TextArea();
        path.setEditable(false);
        path.setMaxSize(350,300);

        Button buttonSearch = new Button("查询");
        buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                paths = calcShortestPath(word1.getText(),word2.getText());
                path.setText(paths);
                totalList.clear();
                indexEnd=0;
            }
        });

        Button buttonNextPic = new Button("切换图片");
        buttonNextPic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (paths.equals("不可达")||paths.equals("至少有一个word不存在")||paths.equals("该点到其他点全都不可达")||
                        paths.equals("无输入")||paths.equals("该单词不存在")){
                    return;
                }
                String[] lines=paths.split("\n");
                List<String> ends = new ArrayList<>();
                for (String line:lines){
                    StringTokenizer st =new StringTokenizer(line);
                    String pre = null;
                    String cur;
                    while (st.hasMoreTokens()){
                        cur=st.nextToken();
                        if (cur.equals(":")){
                            ends.add(pre);
                        }
                        pre=cur;
                    }
                }
                String end = ends.get(indexEnd);
                boolean allTheSame = true;          //表示所有结束的单词都相同
                String pre=null;
                for (String cur:ends){
                    if (pre!=null&&!pre.equals(cur)){
                        allTheSame=false;
                        break;
                    }
                    pre=cur;
                }
                if (!allTheSame){
                    indexEnd=(indexEnd+1)%ends.size();
                    String next = ends.get(indexEnd);
                    while(next.equals(end)){
                        indexEnd=(indexEnd+1)%ends.size();
                        next = ends.get(indexEnd);
                    }
                }
                String start;
                if (!word1.getText().equals("")){
                    start=word1.getText();
                }else if (word1.getText().equals("") && !word2.getText().equals("")){
                    start=word2.getText();
                }else {
                    return; //没有输入
                }
                picName = start+"to"+end+".png";
                ImageView imageView = new ImageView("/sample/resources/images/"+picName);
                imageView.setFitHeight(600);
                imageView.setFitWidth(830);
                middleButton.setGraphic(imageView);

//                digraphImageView.setImage(new Image(Main.class.getResourceAsStream("/sample/resources/" +
//                        "images/"+start+"to"+end+".png")));
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
        GridPane sonGridPane = new GridPane();
        sonGridPane.setVgap(20);
        sonGridPane.setHgap(20);
        sonGridPane.setPadding(new Insets(10,10,10,10));
        sonGridPane.add(buttonSearch,0,0);
        sonGridPane.add(buttonNextPic,1,0);
        gridPane.add(sonGridPane,1,3);

        Stage shortestPathStage = new Stage();
        shortestPathStage.setTitle("查询最短路径");
        Scene scene = new Scene(gridPane,520,340);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        shortestPathStage.setScene(scene);

        return shortestPathStage;
    }

    /*抽取单词*/
    private void wordsToken(String processLine){
        StringTokenizer st = new StringTokenizer(processLine);
        String pre=null;
        String cur=null;
        while(st.hasMoreTokens()){
            cur=st.nextToken();
            if(!dGraph.getVerTex().containsKey(cur)){
                dGraph.addVertex(cur);
            }
            if(pre!=null){
                dGraph.addEdge(pre,cur,1);
            }
            pre=cur;
        }
    }

    /*查询桥接词*/
    private String queryBridgeWords(String word1, String word2){
        Map<String,VertexInterface<String>> vertexMap=dGraph.getVerTex();
        StringBuilder output= new StringBuilder();
        if(!vertexMap.containsKey(word1)){
            output = new StringBuilder("No \""+word1+"\" in the graph");
        }else if (!vertexMap.containsKey(word2)){
            output = new StringBuilder("No \""+word2+"\" in the graph");
        }
        else{
            for (String tmpVertex:vertexMap.keySet()) {
                if(tmpVertex.equals(word1)||tmpVertex.equals(word2)){
                    continue;
                }
                else{
                    if(dGraph.hasEdge(word1,tmpVertex)&&dGraph.hasEdge(tmpVertex,word2)){
                        output.append(tmpVertex);
                        output.append(" ");
                    }
                }
            }
            if(output.toString().equals("")){
                output = new StringBuilder("No bridge words from \""+word1+"\" to \""+word2+"\"");
            }else{
                List<String> usefulWords= new ArrayList<>();
                StringTokenizer st=new StringTokenizer(output.toString());
                while (st.hasMoreTokens()){
                    usefulWords.add(st.nextToken());
                }
                if(usefulWords.size()==1){
                    output = new StringBuilder("The bridge word from \""+word1+"\" to \""+word2+"\" is: ");
                    output.append(usefulWords.get(0));
                    output.append(".");
                }else {
                    output = new StringBuilder("The bridge words from \""+word1+"\" to \""+word2+"\" are:");
                    int size = usefulWords.size();
                    int outNum=0;
                    for (String word:usefulWords) {
                        outNum++;
                        if(outNum!=size){
                            output.append(" ");
                            output.append(word);
                            output.append(',');
                        }else{
                            output.append(" and ");
                            output.append(word);
                            output.append('.');
                        }
                    }
                }
            }
        }
        return output.toString();
    }

    /*生成新文本*/
    private String generateNewText(String originText){
        StringBuilder newText = new StringBuilder();
        Map<String,VertexInterface<String>> vertexMap=dGraph.getVerTex();
        StringTokenizer st = new StringTokenizer(originText);
        String cur;
        String pre=null;
        while(st.hasMoreTokens()){
            cur=st.nextToken();
            if(pre!=null){
                if(vertexMap.containsKey(pre)&&vertexMap.containsKey(cur)){
                    Set<String> bridgeWords=new HashSet<>();
                    for (String tmpWord:vertexMap.keySet()) {
                        if(dGraph.hasEdge(pre,tmpWord)&&dGraph.hasEdge(tmpWord,cur)){
                            bridgeWords.add(tmpWord);
                        }
                    }
                    if (bridgeWords.size()==0){
                        newText.append(" ").append(cur);
                    }else{
                        String[] bridgeWordsArray= (String[]) bridgeWords.toArray(new String[bridgeWords.size()]);
                        newText.append(" ").append(bridgeWordsArray[(int) (Math.random() * bridgeWordsArray.length)]).append(" ").append(cur);
                    }
                }else {
                    newText.append(" ").append(cur);
                }
            }else {
                newText.append(cur);
            }
            pre=cur;
        }
        return newText.toString();
    }

    /*将double类型的权值化为int类型*/
    private int getEdgeWeightInt(String word1, String word2){
        if(dGraph.getEdgeWeight(word1,word2)==Double.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else
            return (int) dGraph.getEdgeWeight(word1,word2);
    }

    /*计算最短路径*/
    private String calcShortestPath(String word1, String word2){
        //都不是空串
        if((!(word1.equals("")))&&((!(word2.equals("")))))
        {
            if (!dGraph.getVerTex().keySet().contains(word1)||!dGraph.getVerTex().keySet().contains(word2)){
                return "至少有一个word不存在";
            }
            Path(word1);
            if (!getPre(new ArrayList<>(),word1,word2)){
                return "不可达";
            }
            localPicture(word1,word2,totalList);
        }
        //都是空串
        else if((word1.equals(""))&&(word2.equals(""))){
            return "无输入";
        }
        //其中一个是空串，另一个不是空串
        else{
            String sourceWord = new String();
            if(word1.equals("")&&(!(word2.equals("")))){
                sourceWord = word2;
            }else if((!(word1.equals("")))&&(word2.equals(""))){
                sourceWord=word1;
            }
            if (!dGraph.getVerTex().keySet().contains(sourceWord)){
                return "该单词不存在";
            }
            Path(sourceWord);
            boolean allUnreachable=true;
            for (String word:dGraph.getVerTex().keySet()) {
                if(!word.equals(sourceWord)){
                    if (getPre(new ArrayList<>(),sourceWord,word)){
                        allUnreachable=false;           //不可达
                    }
                }
            }
            if (allUnreachable){
                return "该点到其他点全都不可达";
            }

            List<List<String>> lists=new ArrayList<>();
            String cur=null;
            String pre=null;
            for(List<String> list:totalList){
                cur=list.get(0);
                if(pre==null||pre.equals(cur)){
                    lists.add(list);
                }else{
                    localPicture(sourceWord,pre,lists);
                    lists.clear();
                    lists.add(list);
                }
                pre=cur;
            }
            localPicture(sourceWord,pre,lists);
            lists.clear();
        }
        String path = "";
        for (List<String> list:totalList)
        {
            String line=new String();
            int sum=0;
            String pre = null;
            int index=list.size()-1;
            while(index>=0)
            {
                String word=list.get(index--);
                if(pre!=null)
                {
                    sum+=dGraph.getEdgeWeight(pre,word);
                    line=line+" -> "+word;
                }
                else
                {
                    line=word;
                }
                pre=word;
            }
            line=line+" : "+sum+"\n";
            path=path+line;
        }
        return path;
    }
    private void Path(String sorceWord)
    {
        //初始化
//        cost.clear();
        Map<String,Integer> cost = new HashMap<>();
        Set<String> allWords = new HashSet<>(dGraph.getVerTex().keySet());
        Set<String> producedWords = new HashSet<>();
        parent.clear();
        allWords=dGraph.getVerTex().keySet();
        producedWords.clear();
        Map<String,Boolean> flag= new HashMap<>();
        for (String word:allWords)
        {
            if (!word.equals(sorceWord))
            {
                int weight = getEdgeWeightInt(sorceWord,word);
                cost.put(word,weight);
                Stack<String> stack =new Stack<>();
                stack.add(sorceWord);
                parent.put(word,stack);  //父节点是sorceword，表示到达了源点，这里包括那些和源点没有边的情况
                flag.put(word,false);   //false表示还没有加入producedWords
            }
        }
        producedWords.add(sorceWord);
        flag.put(sorceWord,true);
//        allWords.remove(sorceWord);
        while(producedWords.size()!=allWords.size()){
            String curStr=new String();
            int curCost = Integer.MAX_VALUE;
//            int curCost = 10000;
            for (Map.Entry<String,Integer> entry:cost.entrySet()) {
                if (!flag.get(entry.getKey()) &&entry.getValue()<curCost){
                    curCost=entry.getValue();
                    curStr=entry.getKey();
                }
            }
            if(curStr.equals("")){
                for (Map.Entry<String,Integer> entry:cost.entrySet()) {
                    if (!flag.get(entry.getKey())){
                        curCost=entry.getValue();
                        curStr=entry.getKey();
                        break;
                    }
                }
            }
            producedWords.add(curStr);
            flag.put(curStr,true);
            for (String word:allWords) {
                if(!flag.get(word)){
                    if (formatPlus(curCost,getEdgeWeightInt(curStr,word))<cost.get(word)){
                        cost.put(word,formatPlus(curCost,getEdgeWeightInt(curStr,word)));
                        parent.get(word).clear();
                        parent.get(word).add(curStr);
                    }else if(formatPlus(curCost,getEdgeWeightInt(curStr,word))==cost.get(word)){
                        parent.get(word).add(curStr);
                    }
                }
            }
        }
    }
    private boolean getPre(List<String> preList, String sourceWord,String word){
        List<String> list = new ArrayList<>(preList);
        list.add(word);
        if(word.equals(sourceWord)){
            totalList.add(list);
            return true;
        }
        boolean rst=true;
        for (String perParent:parent.get(word)) {
            if (getEdgeWeightInt(perParent,word)==Integer.MAX_VALUE)
                return false;
            rst=rst&getPre(list,sourceWord,perParent);
        }
        return rst;
    }

    /*特殊的加法，一个加数为Integer.MAX_VALUE时，和为Integer.MAX_VALUE*/
    private int formatPlus(int num1,int num2){
        if(num1==Integer.MAX_VALUE||num2==Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else{
            return num1+num2;
        }
    }

    /*画图函数*/
    //强调局部路径作图的函数
    private void localPicture(String start,String end,List<List<String>> lists) {
            int colorIndex = 0;
            String[] colors = new String[]{"RED", "GREEN", "BLUE", "PURPLE", "YELLOW", "GRAY"};
            for (List<String> list : lists) {
                String pre = null;
                int index = list.size() - 1;
                while (index >= 0) {
                    String cur = list.get(index--);
                    if (pre != null) {
                        String line = pre + "->" + cur + "[label = " + String.valueOf(getEdgeWeightInt(pre, cur)) + " ][color = " +
                                colors[colorIndex] + " ]\n";
                        dotLines.add(line);
                    }
                    pre = cur;
                }
                colorIndex = (colorIndex + 1) % 6;
            }
            showDirectedPicture(start + "to" + end);
        }

    //作出整个图片的函数
    private void showDirectedPicture(String name){
        for (String word1:dGraph.getVerTex().keySet()){
            for (String word2:dGraph.getVerTex().keySet()){
                if (dGraph.hasEdge(word1,word2)){
                    String line = word1+"->"+word2+"[label = "+String.valueOf(getEdgeWeightInt(word1,word2))+" ]" +
                            "[ color = BLACK ]\n";
                    dotLines.add(line);
                }
            }
        }
        try {
            FileWriter fileOut = new FileWriter("out/production/VisualizeUrText/sample/a.dot");
            fileOut.write("digraph {\n");
            for (String line:dotLines){
                fileOut.write(line);
            }
            fileOut.write("}");
            fileOut.flush();
            fileOut.close();
            MutableGraph gr = Parser.read(Main.class.getResourceAsStream("/sample/a.dot"));
            Graphviz.fromGraph(gr).width(800).render(Format.PNG).toFile(new File("out/production/" +
                    "VisualizeUrText/sample/resources/images/"+name+".png"));
            dotLines.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        digraphImageView.setImage(new Image(Main.class.getResourceAsStream("/sample/resources/" +
//                "images/"+name+".png")));

    }

    private boolean hasSameEdge(List<String> list,String word1,String word2){
        Set<Integer> set= new HashSet<>();
        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).equals(word1)){
                set.add(i);
            }
        }
        for (int i:set){
            if (i<list.size()-1 && list.get(i+1).equals(word2)){
                return true;
            }
        }
        return false;
    }
    private String randomWalk(){
        if (stopFlag){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (String word:dGraph.getVerTex().keySet()){
            if (randomWalkList.isEmpty()||dGraph.hasEdge(randomWalkList.get(randomWalkList.size()-1),word)){
             list.add(word);
            }
        }
        if (list.isEmpty()){
            return "不存在出边了";
        }
        String newStr = list.get((int)(Math.random()*(list.size()-1)));

        boolean sameEdge = false;
        if ((!(randomWalkList.isEmpty()))&&hasSameEdge(randomWalkList,randomWalkList.get(randomWalkList.size()-1),newStr)){
            sameEdge=true;
        }
        randomWalkList.add(newStr);
        String pre=null;
        String cur=null;
        for (String word:randomWalkList){
            cur = word;
            if (pre!=null){
                String line=pre+"->"+cur+"[label = "+String.valueOf(getEdgeWeightInt(pre,cur))+" ][color = GREEN]\n";
                dotLines.add(line);
            }
            pre=cur;
        }
        showDirectedPicture(randomWalkList.get(0)+"to"
                +randomWalkList.get(randomWalkList.size()-1));
        StringBuilder path = new StringBuilder();
        pre=null;
        for (String word:randomWalkList){
            cur=word;
            if (pre==null){
                path = new StringBuilder(cur);
            } else {
               path.append(" -> ").append(cur);
            }
            pre=cur;
        }
        String str=path.toString();
        if (sameEdge){
            stopFlag=true;
        }
        return str;
    }

    /*将路径写入文件*/
    private void writeToRandomText(String content) throws IOException {
        FileWriter fileWriter = new FileWriter("随机游走路径.txt",true);
        String[] strings=content.split("\n");
        String out =strings[strings.length-1]+"\n\t";
        fileWriter.write(out);
        fileWriter.flush();
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException
    {
        launch(args);
    }

}


