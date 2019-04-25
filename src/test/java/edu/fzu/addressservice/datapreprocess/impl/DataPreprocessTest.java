package edu.fzu.addressservice.datapreprocess.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;



/**
 * @Author CH
 * @Description //TODO 
 * @Date 15:45 2019/4/22
 **/
public class DataPreprocessTest {
    DataPreprocess dp = new DataPreprocess();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddressPreprocess() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsChineseAddress() {
		fail("Not yet implemented");
	}

	@Test
	public void testFilterSpecialSymbol() {
		fail("Not yet implemented");
	}

	@Test
	public void testSplitWords() {
		System.out.println(dp.splitWords("福建福州仓山区金山大道198").toString());
	}

}
