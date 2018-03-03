/**
 * Created by gzp on 2018/1/2.
 */
import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;

public class DataCarCountry {

    public static void main(String[] args) throws SQLException {
        //getFileName();
        //getEntityId("BMW");
        String path = "C:\\Users\\dell\\Desktop\\实验数据补充csv2\\test.txt";
        //readAndWriteTxtFile();
        addCarId(path);
    }

    public static String getEntityId(String entityName) throws SQLException {
        String firstLetter = entityName.substring(0,1);
        String entityId = null;
        //System.out.println(firstLetter);
        java.sql.Connection conn = null;
        PreparedStatement psthis = null;
        java.sql.ResultSet valueList = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://10.1.13.29:3306/dbpedia_demo?user=root&password=tdlabDatabase&characterEncoding=utf8");
            psthis = conn.prepareStatement("SELECT * FROM entity WHERE entity_name =\'"+entityName+"\' AND binary entity_name LIKE \'"+firstLetter+"%\'");
            valueList = psthis.executeQuery();
            while(valueList.next()) {
                entityId = valueList.getString(1);
                System.out.println(entityName+"-----"+valueList.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            psthis.close();
            valueList.close();
            conn.close();
        }
        return entityId;
    }

    public static void getFileName() throws SQLException{
        String str = null;
        String path = "C:\\Users\\dell\\Desktop\\实验数据补充csv"; // 路径
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
                str = fs.getName().substring(0,fs.getName().indexOf("."));
                //System.out.println(str);
                getEntityId(str);
                //System.out.println(getEntityId(str));

            }
        }
    }

    public static void readAndWriteTxtFile() throws SQLException{
        String content = null;
        String countryId;
        String str = null;
        String str2 = null;
        String newPath = null;
        String path = "C:\\Users\\dell\\Desktop\\实验数据补充csv"; // 路径
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
                str = fs.getName();
                str2 = fs.getName().substring(0,fs.getName().indexOf("."));
                countryId = getEntityId(str2);
                newPath = path + "\\" + str;
                //System.out.println(str);
                try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

                    /* 读入TXT文件 */
                    File filename = new File(newPath); // 要读取以上路径的input。txt文件
                    InputStreamReader reader = new InputStreamReader(
                            new FileInputStream(filename)); // 建立一个输入流对象reader
                    BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
                    String line = "";
                    line = br.readLine();
                    content = content + line + "," + countryId + "\n";
                    //System.out.println(line);
                    while (line != null) {
                        line = br.readLine(); // 一次读入一行数据
                        content = content + line + "," + countryId + "\n";
                        //System.out.println(line);
                    }

                    /* 写入Txt文件 */
                    File writename = new File("C:\\Users\\dell\\Desktop\\实验数据补充csv2\\test.txt"); //
                    writename.createNewFile(); // 创建新文件
                    BufferedWriter out = new BufferedWriter(new FileWriter(writename));
                    out.write(content); // \r\n即为换行
                    out.flush(); // 把缓存区内容压入文件
                    out.close(); // 最后记得关闭文件


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void addCarId(String newPath) {
        String content = null;
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

                    /* 读入TXT文件 */
            File filename = new File(newPath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            content = content + "id," + line + "\n";
            //System.out.println(line);
            int i = 1;
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                content = content + "carBrand" + i + "," + line + "\n";
                i++;
                //System.out.println(line);
            }

                    /* 写入Txt文件 */
            File writename = new File("C:\\Users\\dell\\Desktop\\实验数据补充csv2\\test2.txt"); //
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(content); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
