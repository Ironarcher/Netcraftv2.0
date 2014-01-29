package com.nitrogenegames.netcraft.block;

import java.util.Random;

import com.nitrogenegames.netcraft.Netcraft;

import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;

public class BlockMatrixCube extends Block {
private Icon icon;


public BlockMatrixCube(int par1, String texture) {
	super(par1, Material.rock);
		setCreativeTab(Netcraft.netcrafttab); //place in creative tabs
		setHardness(4f);
		setResistance(10f);
		setLightValue(0f);
		setStepSound(Block.soundMetalFootstep);
	}



	//drops when broken with pickaxe
	public int idDropped(int par1, Random par2Random, int par3)
		{
		return Netcraft.matrixcube.blockID;
		}
	public int quantityDropped(Random random)
		{
		return 1;
		}
		
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
	
		this.blockIcon = par1IconRegister.registerIcon(Netcraft.modid + ":" + (this.getUnlocalizedName()));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return this.blockIcon;
	}



}