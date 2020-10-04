package cn.itcast.haoke.dubbo.api.graphql;


import cn.itcast.haoke.dubbo.api.service.HouseResourcesService;
import cn.itcast.haoke.dubbo.server.pojo.HouseResources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

//实现功能：将Graph QL对象载入spring 容器 ，并且完成graphql对象的初始化
@Component
public class GraphQLProvider {

    private GraphQL graphQL;


    @Autowired
    private List<MyDataFetcher> myDataFetcherList;

    /**
     * spring 启动时就执行该方法
     */
    @PostConstruct
    public void init() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:haoke.graphqls");

        //通过schema构建graphql
        this.graphQL = GraphQL.newGraphQL(buildSchema(file)).build();
    }

    private GraphQLSchema buildSchema (File file) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(file);
        return new SchemaGenerator().makeExecutableSchema(typeRegistry,buildWriting());
    }

    private RuntimeWiring buildWriting() {
        return RuntimeWiring.newRuntimeWiring().type("HaokeQuery",builder -> {
            for (MyDataFetcher myDataFetcher : myDataFetcherList) {
                builder.dataFetcher(myDataFetcher.fieldName(),environment -> myDataFetcher.dataFetcher(environment));
            }
            return builder;

        }).build();//这里用了lambda表达式
    }

    @Bean
    public GraphQL graphQL() {
        return this.graphQL;
    }


}
