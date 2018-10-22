package keyword;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The class used to read color information
 *
 * @author Yiru Hu
 * @version 1.0
 */
public class InterfaceDefine {
	  Map<String,String> map = new HashMap<>();
	  public Map getMap() throws IOException{
		  String input = "";
		  try {
			  input = Thread.currentThread().getContextClassLoader().getResource("keyword.json").getPath();
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  String content= FileUtils.readFileToString(new File(input),"UTF-8");
		  JSONObject Outer=new JSONObject(content);
		  Map<String, Object> outerMap = new HashMap<>();
		  Map<String, Object> innerMap = new HashMap<>();
		  for(Object k : Outer.keySet() ){
			  Object v = Outer.get(k.toString());
			  outerMap.put(k.toString(), v);
		  }
		  for (Entry<String, Object> entry : outerMap.entrySet()) {
			  String str1 = entry.getValue().toString();
			  JSONObject inner=new JSONObject(str1);
			  for(Object k : inner.keySet() ){
				  Object v = inner.get(k.toString());
				  innerMap.put(k.toString(), v);
			  }
			  for (Entry<String, Object> entry1 : innerMap.entrySet()){
				  map.put(entry1.getValue().toString(), entry.getKey());
			  }

		  }
		  return map;
	  }
	  public Color matchColor(String keyWord) {
    	  //String colorName = "";
    	  Object key = ""+keyWord;
		  String colorName = map.getOrDefault(key, "Black");

    	  Color color = Color.black;
          if(colorName.equals("Yellow")){
          	color = Color.yellow;
		  }else if(colorName.equals("Blue")){
          	color = Color.blue;
		  }else if(colorName.equals("Green")){
          	color = Color.green;
		  }
          return color;
      }
}

