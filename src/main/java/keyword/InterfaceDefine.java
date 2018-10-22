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
	//public static void main(String args[]) throws IOException {
	Map<String,String> map;
	private String currentSyntax;

	public InterfaceDefine() {
	    this("Plain text");
    }

	public InterfaceDefine(String syntax) {
        currentSyntax = syntax;
        map = new HashMap<>();
        readKeywordInfoByGrammar();
    }

	private void readKeywordInfoByGrammar() {
	    // Temporarily hard code to read Java keyword

		String input = "";
		try {
			input = Thread.currentThread().getContextClassLoader().getResource("keyword.json").getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}

        String content= null;
        try {
            content = FileUtils.readFileToString(new File(input),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject Outer=new JSONObject(content);
		Map<String, Object> outerMap = new HashMap<>();
		Map<String, Object> innerMap = new HashMap<>();
		for(Object k : Outer.keySet() ){
			Object v = Outer.get(k.toString());
			outerMap.put(k.toString(), v);
		}

		for (Entry<String, Object> entry : outerMap.entrySet()) {
			String str1 = entry.getValue().toString();
			JSONObject inner = new JSONObject(str1);
			for(Object k : inner.keySet() ){
				Object v = inner.get(k.toString());
				innerMap.put(k.toString(), v);
			}

			for (Entry<String, Object> entry1 : innerMap.entrySet()){
				map.put(entry1.getValue().toString(), entry.getKey());
			}

		}

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

