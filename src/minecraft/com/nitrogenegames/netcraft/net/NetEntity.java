package com.nitrogenegames.netcraft.net;

import java.util.ArrayList;

import net.minecraft.world.World;

public class NetEntity {
	public ArrayList objects = new ArrayList();
	public World worldObj;
	public int[] core;
	public NetEntity(World w) {
		worldObj = w;
	}
	public NetEntity(World w, ArrayList t) {
		worldObj = w;
		objects = t;
	}
}
