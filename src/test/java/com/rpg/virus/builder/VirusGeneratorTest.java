package com.rpg.virus.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.rpg.virus.databuilder.VirusGenerator;
import com.rpg.virus.pojo.VirusInfo;
import com.rpg.virus.util.CommonUtils;

@RunWith(MockitoJUnitRunner.class)
public class VirusGeneratorTest {

	@Mock
	CommonUtils utils;	
	
	@InjectMocks
	VirusGenerator virusGenerator; 
 
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetVirusObject() {
		Set<String> virusSet = new HashSet<String>();
		virusSet.add("nipha");
		virusGenerator.setVirusNameSet(virusSet);
		VirusInfo virusInfo = new VirusInfo();
		virusInfo.setVirusName("nipha");
		when(utils.readDataFromFile(anyString())).thenReturn(virusInfo);
		VirusInfo actualResult = virusGenerator.getVirusObject("nipha");
		assertEquals(actualResult.getVirusName(), virusInfo.getVirusName());
	}
	
	@Test
	public void testGetVirusObjectAsNullWhenAspirinSetIsEmpty() {
		VirusInfo virusInfo = new VirusInfo();
		virusInfo.setVirusName("nipha");
		VirusInfo actualResult = virusGenerator.getVirusObject("nipha");
		assertNull(actualResult);
	}
}
