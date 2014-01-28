package com.nitrogenegames.netcraft.block;

import java.util.List;
import java.util.Random;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.machine.TileEntityProjector;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockProjector extends BlockContainer {
	Netcraft.EnumProjector type;
	public int range = 20;
	public boolean upgraded = false;
	public int rangeupgraded = 0;
	public int itemID;
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
    public int idDropped(int par1, Random par2Random, int par3)
    {

		if(type == Netcraft.EnumProjector.BEAM) {
			return Netcraft.itemProjectorBeam.itemID;
		} else if(type == Netcraft.EnumProjector.CIRCULAR) {
			return Netcraft.itemProjectorRadial.itemID;
		} else {
			return Netcraft.itemProjectorSatelite.itemID;
		}
    }
	
	

}
