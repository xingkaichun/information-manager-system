package com.xingkaichun.information.utils;

import java.io.*;

public class CommonUtilsFile {

    public static String readFileContent(String filePath) throws IOException {
        BufferedReader br = null;
        String content = "";
        try {
            br = new BufferedReader(new FileReader(new File(filePath)));
            String line = null ;
            while ((line=br.readLine())!=null){
                content += line;
            }
        } finally {
            if(br!=null){br.close();}
        }
        return content;
    }
    public static void writeFileContent(String filePath,String content) throws IOException {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(filePath)));
            bw.write(content);
        } finally {
            if(bw!=null){bw.close();}
        }
    }
}
