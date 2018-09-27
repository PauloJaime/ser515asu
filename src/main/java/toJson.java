import org.json.JSONObject;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;


public class toJson {

    public static void main(String[] args){
        try{
            JSONObject color1 = new JSONObject();
            JSONObject color2 = new JSONObject();
            JSONObject color3= new JSONObject();
            JSONObject combine = new JSONObject();
            int key = 1;
            String path = ".\\src\\keywords.txt";
            File filename = new File(path);
            InputStreamReader read = new InputStreamReader(new FileInputStream(filename));
            BufferedReader temp = new BufferedReader(read);
            String get = "";
            get = temp.readLine();
            if(get.equals("Yellow")){
                while(true){
                    get = temp.readLine();
                    if(get.equals("Blue")){
                        break;
                    }
                    else{color1.put(key+"",get);}
                    key ++;
                }
            }
            if(get.equals("Blue")){
                key = 1;
                while(true){
                    get = temp.readLine();
                    if(get.equals("Green")){
                        break;
                    }
                    else{color2.put(key+"",get);}
                    key ++;
                }
            }
            if(get.equals("Green")){
                key = 1;
                while(true){
                    get = temp.readLine();
                    if(get == null){
                        break;
                    }
                    color3.put(key+"",get);
                    key ++;
                }
            }
            String keyword = "{" + "\"Yellow\":" + color1 + "," + "\"Blue\":" + color2 + "," + "\"Green\":" + color3 + "}";
            File jsonfile = new File(".\\src\\keyword.json");
            jsonfile.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(jsonfile));
            output.write(keyword);
            output.flush();
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
