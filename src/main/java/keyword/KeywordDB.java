package keyword;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;
import java.util.logging.Logger;

/**
 * The class used to read color information
 *
 * @author Yiru Hu
 * @version 1.3
 */
public class KeywordDB {
	private Map<String, String> map;
	private static final Logger log = Logger.getLogger("Log");

	private String commentTag;
	private String[] mCommentPair;
	private String[] stringTag;

	public KeywordDB() {
	    this("Plain text");
        commentTag = null;
        mCommentPair = null;
        stringTag = null;
    }

	public KeywordDB(String syntax) {
        map = new HashMap<>();
        switchSyntax(syntax);
    }

    public String getCommentTag() { return commentTag;}

    public String[] getMCommentTags() { return mCommentPair;}

    public String[] getStringTag(){ return stringTag;}

    public Color matchColor(String keyWord) {
	    String colorName = map.getOrDefault(keyWord, "Black");
        switch (colorName) {
            case "Red":
                return Color.red;
            case "Blue":
                return Color.blue;
            case "Purple":
                return Color.magenta;
            case "Grey":
                return Color.gray;
            default:
                return Color.black;
        }

    }

    public Color matchDarkMode(String keyWord){
        String colorName = map.getOrDefault(keyWord, "Black");
        switch (colorName) {
            case "Red":
                return Color.CYAN;
            case "Blue":
                return Color.yellow;
            case "Purple":
                return Color.green;
            case "Grey":
                return Color.lightGray;
            default:
                return Color.white;
        }

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
        for (String k : jsonObject.keySet()) {
            JSONArray arr = (JSONArray) jsonObject.get(k);
            if (k.equals("comment")) {
                commentTag = (String) arr.get(0);
            } else if (k.equals("multi-comment")) {
                mCommentPair = new String[] {(String) arr.get(0), (String) arr.get(1)};
            } else if(k.equals("string")){
                stringTag = new String[] {(String) arr.get(0), (String) arr.get(1)};
            }
            else {
                for (Object anArr : arr) {
                    map.put(anArr.toString(), k);
                }

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

