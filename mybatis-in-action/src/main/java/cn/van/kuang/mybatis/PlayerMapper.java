package cn.van.kuang.mybatis;

public interface PlayerMapper {

    void createTable();

    int create(Player player);

    int delete(int id);

    int update(Player player);

    Player queryById(int id);

}
