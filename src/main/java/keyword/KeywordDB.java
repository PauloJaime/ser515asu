package keyword;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;
import java.awt.*;
import java.util.logging.Logger;

/**
 * The class used to read color information
 *
 * @author Yiru Hu
 * @version 1.2
 */
public class KeywordDB {
	private Map<String,String> map;
	private static final Logger log = Logger.getLogger("Log");

	public KeywordDB() {
	    this("Plain text");
    }

	public KeywordDB(String syntax) {
        map = new HashMap<>();
        switchSyntax(syntax);
    }

    public Color matchColor(String keyWord) {
        //String colorName = "";
        Object key = ""+keyWord;
        String colorName = map.getOrDefault(key, "Black");

        Color color = Color.black;
        if(colorName.equals("Red")){
            color = Color.red;
        }else if(colorName.equals("Blue")){
            color = Color.blue;
        }else if(colorName.equals("Purple")){
            color = Color.magenta;
        }
        return color;
    }

    public void switchSyntax(String syntax) {
        map.clear();
        String content;
        try {
            content = readFile(syntax + "Keyword.json");
        } catch (IOException e) {
            log.warning("Cannot find file: " + e.getMessage());
            throw new RuntimeException(e.getCause());
        }

        JSONObject jsonObject = new JSONObject(content);
		Map<String, Object> outerMap = new HashMap<>();
		for(Object k : jsonObject.keySet() ){
			Object v = jsonObject.get(k.toString());
			outerMap.put(k.toString(), v);
		}

		for (Entry<String, Object> entry : outerMap.entrySet()) {
			String str1 = entry.getValue().toString();
			JSONObject inner = new JSONObject(str1);
			for(Object k : inner.keySet() ){
				Object v = inner.get(k.toString());
                map.put(v.toString(), entry.getKey());
			}

		}

	}

	private String readFile(String name) throws IOException {
        InputStream bis = (InputStream) Thread.currentThread().getContextClassLoader().getResource(name).getContent();
        DataInputStream dis = new DataInputStream(bis);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = dis.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

}

