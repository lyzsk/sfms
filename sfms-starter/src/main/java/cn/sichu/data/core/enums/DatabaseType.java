package cn.sichu.data.core.enums;

import cn.sichu.data.core.function.ISqlFunction;
import lombok.Getter;

import java.io.Serializable;

/**
 * 数据库类型枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
public enum DatabaseType implements ISqlFunction {

    /**
     * MySQL
     */
    MYSQL("MySQL") {
        @Override
        public String findInSet(Serializable value, String set) {
            return "find_in_set('%s', %s) <> 0".formatted(value, set);
        }
    },

    /**
     * PostgreSQL
     */
    POSTGRE_SQL("PostgreSQL") {
        @Override
        public String findInSet(Serializable value, String set) {
            return "(select position(',%s,' in ','||%s||',')) <> 0".formatted(value, set);
        }
    },
    ;

    private final String database;

    DatabaseType(String database) {
        this.database = database;
    }

    /**
     * 获取数据库类型
     *
     * @param database 数据库
     */
    public static DatabaseType get(String database) {
        for (DatabaseType databaseType : DatabaseType.values()) {
            if (databaseType.database.equalsIgnoreCase(database)) {
                return databaseType;
            }
        }
        return null;
    }

}
