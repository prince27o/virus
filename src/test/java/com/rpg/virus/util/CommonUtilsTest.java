package com.rpg.virus.util;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.mockito.InjectMocks;

import com.rpg.virus.BaseTest;

public class CommonUtilsTest extends BaseTest {

	@InjectMocks
	CommonUtils utils;	
 
	@Test
	public void testLoadAspirinProperties() {		
		Set<String> set = utils.loadProperties("aspirin.list.all");
		assertTrue(set.contains("advil"));
		assertTrue(set.contains("motrin"));
		assertTrue(set.contains("aleve"));
	}
	
	@Test
	public void testLoadVirusProperties() {		
		Set<String> set = utils.loadProperties("virus.list.all");
		assertTrue(set.contains("rubella"));
		assertTrue(set.contains("roseola"));
		assertTrue(set.contains("nipah"));
	}
		
}

