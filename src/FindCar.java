import java.io.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by gzp on 2018/1/3.
 */
public class FindCar {

    public static void findCar() {
        String entityName = null;
        String firstLetter = null;
        String newPath = "C:\\Users\\dell\\Desktop\\实验数据补充csv2\\all_cars.txt";
        String exsitCars = null;
        String unExsitCars = null;
        java.sql.Connection conn = null;
        PreparedStatement psthis = null;
        java.sql.ResultSet valueList = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://10.1.13.29:3306/dbpedia_demo?user=root&password=tdlabDatabase&characterEncoding=utf8");
            /* 读入TXT文件 */
            File filename = new File(newPath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if(line != null) {
                    //System.out.println(line);
                    System.out.println(line.replace("\"", ""));
                    entityName = line.replace("\"", "");
                    firstLetter = entityName.substring(0,1);
                    psthis = conn.prepareStatement("SELECT * FROM entity WHERE entity_name =\""+entityName+"\" AND binary entity_name LIKE \""+firstLetter+"%\"");
                    valueList = psthis.executeQuery();
                    if(valueList.next()) {
                        exsitCars = exsitCars + entityName + "\n";
                    } else {
                        unExsitCars = unExsitCars + entityName + "\n";
                    }
                }
            }

            /* 写入Txt文件 */
            File writename = new File("C:\\Users\\dell\\Desktop\\实验数据补充csv2\\exsit_cars.txt"); //
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(exsitCars); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件

            File writename2 = new File("C:\\Users\\dell\\Desktop\\实验数据补充csv2\\unexsit_cars.txt"); //
            writename2.createNewFile(); // 创建新文件
            BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));
            out2.write(unExsitCars); // \r\n即为换行
            out2.flush(); // 把缓存区内容压入文件
            out2.close(); // 最后记得关闭文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        findCar();
    }

}
