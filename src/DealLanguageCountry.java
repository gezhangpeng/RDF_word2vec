import java.io.*;
import java.sql.SQLException;

/**
 * Created by gzp on 2018/1/11.
 */
public class DealLanguageCountry {
    public static void dealLanguageCountry() {
        String newPath = "C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\U-Z.txt";
        String content = "";
        try {
            /* 读入TXT文件 */
            File filename = new File(newPath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            if (line != null) {
                if(line.indexOf("(")>-1)
                    line = line.substring(0,line.indexOf("("));
                if(line.indexOf(":")>-1)
                    line = line.replace(":",",");
            }
            content = content + line + "\n";
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if (line != null) {
                    if(line.indexOf("(")>-1)
                        line = line.substring(0,line.indexOf("("));
                    if(line.indexOf(":")>-1)
                        line = line.replace(":",",");
                    content = content + line + "\n";
                }
            }

            /* 写入Txt文件 */
            File writename = new File("C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\U-Z2.txt"); //
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(content); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteSpace() {
        String newPath = "C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\A-Z\\U-Z2.txt";
        String content = "";
        try {
            /* 读入TXT文件 */
            File filename = new File(newPath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            if (line != null) {
                if (line.endsWith(" "))
                    line = line.substring(0, line.lastIndexOf(" "));
                content = content + line + "\n";
            }
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if (line != null) {
                    if (line.endsWith(" "))
                        line = line.substring(0, line.lastIndexOf(" "));
                    content = content + line + "\n";
                }
            }

            /* 写入Txt文件 */
            File writename = new File("C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\A-Z\\_U-Z2.txt"); //
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(content); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void readLanguages() {
        String text = "";
        String brand = null;
        String path = "C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\A-Z_final"; // 路径
        String newPath = null;
        File f = new File(path);
        if (!f.exists()) {
            System.out.println("不存在");
            return;
        }
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + " [目录]");
            } else {
                text += readAlllanguages(path,fs.getName());
            }
        }

        try {
            // 写入Txt文件
            String writePath = "C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\all_languages_country.txt";
            File writename = new File(writePath);
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(text); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readAlllanguages(String path,String fileName) {
        String content = "";
        String newPath = path + "\\" + fileName;
        try {
            /* 读入TXT文件 */
            File filename = new File(newPath);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            if(line != null)
                content = content + line + "\n";
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if(line != null)
                    content = content + line + "\n";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }


    public static void addLanguageId() {
        String content = "";
        String path = "C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\entity_language.txt";
        int i =2;
        try {
            /* 读入TXT文件 */
            File filename = new File(path);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            if(line != null)
                content = "\"language1\"," + line + "\n";
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if(line != null)
                    content = content + "\"language" + Integer.toString(i) + "\"," + line + "\n";
                i++;
            }

            // 写入Txt文件
            String writePath = "C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\entity_language2.txt";
            File writename = new File(writePath);
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(content); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        //dealLanguageCountry();
        //deleteSpace();
/*        String s = "Bosnian,Bosnia and Herzegovina ";
        if (s.endsWith(" "))
            s = s.substring(0, s.lastIndexOf(" "));
        System.out.println(s);*/
        //readLanguages();
        addLanguageId();
    }
}
