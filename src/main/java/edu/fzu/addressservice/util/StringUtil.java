/**
 * 
 */
package edu.fzu.addressservice.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author HC
 * @create 2017年8月10日
 * @code
 */
public class StringUtil {
	/**
	 * 将集合元素转换成字符串。
	 * 
	 * @param collection
	 *            待转换的集合。集合允许嵌套，即集合元素本身也是一个集合。
	 * @param splitChars
	 *            分隔符集合，collection中从最外层至最内层依次转换成字符串时的分隔符组成的集合。
	 * @return String collection转换得到的字符串。
	 */
	public final static String convertToString(Collection collection, String[] splitChars) {
		StringBuffer strBuf = new StringBuffer();
		for (Iterator i = collection.iterator(); i.hasNext();) {
			Object s = i.next();
			String str = s instanceof Collection ? convertToString((Collection) s, splitChars) : (String) s;
			strBuf.append(str);
			if (i.hasNext())
				strBuf.append(splitChars[0]);
		}
		return strBuf.toString();
	}

	/**
	 * 将集合元素转换成字符串。
	 * 
	 * @param collection
	 *            待转换的一层的元素集合。集合允许嵌套。
	 * @param splitChars
	 *            分隔符集合，collection中从最外层至最内层依次转换成字符串时的分隔符组成的集合。
	 * @param splitIndex
	 *            本层元素集合转换为字符串时的分隔符在splitChars中的下标。
	 * @return collection转换得到的字符串。
	 */
	public final static String convertToString(Collection collection, String[] splitChars, int splitIndex) {
		StringBuffer strBuf = new StringBuffer();
//		System.out.println(collection.size());
		for (Iterator i = collection.iterator(); i.hasNext();) {
			Object s = i.next();
			String str = s instanceof Collection ? convertToString((Collection) s, splitChars, splitIndex + 1)
					: (String) s;
			strBuf.append(str);
			if (i.hasNext())
				strBuf.append(splitChars[splitIndex]);
		}
		return strBuf.toString();
	}

	public static String jointString(String str1, String str2,String splitchar) {
		StringBuffer strBuf = new StringBuffer();
		if(str1.endsWith(splitchar)){
			strBuf.append(str1).append(str2);
		}else{
			strBuf.append(splitchar).append(str1).append(splitchar).append(str2);
		}
		return strBuf.toString();
	}
	
	public static String arrayToString(String[] str) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < str.length; i++){
			strBuf.append(str[i]).append(",");
		}
		strBuf.deleteCharAt(strBuf.length()-1);
		return strBuf.toString();
	}
	public static String intArrayToString(int[] str) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < str.length; i++){
			strBuf.append(String.valueOf(str[i])).append(",");
		}
		strBuf.deleteCharAt(strBuf.length()-1);
		return strBuf.toString();
	}

	public static String convertToString(List<String> value, char c) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
//	
//	public static List<List<String>> listJoin(List<List<String>>afterLastExtraction,List<String> list3){
//		List<List<String>> resultsmiddle = new ArrayList<List<String>>();
//		for(List<String> list4 : afterLastExtraction){
//			List<String> result = new ArrayList<String>();
//			for(int y = 0; y < list3.size() - 1; y++){
//				result.add(list3.get(y));
//			}
//			for(int j = 0; j < list4.size(); j++){
//				result.add(list4.get(j));
//			}
//			resultsmiddle.add(result);
//		}
//		return resultsmiddle;
//	}
//	
//	public static List<String> getList(List<String> list3){
//		List<String> temp = new ArrayList<String>();
//		if(list3.get(4) == null){
//			temp.add("");
//		}else{
//			temp.add(list3.get(4));
//		}
//		return temp;
//	}

}
