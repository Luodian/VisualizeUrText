package sample.control;

import sample.GraphInterface.DirectedGraphInterface;
import sample.boundary.GraphDisplayForm;
import sample.entity.Picture;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomWalk {
    public DirectedGraphInterface<String> dGraph;  //有向图
    private GraphDisplayForm graphDisplayForm;
    private boolean stopFlag;
    private List<String> randomWalkList = new ArrayList<>();        //随机游走记录路径的链表

    private Picture picture = new Picture();
    public List<String> getRandomWalkList() {
        return randomWalkList;
    }
    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }
    public boolean getStopFlag() {
        return stopFlag;
    }
    public RandomWalk(DirectedGraphInterface dGraph, GraphDisplayForm graphDisplayForm) {
        this.dGraph = dGraph;
        this.graphDisplayForm = graphDisplayForm;
        stopFlag = false;
        picture.dGraph = dGraph;
    }

    private boolean hasSameEdge(List<String> list, String word1, String word2){
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

    /*将double类型的权值化为int类型*/
    private int getEdgeWeightInt(String word1, String word2){
        if(dGraph.getEdgeWeight(word1,word2)==Double.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else
            return (int) dGraph.getEdgeWeight(word1,word2);
    }
    public String randomWalk(){
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
                picture.dotLines.add(line);
            }
            pre=cur;
        }
        picture.showDirectedPicture(randomWalkList.get(0)+"to"
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
    
}
