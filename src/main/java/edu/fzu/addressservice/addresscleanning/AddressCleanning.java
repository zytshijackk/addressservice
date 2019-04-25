package edu.fzu.addressservice.addresscleanning;



import edu.fzu.addressservice.util.PropertyOpt;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 功能：地址去重模块入口函数
 * @author HC
 * @create 2017年10月24日
 * @code
 */
public class AddressCleanning {
	 public List<List<String>> addressCleanning(List<List<String>> resultsmiddle) throws IOException{
	   //待去重地址存放路径
//	   List<List<String>> addressList = FileUtils.getListFromFile("result/result2.txt");
	   List<List<String>> addressList = resultsmiddle;
	   PropertyOpt prop = PropertyOpt.getInstance();
	   //以TreeMap<key,value>的形式存放地址
	   TreeMap<Integer,List<String>> treeMapSourse = new TreeMap<Integer,List<String>>();
	   Long startTime=System.currentTimeMillis();
       TreeMap<String, List<TreeMap<Integer, List<String>>>> map = new TreeMap<String, List<TreeMap<Integer, List<String>>>>();
       SplitChunks sc = new SplitChunks();
	   SNM snm = new SNM();
	   HashMap<String,Set<List<String>>> hm= new HashMap<String,Set<List<String>>>();
	   List<List<String>> result = new ArrayList<List<String>>();//存放结果地址集
	   int itr =0;
	  //将地址表示成TreeMap<key,value>的形式。以输入的次序为key，以地址为value。
	   for(List<String> strTemp :addressList){
		   treeMapSourse.put(itr, strTemp);
		   itr++;
	   }
   
//	   System.out.println("排序前。。。。。。。。。。。");
//	   Iterator titer=treeMapSourse.entrySet().iterator();  
//        while(titer.hasNext()){  
//            Map.Entry ent=(Map.Entry )titer.next();  
//            String key=ent.getKey().toString();  
//            String value=ent.getValue().toString();  
//            System.out.println(key+""+value);  
//        }  
	   //对地址进行排序
	   List<Entry<Integer,  List<String>>> list = new ArrayList<Entry<Integer, List<String>>>(treeMapSourse.entrySet());
        // 通过比较器来实现排序
        Collections.sort(list, new Comparator<Entry<Integer, List<String>>>() {
            @Override
            public int compare(Entry<Integer, List<String>> o1, Entry<Integer, List<String>> o2) {
                // 升序排序
                return (o2.getValue().toString().replaceAll("null", "")).compareTo(o1.getValue().toString().replaceAll("null", ""));
            }
        });
//        for (Entry<Integer, List<String>> mapping : list) {
//            System.out.println(mapping.getKey() + ":" + mapping.getValue());
//        }
//        System.out.println("排序后。。。。。。。。。。。");
//        for(Entry<Integer,  List<String>> en:list){
//        	System.out.println(en.getKey()+""+en.getValue());
//        }
       //对地址进行分块
	   map=sc.splitChunk(list);
	   for (Entry<String, List<TreeMap<Integer, List<String>>>> entry : map.entrySet()) {
		   //System.out.println("key:"+entry.getKey()+"  value:"+entry.getValue().toString());
		}
	   //地址按key升序
	   TreeMap<Integer,List<String>> resultTemp = new TreeMap<Integer,List<String>>(new Comparator<Integer>(){
			@Override
			public int compare(Integer a, Integer b) {
				return a-b;
			}
	   });

	   //对地址进行补全操作
	   Iterator it=map.entrySet().iterator();
        while(it.hasNext()){
            Entry entry=(Entry )it.next();
            List<TreeMap<Integer, List<String>>> lo =  (List<TreeMap<Integer, List<String>>>) entry.getValue();
            for(TreeMap<Integer, List<String>> trm:snm.addressCleaning(lo)){
            	resultTemp.putAll(trm);
            }
        }
        //将地址次序还原为输入次序
        Iterator its=resultTemp.entrySet().iterator();
        while(its.hasNext()){
            Entry entry=(Entry )its.next();
            result.add((List<String>) entry.getValue());
        }  
        
//        for(List<String> each:result){
//        	System.out.println(each.toString());
//        	
//        }
       
	   Long endTime=System.currentTimeMillis();
	   //System.out.println("address cleanning ending ,total consume time:"+(endTime-startTime)+"ms"); 
	   return result;
	 }


 }
