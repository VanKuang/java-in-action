package cn.van.kuang.java.core.design.pattern.builder;

public class Player {

    private final String name;
    private final String sex;
    private final double height;
    private final String position;
    private final String club;

    private Player(String name,
                   String sex,
                   double height,
                   String position,
                   String club) {
        this.name = name;
        this.sex = sex;
        this.height = height;
        this.position = position;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
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
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", height=" + height +
                ", position='" + position + '\'' +
                ", club='" + club + '\'' +
                '}';
    }

    public static class Builder {
        private final String name;
        private final String sex;

        private double height;
        private String position;
        private String club;

        public Builder(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }

        public Builder height(double height) {
            this.height = height;
            return this;
        }

        public Builder position(String position) {
            this.position = position;
            return this;
        }

        public Builder club(String club) {
            this.club = club;
            return this;
        }

        public Player build() {
            return new Player(name, sex, height, position, club);
        }
    }

    public static void main(String[] args) {
        Player player = new Builder("Kobe", "Male")
                .height(6.9d)
                .position("SG")
                .club("LAKERS")
                .build();

        System.out.println(player.getName());
        System.out.println(player.getSex());
        System.out.println(player.getHeight());
        System.out.println(player.getPosition());
        System.out.println(player.getClub());
        System.out.println(player);
    }
}
