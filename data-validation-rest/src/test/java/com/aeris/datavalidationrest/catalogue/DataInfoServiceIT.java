package com.aeris.datavalidationrest.catalogue;

import com.aeris.datavalidationrest.catalogue.datainfo.DataInfo;
import com.aeris.datavalidationrest.catalogue.datainfo.DataInfoDao;
import com.aeris.datavalidationrest.catalogue.datainfo.DataInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DataInfoServiceIT {
    @Autowired
    private DataInfoDao dataInfoDao;
    @Autowired
    private RestTemplate restTemplate;

    private final DataInfoService dataInfoService = new DataInfoService();

    @Test
    @Order(1)
    public void getDataInfo() {
        DataInfo dataInfo = dataInfoService.getDataInfo(restTemplate);
        Assert.assertNotEquals(dataInfo, null);
    }

    @Test
    @Disabled
    @Order(2)
    public void deleteAllDataInfo() {
        List<DataInfo> dataInfos;
        dataInfoDao.deleteAll();
        dataInfos = dataInfoDao.findAll();
        Assert.assertTrue(dataInfos.isEmpty());
    }

    @Test
    @Order(3)
    public void initDataInfo() {
        List<DataInfo> dataInfos;
        DataInfo dataInfo = dataInfoService.getDataInfo(restTemplate);
        dataInfoDao.save(dataInfo);
        dataInfos = dataInfoDao.findAll();
        Assert.assertFalse(dataInfos.isEmpty());
    }
}
