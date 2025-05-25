package com.example.surfacedrawexample.Map;

public  class Crafts {
    public static Craft[]  crafts = new Craft[2];
    public Crafts(){
        crafts[0] = new Craft(new int[]{3}, new Item(5), new Item(1), 500);
        crafts[1] = new Craft(new int[]{3}, new Item(1), new Item(2), 500);
    }
    public static Item getCraftIng(int idCraft, int iding){
        for(Craft craft : crafts){
            boolean f1 = false, f2 = false;
            for(Item ing : craft.ingredients){
                if(ing.id == iding)
                    f1 = true;
            }
            for(int crafter : craft.idCraft){
                if(crafter == idCraft)
                    f2 = true;
            }
            if(f1 & f2)
                return new Item(craft.product.id, craft.product.num);
        }
        return null;
    }
    public static Craft getProductId(int idCraft, int id){
        for(Craft craft : crafts) {
            if(craft.product.id == id){
                for(int crafter : craft.idCraft){
                    if(crafter == idCraft)
                        return craft;
                }
            }
        }
        return null;
    }
}
