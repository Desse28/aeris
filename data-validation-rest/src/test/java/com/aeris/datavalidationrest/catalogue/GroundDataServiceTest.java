package com.aeris.datavalidationrest.catalogue;

import com.aeris.datavalidationrest.catalogue.data.GroundDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroundDataServiceTest {
    private final GroundDataService groundDataService = new GroundDataService();

    @Test
    public void getGroundData() {
        groundDataService.getGroundData();
    }
}
