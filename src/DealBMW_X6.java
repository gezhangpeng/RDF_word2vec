import java.io.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by gzp on 2018/1/9.
 */
public class DealBMW_X6 {
    public static void addCarBrand(String brand,String path,String fileName) {
        String content = null;
        String newPath = path + "\\" + fileName;
        try {
            /* 读入TXT文件 */
            File filename = new File(newPath);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            if(line != null)
                content = brand + "_" + line + "\n";
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if(line != null)
                    content = content + brand + "_" + line + "\n";
            }

            // 写入Txt文件
            path += "2";
            String writePath = path + "\\" + fileName;
            System.out.println(writePath);
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


    public static void addCarBrandInModel() throws SQLException {
        String text = "";
        String brand = null;
        //String path = "C:\\Users\\dell\\Desktop\\实验数据补充csv2\\USA"; // 路径
        String path = "C:\\Users\\dell\\Desktop\\实验数据补充csv3"; // 路径
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
                brand = fs.getName().substring(0,fs.getName().indexOf("."));
                //System.out.println(newPath);
                //addCarBrand(brand,path,fs.getName());
                //addModelCarBrandId(brand,path,fs.getName());
                text += readAllModels(path,fs.getName());
            }
        }

        try {
            // 写入Txt文件
            String writePath = "C:\\Users\\dell\\Desktop\\实验数据补充csv3\\all_models.txt";
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

    public static String readAllModels(String path,String fileName) {
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

    public static void addModelCarBrandId(String brand,String path,String fileName) throws SQLException {
        String brandId = getEntityId(brand);
        String content = null;
        String newPath = path + "\\" + fileName;
        try {
            /* 读入TXT文件 */
            File filename = new File(newPath);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            if(line != null)
                content = "carBrand_id,entity_name\n" + brandId + "," + line + "\n";
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if(line != null)
                    content = content + brandId + "," + line + "\n";
            }

            // 写入Txt文件
            path += "3";
            String writePath = path + "\\" + fileName;
            System.out.println(writePath);
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
            psthis = conn.prepareStatement("SELECT * FROM entity_car WHERE entity_name =\'"+entityName+"\' AND binary entity_name LIKE \'"+firstLetter+"%\'");
            valueList = psthis.executeQuery();
            while(valueList.next()) {
                entityId = valueList.getString(2);
                System.out.println(entityName+"-----"+valueList.getString(2));
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


    public static void addModelId() {
        String content = "";
        String path = "C:\\Users\\dell\\Desktop\\实验数据补充csv3\\all_models.txt";
        int i =2;
        try {
            /* 读入TXT文件 */
            File filename = new File(path);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            if(line != null)
                content = "carModel1," + line + "\n";
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if(line != null)
                    content = content + "carModel" + Integer.toString(i) + "," + line + "\n";
                i++;
            }

            // 写入Txt文件
            String writePath = "C:\\Users\\dell\\Desktop\\实验数据补充csv3\\all_models_2.txt";
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

    public static void main(String args[]) throws SQLException {
        //addCarBrandInModel();
        addModelId();
    }
}
