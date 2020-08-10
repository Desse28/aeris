package com.aeris.datavalidationrest.catalogue;

import com.aeris.datavalidationrest.flags.Flag;
import com.aeris.datavalidationrest.flags.FlagDao;
import com.aeris.datavalidationrest.flags.FlagService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlagServiceIT {
    @Autowired
    private FlagDao flagDao;
    @Autowired
    private RestTemplate restTemplate;

    private final FlagService flagService = new FlagService();

    @Test
    @Order(1)
    public void getDataInfo() {
        Flag[] flags = flagService.getFlag(restTemplate);
        Assert.assertTrue( 0 < flags.length  );
    }

    @Test
    @Order(2)
    public void deleteAllFlags() {
        List<Flag> flags;
        flagDao.deleteAll();
        flags = flagDao.findAll();
        Assert.assertTrue(flags.isEmpty());
    }

    @Test
    @Order(3)
    public void initGroundData() {
        List<Flag> dbFlags;
        Flag[] flags = flagService.getFlag(restTemplate);
        for( Flag flag: flags ) {
            flagDao.save(flag);
        }
        dbFlags = flagDao.findAll();
        Assert.assertFalse(dbFlags.isEmpty());
    }
}
