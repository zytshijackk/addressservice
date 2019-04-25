package edu.fzu.addressservice.addressmanager;

import static org.junit.Assert.*;

import edu.fzu.addressservice.util.FuzzyMatching;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @description  模糊匹配测试
 * @author HC
 * @create 2017年7月20日
 * @code
 */
public class FuzzyMatchTest {

		FuzzyMatching fm = new FuzzyMatching();
		@Before
		public void setUp() throws Exception {
		}

		@After
		public void tearDown() throws Exception {
		}

		@Test
		public void testFuzzyMatch() {
			System.out.println(fm.fuzzyMatch("槐店回族镇","槐店镇",1));//f
			System.out.println(fm.fuzzyMatch("大兴安岭地区", "大兴区",1));//f
			System.out.println(fm.fuzzyMatch("凤凰街道", "凤凰镇", 0));//f
			System.out.println(fm.fuzzyMatch("凤凰镇", "凤凰街道", 0));//f
			System.out.println(fm.fuzzyMatch("凤凰街道", "凤凰镇", 1));//t
			System.out.println(fm.fuzzyMatch("凤凰镇", "凤凰街道", 1));//t
			System.out.println(fm.fuzzyMatch("仓山区", "仓山县", 0));//f
			System.out.println(fm.fuzzyMatch("仓山县", "仓山区", 0));//f
			System.out.println(fm.fuzzyMatch("仓山区", "仓山县", 1));//t
			System.out.println(fm.fuzzyMatch("仓山县", "仓山区", 1));//t
			System.out.println(fm.fuzzyMatch("凤凰乡", "凤凰镇", 0));//f
			System.out.println(fm.fuzzyMatch("凤凰镇", "凤凰乡", 1));//t
			System.out.println(fm.fuzzyMatch("凤凰街道", "凤凰乡", 0));//f
			System.out.println(fm.fuzzyMatch("凤凰乡", "凤凰街道", 1));//t
			
		}

		@Test
		public void testLCS() {
			char[] a ="福州市晋安区".toCharArray();
	        char[] b ="福州晋安".toCharArray();
			//System.out.println(fm.LCS(a, b));
		}



}
