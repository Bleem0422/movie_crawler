package model;

public class myMovie {
    private int movieRank;//电影排名
    private String movieName;//电影名
    private String releaseTime;//上映时间
    private String star;//电影主演
    private String score;//电影评分

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getMovieRank() {
        return movieRank;
    }

    public void setMovieRank(int movieRank) {
        this.movieRank = movieRank;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    @Override
    public String toString() {
        return "myMovie{" +
                "movieRank='" + movieRank + '\'' +
                ", movieName='" + movieName + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                '}';
    }
}

