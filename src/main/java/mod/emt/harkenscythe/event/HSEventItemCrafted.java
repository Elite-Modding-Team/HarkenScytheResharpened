package mod.emt.harkenscythe.event;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.recipe.HSRecipeRefreshTomeUse;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventItemCrafted {
    @SubscribeEvent
    public static void onCrafting(PlayerEvent.ItemCraftedEvent event)
    {
        // Convert enchantment levels into essence and experience
        if (!event.player.world.isRemote && HSRecipeRefreshTomeUse.matches(event.craftMatrix))
        {
            for (int index = 0; index < event.craftMatrix.getSizeInventory(); index++)
            {
                ItemStack stack = event.craftMatrix.getStackInSlot(index);

                if (!stack.isEmpty() && stack.isItemEnchanted())
                {
                    NBTTagList tagList = stack.getEnchantmentTagList();
                    int totalLevels = 0;

                    for (int tagIndex = 0; tagIndex < tagList.tagCount(); tagIndex++)
                    {
                        NBTTagCompound tag = tagList.getCompoundTagAt(tagIndex);
                        totalLevels += tag.getInteger("lvl");
                    }

                    if (totalLevels > 0)
                    {
                    	for (int slots = event.craftMatrix.getSizeInventory(), i = 0; i < slots; ++i)
                    	{
                        // TODO: Make it spawn experience

                    		// Add essence into the tome
                    		if (event.craftMatrix.getStackInSlot(i).getItem() == HSItems.refresh_tome)
                    		{
                    			event.craftMatrix.getStackInSlot(i).damageItem(-totalLevels, event.player);
                    		}
                    	}
                    }

                    break;
                }
            }
        }
    }
}
