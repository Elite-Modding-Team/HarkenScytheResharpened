package mod.emt.harkenscythe.block;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.List;
import mod.emt.harkenscythe.init.HSBlocks;

public class HSBlockCloth extends Block implements IShearable
{
    public HSBlockCloth(MapColor mapColor)
    {
        super(Material.CLOTH, mapColor);
        this.setHardness(0.8F);
        this.setSoundType(SoundType.CLOTH);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!world.isRemote && stack.getItem() instanceof ItemShears)
        {
            player.addStat(StatList.getBlockStats(this));
            spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this), 1));
        }
        else
        {
            super.harvestBlock(world, player, pos, state, te, stack);
        }
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return this == HSBlocks.bloodweave_cloth || this == HSBlocks.soulweave_cloth;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }
}
