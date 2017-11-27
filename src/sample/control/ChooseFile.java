package sample.control;

import sample.GraphInterface.DirectedGraph;
import sample.GraphInterface.DirectedGraphInterface;
import sample.entity.TextFile;

import java.io.File;

public class ChooseFile {

    private String processLine="";                                    //处理后的文本
    private String originLine="";                                     //处理前的文本
    public DirectedGraphInterface<String> dGraph = new DirectedGraph<>();  //有向图
    private File file;
    public ChooseFile(File file) {
        this.file = file;
    }
    public void choose(){
        TextFile textFile = new TextFile();
        textFile.setInputFilePath(file.toString());
        originLine = textFile.readIn();
        processLine = textFile.filter();
        dGraph = textFile.dGraph;
    }

    public String getProcessLine() {
        return processLine;
    }

    public String getOriginLine() {
        return originLine;
    }
}
