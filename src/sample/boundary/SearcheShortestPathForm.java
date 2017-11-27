package sample.boundary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.GraphInterface.DirectedGraphInterface;
import sample.Main;
import sample.control.ShortestPath;

import java.util.*;

public class SearcheShortestPathForm {

    public DirectedGraphInterface<String> dGraph;  //有向图
    private GraphDisplayForm graphDisplayForm;
    private int indexEnd=0;                                            //要切换的路线的最终word的id
    private String paths;                                               //最短路径的表示
    private Map<String, Stack<String>> parent= new HashMap<>();         //记录父节点，使用Stack，这样可以找多个路径
    private List<List<String>> totalList=new ArrayList<>();             //逆向路径总表
    public SearcheShortestPathForm(DirectedGraphInterface dGraph,GraphDisplayForm graphDisplayForm) {
        this.dGraph = dGraph;
        this.graphDisplayForm = graphDisplayForm;
    }

    public void show() {
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
                ShortestPath shortestPath = new ShortestPath(dGraph);

                paths = shortestPath.calcShortestPath(word1.getText(),word2.getText());
                path.setText(paths);
                totalList.clear();
                indexEnd=0;
            }
        });

        Button buttonNextPic = new Button("切换图片");
        buttonNextPic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (paths.equals("不可达") || paths.equals("至少有一个word不存在") || paths.equals("该点到其他点全都不可达") ||
                        paths.equals("无输入") || paths.equals("该单词不存在")) {
                    return;
                }
                String[] lines = paths.split("\n");
                List<String> ends = new ArrayList<>();
                for (String line : lines) {
                    StringTokenizer st = new StringTokenizer(line);
                    String pre = null;
                    String cur;
                    while (st.hasMoreTokens()) {
                        cur = st.nextToken();
                        if (cur.equals(":")) {
                            ends.add(pre);
                        }
                        pre = cur;
                    }
                }
                String end = ends.get(indexEnd);
                boolean allTheSame = true;          //表示所有结束的单词都相同
                String pre = null;
                for (String cur : ends) {
                    if (pre != null && !pre.equals(cur)) {
                        allTheSame = false;
                        break;
                    }
                    pre = cur;
                }
                if (!allTheSame) {
                    indexEnd = (indexEnd + 1) % ends.size();
                    String next = ends.get(indexEnd);
                    while (next.equals(end)) {
                        indexEnd = (indexEnd + 1) % ends.size();
                        next = ends.get(indexEnd);
                    }
                }
                String start;
                if (!word1.getText().equals("")) {
                    start = word1.getText();
                } else if (word1.getText().equals("") && !word2.getText().equals("")) {
                    start = word2.getText();
                } else {
                    return; //没有输入
                }
                String picName = start + "to" + end + ".png";
                graphDisplayForm.showFromShortestPath(picName);
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
        shortestPathStage.show();
    }
}
