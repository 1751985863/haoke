package cn.itcast.haoke.dubbo.api.graphql;

import cn.itcast.haoke.dubbo.api.service.HouseResourcesService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HouseResourceDataFetcher implements MyDataFetcher {


    @Autowired
    private HouseResourcesService houseResourcesService;
    @Override
    public String fieldName() {
        return "HouseResources";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        Long id = environment.getArgument("id");
        return this.houseResourcesService.queryHouseResources(id);
    }
}