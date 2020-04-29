package com.WhiskyTangoFox.ReForge;

public class BookFixer {
/*
    public boolean onCommand(PlayerEntity player) {

        for(int i = 0; i < player.inventory.getSizeInventory()-1; i++){
                ItemStack itemstack = player.inventory.getStackInSlot(i);
                if(itemstack != null) {
                    if(itemstack.getItem().equals(Items.ENCHANTED_BOOK)){
                        //EnchantmentStorageMeta meta = itemstack.(EnchantmentStorageMeta)itemstack.getItemMeta();
                        ListNBT meta = itemstack.getEnchantmentTagList();
                        //TODO- understand the enchantment tag list
                        ReForge.LOGGER.info("Enchantment NBT: "+meta.toString());
                        if(meta.isEmpty()) {
                            ReForge.LOGGER.info("Found Runic Book without enchantment! Fixing now!");
                            String metaString = meta.toString();
                            String[] msArray = metaString.split("=");
                            String enchantStr = msArray[msArray.length-2].replaceAll("[\\[\\](){}]","");
                            String enchantLvlStr = msArray[msArray.length-1].replaceAll("[\\[\\](){}]","");
                            //meta.addStoredEnchant(EnchantmentWrapper.getByName(enchantStr), Integer.parseInt(enchantLvlStr), true);
                            EnchantedBookItem.getEnchantedItemStack().
                        }


                        itemstack.setItemMeta(meta);
                        player.updateInventory();

                    }
                }
            }
            return true;
        }


*/

}
