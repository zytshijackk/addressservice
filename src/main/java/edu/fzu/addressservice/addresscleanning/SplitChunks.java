package edu.fzu.addressservice.addresscleanning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class SplitChunks {
	/**
	 * 功能：按照地址的前几级对地址进行分块
	 * @param list 待分块的地址集
	 * @return 分块后的地址集 TreeMap<String, List<TreeMap<Integer, List<String>>>>
	 */
  public static TreeMap<String, List<TreeMap<Integer, List<String>>>> splitChunk(List<Entry<Integer, List<String>>> list){
	  TreeMap<Integer,List<String>> temp = new TreeMap<Integer,List<String>>();
	  List<TreeMap<Integer,List<String>>> tempList = new ArrayList<TreeMap<Integer,List<String>>>();
	  TreeMap<String,List<TreeMap<Integer,List<String>>>> treeMap = new  TreeMap<String,List<TreeMap<Integer,List<String>>>>();
	  
	  for(Entry<Integer, List<String>> str :list){
//		  System.out.println(str);
		   //构造分块的地址key
		  	StringBuffer strBuf = new StringBuffer();
	  		for(int i=0;i<2;i++){
	  		  strBuf.append(str.getValue().get(i));
		  	}
			String strs =strBuf.toString();
			//如果map中不含该key则直接插入，否则先获取该key有的地址集，先将该地址加入到已有的地址集中，再加入map中
			if(!treeMap.containsKey(strs)){
				temp.put(str.getKey(), str.getValue());
				tempList.add(temp);
				
			}else{
				temp.put(str.getKey(), str.getValue());
				tempList = treeMap.get(strs);
				tempList.add(temp);
			}
			
			treeMap.put(strs, tempList);
			temp = new TreeMap<Integer,List<String>>();
			tempList = new ArrayList<TreeMap<Integer,List<String>>>();
	  }
	  return treeMap;
  }
	
}
