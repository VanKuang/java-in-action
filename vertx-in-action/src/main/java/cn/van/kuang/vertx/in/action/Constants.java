package cn.van.kuang.vertx.in.action;

public interface Constants {

    String EVENT_BUS_REQUEST_QUEUE = "request.queue";

    String HEADER_ACTION = "action";

    String ACTION_LIST_ALL = "list-all";

    String CONFIG_KEY_JDBC_URL = "jdbc.url";

    String CONFIG_VALUE_JDBC_URL = "jdbc:hsqldb:file:db/nba";

    String CONFIG_KEY_JDBC_DRIVER = "jdbc.driver";

    String CONFIG_VALUE_JDBC_DRIVER = "org.hsqldb.jdbcDriver";

    String CONFIG_KEY_JDBC_MAX_POOL_SIZE = "jdbc.max.pool.size";

    int CONFIG_VALUE_JDBC_MAX_POOL_SIZE = 30;

    int ERROR_CODE_NO_ACTION = 10;

    int ERROR_CODE_NOT_SUPPORT_ACTION = 11;

    int ERROR_CODE_FAIL_TO_GET_DB_CONNECTION = 20;

    int ERROR_CODE_FAIL_TO_EXECUTE_SQL = 21;

}
