package io.github.danpatpang.buttonevent

class CheckingItem(itemName: String, itemCount: Int, itemImage: String) : Item(itemName, itemCount, itemImage){

    fun increaseItemCount() {
        if(itemCount != 25){
            this.itemCount++;
        }
    }

    fun decreaseItemCount() {
        if(itemCount != 0){
            this.itemCount--;
        }
    }
}