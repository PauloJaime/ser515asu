package ui;
import java.io.*;  

public class Terminal {  
      
    public static String executeCmd(String strCmd)throws Exception{
    	Process p = Runtime.getRuntime().exec(strCmd);
    	StringBuilder sbCmd = new StringBuilder();
    	BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
    	String line;
    	while ((line = br.readLine()) != null) { 
    		sbCmd.append(line + "\n");
    	}
    	return sbCmd.toString();
    } 
} 
