package cn.van.kuang.vertx.in.action;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static cn.van.kuang.vertx.in.action.Constants.*;

public class DatabaseVerticle extends AbstractVerticle {

    private final static Logger logger = LoggerFactory.getLogger(DatabaseVerticle.class);

    private static final String SQL_CREATE_TABLE = "create table if not exists Players (Id integer identity primary key, Name varchar(255) unique, Content clob)";
    private static final String SQL_GET = "select Id, Content from Players where Name = ?";
    private static final String SQL_CREATE = "insert into Players values (NULL, ?, ?)";
    private static final String SQL_UPDATE = "update Players set Content = ? where Id = ?";
    private static final String SQL_ALL = "select Name from Players";
    private static final String SQL_DELETE = "delete from Players where Id = ?";

    private JDBCClient jdbcClient;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        jdbcClient = JDBCClient.createShared(vertx,
                new JsonObject()
                        .put("url", config().getValue(CONFIG_KEY_JDBC_URL, CONFIG_VALUE_JDBC_URL))
                        .put("driver_class", config().getValue(CONFIG_KEY_JDBC_DRIVER, CONFIG_VALUE_JDBC_DRIVER))
                        .put("max_pool_size", config().getValue(CONFIG_KEY_JDBC_MAX_POOL_SIZE, CONFIG_VALUE_JDBC_MAX_POOL_SIZE)));

        jdbcClient.getConnection(ar -> {
            if (ar.failed()) {
                logger.error("Fail to open DataBase connection", ar.cause());
                startFuture.fail(ar.cause());
            } else {
                SQLConnection connection = ar.result();
                connection.execute(SQL_CREATE_TABLE, handler -> {
                    connection.close();

                    if (handler.failed()) {
                        logger.error("Fail to create table", handler.cause());
                        startFuture.fail(handler.cause());
                    } else {
                        vertx.eventBus().consumer(
                                config().getString(EVENT_BUS_REQUEST_QUEUE, EVENT_BUS_REQUEST_QUEUE),
                                this::onMessage);

                        startFuture.complete();
                    }
                });
            }
        });
    }

    private void onMessage(Message<JsonObject> message) {
        if (!message.headers().contains(HEADER_ACTION)) {
            logger.error("No action");

            message.fail(ERROR_CODE_NO_ACTION, "No action");

            return;
        }

        String action = message.headers().get(HEADER_ACTION);

        switch (action) {
            case ACTION_LIST_ALL:
                getAll(message);
                break;
            default:
                message.fail(ERROR_CODE_NOT_SUPPORT_ACTION, "Not support action:[" + action + "]");
        }
    }

    private void getAll(Message<JsonObject> message) {
        jdbcClient.getConnection(result -> {
            if (result.failed()) {
                logger.error("Fail to get DB connection", result.cause());
                message.fail(ERROR_CODE_FAIL_TO_GET_DB_CONNECTION, "Fail to get DB connection");
            } else {
                SQLConnection connection = result.result();

                connection.query(SQL_ALL, rs -> {
                    connection.close();

                    if (rs.failed()) {
                        logger.error("Fail to query DB", rs.cause());
                        message.fail(ERROR_CODE_FAIL_TO_EXECUTE_SQL, "Fail to query DB");
                    } else {
                        List<String> players = rs.result()
                                .getResults()
                                .stream()
                                .map(json -> json.getString(0))
                                .sorted()
                                .collect(Collectors.toList());

                        message.reply(new JsonObject().put("players", new JsonArray(players)));
                    }
                });
            }
        });
    }

}
