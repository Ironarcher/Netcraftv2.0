package com.nitrogenegames.netcraft.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.machine.ContainerCore;
import com.nitrogenegames.netcraft.machine.TileEntityCore;

public class GuiCore extends GuiContainer {
		TileEntityCore tel;
        public GuiCore (InventoryPlayer inventoryPlayer, TileEntityCore tileEntity) {
                //the container is instanciated and passed to the superclass for handling
                super(new ContainerCore(inventoryPlayer, tileEntity));
                tel = tileEntity;
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int param1, int param2) {
                //draw text and stuff here
                //the parameters for drawString are: string, x, y, color
                fontRenderer.drawString("Net Core", 66, 6, 4210752);
                fontRenderer.drawString("EU: " + tel.energy, 140, 6, 4210752);
                ItemStack par1ItemStack = tel.getStackInSlot(0);
                if(par1ItemStack != null) {
        		if( par1ItemStack.stackTagCompound == null )
                    par1ItemStack.setTagCompound( new NBTTagCompound( ) );

                NBTTagCompound tagCompound = par1ItemStack.getTagCompound();	
                NBTTagList tagList = tagCompound.getTagList("Marked");
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
                    fontRenderer.drawString(tag.getString("MarkedThing"), 90, (i * 10) + 30, 4210752);
                }
                }
                //draws "Inventory" or your regional equivalent
                fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);

        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
        {
        	final ResourceLocation texture = new ResourceLocation(Netcraft.modid.toLowerCase(), "/textures/gui/guiModule.png");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture(texture);
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            //this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
            drawTexturedQuadFit(x, y, xSize, ySize, 0);
        }
        
        public static void drawTexturedQuadFit(double x, double y, double width, double height, double zLevel){
    		Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(x + 0, y + height, zLevel, 0,1);
            tessellator.addVertexWithUV(x + width, y + height, zLevel, 1, 1);
            tessellator.addVertexWithUV(x + width, y + 0, zLevel, 1,0);
            tessellator.addVertexWithUV(x + 0, y + 0, zLevel, 0, 0);
            tessellator.draw();
    	}

}