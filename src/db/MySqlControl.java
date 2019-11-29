package db;
/**
 * 对数据库进行控制
 * 例如：增删改查
 */

import model.myMovie;

import java.sql.*;
import java.util.List;
import model.myMovie;

public class MySqlControl {
    /**
     * 连接数据库
     * @return
     */
    public static Connection getConn() {
        // JDBC 驱动名及数据库 URL
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/Crawler?useSSL=false&serverTimezone=UTC";

        // 数据库的用户名与密码，需要根据自己的设置
        String USER = "root";
        String PASS = "20000422";

        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("link success.");
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
            return conn;
    }

    /**
     * 插入数据库
     * @param params
     * @param movieList
     * @param c
     */
    public static void insertDate(Object params[][], List<myMovie> movieList,Connection c){
        try {
            for(int i = 0;i<params.length;i++) {
                PreparedStatement pstmt = c.prepareStatement("insert into movie_rank values(?,?,?,?,?)");
                params[i][0] = movieList.get(i).getMovieRank();
                params[i][1] = movieList.get(i).getMovieName();
                params[i][2] = movieList.get(i).getStar();
                params[i][3] = movieList.get(i).getScore();
                params[i][4] = movieList.get(i).getReleaseTime();

                pstmt.setInt(1, (Integer) params[i][0]);
                pstmt.setString(2, (String) params[i][1]);
                pstmt.setString(3, (String) params[i][2]);
                pstmt.setString(4, (String) params[i][3]);
                pstmt.setString(5, (String) params[i][4]);
                pstmt.executeLargeUpdate();
            }
            System.out.println("成功插入所有数据。");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 打印数据
     * @param c
     */
    public static void printDate(Connection c){
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String query = "select * from movie_rank";
            ResultSet resultSet = stmt.executeQuery(query);
            System.out.println("ID   Name   Star   Score   RealeseTime");

            //打印数据
            while(resultSet.next()){
                int rank = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String star = resultSet.getString("Star");
                String score = resultSet.getString("Score");
                String time = resultSet.getString("RealeseTime");

                System.out.println(rank +"   " + name + "    " + star + "      " + score+"  "+time);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


