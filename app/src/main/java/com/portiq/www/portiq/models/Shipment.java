package com.portiq.www.portiq.models;

import java.io.Serializable;

/**
 * Created by cbono on 11/19/16.
 */

public class Shipment implements Serializable {

    public String containerNum;
    public String timeIn;
    public String timeOut;
    public String shipmentType; // arrive or depart

    public Shipment(String timeIn, String containerNum, String shipmentType, String timeOut) {
        this.timeIn = timeIn;
        this.containerNum = containerNum;
        this.shipmentType = shipmentType;
        this.timeOut = timeOut;
    }
}
