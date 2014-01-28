package com.nitrogenegames.netcraft.block;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.machine.TileEntityProjector;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockProjector extends BlockContainer {
	Netcraft.EnumProjector type;
	protected BlockProjector(int par1, Material par2Material, Netcraft.EnumProjector par3) {
		super(par1, par2Material);
		type = par3;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityProjector();
	}

}
