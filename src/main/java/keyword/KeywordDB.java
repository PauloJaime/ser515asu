package keyword;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The class used to read color information
 *
 * @author Yiru Hu
 * @version 1.2
 */
public class KeywordDB {
	//public static void main(String args[]) throws IOException {
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
        if(colorName.equals("Yellow")){
            color = Color.yellow;
        }else if(colorName.equals("Blue")){
            color = Color.blue;
        }else if(colorName.equals("Green")){
            color = Color.green;
        }
        return color;
    }

    public void switchSyntax(String syntax) {
	    // Temporarily hard code to read Java keyword
        map.clear();
		String input = "";
		try {
			input = Thread.currentThread().getContextClassLoader().getResource(syntax + "Keyword.json").getPath();
		} catch (NullPointerException e) {
		    log.info("Cannot find syntax:" + syntax);
		    log.info("By default, we regard this as plain text file");
			return;
		}

        String content;
        try {
            content = FileUtils.readFileToString(new File(input),"UTF-8");
        } catch (IOException e) {
            log.warning("Cannot find file: " + input);
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

}

