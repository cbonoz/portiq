package com.portiq.www.portiq.listcontent;

import com.portiq.www.portiq.models.Port;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PortContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Port> ITEMS = new ArrayList<Port>();

    /**
     * A map of sample (dummy) items, by ID.
     */
//    public static final Map<String, Port> ITEM_MAP = new HashMap<String, Port>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPort(i));
        }
    }

    private static void addItem(Port item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
    }

    public static Port createPort(int position) {
        return new Port("Port " + position);
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
