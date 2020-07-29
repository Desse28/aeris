package com.aeris.datavalidationrest.catalogue;

import com.aeris.datavalidationrest.catalogue.flag.Flag;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlagControllerIT {
    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testGetFlags() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/flags";
        URI uri = new URI(baseUrl);
        Flag[] flags = restTemplate.getForObject(uri, Flag[].class);
        Assert.assertEquals(6, flags.length);
    }
}
