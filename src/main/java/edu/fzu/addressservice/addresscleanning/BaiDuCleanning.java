package edu.fzu.addressservice.addresscleanning;

import edu.fzu.bigdatalab.addressrecognition.addressmanager.StandardAddress;
import edu.fzu.bigdatalab.addressrecognition.addressmanager.TrieTree;
import edu.fzu.bigdatalab.addressrecognition.addressmanager.impl.AddressManager;
import edu.fzu.bigdatalab.addressrecognition.addressmanager.impl.AddressModelManager;
import edu.fzu.bigdatalab.addressrecognition.addressrecognition.ExtractTheRestAddresses;
import edu.fzu.bigdatalab.addressrecognition.datapreprocess.impl.DataPreprocess;
import edu.fzu.bigdatalab.addressrecognition.util.PropertyOpt;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：利用百度地图地址进行地址补全
 * @author HC
 * @create 2017年11月19日
 * @code
 */
public class BaiDuCleanning {
	  /**
	   * 功能：利用百度API的结果进行地址补全
	   * @param cleanningAddress  待补全的地址
	   * @param baiDuAddress 百度返回的地址
	   * @return 补全后的地址
	   * @throws Exception
	   */
      public static List<String> baiDuCleanning(List<String> cleanningAddress,String baiDuAddress) throws Exception{
    	//参数读取设置
    	PropertyOpt prop = PropertyOpt.getInstance();
        String standardPath = prop.getStandardaddressinputpath();
        //建立行政级别的地址标准树
    	AddressManager addressManager = new AddressManager();
		AddressModelManager amm = new AddressModelManager();
		List<StandardAddress> standardAddresses = addressManager.loadFromFile(standardPath);
		TrieTree tree = amm.buildModel(standardAddresses);
		//地址预处理模块，实现分词功能
		DataPreprocess dp = new DataPreprocess();
		//提取后6级地址
		ExtractTheRestAddresses ela = new ExtractTheRestAddresses();
		//打印分词后的结果
		//System.out.println(dp.addressPreprocess(baiDuAddress).toString());
		//存放补全后的地址
		List<String> resultAddress = new ArrayList<String> ();
		
		//提取前四级地址
		List<List<String>> afterFourExtraction = amm.searchModel(dp.addressPreprocess(baiDuAddress)); // 外围list的大小可能大于1.
		for (List<String> list3 : afterFourExtraction) {
			// 创建一个List<String>，完整存放样本数据的一种匹配地址。可以直接利用list3。
			// 创建一个临时的List<String>，用于传给ExtractLastAddress.extractLastAddress进行后六级提取。
			List<String> temp = new ArrayList<String>();
			// temp = StringUtil.getList(list3);
			// List<String> temp = new ArrayList<String>();
			if (list3.get(4) == null) {
				temp.add("");
			} else {
				temp.add(list3.get(4));
			}
			//提取后六级地址
			List<List<String>> afterLastExtraction = ela.extractLastAddress(temp);
			for (List<String> list4 : afterLastExtraction) {
				List<String> result = new ArrayList<String>();
				for (int y = 0; y < list3.size() - 1; y++) {
					result.add(list3.get(y));
				}
				for (int j = 0; j < list4.size(); j++) {
					result.add(list4.get(j));
				}
				//System.out.println("result:"+result.toString());
				//调用地址补全函数进行补全
				resultAddress=completeAddress(cleanningAddress,result);
			}
			
      }
		//返回补全后的地址
		return resultAddress;
		
  }
      /**
       * 功能：地址补全算法
       * @param cleanningAddress 待补全的地址
       * @param baiDuAddress 百度返回的地址
       * @return 补全后的地址
       */
      private static List<String> completeAddress(List<String> cleanningAddress,List<String> baiDuAddress){
    	  for(int i=0;i<=9;i++){
    		  //if()
    		  if(("null".equals(cleanningAddress.get(i)))&&(!("null".equals(baiDuAddress.get(i))))){
    			  cleanningAddress.set(i, baiDuAddress.get(i));
    		  }
    	  }
    	  
    	  return cleanningAddress;
    	  
      }
//      public static void main(String[] args) throws Exception {
//    	  List <String> cleanningAddress = new ArrayList <String>();
//    	  cleanningAddress.add("福建省");
//    	  cleanningAddress.add("福州市");
//    	  cleanningAddress.add("null");
//    	  cleanningAddress.add("null");
//    	  cleanningAddress.add("null");
//    	  cleanningAddress.add("null");
//    	  cleanningAddress.add("福州大学");
//    	  cleanningAddress.add("null");
//    	  cleanningAddress.add("null");
//    	  cleanningAddress.add("null");
//    	  
//    	  System.out.println(baiDuCleanning(cleanningAddress,"福建省福州市学园路2号").toString());
//    	  
//	}
}
