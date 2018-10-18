package keyword;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * The class used to read color information
 *
 * @author Yiru Hu
 * @version 1.0
 */
public class InterfaceDefine {
    //public static void main(String args[]) throws IOException {
	  public String matchColor(String keyWord) throws IOException {
    	  String color = "";
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
    	  Map<String,String> map = new HashMap<>();
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

              for(Entry<String,String> entry2 : map.entrySet()){
              	if(entry2.getKey().equals(keyWord)){
              		color = entry2.getValue();
				}
			  }

        	  innerMap = new HashMap<>();
          }
          return color;
      }
}

