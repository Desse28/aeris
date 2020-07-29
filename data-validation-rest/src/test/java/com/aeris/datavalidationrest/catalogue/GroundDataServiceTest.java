package com.aeris.datavalidationrest.catalogue;

import com.aeris.datavalidationrest.catalogue.data.GroundData;
import com.aeris.datavalidationrest.catalogue.data.GroundDataDao;
import com.aeris.datavalidationrest.catalogue.data.GroundDataService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroundDataServiceTest {
    @Autowired
    private GroundDataDao groundDataDao;
    @Autowired
    private RestTemplate restTemplate;

    private final GroundDataService groundDataService = new GroundDataService();

    @Test
    @Order(1)
    public void getGroundData() {
        List<GroundData> groundDataList =  groundDataService.getGroundData(restTemplate);
        Assert.assertFalse(groundDataList.isEmpty());
    }

    @Test
    @Order(2)
    public void deleteAllGroundData() {
        List<GroundData> groundDataList;
        groundDataDao.deleteAll();
        groundDataList = groundDataDao.findAll();
        Assert.assertTrue(groundDataList.isEmpty());
    }

    @Test
    @Order(3)
    public void initGroundData() {
        List<GroundData> dbGroundDataList;
        List<GroundData> groundDataList = groundDataService.getGroundData(restTemplate);
        for(GroundData groundData : groundDataList) {
            groundDataDao.save(groundData);
        }
        dbGroundDataList = groundDataDao.findAll();
        Assert.assertFalse(dbGroundDataList.isEmpty());
    }
}
