package cn.van.kuang.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerService {

    private final static Logger logger = LoggerFactory.getLogger(PlayerService.class);

    public void createTable() {
        logger.info("Going to create player table");

        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtils.getSqlSession();

            PlayerMapper playerMapper = sqlSession.getMapper(PlayerMapper.class);
            playerMapper.createTable();

            sqlSession.commit();

            logger.info("Created player table successfully");
        } catch (Throwable throwable) {
            logger.error("Fail to create player table", throwable);

            SqlSessionUtils.rollback(sqlSession);
            throw new RuntimeException(throwable);
        } finally {
            SqlSessionUtils.close(sqlSession);
        }
    }

    public int createPlayer(Player player) {
        logger.info("Going to create {}", player);

        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtils.getSqlSession();

            PlayerMapper playerMapper = sqlSession.getMapper(PlayerMapper.class);
            int id = playerMapper.create(player);

            sqlSession.commit();

            logger.info("Created player");

            return id;
        } catch (Throwable throwable) {
            logger.error("Fail to create player", throwable);

            SqlSessionUtils.rollback(sqlSession);
            throw new RuntimeException(throwable);
        } finally {
            SqlSessionUtils.close(sqlSession);
        }
    }

    public void deletePlayer(int id) {
        logger.info("Going to delete player with id=[{}]", id);

        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtils.getSqlSession();

            PlayerMapper playerMapper = sqlSession.getMapper(PlayerMapper.class);
            playerMapper.delete(id);

            sqlSession.commit();

            logger.info("Delete player");
        } catch (Throwable throwable) {
            logger.error("Fail to delete player", throwable);

            SqlSessionUtils.rollback(sqlSession);
            throw new RuntimeException(throwable);
        } finally {
            SqlSessionUtils.close(sqlSession);
        }
    }

    public void updatePlayer(Player player) {
        logger.info("Going to update {}", player);

        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtils.getSqlSession();

            PlayerMapper playerMapper = sqlSession.getMapper(PlayerMapper.class);
            playerMapper.update(player);

            sqlSession.commit();

            logger.info("Updated player");
        } catch (Throwable throwable) {
            logger.error("Fail to update player", throwable);

            SqlSessionUtils.rollback(sqlSession);
            throw new RuntimeException(throwable);
        } finally {
            SqlSessionUtils.close(sqlSession);
        }
    }

    public Player queryPlayerById(int id) {
        logger.info("Query player by id=[{}]", id);

        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtils.getSqlSession();

            PlayerMapper playerMapper = sqlSession.getMapper(PlayerMapper.class);
            return playerMapper.queryById(id);
        } catch (Throwable throwable) {
            logger.error("Fail to query player", throwable);
            throw new RuntimeException(throwable);
        } finally {
            SqlSessionUtils.close(sqlSession);
        }
    }
}
