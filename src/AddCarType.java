import java.io.*;
import java.sql.DriverManager;

/**
 * Created by gzp on 2018/1/4.
 */
public class AddCarType {

    public static void addCarType() {
        String newPath = "C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\entity_language2.txt";
        String content = "";
        try {
            /* 读入TXT文件 */
            File filename = new File(newPath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if (line != null) {
//                    content = content + line + ",\"t7\"\n";
//                    content = content + line + ",\"t195\"\n";
//                    content = content + line + ",\"t647\"\n";
//                    content = content + line + ",\"t1753\"\n";
//                    content = content + line + ",\"t6165\"\n";
//                    content = content + line + ",\"t6168\"\n";
//                    content = content + line + ",\"t6864\"\n";
//                    content = content + line + ",\"t7815\"\n";
//                    content = content + line + ",\"t8530\"\n";

//                    content = content + line + ",\"t647\"\n";
//                    content = content + line + ",\"t2218\"\n";
//                    content = content + line + ",\"t5219\"\n";
//                    content = content + line + ",\"t6715\"\n";

                    content = content + line + ",\"t1\"\n";
                    content = content + line + ",\"t8530\"\n";
                }
            }

            /* 写入Txt文件 */
            File writename = new File("C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\edge_language_type.txt"); //
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
        addCarType();
    }
}
