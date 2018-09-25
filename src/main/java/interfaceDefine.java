import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;




import net.sf.json.JSON;


//import net.sf.json;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class interfaceDefine {
    public static void main(String args[]) throws IOException {
        File file = new File("F:\\java\\1\\src\\main\\java\\keyword.json");
        String content= FileUtils.readFileToString(file,"UTF-8");
        JSONObject json1=new JSONObject(content);
        //JSONObject json1=new JSONObject("{'username' : '11111','clientid' : '','password' : '222222'}");
        Map<String, Object> map = new HashMap<>();
        Iterator it = json1.keys();
        while (it.hasNext()){
            String key = (String)it.next();
            String value = (String)json1.get(key);
            map.put(key,value);
        }
        //System.out.println(map);
        //Map saleMap = JSON.parseObject(json, Map.class);
        //JSONObject json1=new JSONObject(content);
        //Map<String, Object> map =(Map)json1;
        //Map saleMap = JSON.parseObject(json1, Map.class);
        //Map<String, Object> map = new HashMap<>();
        //map = json1


        //HashMap<String,Object> map2 = map1;

        for (Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey()+"="+entry.getValue());
        }

    }
}

