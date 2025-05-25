package com.example.surfacedrawexample.Map;

public  class Crafts {
    public static Craft[] craftsItem = new Craft[19];
    public Crafts(){
        craftsItem[0] = new Craft(new int[]{3}, new Item(5), new Item(4), 500);
        craftsItem[1] = new Craft(new int[]{3}, new Item(16), new Item(17), 500);
        craftsItem[2] = new Craft(new int[]{3}, new Item[]{new Item(14)}, new Item(7), 500);
        craftsItem[3] = new Craft(new int[]{11}, new Item[]{new Item(14)}, new Item(22), 3000);
        craftsItem[4] = new Craft(new int[]{11}, new Item[]{new Item(7), new Item(22)}, new Item(15), 3000);
        craftsItem[5] = new Craft(new int[]{3}, new Item[]{new Item(16)}, new Item(17), 500);
        craftsItem[6] = new Craft(new int[]{11}, new Item[]{new Item(17), new Item(15)}, new Item(18), 3000);
        craftsItem[7] = new Craft(new int[]{11}, new Item[]{new Item(4)}, new Item(13,5), 1000);
        craftsItem[8] = new Craft(new int[]{11}, new Item[]{new Item(17), new Item(15)}, new Item(18), 1000);
        craftsItem[9] = new Craft(new int[]{11}, new Item[]{new Item(17, 5), new Item(13, 10)}, new Item(9), 3000);
        craftsItem[10] = new Craft(new int[]{11}, new Item[]{new Item(4), new Item(18, 3), new Item(17, 2)}, new Item(19), 4000);
        craftsItem[11] = new Craft(new int[]{11}, new Item[]{new Item(18), new Item(4)}, new Item(20), 3000);
        craftsItem[12] = new Craft(new int[]{11, 5}, new Item[]{new Item(19, 10), new Item(20, 15), new Item(13, 15)}, new Item(21), 20000);
        craftsItem[13] = new Craft(new int[]{11}, new Item[]{new Item(13), new Item(18)}, new Item(11), 10000);
        craftsItem[14] = new Craft(new int[]{11}, new Item[]{new Item(4)}, new Item(12, 2), 1000);
        craftsItem[15] = new Craft(new int[]{11}, new Item[]{new Item(13, 4), new Item(12, 1)}, new Item(1), 1500);
        craftsItem[16] = new Craft(new int[]{11}, new Item[]{new Item(13, 10), new Item(17, 3)}, new Item(2), 2000);
        craftsItem[17] = new Craft(new int[]{11}, new Item[]{new Item(14, 5), new Item(4, 3)}, new Item(3), 6000);
        craftsItem[18] = new Craft(new int[]{11}, new Item[]{new Item(1, 5)}, new Item(8), 3000);


    }
    public static Item getCraftIng(int idCraft, int iding){
        for(Craft craft : craftsItem){
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
        for(Craft craft : craftsItem) {
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
