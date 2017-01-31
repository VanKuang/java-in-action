package cn.van.kuang.spring.boot.model;

public class Player {

    public final static Player NULL = new Player(new PlayerBuilder(0).name("").height(0.0d).position("").club(""));

    private final int id;
    private final String name;
    private final double height;
    private final String position;
    private final String club;

    public Player(PlayerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.height = builder.height;
        this.position = builder.position;
        this.club = builder.club;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    public String getPosition() {
        return position;
    }

    public String getClub() {
        return club;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", position='" + position + '\'' +
                ", club='" + club + '\'' +
                '}';
    }

}
