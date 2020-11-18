package com.aeris.datavalidationrest.netcdf;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;

public class ReadNetcdf {
    static final Gson gson = new Gson();
    static  String VARIABLES = "variables";
    static final ClassLoader classLoader = ReadNetcdf.class.getClassLoader();
    static String fileName = "netcdf/P2OA-CRA-Lannemezan_UHF-RADAR_L2A-HM-Hourly-Mean_2019-12-16T00-00-00_1D_V2-00_UWE.json";

    public static Parameter getMainParameter(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
        FileInputStream fileInputStream = new FileInputStream(classLoader.getResource(filePath).getFile());
        InputStreamReader reader = new InputStreamReader(fileInputStream, "UTF-8");
        JsonElement json = JsonParser.parseReader(reader);
        JsonObject variables = json.getAsJsonObject().get(VARIABLES).getAsJsonObject();
        String mainParameterName = (String) variables.keySet().toArray()[0];
        JsonObject mainParameterJson = variables.get(mainParameterName).getAsJsonObject();
        return gson.fromJson(mainParameterJson, Parameter.class);
    }

    public static NetCdf getNetCdf(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(classLoader.getResource(filePath).getFile()));
        NetCdf netCdf = gson.fromJson(bufferedReader, NetCdf.class);
        netCdf.getVariables().setMain(getMainParameter(filePath));

        return netCdf;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Test netCdf : ");
            System.out.println(getNetCdf(fileName));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}