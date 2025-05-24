package mod.emt.harkenscythe.event;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.recipe.HSRecipeRefreshTomeUse;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventItemCrafted
{
    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event)
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
                            if (event.craftMatrix.getStackInSlot(i).getItem() == HSItems.refresh_tome)
                            {
                                // Add essence into the tome
                                event.craftMatrix.getStackInSlot(i).damageItem(-totalLevels, event.player);

                                // Spawn experience orbs
                                int j = totalLevels + event.player.world.rand.nextInt(5) + event.player.world.rand.nextInt(5);
                                while (j > 0)
                                {
                                    int k = EntityXPOrb.getXPSplit(i);
                                    j -= k;
                                    event.player.world.spawnEntity(new EntityXPOrb(event.player.world, event.player.posX, event.player.posY, event.player.posZ, j));
                                }

                                break;
                            }
                        }
                    }

                    break;
                }
            }
        }
    }
}
