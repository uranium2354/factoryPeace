package com.example.surfacedrawexample.Map;

public class Craft {
    public int[] idCraft;
    public Item[] ingredients;
    public Item product;
    int time;
    public Craft(int[] idCraft, Item[] ingredients,  Item product, int time){
        this.idCraft = idCraft;
        this.ingredients = ingredients;
        this.product = product;
        this.time = time;
    }
    public Craft(int[] idCraft, Item ingredients,  Item product, int time){
        this.idCraft = idCraft;
        this.ingredients = new Item[]{ingredients};
        this.product = product;
        this.time = time;
    }
}
