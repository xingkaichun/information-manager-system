package com.xingkaichun.utils;

import java.io.*;

public class CommonUtilsFile {

    public static String readFileContent(String filePath) throws IOException {
        BufferedReader br = null;
        String content = "";
        try {
            br = new BufferedReader(new FileReader(new File(filePath)));
            String line = null ;
            while ((line=br.readLine())!=null){
                content += line +"\r\n";
            }
        } finally {
            if(br!=null){br.close();}
        }
        return content;
    }
    public static void writeFileContent(String fileDir,String fileName, String content) throws IOException {
        BufferedWriter bw = null;
        try {
            //创建目录
            File dir = new File(fileDir);
            if(!dir.exists()){
                dir.mkdirs();
            }
            //创建文件
            File file = new File(dir,fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
        } finally {
            if(bw!=null){bw.close();}
        }
    }
    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
        }
        return file.delete();
    }
}
