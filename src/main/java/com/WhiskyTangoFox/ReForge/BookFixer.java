package com.WhiskyTangoFox.ReForge;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.Logger;

public class BookFixer {

    static Logger LOGGER = ReForge.LOGGER;

    public static void fixBook(ChestTileEntity chest, BlockPos pos) {
        for (int i = 0; i < chest.getSizeInventory() - 1; i++) {
            ItemStack itemstack = chest.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem().equals(Items.ENCHANTED_BOOK)) {
                    itemstack.getTag().get("Enchantments");
                    CompoundNBT oldTag = itemstack.getTag();
                    if (oldTag.contains("Enchantments")) {
                        LOGGER.info("fixing broken enchantment at " + pos.toString());
                        oldTag.put("StoredEnchantments", oldTag.get("Enchantments"));
                        oldTag.remove("Enchantments");
                    }
                }
            }
        }
    }


}
