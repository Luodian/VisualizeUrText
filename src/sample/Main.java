package sample;

import com.kitfox.svg.Text;
import guru.nidi.codeassert.config.In;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
<<<<<<< HEAD
//import org.apache.xpath.operations.String;
import org.apache.xpath.operations.Bool;
=======
>>>>>>> 04f905a030f77f3b70f341cf59ec566a09af1b12
import sample.GraphInterface.DirectedGraph;
import sample.GraphInterface.DirectedGraphInterface;
import sample.GraphInterface.VertexInterface;

import java.awt.*;
import java.io.*;
<<<<<<< HEAD
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.*;

=======
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
>>>>>>> 04f905a030f77f3b70f341cf59ec566a09af1b12

public class Main extends Application {

    private final Desktop desktop = Desktop.getDesktop();
<<<<<<< HEAD
=======
    private String processLine="";                              //处理后的文本
    private String originLine="";                               //处理前的文本
    private  ImageView digraphImageView= new ImageView();      //中间的有向图
    DirectedGraphInterface<String> graph = new DirectedGraph<>();
//    private
>>>>>>> 04f905a030f77f3b70f341cf59ec566a09af1b12

    private DirectedGraphInterface<String> dGraph = new DirectedGraph<>();

//    private Map<String,String> endFromStart = new HashMap<>();             //存放终点和起点
    private int indexMap=0;
    String paths;

    private String processLine="";                                    //处理后的文本
    private String originLine="";                                     //处理前的文本
    private  ImageView digraphImageView= new ImageView();                      //中间的有向图
    private Map<String,Integer> cost = new HashMap<>();
    private Map<String, Stack<String>> parent= new HashMap<>();    //这里父节点使用Stack，这样可以找多个路径
    private List<List<String>> totalList=new ArrayList<>();
    private Set<String> allWords = new HashSet<>(dGraph.getVerTex().keySet());
    private Set<String> producedWords = new HashSet<>();

    private List<String> randomWalkList = new ArrayList<>();        //随机游走记录路径的链表
    private boolean stopFlag=true;

    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane border=new BorderPane();
        border.setTop(addHBoxTop());
        border.setLeft(addGridLeft(primaryStage));
        border.setCenter(addCenter());
        border.setRight(addGridRight());

        Rectangle2D currentScreenBounds = Screen.getPrimary().getVisualBounds();

        double screenHeight = currentScreenBounds.getHeight();
        double screenWidth = currentScreenBounds.getWidth();

        System.out.println(screenHeight + "+" + screenWidth);

        Scene scene = new Scene(border, screenWidth - 200, screenHeight - 100);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
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
<<<<<<< HEAD
                //调用整体画图函数
                wholePicture();
                digraphImageView.setImage(new Image(Main.class.getResourceAsStream("/sample/resources/" +
                        "images/pic.png")));
=======
                //调用画图函数

