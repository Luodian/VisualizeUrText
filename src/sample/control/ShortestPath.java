package sample.control;

import sample.GraphInterface.DirectedGraphInterface;
import sample.entity.Picture;

import java.util.*;

public class ShortestPath {

    public DirectedGraphInterface<String> dGraph;  //有向图
    private Picture picture = new Picture();
    private int indexEnd=0;                                            //要切换的路线的最终word的id
    private String paths;                                               //最短路径的表示
    private Map<String, Stack<String>> parent= new HashMap<>();         //记录父节点，使用Stack，这样可以找多个路径
    private List<List<String>> totalList=new ArrayList<>();             //逆向路径总表
    public ShortestPath(DirectedGraphInterface dGraph) {
        this.dGraph = dGraph;
        picture.dGraph = dGraph;
    }
    /*将double类型的权值化为int类型*/
    private int getEdgeWeightInt(String word1, String word2){
        if(dGraph.getEdgeWeight(word1,word2)==Double.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else
            return (int) dGraph.getEdgeWeight(word1,word2);
    }



    public String calcShortestPath(String word1, String word2){
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
            picture.localPicture(word1,word2,totalList);
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
                    picture.localPicture(sourceWord,pre,lists);
                    lists.clear();
                    lists.add(list);
                }
                pre=cur;
            }
            picture.localPicture(sourceWord,pre,lists);
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

}
