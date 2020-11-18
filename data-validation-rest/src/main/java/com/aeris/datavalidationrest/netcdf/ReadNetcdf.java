package com.aeris.datavalidationrest.netcdf;


import com.google.gson.Gson;

import java.io.*;

public class ReadNetcdf {

    static String fileName = "P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00.json";

    public static void main(String[] args) {
        NetCdf netCdf;
        Gson gson = new Gson();
        BufferedReader bufferedReader;
        ClassLoader classLoader = ReadNetcdf.class.getClassLoader();

        try {
            bufferedReader = new BufferedReader(new FileReader(classLoader.getResource(fileName).getFile()));
            netCdf = gson.fromJson(bufferedReader, NetCdf.class);

            System.out.println("Test getJSON obj : ");
            System.out.println(netCdf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}