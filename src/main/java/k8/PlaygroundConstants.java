package k8;

import java.util.HashMap;
import java.util.Map;

public class PlaygroundConstants {

	
public static Map<String, String> imageMap = new HashMap();
	
	static{
		imageMap.put("python3", "0cloud0/playground:python3");
		imageMap.put("mariadb10", "0cloud0/playground:mysql");
		imageMap.put("rhel7", "0cloud0/playground:rhelubi7");
		imageMap.put("rhel8", "0cloud0/playground:rhelubi8");
		imageMap.put("php7fpm", "0cloud0/playground:php7");
		imageMap.put("nodejs", "0cloud0/playground:nodejs");
		imageMap.put("ansible", "0cloud0/playground:ansible");
		
		
	}
	
	
	
}
