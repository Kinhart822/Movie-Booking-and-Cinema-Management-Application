package vn.edu.usth.mcma.frontend.utils.mapper;

import java.util.List;

import vn.edu.usth.mcma.frontend.model.item.ItemsOrderedItem;
import vn.edu.usth.mcma.frontend.model.parcelable.ItemsOrderedParcelable;

public class ItemsOrderedMapper {
    public static ItemsOrderedItem fromParcelable(ItemsOrderedParcelable parcelable) {
        return ItemsOrderedItem.builder()
                .quantity(parcelable.getQuantity())
                .name(parcelable.getName())
                .totalPrice(parcelable.getTotalPrice()).build();
    }
    public static List<ItemsOrderedItem> fromParcelableList(List<ItemsOrderedParcelable> parcelables) {
        return parcelables.stream()
                .map(ItemsOrderedMapper::fromParcelable)
                .toList();
    }
}
