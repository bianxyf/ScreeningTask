package uk.co.screening;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public class TaskProcessor {
	
	public static String process(String task, String dependencies) {
		String toReturn = null;
		if(!StringUtils.isEmpty(dependencies)) {
			String[] dependency = StringUtils.split(dependencies, ",");
			int count = 0;
			List<String> dependencyList = new ArrayList<String>();
			while(count < dependency.length) {
				String[] token = StringUtils.split(dependency[count], " => ");
				if(!dependencyList.isEmpty()) {
					int index = dependencyList.indexOf(token[0]);
					if(index > 0 && index != dependencyList.size()-1) {
						//Check if element is already processed and not the last task in process queue
						dependencyList.add(index, token[1]);
					} else if(dependencyList.get(dependencyList.size()-1).equals(token[1])) {
						toReturn = "Error - this is a cyclic dependency";
					} else {
						int indexToken1 = dependencyList.indexOf(token[1]);
						if(dependencyList.get(0).equals(token[0]) && 
								(!dependencyList.get(dependencyList.size()-1).equals(token[1]) &&
										indexToken1 == -1)) {
							dependencyList.add(0, token[1]);
						}else if(indexToken1 > 0) {
							toReturn = "Error - this is a cyclic dependency";
						}else {
							//for new task processed
							dependencyList.add(token[1]);
							dependencyList.add(token[0]);
						}
					}
				} else {
					dependencyList.add(token[1]);
					dependencyList.add(token[0]);
				}
				count++;
			}
			return Optional.ofNullable(toReturn).orElse(String.join(",", dependencyList));
		} else {
			if(StringUtils.isNotEmpty(task) && 
					StringUtils.split(task, ",").length > 50) {
				return "Error - maximum number of tasks allowed exceeded";
			}
			return task;
		}
	}
	
}
