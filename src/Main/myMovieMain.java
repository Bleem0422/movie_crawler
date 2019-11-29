package Main;

import db.MySqlControl;
import model.myMovie;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import util.URLHandle;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.MySqlControl.getConn;


public class myMovieMain {

    /**
     * 解析页面，并把数据插入至数据库
     */
    public static List<myMovie> getOnePage(String url,List<myMovie> movieList,HttpClient client,Connection c) {
        try {
            movieList = URLHandle.urlParser(client, url);
            //如果数组不为空，将数据插入至数据库
            if (movieList != null) {
                Object[][] params = new Object[movieList.size()][5];
                MySqlControl.insertDate(params, movieList, c);
            }else {
                try {
                    System.out.println("准备关闭数据库...");
                    c.close();
                    System.out.println("成功关闭数据库.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch(ParseException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return movieList;
    }

     /**
     * 数据库操作
     */
    public static void databaseControl(Connection c,List<myMovie> movieList){
            System.out.println("开始打印数据...");
            MySqlControl.printDate(c);
            System.out.println("数据库操作执行完毕.");

            try {
                System.out.println("准备关闭数据库...");
                c.close();
                System.out.println("成功关闭数据库.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void main(String[] args) {

        /**
         * 初始化
         */
        List<myMovie> movieList = null;

        //生成客户端
        System.out.println("正在生成客户端...");
        HttpClient client = HttpClientBuilder.create().build();
        System.out.println("客户端生成完毕.");

        //连接数据库
        Connection c = getConn();
        
        System.out.println("解析url...");
        //翻页爬取
        for(int i = 0; i < 10 ; i++) {
            String offset = String.valueOf(i*10);//将i*10转换为字符串
            String url =  "https://maoyan.com/board/4?" + "offset=" + offset;
            movieList = getOnePage(url,movieList,client,c);
        }
        System.out.println("所有数据均已插入数据库.");

        //对数据库进行操作
        databaseControl(c,movieList);

        System.out.println("所有操作已完成，结束程序运行.");
    }
}

