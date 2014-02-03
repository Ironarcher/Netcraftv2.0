package com.nitrogenegames.netcraft.net;

import java.util.ArrayList;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.block.BlockNodeConnection;

import net.minecraft.world.World;

public class NetEntity {
	public ArrayList objects = new ArrayList();
	public ArrayList nodes = new ArrayList();
	public ArrayList connectors = new ArrayList();
	public World worldObj;
	public int[] core;
	public NetEntity(World w) {
		worldObj = w;
		
	}
	public NetEntity(World w, ArrayList t) {
		worldObj = w;
		objects = t;
	}
	public void update() {
    	ArrayList c = Netcraft.getConnectedObjects(worldObj, core[0], core[1], core[2]);
    	ArrayList d = new ArrayList();
        for(int i = 0; i < c.size(); i++) {
        	if(worldObj.getBlockId(((int[]) c.get(i))[0], ((int[]) c.get(i))[1], ((int[]) c.get(i))[2]) == Netcraft.connectionnode.blockID) {
        		d.add(new int[]{((int[]) c.get(i))[0], ((int[]) c.get(i))[1], ((int[]) c.get(i))[2]});
        	}
        }
        connectors = d;
	}
	
}
