import java.sql.*;

/**
 * Created by gzp on 2017/11/2.
 */
public class TestJDBC {
    private Statement statement;

    public TestJDBC() {
        try
        {
            Class.forName(Values.DRIVER_MYSQL);     //加载JDBC驱动
            System.out.println("Driver Load Success.");

            Connection connection = DriverManager.getConnection(Values.URL);    //创建数据库连接对象
            statement = connection.createStatement();       //创建Statement对象
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * 根据sql查询数据库，返回一个结果集
     * 输    入:SQL语句
     * 返回值:ResultSet 查询结果
     */
    public ResultSet query(String sql) {
        ResultSet result = null;

        try
        {
            result = statement.executeQuery(sql);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /*
     * 打印type表的数据
     * 输    入:结果集(数据表)
     * 返回值:空
     */
    public void printInfo(ResultSet result) {
        try
        {
            while(result.next()) {
                System.out.println(result.getString(1));
            }
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * 执行数据操作
     * 输    入:SQL语句
     * 返回值:空
     */
    public void executeSql(String sql) {
        try
        {
            statement.execute(sql);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub

        String sql = "SELECT * FROM edge WHERE subject_id = 1";
        TestJDBC db = new TestJDBC();
        ResultSet result = db.query(sql);

        String sql1 = "SELECT name FROM entity_type WHERE id = '1' ";
        ResultSet result1 = db.query(sql1);
        db.printInfo(result1);
    }
}
