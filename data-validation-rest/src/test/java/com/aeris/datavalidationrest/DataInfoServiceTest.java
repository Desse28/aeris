package com.aeris.datavalidationrest;

import com.aeris.datavalidationrest.catalogue.datainfo.DataInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DataInfoServiceTest {
    @InjectMocks
    private DataInfoService dataInfoService = new DataInfoService();

    @Test
    public void getDataInfo() {
        dataInfoService.getCatalogue();
    }
}
