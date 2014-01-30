package com.nitrogenegames.netcraft.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.machine.TileEntityProjector;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockProjector extends BlockContainer {
	Netcraft.EnumProjector type;
	public int range = 20;
	public boolean upgraded = false;
	public int rangeupgraded = 0;
	public int itemID;
    private final Random furnaceRand = new Random();
     public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par2List, boolean par4)
     {
		 par2List.add("Range: "+ range);

    }
	public BlockProjector(int par1, Material par2Material, Netcraft.EnumProjector par3) {
		super(par1, par2Material);
		type = par3;

	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityProjector();
	}
    /*public int idDropped(int par1, Random par2Random, int par3)
    {

		if(type == Netcraft.EnumProjector.BEAM) {
			return Netcraft.itemProjectorBeam.itemID;
		} else if(type == Netcraft.EnumProjector.CIRCULAR) {
			return Netcraft.itemProjectorRadial.itemID;
		} else {
			return Netcraft.itemProjectorSatelite.itemID;
		}
    }*/
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        float f = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
        float f1 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
        float f2 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

    	ItemStack s;
		if(type == Netcraft.EnumProjector.BEAM) {
	    	s = new ItemStack(Netcraft.itemProjectorBeam, 1);
		} else if(type == Netcraft.EnumProjector.CIRCULAR) {
	    	s = new ItemStack(Netcraft.itemProjectorRadial, 1);
		} else {
	    	s = new ItemStack(Netcraft.itemProjectorSatelite, 1);
		}
    	TileEntityProjector t = (TileEntityProjector) par1World.getBlockTileEntity(par2, par3, par4);
    	Netcraft.InstantiateStackNBT(s);
    	s.stackTagCompound.setInteger("range", t.range);
        EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), s);
        if (s.hasTagCompound())
        {
            entityitem.getEntityItem().setTagCompound((NBTTagCompound)s.getTagCompound().copy());
        }
        float f3 = 0.05F;
        entityitem.motionX = (double)((float)this.furnaceRand.nextGaussian() * f3);
        entityitem.motionY = (double)((float)this.furnaceRand.nextGaussian() * f3 + 0.2F);
        entityitem.motionZ = (double)((float)this.furnaceRand.nextGaussian() * f3);
        par1World.spawnEntityInWorld(entityitem);
        if (hasTileEntity(par6) && !(this instanceof BlockContainer))
        {
            par1World.removeBlockTileEntity(par2, par3, par4);
        }
        
    }
    public ArrayList<ItemStack> getBlockDropped(World w, int x, int y, int z, int meta, int fortune){
    	ArrayList<ItemStack> list = new ArrayList<ItemStack>();


		return list;
    }
	
	

}
