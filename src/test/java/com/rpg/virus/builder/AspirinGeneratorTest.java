package com.rpg.virus.builder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.rpg.virus.databuilder.AspirinGenerator;
import com.rpg.virus.pojo.Aspirin;
import com.rpg.virus.util.CommonUtils;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class AspirinGeneratorTest {

	@Mock
	CommonUtils utils;	
	
	@InjectMocks
	AspirinGenerator aspirinGenerator; 
 
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetAspirinObject() {
		Set<String> aspirinSet = new HashSet<String>();
		aspirinSet.add("aspirinName");
		aspirinGenerator.setAspirinNameSet(aspirinSet);
		Aspirin aspirin = new Aspirin();
		aspirin.setAspirinName("aspirinName");
		when(utils.readDataFromFile(anyString())).thenReturn(aspirin);
		Aspirin actualResult = aspirinGenerator.getAspirinObject("aspirinName");
		assertEquals(actualResult.getAspirinName(), aspirin.getAspirinName());
	}
	
	@Test
	public void testGetAspirinObjectAsNullWhenAspirinSetIsEmpty() {
		Aspirin aspirin = new Aspirin();
		aspirin.setAspirinName("aspirinName");
		Aspirin actualResult = aspirinGenerator.getAspirinObject("aspirinName");
		assertNull(actualResult);
	}
}
