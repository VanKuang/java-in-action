package cn.van.kuang.spring.boot.model;

public class PlayerBuilder {

    final int id;
    String name;
    double height;
    String position;
    String club;

    public PlayerBuilder(int id) {
        this.id = id;
    }

    public PlayerBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PlayerBuilder height(double height) {
        this.height = height;
        return this;
    }

    public PlayerBuilder position(String position) {
        this.position = position;
        return this;
    }

    public PlayerBuilder club(String club) {
        this.club = club;
        return this;
    }

}