                digraphImageView.setImage(new Image(Main.class.getResourceAsStream("03.jpg")));
>>>>>>> 04f905a030f77f3b70f341cf59ec566a09af1b12
        }
        });

        gridLeft.add(openButton,1,0);
        gridLeft.add(graphButton,0,1);
        return gridLeft;
    }

    /*添加中间布局*/
   public StackPane addCenter(){
       StackPane middleGraph = new StackPane();
       Image digraphImage = new Image(Main.class.getResourceAsStream("resources/images/test.jpg"));
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

       Button start = new Button("开始");
       Button nextStep = new Button("下一步");
       Button stop = new Button("终止");
       TextArea text = new TextArea();
       text.setMaxSize(300,500);

       start.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               stopFlag=false;
               randomWalkList.clear();
               text.setText(randomWalk()+"\n");
               digraphImageView.setImage(new Image(Main.class.getResourceAsStream("/sample/resources/" +
                       "images/"+randomWalkList.get(0)+"to"+randomWalkList.get(randomWalkList.size()-1)+
                       ".png")));
           }
       });
       nextStep.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               if (stopFlag){
                   text.setText(text.getText()+"已经停止了\n");
               } else {
                   text.setText(text.getText()+randomWalk()+"\n");
               }
               digraphImageView.setImage(new Image(Main.class.getResourceAsStream("/sample/resources/" +
                       "images/"+randomWalkList.get(0)+"to"+randomWalkList.get(randomWalkList.size()-1)+
                       ".png")));
           }
       });
       stop.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               stopFlag=true;
               text.setText(text.getText()+"已经停止了\n");
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

       GridPane subGrid = new GridPane();
       subGrid.setVgap(20);
       subGrid.setVisible(false);
       subGrid.add(start,0,3);
       subGrid.add(nextStep,0,4);
       subGrid.add(stop,0,5);
       subGrid.add(text,3,3,2,4);
       gridRight.add(subGrid,0,3,3,4);


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
    public Stage openWindow(File file) {

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
<<<<<<< HEAD
        String tmpStr = "";
        while (line != null) {
            originLine += line;
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
=======
        String tmpStr="";
        while(line!=null){
            originLine+=line;
            char[] chars = line.toCharArray();
            tmpStr+=' ';
            for (char chr:chars) {
                if(chr>='A'&&chr<='Z') {
                    chr += ('a' - 'A');
                }else if(chr==','||chr=='.'||chr=='?'||chr=='!'||chr==34||chr==39||chr==' '){
                    chr=' ';
                }else if(chr<'a'||chr>'z'){
                    continue;
                }
                tmpStr+=chr;
>>>>>>> 04f905a030f77f3b70f341cf59ec566a09af1b12
            }
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringTokenizer st = new StringTokenizer(tmpStr);
<<<<<<< HEAD
        while (st.hasMoreTokens()) {
            String tmpWord = st.nextToken();
            processLine += tmpWord;
            processLine += ' ';
        }
//        processLine=processLine.substring(0,-1);
        processLine = processLine.trim();
        System.out.println("processline:" + processLine);
        wordsToken(processLine);
=======
        while(st.hasMoreTokens()){
            String tmpWord=st.nextToken();
            processLine+=tmpWord;
            processLine+=' ';
        }
//        processLine=processLine.substring(0,-1);
        processLine=processLine.trim();
        System.out.println("processline:"+processLine);
        wordsToken(processLine);

>>>>>>> 04f905a030f77f3b70f341cf59ec566a09af1b12

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
        Scene scene = new Scene(openGrid,430,340);
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
                output.setText(bridgeWords(word1.getText(),word2.getText()));
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
        Scene scene = new Scene(gridPane,430,340);
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
//                newText.setText("这是转换后的文字");
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
<<<<<<< HEAD
        Scene scene = new Scene(gridPane,430,340);
=======
        Scene scene = new Scene(gridPane,380,220);
>>>>>>> 04f905a030f77f3b70f341cf59ec566a09af1b12
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
        path.setMaxSize(350,300);

        Button buttonSearch = new Button("查询");
//        final String[] str = new String[1];

        buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                path.setText("这就是查询到的最短路径");
                paths = calcShortestPath(word1.getText(),word2.getText());
                path.setText(paths);
                totalList.clear();
                indexMap=0;
            }
        });


        Button buttonNextPic = new Button("切换图片");
        buttonNextPic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                String[] ends = endFromStart.keySet().toArray(new String[0]);
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
                System.out.println(ends);
                String end = ends.get(indexMap);
                String start;
                if (!word1.getText().equals("")){
                    start=word1.getText();
                }else if (word1.getText().equals("") && !word2.getText().equals("")){
                    start=word2.getText();
                }else {
                    return; //没有输入
                }
