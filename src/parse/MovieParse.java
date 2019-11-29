package parse;

import model.myMovie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieParse {

    public static List<myMovie> getData(String entity){
        List<myMovie> data = new ArrayList<>();

        //采用jsoup解析
        Document doc = Jsoup.parse(entity);

        //根据页面内容分析出需要的元素
        Elements elements = doc.select("dl[class=\"board-wrapper\"]").select("dd");
        //利用正则表达式对文本内容进行处理
        //去掉“主演：”，将主演名字提取出来
        String patternStar = "[^\\主演：]\\S*";
        Pattern reStar = Pattern.compile(patternStar);

        //创建将电影评分的integer和fraction合在一起的字符串
        String score = "";

        // 将时间信息单独提取出来
        String patternTime = "\\d{4}?\\D\\d{2}?\\D\\d{2}|\\d{4}";
        Pattern reTime = Pattern.compile(patternTime);


        //开始提取关键信息并添加至数组
        for(Element element : elements) {
            myMovie movie = new myMovie();
            //电影排名
            movie.setMovieRank(Integer.parseInt(element.select("i.board-index").text()));//class等于board-index的i标签
            //电影名
            movie.setMovieName(element.select("p[class=\"name\"]").text());//带有class属性的p元素

            //电影主演
            //movie.setStar(element.select("p[class=\"star\"]").text());
            Matcher mStar = reStar.matcher(element.select("p[class=\"star\"]").text());
            if (mStar.find()){
                movie.setStar(mStar.group());
            }else {
                System.out.println("演员部分匹配失败。");
            }

            //电影评分
            score = element.select("i.integer").text()+element.select("i.fraction").text();
            movie.setScore(score);

            //电影上映时间
            //movie.setReleaseTime(element.select("p[class=\"releasetime\"]").text());
            Matcher mTime = reTime.matcher(element.select("p[class=\"releasetime\"]").text());
            if(mTime.find()){
                movie.setReleaseTime(mTime.group());
            }else{
                System.out.println("时间部分匹配失败。");
            }

            data.add(movie);

        }
        return data;
    }
}

