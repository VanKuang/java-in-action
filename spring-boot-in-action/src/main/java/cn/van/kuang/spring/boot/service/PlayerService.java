package cn.van.kuang.spring.boot.service;

import cn.van.kuang.spring.boot.model.Player;
import cn.van.kuang.spring.boot.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final static Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getPlayer(int id) {
        try {
            return playerRepository.getPlayerById(id);
        } catch (Exception e) {
            logger.error("Fail to get player by id=[" + id + "]", e);
            throw e;
        }
    }

    public List<Player> list() {
        try {
            return playerRepository.list();
        } catch (Exception e) {
            logger.error("Fail to list players", e);
            throw e;
        }
    }
}
