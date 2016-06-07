package cn.van.kuang.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public final class SqlSessionUtils {

    static {
        try {
            InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(false);
    }

    public static void close(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    public static void rollback(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.rollback();
        }
    }

    private SqlSessionUtils() {
    }
}
