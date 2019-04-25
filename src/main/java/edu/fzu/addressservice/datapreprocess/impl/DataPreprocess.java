package edu.fzu.addressservice.datapreprocess.impl;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import edu.fzu.addressservice.datapreprocess.IDataPreprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * @description 地址预处理
 * @author HC
 * @create 2017年7月20日
 *
 * 将源地址数据输入后进行去除噪声地址、
 * 去除地址中的特殊符号等工作后，由HanLP切词工具粗分成词。
 *
 */
public class DataPreprocess implements IDataPreprocess {
	/**
	 * @description 对地址数据进行清洗，去除地址中的干扰字符，并对中文地址进行切词。
	 * @param inputpath 待分词地址所在的文件路径
	 * @return 分词后的词列表。以二重嵌套列表的形式返回。
	 * @throws IOException
	 */
	public List<String> addressPreprocess(String address) throws IOException {
		String tempstr=filterSpecialSymbol(address);
		List<String> temp =new ArrayList<String>();
		//if(isChineseAddress(tempstr)){
		tempstr=tempstr.replaceAll(" ", "");
		//分词
		temp =	splitWords(tempstr);
//		}else{
//			temp = null;
//		}

		return temp;
	}


	/**
	 * @description 判断地址编码是否异常以及是否是中文地址
	 * @param str 待识别的地址串
	 * @return true/false
	 */
	public static boolean isChineseAddress(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
	/**
	 * @description 地址中的删除特殊字符
	 * @param str 待处理的地址字符串
	 * @return 清除特殊符号后的地址字符串
	 * @throws PatternSyntaxException
	 */
	public   static  String filterSpecialSymbol(String  str)   throws   PatternSyntaxException   {
        // 清除掉所有特殊字符
		String regEx="[`~!@$%^&*()+=|{}':;',\\[\\].�Ȫ㽭<>/?~！@￥%……&*（）+_|{}【】‘；：”“’。，、？]";
		Pattern   p   =   Pattern.compile(regEx);
		Matcher   m   =   p.matcher(str);
		String resultstr = m.replaceAll("").trim();
		//String result = resultstr.replace("-", "");
		return  resultstr ;
  }
	/**
	 * @description 对中文地址进行分词。上海林原信息科技有限公司开发的hanlp，版本为 version 1.2.8 。
	 * @param address 待分词地址
	 * @return 分词后的词集合
	 */
	public static List<String> splitWords(String address){
		//调用HanLp分词
		HanLP.Config.ShowTermNature = true;
		List<String> temp = new ArrayList<String>();
		List<Term> wordlist = HanLP.segment(address);
		for(Term term : wordlist){
			if(term.word != null && (!"".equals((term.word).trim()))) {
				temp.add(term.toString());
			}
		}
		// 地址结果存为list
		//System.out.println(temp.toString());
		return temp;

	}

	public static void main(String[] args) {
		HanLP.Config.ShowTermNature = false;
		List<String> temp = new ArrayList<String>();
		List<Term> wordlist = HanLP.segment("这里分词");
		for(Term term : wordlist){
			if(term.word != null && (!"".equals((term.word).trim()))) {
				temp.add(term.toString());
			}
		}
//		System.out.println(temp.toString());
	}

}