//                String start = endFromStart.get(end);
                digraphImageView.setImage(new Image(Main.class.getResourceAsStream("/sample/resources/" +
                        "images/"+start+"to"+end+".png")));
                indexMap=(indexMap+1)%ends.size();
                System.out.println(indexMap);
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
//        gridPane.add(button,1,3);
        GridPane sonGridPane = new GridPane();
        sonGridPane.setVgap(20);
        sonGridPane.setHgap(20);
        sonGridPane.setPadding(new Insets(10,10,10,10));
        sonGridPane.add(buttonSearch,0,0);
        sonGridPane.add(buttonNextPic,1,0);
        gridPane.add(sonGridPane,1,3);

        Stage shortestPathStage = new Stage();
        shortestPathStage.setTitle("查询最短路径");
        Scene scene = new Scene(gridPane,430,340);
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
                System.out.println("这里添加边："+pre+" "+cur);
            }
            pre=cur;
        }
    }

    /*查询桥接词*/
    private String bridgeWords(String word1, String word2){
        Map<String,VertexInterface<String>> vertexMap=dGraph.getVerTex();
        String output="";
        if (!vertexMap.containsKey(word1)||!vertexMap.containsKey(word2)){
            output="No word1 or word2 in the graph";
        }
        else{
            System.out.println(dGraph.hasEdge("a","r"));
            for (String tmpVertex:vertexMap.keySet()) {
                System.out.println("当前点是："+tmpVertex);
                if(tmpVertex.equals(word1)||tmpVertex.equals(word2)){
                    continue;
                }
                else{
                    if(dGraph.hasEdge(word1,tmpVertex)&&dGraph.hasEdge(tmpVertex,word2)){
                        output+=tmpVertex;
                        output+=" ";
                        System.out.println("找到桥街点："+tmpVertex);
                    }
                }
            }
            if(output.equals("")){
                output="No bridge words from word1 to word2";
            }else{
                List<String> usefulWords= new ArrayList<>();
                StringTokenizer st=new StringTokenizer(output);
                while (st.hasMoreTokens()){
                    usefulWords.add(st.nextToken());
                }
                System.out.println("usefulWords: "+usefulWords);
                System.out.println("size: "+usefulWords.size());

                if(usefulWords.size()==1){
                    output="The bridge word from word1 to word2 is: ";
                    output+=usefulWords.get(0);
                    output+=".";
                }else {
                    output="The bridge words from word1 to word2 are:";
                    int size = usefulWords.size();
                    int outNum=0;
                    for (String word:usefulWords) {
                        outNum++;
                        if(outNum!=size){
                            output+=" ";
                            output+=word;
                            output+=',';
                        }else{
                            output+=" and ";
                            output+=word;
                            output+='.';
                        }
                    }
                }
            }
        }
        return output;
    }

