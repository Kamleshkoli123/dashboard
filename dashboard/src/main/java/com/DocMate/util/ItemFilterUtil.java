package com.DocMate.util;

import com.DocMate.model.DashboardItem;

import java.util.List;
import java.util.stream.Collectors;

public class ItemFilterUtil {

    /**
     * Removes items with status 'close' from the provided list.
     *
     * @param items List of DashboardItem objects
     * @return Filtered list of DashboardItem objects
     */
    public static List<DashboardItem> removeClosedItems(List<DashboardItem> items) {
        return items.stream()
                .filter(item -> !"close".equalsIgnoreCase(item.getStatus()))
                .collect(Collectors.toList());
    }
}
