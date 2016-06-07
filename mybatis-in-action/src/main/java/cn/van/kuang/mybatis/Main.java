package cn.van.kuang.mybatis;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        PlayerService playerService = new PlayerService();

        playerService.createTable();

        Player kobe = new Player();
        kobe.setName("Kobe Bryant");
        kobe.setPosition("PG");
        kobe.setCreateTime(new Date());

        int id = playerService.createPlayer(kobe);
        System.out.println("++++++++++++++" + id);

        Player playerById = playerService.queryPlayerById(id);
        System.out.println("++++++++++++++" + playerById);

        kobe.setPosition("SG");
        playerService.updatePlayer(kobe);

        playerById = playerService.queryPlayerById(id);
        System.out.println("++++++++++++++" + playerById);

        playerService.deletePlayer(id);

        playerById = playerService.queryPlayerById(id);
        System.out.println("++++++++++++++" + playerById);
    }

}
