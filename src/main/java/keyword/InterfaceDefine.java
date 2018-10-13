package keyword;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Keywords "database" class. Cache programming language keywords
 * and return highlighting color
 *
 * @author Yiru Hu
 * @version 1.0
 */
public class InterfaceDefine {
    //public static void main(String args[]) throws IOException {
    public String matchColor(String keyWord) throws IOException {
        String color = "";
        File file = new File("src/main/java/keyword.json");
        String content= FileUtils.readFileToString(file,"UTF-8");
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
                if(entry1.getValue().toString().equals(keyWord) ){
                    color = entry.getKey();
                    System.out.println(entry.getKey());
                }
            }

            innerMap = new HashMap<>();
        }
        return color;
    }
}