<<<<<<< HEAD
    /*生成新文本*/
    private String generateNewText(String originText){
        String newText ="";
        Map<String,VertexInterface<String>> vertexMap=dGraph.getVerTex();
        StringTokenizer st = new StringTokenizer(originText);
        String cur=null;
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
                        newText = newText + " "+cur;
                    }else{
                        String[] bridgeWordsArray= (String[]) bridgeWords.toArray(new String[bridgeWords.size()]);
                        newText =  newText+" "+bridgeWordsArray[(int)(Math.random()*bridgeWordsArray.length)]+" "+cur;
                    }
                }else {
                    newText=newText+" "+cur;
                }
            }else {
                newText=newText+cur;
            }
            pre=cur;
        }
        return newText;
    }

    public int getEdgeWeightInt(String word1,String word2){
        if(dGraph.getEdgeWeight(word1,word2)==Double.MAX_VALUE){
            return Integer.MAX_VALUE;
//            return 10000;           //这里不使用Integer.MAX_VALUE,防止数据范围
        }else
            return (int) dGraph.getEdgeWeight(word1,word2);
    }
    private String calcShortestPath(String word1, String word2){
        //都不是空串
        if((!(word1.equals("")))&&((!(word2.equals(""))))){
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
            Path(sourceWord);
            boolean allUnreachable=true;
            for (String word:dGraph.getVerTex().keySet()) {
                if(!word.equals(sourceWord)){
                    if (getPre(new ArrayList<>(),sourceWord,word)){
                        allUnreachable=false;           //不可达
                    }
                }
            }
=======
    /*抽取单词*/
    public void wordsToken(String processLine){
        StringTokenizer st = new StringTokenizer(processLine);
        String pre=null;
        String cur=null;
        while(st.hasMoreTokens()){
//            System.out.println(st.nextToken());

            cur=st.nextToken();
            graph.addVertex(cur);
            if(pre!=null){
                graph.addEdge(pre,cur,1);
                if(pre=="a"&&cur=="r"){
                    System.out.println("存不存在？"+graph.hasEdge("a","r"));
                }
                System.out.println("这里添加边："+pre+" "+cur);
            }
            pre=cur;
        }
        System.out.println("添加了么？"+graph.hasEdge("a","r"));
    }

    /*查询桥接词*/
    public String bridgeWords(String word1,String word2){
        System.out.println("word1: "+word1);
        System.out.println("word2: "+word2);
        System.out.println("有没有边："+graph.hasEdge("a","r")+"和"+graph.hasEdge("r","b"));
        Map<String,VertexInterface<String>> vertexMap=graph.getVerTex();
        String output="";
        if (!vertexMap.containsKey(word1)||!vertexMap.containsKey(word2)){
            output="No word1 or word2 in the graph";
        }
        else{
            System.out.println(graph.hasEdge("a","r"));
            for (String tmpVertex:vertexMap.keySet()) {
                System.out.println("当前点是："+tmpVertex);
                if(tmpVertex.equals(word1)||tmpVertex.equals(word2)){
                    continue;
                }
                else{
                    if(graph.hasEdge(word1,tmpVertex)&&graph.hasEdge(tmpVertex,word2)){
                        output+=tmpVertex;
                        output+=" ";
                        System.out.println("找到桥街点："+tmpVertex);
                    }
                }
            }
            if(output.equals("")){
                output="No bridge words from word1 to word2";
            }else{
                List<String> usefulWords= new ArrayList<>();
                StringTokenizer st=new StringTokenizer(output);
                while (st.hasMoreTokens()){
                    usefulWords.add(st.nextToken());
                }
                System.out.println("usefulWords: "+usefulWords);
                System.out.println("size: "+usefulWords.size());

                if(usefulWords.size()==1){
                    output="The bridge word from word1 to word2 is: ";
                    output+=usefulWords.get(0);
                    output+=".";
                }else {
                    output="The bridge words from word1 to word2 are:";
                    int size = usefulWords.size();
                    int outNum=0;
                    for (String word:usefulWords) {
                        outNum++;
                        if(outNum!=size){
                            output+=" ";
                            output+=word;
                            output+=',';
                        }else{
                            output+=" and ";
                            output+=word;
                            output+='.';
                        }
                    }
                }
            }
        }
        return output;
    }
>>>>>>> 04f905a030f77f3b70f341cf59ec566a09af1b12

            List<List<String>> lists=new ArrayList<>();
            String cur=null;
            String pre=null;
            for(List<String> list:totalList){
                cur=list.get(0);
                if(pre==null||pre.equals(cur)){
                    lists.add(list);
                }else{
                    System.out.println(lists);
                    localPicture(sourceWord,pre,lists);
                    lists.clear();
                    lists.add(list);
                }
                pre=cur;
            }
            System.out.println(lists);
            localPicture(sourceWord,pre,lists);
            lists.clear();
        }
        String path = "";
        for (List<String> list:totalList){
            String line=new String();
            int sum=0;
            String pre = null;
            int index=list.size()-1;
            while(index>=0){
                String word=list.get(index--);
//                list.remove(word);
                if(pre!=null){
                    sum+=dGraph.getEdgeWeight(pre,word);
                    line=line+" -> "+word;
                }else{
                    line=word;
                }
                pre=word;
            }
            line=line+" : "+sum+"\n";
            path=path+line;
        }
        return path;
    }
    private void Path(String sorceWord){
        //初始化
        cost.clear();
        parent.clear();
        allWords=dGraph.getVerTex().keySet();
        producedWords.clear();
        Map<String,Boolean> flag= new HashMap<>();
        for (String word:allWords) {
            if (!word.equals(sorceWord)){
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
//            allWords.remove(curStr);
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

<<<<<<< HEAD
    /*特殊的加法，一个加数为Integer.MAX_VALUE时，和为Integer.MAX_VALUE*/
    private int formatPlus(int num1,int num2){
        if(num1==Integer.MAX_VALUE||num2==Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else{
            return num1+num2;
        }
    }
=======
    public static void main(String[] args) {
        launch(args);
//        DirectedGraphInterface<String> graph = new DirectedGraph<>();
//        graph.addVertex("a");
//        graph.addVertex("b");
//        graph.addEdge("a","b",1);
//        System.out.println(graph.hasEdge("a","b"));
//        System.out.println(graph.hasEdge("a","c"));
>>>>>>> 04f905a030f77f3b70f341cf59ec566a09af1b12

    /*画图函数*/
    //强调局部路径作图的函数
    private void localPicture(String start,String end,List<List<String>> lists) {
//        endFromStart.put(end,start);
        Map<String,Node> map=new HashMap<>();
        for (String word:dGraph.getVerTex().keySet()){
            map.put(word,node(word));
        }
        int colorIndex = 0;
        Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.PURPLE, Color.YELLOW,Color.GRAY};
        for (List<String> list : lists) {
            String pre = null;
            int index = list.size() - 1;
//            System.out.println(list);
            while (index >= 0) {
                String cur = list.get(index--);
                if (pre != null) {
                    Node preNode = map.get(pre);
                    preNode = preNode.link(to(map.get(cur)).with(guru.nidi.graphviz.model.Label.of(Integer.toString(getEdgeWeightInt(pre, cur))), colors[colorIndex]));
                    map.put(pre, preNode);
                }
                pre = cur;
            }
            colorIndex = (colorIndex + 1) % 6;
        }
        for (String word1:dGraph.getVerTex().keySet()){
            for (String word2:dGraph.getVerTex().keySet()){
                if (dGraph.hasEdge(word1,word2)){
                    Node curNode = map.get(word1);
                    curNode=curNode.link(to(map.get(word2)).with(guru.nidi.graphviz.model.Label.of(Integer.toString(getEdgeWeightInt(word1,word2))),Color.BLACK));
                    map.put(word1,curNode);
                }
            }
        }
         Graph g = graph("example2").directed().with(map.values().toArray(new Node[0]));
        try {
            Graphviz.fromGraph(g).width(650).render(Format.PNG).toFile(new File("out/production/Visual" +
                    "izeUrText/sample/resources/images/"+start+"to"+end+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //作出整个图片的函数
    private void wholePicture(){
        Map<String,Node> map=new HashMap<>();
        for (String word:dGraph.getVerTex().keySet()){
            map.put(word,node(word));
        }
        for (String word1:dGraph.getVerTex().keySet()){
            for (String word2:dGraph.getVerTex().keySet()){
                if (dGraph.hasEdge(word1,word2)){
                    Node curNode = map.get(word1);
                    curNode=curNode.link(to(map.get(word2)).with(guru.nidi.graphviz.model.Label.of(Integer.toString(getEdgeWeightInt(word1,word2)))));
                    map.put(word1,curNode);
                }
            }
        }
        Graph g = graph("example2").directed().with(map.values().toArray(new Node[0]));
        try {
            Graphviz.fromGraph(g).width(650).render(Format.PNG).toFile(new File("out/production/Visual" +
                    "izeUrText/sample/resources/images/pic.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Map<String,Node> map = new HashMap<>();
        for (String word:dGraph.getVerTex().keySet()){
            map.put(word,node(word));
        }
        String pre=null;
        String cur=null;
        for (String word:randomWalkList){
            cur = word;
            if (pre!=null){
                Node preNode = map.get(pre);
                preNode = preNode.link(to(map.get(cur)).with(guru.nidi.graphviz.model.Label.of(Integer.toString(getEdgeWeightInt(pre, cur))), Color.RED3));
                map.put(pre, preNode);
            }
            pre=cur;
        }
        for (String word1:dGraph.getVerTex().keySet()){
            for (String word2:dGraph.getVerTex().keySet()){
                if (dGraph.hasEdge(word1,word2)){
                    Node curNode = map.get(word1);
                    curNode=curNode.link(to(map.get(word2)).with(guru.nidi.graphviz.model.Label.of(Integer.toString(getEdgeWeightInt(word1,word2))),Color.BLACK));
                    map.put(word1,curNode);
                }
            }
        }
        Graph g = graph("example2").directed().with(map.values().toArray(new Node[0]));
        try {
            Graphviz.fromGraph(g).width(650).render(Format.PNG).toFile(new File("out/production/Visual" +
                    "izeUrText/sample/resources/images/"+randomWalkList.get(0)+"to"
                    +randomWalkList.get(randomWalkList.size()-1)+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public static void main(String[] args) throws IOException
    {
        launch(args);
    }
}
