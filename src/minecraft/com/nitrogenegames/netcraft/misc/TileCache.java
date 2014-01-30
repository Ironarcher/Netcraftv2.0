package com.nitrogenegames.netcraft.misc;

import net.minecraft.tileentity.TileEntity;

public class TileCache {
	public int[] coords;
	public TileEntity entity;
	public TileCache(TileEntity t, int[] c) {
		coords = c;
		entity = t;
	}
	public TileEntity getEntity() {
		return entity;
	}
	public int[] getCoordinates() {
		return coords;
	}
}
