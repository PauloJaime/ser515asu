package ui;

import java.io.*;


/**
 * The class provide cmd operations
 * @author yiru hu
 * @version 1.0
 */
public class TerminalConsole {  
	static StringBuilder s1  = new StringBuilder();

    class ReaderConsole implements Runnable{  
        private InputStream is;  
        public ReaderConsole(InputStream is){  
            this.is = is;  
        }  
        public void run(){  
            InputStreamReader isr = null;  
            try {  
                isr = new InputStreamReader(is, "gbk");  
            } catch (UnsupportedEncodingException e1) {  
                e1.printStackTrace();  
            }  
            BufferedReader br = new BufferedReader(isr);  
              
            int c = -1;  
            try{  
                while((c = br.read()) != -1){   
                    s1.append((char)c);
                }
            }catch(Exception e){  
                e.printStackTrace(); 
            }

        }  
    }  
      

    public String execute() throws Exception{  
        String[] cmds = {"cmd"};  
        String r;
        Process process = Runtime.getRuntime().exec(cmds);  
        InputStream is = process.getInputStream();             
        Thread t1 = new Thread(new ReaderConsole(is));    
        t1.start(); 
        Thread.sleep(1000);
        t1.interrupt();
        r = s1.toString();
        return r; 
    }
} 
