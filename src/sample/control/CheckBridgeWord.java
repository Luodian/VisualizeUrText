package sample.control;

import sample.GraphInterface.DirectedGraphInterface;
import sample.GraphInterface.VertexInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class CheckBridgeWord {

    public DirectedGraphInterface<String> dGraph;  //有向图
    public CheckBridgeWord(DirectedGraphInterface dGraph) {
        this.dGraph = dGraph;
    }
    /*查询桥接词*/
    public String queryBridgeWords(String word1, String word2){
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
}
