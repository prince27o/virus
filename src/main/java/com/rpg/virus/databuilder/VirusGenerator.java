package com.rpg.virus.databuilder;

import static com.rpg.virus.constants.VirusConstants.SAV_FILE_EXTENTION;

import java.util.Set;

import com.rpg.virus.pojo.VirusInfo;
import com.rpg.virus.util.CommonUtils;

public class VirusGenerator {
	
	private CommonUtils utils;
	private Set<String> virusNameSet;	
	
	public VirusGenerator(CommonUtils utils) {
		this.utils = utils;
		this.virusNameSet = this.utils.loadProperties("virus.list.all");		
	}
	
	/**
	 * Method is a factory of Virus. Creates Virus Object with the given name
	 * from the pre-saved virus information
	 * 
	 * @param aspirinName
	 * @return
	 */
	public VirusInfo getVirusObject(String virusName) {
		VirusInfo virusInfo = null;
		if (virusNameSet != null && virusNameSet.contains(virusName)) {
			String datafilePath = virusName + SAV_FILE_EXTENTION;
			Object object = utils.readDataFromFile(datafilePath);
			virusInfo = (object instanceof VirusInfo) ? (VirusInfo) object : null;
		}
		return virusInfo;
	}

	public Set<String> getVirusNameSet() {
		return virusNameSet;
	}

	public void setVirusNameSet(Set<String> virusNameSet) {
		this.virusNameSet = virusNameSet;
	}
	
}
