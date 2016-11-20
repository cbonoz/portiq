package com.portiq.www.portiq.models;

import java.io.Serializable;

/**
 * Created by cbono on 11/19/16.
 */

public class Shipment implements Serializable {

    public String name;
    public String containerNum;
    public String timeIn;
    public String timeOut;
    public String port;
//    public String shipmentType; // arrive or depart

    public Shipment( String containerNum, String timeIn, String timeOut) {
        this.containerNum = containerNum;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public Shipment( String containerNum, String timeIn, String timeOut, String port) {
        this.containerNum = containerNum;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.port = port;
    }

//    public Shipment(String name) {
//        this.name = name + "11";
//    }

    public Shipment(String container) {
        this.containerNum = container;
        this.timeIn = "";
        this.timeOut = "";
    }

    @Override
    public String toString() {
     return containerNum + ":\n\tArrival: " + timeIn + "\n\tDeparture: " + timeOut;
//        return name;
    }
}
