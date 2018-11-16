package io.github.danpatpang.buttonevent

abstract class Item (itemName: String, itemCount:Int, itemImage: String) {
    val itemName = itemName;
    var itemCount = itemCount;
    val itemImage = itemImage;

    override fun toString(): String {
        return "${itemName}, ${itemCount}, ${itemImage}";
    }
}