package cn.van.kuang.spring.boot.repository;

import cn.van.kuang.spring.boot.model.Player;
import cn.van.kuang.spring.boot.model.PlayerBuilder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerRepository {

    private List<Player> players = new ArrayList<>();

    public PlayerRepository() {
        players.add(new Player(new PlayerBuilder(1).name("Kobe Bryant").height(6.9d).position("PG").club("Lakers")));
        players.add(new Player(new PlayerBuilder(2).name("LeBron James").height(7.0d).position("SF").club("Cavaliers")));
        players.add(new Player(new PlayerBuilder(3).name("Stephen Curry").height(6.3d).position("PG").club("Warriors")));
    }

    public Player getPlayerById(int id) {
        for (Player player : players) {
            if (id == player.getId()) {
                return player;
            }
        }

        return Player.NULL;
    }

    public List<Player> list() {
        return players;
    }

}
