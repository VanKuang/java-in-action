package cn.van.kuang.jersey.jetty.param;

public class JobInfo {

    private final String level;
    private final String title;

    public JobInfo(String content) {
        this.level = content.split(",")[0];
        this.title = content.split(",")[1];
    }

    @Override
    public String toString() {
        return "JobInfo{" +
                "level='" + level + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
