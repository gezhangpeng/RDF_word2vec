import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gzp
 *
 */
public class RDF_word2vec {

    //布隆过滤器的容量，设为10000000
    static int size = 10000000;

    //布隆过滤器去掉重复的节点
    static BloomFilter<String> searchedNodeFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), size, 0.000001);

    //静态全局变量content，用来存储准备写入txt文档的语料信息
    static String content = "";

    public static void main(String args[]) {
        //四个参数，前两个控制一个线程的遍历搜索范围

        int startId = 1;//起始id
        int endId = 500;//结束id
        int layer = 3;//BFS的搜索范围（最外层节点到起始节点的距离），假设设为3，则表明除了初始节点外，还有三层节点
        int notStartLayer = 2;//控制距离起始点几跳之内的节点不再作为起始节点：假设设为2，则包括起始节点在内，一共三层的节点下次都不能再作为起始节点进行搜索

        SimpleDateFormat startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time1 = startTime.format(System.currentTimeMillis());

        chooseStartEnd(startId,endId,layer,notStartLayer);

        SimpleDateFormat endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time2 = endTime.format(System.currentTimeMillis());
        System.out.println("完成！");
        System.out.println("开始时间是："+time1);
        System.out.println("结束时间是："+time2);
        //bloomFilterTest();
        //bloomFilterStringTest();
    }

    public static void chooseStartEnd(int startId,int endId,int layer,int notStartLayer) {
        java.sql.Connection conn = null;
        PreparedStatement psthis = null;
        java.sql.ResultSet valueList = null;
        String startNode = null;
        try {
            //访问数据库
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://10.1.13.29:3306/dbpedia_demo?user=root&password=tdlabDatabase&characterEncoding=utf8");

            for(int i=startId;i<=endId;i++) {
                //在输入的边id的范围内遍历，以该边的第一个节点作为起始节点，
                //然后进行BFS搜索，再把这个节点加到已被访问的list中，避免重复访问
                psthis = conn.prepareStatement("SELECT entity_id1 FROM edge_no_string_complete WHERE id = \'"+i+"\'");
                valueList = psthis.executeQuery();
                while(valueList.next()) {
                    startNode = valueList.getString(1);
                    if(!searchedNodeFilter.mightContain(startNode)) {
                        //如果searchedNodeFilter中没有该节点，则把该节点作为初始中心节点，
                        System.out.println(startNode);
                        BFS(startNode,layer,notStartLayer);// 进行BFS搜索
                        content += "\n";
                        searchedNodeFilter.put(startNode);//加入到searchedNodeList中
                    }
                }
            }
            // 写入Txt文件
            String writePath = "F:\\neo4j_import_csv_data\\完整三元组的边数据\\语料.txt";
            File writename = new File(writePath);
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(content); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void BFS(String startNode,int layer,int notStartLayer) {//保存entity_id2以及predicate_name两个信息，作为判断是否重复的依据

        java.sql.Connection conn = null;
        PreparedStatement psthis = null;
        java.sql.ResultSet valueList = null;

        //声明一个名为dist的map，结构是一个节点的id对应一个层数（相对于初始中心点的距离）
        HashMap<String,Integer> dist = new HashMap<String,Integer>();

        Queue<String> queue = new LinkedList<String>();
        queue.offer(startNode);//起始节点进队列
        int d = 0;//初始距离为0，只有一个节点的情况
        dist.put(startNode, d);
        int i=0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://10.1.13.29:3306/dbpedia_demo?user=root&password=tdlabDatabase&characterEncoding=utf8");
            while(!queue.isEmpty())
            {
                String top=queue.poll();//取出队首元素
                if(dist.get(top)>(layer-1)) break;
                i++;
                //System.out.println("第"+i+"个元素："+top+"---距离初始节点的距离："+dist.get(top));
                d = dist.get(top)+1;//得出其周边还未被访问的节点的距离
                psthis = conn.prepareStatement("SELECT * FROM edge_no_string_complete WHERE entity_id1 = \'"+top+"\'");
                valueList = psthis.executeQuery();
                while(valueList.next()) {//遍历top节点的所有邻接点
                    String secondNodeId = valueList.getString(3);
                    //System.out.println(valueList.getString(4)+" "+valueList.getString(5)+" "+valueList.getString(6));
                    content += valueList.getString(4)+" "+valueList.getString(5)+" "+valueList.getString(6)+" ";
                    if(!dist.containsKey(secondNodeId)) {//如果dist中还没有该节点说明还没有被访问
                        dist.put(secondNodeId, d);//把该节点id以及到起始节点的距离作为键值对存到dist中
                        queue.offer(secondNodeId);//该节点进队列
                    }
                    if(d<=notStartLayer){//控制距离起始节点notStartLayer跳之内的节点不再作为起始节点
                        searchedNodeFilter.put(secondNodeId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] distNum = {0,0,0,0,0,0,0,0,0};
        //遍历dist，得到不同距离的节点数量
        for (Integer value : dist.values()) {
            switch (value) {
                case 0:distNum[0]++;break;
                case 1:distNum[1]++;break;
                case 2:distNum[2]++;break;
                case 3:distNum[3]++;break;
                case 4:distNum[4]++;break;
                case 5:distNum[5]++;break;
                case 6:distNum[6]++;break;
                case 7:distNum[7]++;break;
                case 8:distNum[8]++;break;
            }
        }
        System.out.println("起始节点数量："+distNum[0]);
        System.out.println("第2层节点数量："+distNum[1]);
        System.out.println("第3层节点数量："+distNum[2]);
        System.out.println("第4层节点数量："+distNum[3]);
        System.out.println("第5层节点数量："+distNum[4]);
        System.out.println("第6层节点数量："+distNum[5]);
        System.out.println("第7层节点数量："+distNum[6]);
        System.out.println("*****************************************************************************************************************************************");

    }

    public static HashMap<String, LinkedList<String>> createGraph() {
        // s顶点的邻接表
        LinkedList<String> list_s = new LinkedList<String>();
        list_s.add("w");
        list_s.add("r");
        LinkedList<String> list_w = new LinkedList<String>();
        list_w.add("s");
        list_w.add("x");
        list_w.add("i");
        LinkedList<String> list_r = new LinkedList<String>();
        list_r.add("s");
        list_r.add("v");
        LinkedList<String> list_x = new LinkedList<String>();
        list_x.add("w");
        list_x.add("y");
        list_x.add("u");
        LinkedList<String> list_v = new LinkedList<String>();
        list_v.add("r");
        LinkedList<String> list_i = new LinkedList<String>();
        list_i.add("w");
        LinkedList<String> list_u = new LinkedList<String>();
        list_u.add("x");
        LinkedList<String> list_y = new LinkedList<String>();
        list_y.add("x");
        HashMap<String, LinkedList<String>> graph = new HashMap<String, LinkedList<String>>();
        graph.put("s", list_s);
        graph.put("w", list_w);
        graph.put("r", list_r);
        graph.put("x", list_x);
        graph.put("v", list_v);
        graph.put("i", list_i);
        graph.put("y", list_y);
        graph.put("u", list_u);
        return graph;
    }

    public static void classicBFS(HashMap<String, LinkedList<String>> graph) {

        String startNode = "s";
        HashMap<String, Integer> dist = new HashMap<String, Integer>();
        Queue<String> queue = new LinkedList<String>();
        queue.offer(startNode);
        dist.put(startNode, 0);
        int i = 0;
        while (!queue.isEmpty()) {

            String top = queue.poll();//取出队首元素
            if(dist.get(top)>3) break;//控制距离起始节点的跳数
            i++;
            System.out.println("第" + i + "个元素：" + top + "---距离初始节点的距离：" + dist.get(top));
            //System.out.println("距离初始节点的距离："+dist.get(top));
            int d = dist.get(top) + 1;//得出其周边还未被访问的节点的距离
            for (String c : graph.get(top)) {//遍历top节点的所有邻接点
                if (!dist.containsKey(c))//如果dist中还没有该元素说明还没有被访问
                {
                    dist.put(c, d);
                    queue.add(c);
                }
            }

        }
    }

    public static void bloomFilterTest() {
        int size = 1000000;

        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size);

        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }

        for (int i = 0; i < size; i++) {
            if (!bloomFilter.mightContain(i)) {
                System.out.println("有坏人逃脱了");
            }
        }

        List<Integer> list = new ArrayList<Integer>(1000);
        for (int i = size + 10000; i < size + 20000; i++) {
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("有误伤的数量：" + list.size());
    }

    public static void bloomFilterStringTest() {
        int size = 10000000;
        BloomFilter<String> a = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), size, 0.000001);
        a.put("1234");
        a.put("5678");
        a.put("9090");
        String str = "6732";
        if(!a.mightContain(str)){//如果池子里没有str，那么加到池子里
            System.out.println("目前池子里没有str，现在把str加入到池子里");
            a.put(str);
        }
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        if(a.mightContain(str)){//如果池子里没有str，那么加到池子里
            System.out.println("有了");
        }
    }

}
