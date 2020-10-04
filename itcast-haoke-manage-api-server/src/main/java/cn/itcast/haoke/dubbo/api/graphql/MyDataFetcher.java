package cn.itcast.haoke.dubbo.api.graphql;

import graphql.schema.DataFetchingEnvironment;

public interface MyDataFetcher {

    /**
     * 获取模式名
     * @return
     */
    String fieldName();

    /**
     * 数据查询
     * @param environment
     * @return
     */
    Object dataFetcher(DataFetchingEnvironment environment);
}
