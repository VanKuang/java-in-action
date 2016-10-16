package cn.van.kuang.spring.boot.contoller;

import cn.van.kuang.spring.boot.model.Player;
import cn.van.kuang.spring.boot.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping("/player/{id}")
    public Player getById(@PathVariable("id") int id) {
        return playerService.getPlayer(id);
    }

    @RequestMapping("/player")
    public List<Player> lsit() {
        return playerService.list();
    }
}
