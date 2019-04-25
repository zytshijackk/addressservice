package edu.fzu.addressservice.datapreprocess;

import edu.fzu.addressservice.datapreprocess.impl.DataPreprocess;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TestSplitWord {

	 public static void main(String[] args) throws IOException {
		 TestSplitWord splitWordTest  = new  TestSplitWord();
		 splitWordTest.testRun();

	   }
	 @Test
	 public void testRun() throws IOException{
		System.out.println("首次编译运行时，HanLP会自动构建词典缓存，请稍候……\n");
//        String inputpath ="./sampledata/test.txt";
		 //n名词，m数词
        String inputpath ="福建福州仓山区金山大道198!";//福建/n 福州/n 仓山区/n 金山大道/n 198/m
        List<String> list = new ArrayList<String>();
        DataPreprocess segmentAddress = new DataPreprocess();
        list = segmentAddress.addressPreprocess(inputpath);
        for (String list1 :list ){
        		System.out.print(list1+" ");
        }
	 }

}
