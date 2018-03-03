import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by gzp on 2018/1/3.
 */
public class AddRandomPredicate {

    public static Object[] addRandomPredicate() {

        Random r = new Random();
        //int count = r.nextInt(4)+2;//产生3-5之间的随机数，作为汽车和国家之间的谓词数量
        //int count = r.nextInt(2)+3;//产生3-4之间的随机数，作为车型和品牌之间的谓词数量
        int count = r.nextInt(3)+6;//产生6-8之间的随机数，作为国家和语言之间的谓词数量
        //System.out.println("count-----"+count);

        Object[] values = new Object[count];
        //System.out.println("length-----"+values.length);
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

        // 生成随机数字并存入HashMap
        int j = 0;
        while(j>-1) {
            if(hashMap.size() == count) break;
            //String predicateNumber = Integer.toString(r.nextInt(5)+1);//产生1-5之间的随机数，作为谓词编号，从map里面去匹配具体是哪一个谓词
            //String predicateNumber = Integer.toString(r.nextInt(4)+1);//产生1-4之间的随机数(车型与品牌的关系只从四个里面选)
            String predicateNumber = Integer.toString(r.nextInt(8)+1);//产生1-8之间的随机数(车型与品牌的关系只从四个里面选)
            hashMap.put(predicateNumber, j);
            j++;
        }

        // 从HashMap导入数组
        values = hashMap.keySet().toArray();

        return values;
    }

    public static void randomWriteEdge() {
        Map<String, String> predicate = new HashMap<>();

/*        predicate.put("1","production");
        predicate.put("2","assembly");
        predicate.put("3","location");
        predicate.put("4","place_of_origin");
        predicate.put("5","establish");*/

/*        predicate.put("1","manufacturer");
        predicate.put("2","designer");
        predicate.put("3","supplier");
        predicate.put("4","service");*/

        predicate.put("1","spoke_in");
        predicate.put("2","language");
        predicate.put("3","official_language");
        predicate.put("4","speak");
        predicate.put("5","tongue");
        predicate.put("6","used_in");
        predicate.put("7","dialect");
        predicate.put("8","oral_language");

        String content = "";
        String newPath = "C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\edge_language_country.txt";
        try {
            /* 读入TXT文件 */
            File filename = new File(newPath);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            content = line + "\n";
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                Object[] values = addRandomPredicate();
                // 遍历数组并打印数据
                for(int i = 0;i < values.length;i++){
                    //System.out.print(values[i] + "\t");
                    //System.out.println(predicate.get(values[i]));
                    content = content + line + ",\"" + predicate.get(values[i]) + "\"\n";
                }
            }

            /* 写入Txt文件 */
            File writename = new File("C:\\Users\\dell\\Desktop\\实验数据_国家和语言\\edge_language_country2.txt"); //
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

        randomWriteEdge();

    }
}
