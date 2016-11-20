package com.portiq.www.portiq.listcontent;

import com.portiq.www.portiq.models.Shipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cbono on 11/19/16.
 */

public class ShipmentContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Shipment> ITEMS = new ArrayList<Shipment>();

    /**
     * A map of sample (dummy) items, by ID.
     */
//    public static final Map<String, Shipment> ITEM_MAP = new HashMap<String, Shipment>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createShipment(i));
        }
    }

    private static void addItem(Shipment item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
    }

    public static Shipment createShipment(int position) {
        return new Shipment("1234214125");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}
