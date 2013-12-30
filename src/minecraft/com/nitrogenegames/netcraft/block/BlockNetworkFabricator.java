package com.nitrogenegames.netcraft.block;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.machine.TileEntityNetworkFabricator;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNetworkFabricator extends BlockContainer{
	
	public static boolean isActive;

	public BlockNetworkFabricator(int id, boolean isActive) {
		super(id, Material.iron);
		this.isActive = isActive;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityNetworkFabricator();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float a, float b, float c){
		FMLNetworkHandler.openGui(player, Netcraft.instance, Netcraft.GuiIDNetworkFabricator, world, x, y, z);
	    return true;
	}

}
