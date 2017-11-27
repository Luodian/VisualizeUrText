package sample.control;

import sample.GraphInterface.DirectedGraphInterface;
import sample.GraphInterface.VertexInterface;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class GenerateNewText {
    public DirectedGraphInterface<String> dGraph;  //有向图

    public GenerateNewText(DirectedGraphInterface dGraph) {
        this.dGraph = dGraph;
    }

    /*生成新文本*/
    public String generateNewText(String originText){
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
}
