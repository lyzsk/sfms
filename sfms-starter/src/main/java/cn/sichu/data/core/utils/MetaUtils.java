package cn.sichu.data.core.utils;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.sichu.core.exception.BusinessException;
import cn.sichu.core.utils.DateUtils;
import cn.sichu.data.core.enums.DatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数据库元数据信息工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class MetaUtils {

    private MetaUtils() {
    }

    /**
     * 获取数据库类型（如果获取不到数据库类型，则返回默认数据库类型）
     *
     * @param dataSource   数据源
     * @param defaultValue 默认数据库类型
     * @return cn.sichu.data.core.enums.DatabaseType 数据库类型
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static DatabaseType getDatabaseTypeOrDefault(DataSource dataSource,
        DatabaseType defaultValue) {
        DatabaseType databaseType = getDatabaseType(dataSource);
        return null == databaseType ? defaultValue : databaseType;
    }

    /**
     * 获取数据库类型
     *
     * @param dataSource 数据源
     * @return cn.sichu.data.core.enums.DatabaseType 数据库类型
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static DatabaseType getDatabaseType(DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            return DatabaseType.get(databaseProductName);
        } catch (SQLException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 获取所有表信息
     *
     * @param dataSource 数据源
     * @return java.util.List<cn.sichu.data.core.utils.Table> 表信息列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static List<Table> getTables(DataSource dataSource) throws SQLException, ParseException {
        return getTables(dataSource, null);
    }

    /**
     * 获取所有表信息
     *
     * @param dataSource 数据源
     * @param tableName  表名称
     * @return java.util.List<cn.sichu.data.core.utils.Table> 表信息列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static List<Table> getTables(DataSource dataSource, String tableName)
        throws SQLException, ParseException {
        String querySql = "SHOW TABLE STATUS";
        List<Entity> tableEntityList;
        Db db = Db.use(dataSource);
        if (CharSequenceUtil.isNotBlank(tableName)) {
            tableEntityList = db.query("%s WHERE NAME = ?".formatted(querySql), tableName);
        } else {
            tableEntityList = db.query(querySql);
        }
        List<Table> tableList = new ArrayList<>(tableEntityList.size());
        for (Entity tableEntity : tableEntityList) {
            Table table = new Table(tableEntity.getStr("NAME"));
            table.setComment(tableEntity.getStr("COMMENT"));
            table.setEngine(tableEntity.getStr("ENGINE"));
            table.setCharset(tableEntity.getStr("COLLATION"));
            table.setCreateTime(DateUtils.parseMillisecond(tableEntity.getDate("CREATE_TIME")));
            table.setUpdateTime(DateUtils.parseMillisecond(tableEntity.getDate("UPDATE_TIME")));
            tableList.add(table);
        }
        return tableList;
    }

    /**
     * 获取所有列信息
     *
     * @param dataSource 数据源
     * @param tableName  表名称
     * @return java.util.Collection<cn.hutool.db.meta.Column> 列信息列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static Collection<Column> getColumns(DataSource dataSource, String tableName) {
        cn.hutool.db.meta.Table table = MetaUtil.getTableMeta(dataSource, tableName);
        return table.getColumns();
    }
}
