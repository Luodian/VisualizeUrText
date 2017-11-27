package sample.entity;

import sample.GraphInterface.DirectedGraph;
import sample.GraphInterface.DirectedGraphInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TextFile {
    private List<String> dotLines = new ArrayList<>();                 //dot命令
    private String inputFilePath;
    private String randomFilePath;

    private String processLine="";                                    //处理后的文本
    private String originLine="";                                     //处理前的文本

    private String tmpStr = "";

    public DirectedGraphInterface<String> dGraph = new DirectedGraph<>();  //有向图

    //读入文件
    public String readIn() {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(inputFilePath));
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
        return originLine;
    }

    //过滤信息
    public String filter() {
        StringTokenizer st = new StringTokenizer(tmpStr);
        while (st.hasMoreTokens()) {
            String tmpWord = st.nextToken();
            processLine += tmpWord;
            processLine += ' ';
        }
        processLine = processLine.trim();
        StringTokenizer st1 = new StringTokenizer(processLine);
        String pre=null;
        String cur=null;
        while(st1.hasMoreTokens()){
            cur=st1.nextToken();
            if(!dGraph.getVerTex().containsKey(cur)){
                dGraph.addVertex(cur);
            }
            if(pre!=null){
                dGraph.addEdge(pre,cur,1);
            }
            pre=cur;
        }
        return processLine;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    /*将路径写入文件*/
    public void writeToRandomText(String content) throws IOException {
        FileWriter fileWriter = new FileWriter("随机游走路径.txt",true);
        String[] strings=content.split("\n");
        String out =strings[strings.length-1]+"\n\t";
        fileWriter.write(out);
        fileWriter.flush();
        fileWriter.close();
    }
}
