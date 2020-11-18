package com.aeris.datavalidationrest.netcdf;

import com.aeris.datavalidationrest.auth.LoginResource;
import com.aeris.datavalidationrest.common.CommonService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@Service
public class NetCdfService {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CommonService commonService;
    private final static String BASE_NAME = "netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00";

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    public ResponseEntity<NetCdf> getParameterByName(String name) {
        NetCdf netcdf;
        ResponseEntity<NetCdf> response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(null);

        if (this.commonService.isPI(request)) {
            try {
                netcdf = ReadNetcdf.getNetCdf(BASE_NAME + "_" + name);
                response = ResponseEntity.status(HttpStatus.SC_OK).body(netcdf);
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                //e.printStackTrace();
            }
        }

        return response;
    }
}
