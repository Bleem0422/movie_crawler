package util;

import model.myMovie;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;
import parse.MovieParse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class URLHandle {

    public static List<myMovie> urlParser(HttpClient client, String url) throws IOException {
        //创建一个接受数据的数组
        List<myMovie> data = new ArrayList<>();

        //获取响应资源
        HttpResponse response = HTTPUtils.getHtml(client,url);

        //获取响应状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        if(statusCode == 200) {//200表示成功
            //获取响应实体内容，并且将其转换为utf-8形式的字符串编码
            String entity = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println("开始解析...");
            data = MovieParse.getData(entity);
            System.out.println("URL解析完成.");
        } else {
            EntityUtils.consume(response.getEntity());//释放资源实体
        }
        return data;
    }
}

