package sample.entity;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import sample.GraphInterface.DirectedGraph;
import sample.GraphInterface.DirectedGraphInterface;
import sample.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Picture {
    String picPath = "";
    public DirectedGraphInterface<String> dGraph;  //有向图
    public List<String> dotLines = new ArrayList<>();                 //dot命令

    /*将double类型的权值化为int类型*/
    private int getEdgeWeightInt(String word1, String word2){
        if(dGraph.getEdgeWeight(word1,word2)==Double.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else
            return (int) dGraph.getEdgeWeight(word1,word2);
    }

    /*画图函数*/
    //强调局部路径作图的函数
    public void localPicture(String start,String end,List<List<String>> lists) {
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
    public void showDirectedPicture(String name){
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

    }


}
