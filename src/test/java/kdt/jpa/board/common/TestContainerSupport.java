package kdt.jpa.board.common;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;

public abstract class TestContainerSupport {

    private static final String MYSQL_IMAGE = "mysql:8";

    private static final JdbcDatabaseContainer MYSQL;

    static {
        MYSQL = new MySQLContainer(MYSQL_IMAGE);

        MYSQL.start();
    }
}
