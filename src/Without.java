package sample;

import java.io.*;
import java.util.*;

public class Without {
	private sample.DirectedGraphInterface<String> dGraph  =  new sample.DirectedGraph<> ();  //有向图
	
	private  int indexEnd = 0; //要切换的路线的最终word的id
	private  String paths;                                               //最短路径的表示
	
	private  String processLine = "";                                    //处理后的文本
	private  String originLine = "";                                     //处理前的文本
	
	private  Map<String,  Stack<String>> parent =  new HashMap<>();         //记录父节点，使用Stack，这样可以找多个路径
	private  List<List<String>> totalList = new ArrayList<>();             //逆向路径总表
	
	public void readFile(String fileName){
		
		dGraph.clear();
		indexEnd = 0;
		paths  =  "";
		processLine = "";
		originLine = "";
		parent.clear();
		totalList.clear();
		InputStreamReader reader  = new InputStreamReader(System.in);
		try {
			reader  =  new InputStreamReader(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br  =  new BufferedReader(reader);
		String line  =  "";
		
		try {
			line  =  br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String tmpStr  =  "";
		StringBuffer tmp = new StringBuffer("");
		while (line !=  null) {
			originLine +=  line + "\n";
			char[] chars  =  line.toCharArray();
			tmp.append(' ');
			for (char chr : chars) {
				if (chr >=  'A' && chr <=  'Z') {
					chr +=  ('a' - 'A');
				} else if (chr  ==  ',' || chr  ==  '.' || chr  ==  '?' || chr  ==  '!' || chr  ==  34 || chr  ==  39 || chr  ==  ' ') {
					chr  =  ' ';
				} else if (chr < 'a' || chr > 'z') {
					continue;
				}
				tmp.append(chr);
			}
			tmpStr = tmp.toString();
			try {
				line  =  br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		StringTokenizer st  =  new StringTokenizer(tmpStr);
		while (st.hasMoreTokens()) {
			String tmpWord  =  st.nextToken();
			processLine +=  tmpWord;
			processLine +=  ' ';
		}
		processLine  =  processLine.trim();
		st  =  new StringTokenizer(processLine);
		String pre = null;
		String cur = null;
		while(st.hasMoreTokens()){
			cur = st.nextToken();
			if(!dGraph.getVerTex().containsKey(cur)){
				dGraph.addVertex(cur);
			}
			if(pre!= null){
				dGraph.addEdge(pre, cur, 1);
			}
			pre = cur;
		}
	}
	
	/*接上桥接词*/
	public String listToStr(List<String> usefulWords) {
		int size  =  usefulWords.size();
		StringBuilder output =  new StringBuilder();
		int outNum = 0;
		for (String word:usefulWords) {
			outNum++;
			if (outNum != size){
				output.append(" ");
				output.append(word);
				output.append(',');
			} else{
				output.append(" and ");
				output.append(word);
				output.append('.');
			}
		}
		return output.toString();
	}
	
	/*查询桥接词*/
	public String queryBridgeWords(String fileName,String word1,  String word2){
		
		readFile(fileName);
		
		Map<String, sample.VertexInterface<String>> vertexMap = dGraph.getVerTex();
		List<String> usefulWords = new ArrayList<>();
		StringBuilder output =  new StringBuilder();
		if ((!vertexMap.containsKey(word1))&&(!vertexMap.containsKey(word2))){
			output  =  new StringBuilder("No \""+ word1+ "\" and \""+word2+"\" in the graph");
		} else if(!vertexMap.containsKey(word1)) {
			output  =  new StringBuilder("No \""+ word1+ "\" in the graph");
		} else if (!vertexMap.containsKey(word2)){
			output  =  new StringBuilder("No \""+ word2+ "\" in the graph");
		} else {
			for (String tmpVertex:vertexMap.keySet()) {
//                if (tmpVertex.equals(word1) || tmpVertex.equals(word2)){
//                    continue;
//                }else
				if (dGraph.hasEdge(word1, tmpVertex) && dGraph.hasEdge(tmpVertex, word2)){
					usefulWords.add(tmpVertex);
				}
			}
			if (usefulWords.isEmpty()){
				output  =  new StringBuilder("No bridge words from \""+ word1+ "\" to \"" + word2+ "\"");
			} else if (usefulWords.size() == 1) {
				output  =  new StringBuilder("The bridge word from \""+ word1 + "\" to \""+ word2 + "\" is: ");
				output.append(usefulWords.get(0));
				output.append(".");
			} else {
				output  =  new StringBuilder("The bridge words from \""+ word1 + "\" to \""+ word2 + "\" are:");
				output.append(listToStr(usefulWords));
			}
		}
		return output.toString();
	}
	
	/*将double类型的权值化为int类型*/
	private int getEdgeWeightInt(String word1,  String word2){
		if(dGraph.getEdgeWeight(word1, word2) == Double.MAX_VALUE){
			return Integer.MAX_VALUE;
		}else {
			return (int) dGraph.getEdgeWeight(word1, word2);
		}
	}
	
	private void Path(String sorceWord) {
		//初始化
//        cost.clear();
		Map<String, Integer> cost  =  new HashMap<>();
		Set<String> producedWords  =  new HashSet<>();
		parent.clear();
		Set<String> allWords = dGraph.getVerTex().keySet();
		producedWords.clear();
		Map<String, Boolean> flag =  new HashMap<>();
		for (String word:allWords)
		{
			if (!word.equals(sorceWord))
			{
				int weight  =  getEdgeWeightInt(sorceWord, word);
				cost.put(word, weight);
				Stack<String> stack  = new Stack<>();
				stack.add(sorceWord);
				parent.put(word, stack);  //父节点是sorceword，表示到达了源点，这里包括那些和源点没有边的情况
				flag.put(word, false);   //false表示还没有加入producedWords
			}
		}
		producedWords.add(sorceWord);
		flag.put(sorceWord, true);
//        allWords.remove(sorceWord);
		while(producedWords.size()!= allWords.size()){
			String curStr = null;
			int curCost  =  Integer.MAX_VALUE;
//            int curCost  =  10000;
			for (Map.Entry<String, Integer> entry:cost.entrySet()) {
				if (!flag.get(entry.getKey()) &&entry.getValue()<curCost){
					curCost = entry.getValue();
					curStr = entry.getKey();
				}
			}
			if(curStr!=null && curStr.equals("")){
				for (Map.Entry<String, Integer> entry:cost.entrySet()) {
					if (!flag.get(entry.getKey())){
						curCost = entry.getValue();
						curStr = entry.getKey();
						break;
					}
				}
			}
			producedWords.add(curStr);
			flag.put(curStr, true);
			for (String word:allWords) {
				if(!flag.get(word)){
					if (formatPlus(curCost, getEdgeWeightInt(curStr, word))<cost.get(word)){
						cost.put(word, formatPlus(curCost, getEdgeWeightInt(curStr, word)));
						parent.get(word).clear();
						parent.get(word).add(curStr);
					}else if(formatPlus(curCost, getEdgeWeightInt(curStr, word)) == cost.get(word)){
						parent.get(word).add(curStr);
					}
				}
			}
		}
	}
	
	private int formatPlus(int num1, int num2){
		if(num1 == Integer.MAX_VALUE||num2 == Integer.MAX_VALUE){
			return Integer.MAX_VALUE;
		}else{
			return num1+num2;
		}
	}
	
	
	private boolean getPre(List<String> preList,  String sourceWord, String word){
		List<String> list  =  new ArrayList<>(preList);
		list.add(word);
		if(word.equals(sourceWord)){
			totalList.add(list);
			return true;
		}
		boolean rst = true;
		for (String perParent:parent.get(word)) {
			if (getEdgeWeightInt(perParent, word) == Integer.MAX_VALUE)
			{
				return false;
			}
			rst = rst&getPre(list, sourceWord, perParent);
		}
		return rst;
	}
	
	
	public String calcShortestPath(String fileName,String word1,String word2){
		
		readFile(fileName);
		
		if (dGraph.getVerTex ().keySet ().size () == 0)
		{
			return "空图";
		}
		
		if( !(word1.equals("")) && !(word2.equals("")))
		{
			if (!dGraph.getVerTex().keySet().contains(word1)||!dGraph.getVerTex().keySet().contains(word2)){
				return "至少有一个word不存在";
			}
			Path(word1);
			if (!getPre(new ArrayList<>(), word1, word2)){
				return "不可达";
			}
		}
		//都是空串
		else if (word1.equals("") && word2.equals("")){
			return "起点和终点均为空";
		}
		else if (word1.equals (""))
		{
			return "起点不能为空";
		}
		//其中一个是空串，另一个不是空串
		else{
			String sourceWord  =  null;
			if (word1.equals("") && !(word2.equals(""))){
				sourceWord  =  word2;
			} else if (!(word1.equals("")) && word2.equals("")){
				sourceWord = word1;
			}
			if (!dGraph.getVerTex().keySet().contains(sourceWord)){
				return "该单词不存在";
			}
			Path(sourceWord);
			boolean allUnreachable = true;
			for (String word:dGraph.getVerTex().keySet()) {
				if (!word.equals(sourceWord) && getPre(new ArrayList<>(), sourceWord, word)){
					allUnreachable = false;           //不可达
				}
			}
			if (allUnreachable){
				return "该点到其他点全都不可达";
			}
			
			List<List<String>> lists = new ArrayList<>();
			String cur = null;
			String pre = null;
			for (List<String> list:totalList){
				cur = list.get(0);
				if (pre == null || pre.equals(cur)){
					lists.add(list);
				} else{
					lists.clear();
					lists.add(list);
				}
				pre = cur;
			}
//            localPicture(sourceWord, pre, lists);
			lists.clear();
		}
		StringBuffer path  =  new StringBuffer("");
		for (List<String> list:totalList)
		{
			StringBuffer line = new StringBuffer("");
			int sum = 0;
			String pre  =  null;
			int index = list.size() - 1;
			while (index >= 0)
			{
				String word = list.get(index--);
				if (pre != null)
				{
					sum += dGraph.getEdgeWeight(pre, word);
					line.append(" -> "+ word);
				}
				else
				{
					line = new StringBuffer(word);
				}
				pre = word;
			}
			line.append(" : "+ sum+ "\n");
			path.append(line);
		}
		return path.toString();
	}
	
	public static void main(String[] args) {

	}
}


