package com.portiq.www.portiq.models;

/**
 * Created by cbono on 11/19/16.
 */

public class Port {

    public String name;

    public Port(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
