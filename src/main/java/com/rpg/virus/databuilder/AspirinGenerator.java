package com.rpg.virus.databuilder;

import static com.rpg.virus.constants.VirusConstants.SAV_FILE_EXTENTION;

import java.util.Set;

import com.rpg.virus.pojo.Aspirin;
import com.rpg.virus.util.CommonUtils;

public class AspirinGenerator {

	private CommonUtils utils;
	private Set<String> aspirinNameSet;

	public AspirinGenerator(CommonUtils utils) {
		this.utils = utils;
		this.aspirinNameSet = this.utils.loadProperties("aspirin.list.all");
	}
	
	/**
	 * Method is a factory of Aspirin. Creates Aspirin Object with the given name
	 * @param aspirinName
	 * @return
	 */
	public Aspirin getAspirinObject(String aspirinName) {
		Aspirin aspirin = null;
		if(aspirinNameSet!= null & aspirinNameSet.contains(aspirinName)){
			String datafilePath = aspirinName + SAV_FILE_EXTENTION;
			Object object= utils.readDataFromFile(datafilePath);
			aspirin = (object instanceof Aspirin) ? (Aspirin) object : null; 
		}
		return aspirin;
	}

	
	public Set<String> getAspirinNameSet() {
		return aspirinNameSet;
	}

	public void setAspirinNameSet(Set<String> aspirinNameSet) {
		this.aspirinNameSet = aspirinNameSet;
	}
	
}
