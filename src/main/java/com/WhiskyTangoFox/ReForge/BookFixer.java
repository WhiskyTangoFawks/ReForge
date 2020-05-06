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

/* function to do the entire world- currently just crashes and doesnt actually run

    @SubscribeEvent
    public void loadWorld(WorldEvent.Load event){
        fixBooks(event.getWorld());
    }
    double totalLoops = 832;
    double count = 0;
    double percent = 100;
    public void fixBooks(IWorld world){
        for (double i = ReforgeConfig.minX; i < ReforgeConfig.maxX; i=i+16){
            for (int j = ReforgeConfig.minZ; j < ReforgeConfig.maxZ; j=j+16){
                IChunk chunk = world.getChunk(new BlockPos(i, 0, j));
                Set<BlockPos> entitiesList = chunk.getTileEntitiesPos();
                for (BlockPos pos : entitiesList) {
                    if (chunk.getTileEntity(pos) instanceof ChestTileEntity) {
                        BookFixer.fixBook((ChestTileEntity) chunk.getTileEntity(pos), pos);
                    }
                }

            }
            count++;
            double done = count/totalLoops*percent;
            ReForge.LOGGER.info("Rows Finished = " + count + ", finished percent = " + done);
        }
        ReForge.LOGGER.info("finished fixing all books");
    }
*/

}
