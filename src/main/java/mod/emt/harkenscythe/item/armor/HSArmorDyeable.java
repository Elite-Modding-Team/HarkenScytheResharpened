package mod.emt.harkenscythe.item.armor;

import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HSArmorDyeable extends HSArmor implements IItemColor
{
    private static final String[] BLOODWEAVE_ARMOR_TEXTURES = new String[] {"harkenscythe:textures/models/armor/bloodweave_1.png", "harkenscythe:textures/models/armor/bloodweave_2.png", "harkenscythe:textures/models/armor/bloodweave_1_overlay.png", "harkenscythe:textures/models/armor/bloodweave_2_overlay.png"};
    private static final String[] SOULWEAVE_ARMOR_TEXTURES = new String[] {"harkenscythe:textures/models/armor/soulweave_1.png", "harkenscythe:textures/models/armor/soulweave_2.png", "harkenscythe:textures/models/armor/soulweave_1_overlay.png", "harkenscythe:textures/models/armor/soulweave_2_overlay.png"};
    private final int defaultColor;

    public HSArmorDyeable(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, EnumRarity rarity, int defaultColor)
    {
        super(materialIn, renderIndexIn, equipmentSlotIn, rarity);
        this.defaultColor = defaultColor;
    }

    public int getDefaultColor()
    {
        return defaultColor;
    }

    @Override
    public boolean hasColor(ItemStack stack)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("display", 10) && stack.getTagCompound().getCompoundTag("display").hasKey("color", 3);
    }

    @Override
    public int getColor(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (nbttagcompound1.hasKey("color", 3))
            {
                return nbttagcompound1.getInteger("color");
            }
        }

        return getDefaultColor();
    }

    @Override
    public void removeColor(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (nbttagcompound1.hasKey("color"))
            {
                nbttagcompound1.removeTag("color");
            }
        }
    }

    @Override
    public void setColor(ItemStack stack, int color)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            stack.setTagCompound(nbttagcompound);
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

        if (!nbttagcompound.hasKey("display", 10))
        {
            nbttagcompound.setTag("display", nbttagcompound1);
        }

        nbttagcompound1.setInteger("color", color);
    }

    @Override
    public boolean hasOverlay(ItemStack stack)
    {
        return true;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        if (stack.getItem() == HSItems.bloodweave_hood || stack.getItem() == HSItems.bloodweave_robe || stack.getItem() == HSItems.bloodweave_pants || stack.getItem() == HSItems.bloodweave_shoes)
        {
            if (type != null && type.equals("overlay"))
            {
                return slot == EntityEquipmentSlot.LEGS ? BLOODWEAVE_ARMOR_TEXTURES[3] : BLOODWEAVE_ARMOR_TEXTURES[2];
            }
            return slot == EntityEquipmentSlot.LEGS ? BLOODWEAVE_ARMOR_TEXTURES[1] : BLOODWEAVE_ARMOR_TEXTURES[0];
        }
        else
        {
            if (type != null && type.equals("overlay"))
            {
                return slot == EntityEquipmentSlot.LEGS ? SOULWEAVE_ARMOR_TEXTURES[3] : SOULWEAVE_ARMOR_TEXTURES[2];
            }
            return slot == EntityEquipmentSlot.LEGS ? SOULWEAVE_ARMOR_TEXTURES[1] : SOULWEAVE_ARMOR_TEXTURES[0];
        }
    }

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex)
    {
        return tintIndex > 0 ? -1 : getColor(stack);
    }
}
