package com.aeris.datavalidationrest.catalogue.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroundData {
    @Id
    private String id;

    @Field("Date_Time")
    @JsonProperty("Date_Time")
    @CsvBindByName( column = "Date_Time", required = true )
    private String dateTime;
    @JsonProperty("0°σspR")
    @Field("0°σspR")
    @CsvBindByName( column = "0°σspR", required = true )
    private double zeroDegσspR;

    @JsonProperty("0°σspG")
    @Field("0°σspG")
    @CsvBindByName( column = "0°σspG", required = true )
    private double zeroDegσspG;

    @JsonProperty("0°σspB")
    @Field("0°σspB")
    @CsvBindByName( column = "0°σspB", required = true )
    private double zeroDegσspB;

    @JsonProperty("90°σspR")
    @Field("90°σspR")
    @CsvBindByName( column = "90°σspR", required = true )
    private double σspR;

    @JsonProperty("90°σspG")
    @Field("90°σspG")
    @CsvBindByName( column = "90°σspG", required = true )
    private double ninetyDegσspG;

    @JsonProperty("90°σspB")
    @Field("90°σspB")
    @CsvBindByName( column = "90°σspB", required = true )
    private double ninetyDegσspB;

    @JsonProperty("Air Temp")
    @Field("Air Temp")
    @CsvBindByName( column = "Air Temp", required = true )
    private double airTemp;

    @JsonProperty("Cell Temp")
    @Field("Cell Temp")
    @CsvBindByName( column = "Cell Temp", required = true )
    private double cellTemp;

    @JsonProperty("RH")
    @Field("RH")
    @CsvBindByName( column = "RH", required = true )
    private double rH;

    @JsonProperty("Pressure")
    @Field("Pressure")
    @CsvBindByName( column = "Pressure", required = true )
    private double pressure;

    public GroundData() {
    }

    public GroundData(String dateTime, double zeroDegσspR, double zeroDegσspG, double zeroDegσspB, double σspR,
                      double ninetyDegσspG, double ninetyDegσspB, double airTemp, double cellTemp, double rH,
                      double pressure) {

        this.dateTime = dateTime;
        this.zeroDegσspR = zeroDegσspR;
        this.zeroDegσspG = zeroDegσspG;
        this.zeroDegσspB = zeroDegσspB;
        this.σspR = σspR;
        this.ninetyDegσspG = ninetyDegσspG;
        this.ninetyDegσspB = ninetyDegσspB;
        this.airTemp = airTemp;
        this.cellTemp = cellTemp;
        this.rH = rH;
        this.pressure = pressure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public double getZeroDegσspR() {
        return zeroDegσspR;
    }

    public void setZeroDegσspR(double zeroDegσspR) {
        this.zeroDegσspR = zeroDegσspR;
    }

    public double getZeroDegσspG() {
        return zeroDegσspG;
    }

    public void setZeroDegσspG(double zeroDegσspG) {
        this.zeroDegσspG = zeroDegσspG;
    }

    public double getZeroDegσspB() {
        return zeroDegσspB;
    }

    public void setZeroDegσspB(double zeroDegσspB) {
        this.zeroDegσspB = zeroDegσspB;
    }

    public double getΣspR() {
        return σspR;
    }

    public void setΣspR(double σspR) {
        this.σspR = σspR;
    }

    public double getNinetyDegσspG() {
        return ninetyDegσspG;
    }

    public void setNinetyDegσspG(double ninetyDegσspG) {
        this.ninetyDegσspG = ninetyDegσspG;
    }

    public double getNinetyDegσspB() {
        return ninetyDegσspB;
    }

    public void setNinetyDegσspB(double ninetyDegσspB) {
        this.ninetyDegσspB = ninetyDegσspB;
    }

    public double getAirTemp() {
        return airTemp;
    }

    public void setAirTemp(double airTemp) {
        this.airTemp = airTemp;
    }

    public double getCellTemp() {
        return cellTemp;
    }

    public void setCellTemp(double cellTemp) {
        this.cellTemp = cellTemp;
    }

    public double getrH() {
        return rH;
    }

    public void setrH(double rH) {
        this.rH = rH;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "GroundData{" +
                "id='" + id + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", zeroDegσspR=" + zeroDegσspR +
                ", zeroDegσspG=" + zeroDegσspG +
                ", zeroDegσspB=" + zeroDegσspB +
                ", σspR=" + σspR +
                ", ninetyDegσspG=" + ninetyDegσspG +
                ", ninetyDegσspB=" + ninetyDegσspB +
                ", airTemp=" + airTemp +
                ", cellTemp=" + cellTemp +
                ", rH=" + rH +
                ", pressure=" + pressure +
                '}';
    }
}