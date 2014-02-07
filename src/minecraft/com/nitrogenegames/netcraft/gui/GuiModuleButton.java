package com.nitrogenegames.netcraft.gui;
 
import org.lwjgl.opengl.GL11;

import com.nitrogenegames.netcraft.Netcraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
 
public class GuiModuleButton extends GuiButton {
	
	/*
	 * Stores the information for the tab button on the core GUI
	 * Also has the information of the tab itself
	 */
	
	//syntax for sheet is following order of buttons, going from top to bottom: normal, normal hovered, pressed, pressed+hovered
	private ResourceLocation texture1;
	private boolean pressed = false;
	private int mode = 0;
 
	//for default size: 120 width and 20 height
    public GuiModuleButton(int id, int startx, int starty, String reslocation)
    {
        this(id, startx, starty, 120, 20, reslocation);
    }
 
    //use this
    public GuiModuleButton(int id, int xposition, int yposition, int width, int height, String reslocation, boolean pressed)
    {
        super(id, xposition, yposition, width, height, "");
        texture1 = new ResourceLocation(Netcraft.modid.toLowerCase(), reslocation);
        this.setPressed(pressed);
        }

    public GuiModuleButton(int id, int xposition, int yposition, int width, int height, String reslocation)
    {
    	this(id, xposition, yposition, width, height, reslocation, false);
    }
    
    @Override
    public void drawButton(Minecraft mc, int mx, int my)
    {
    	if(this.drawButton){
	        mc.renderEngine.bindTexture(texture1);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        if(!pressed){
	        	switch(mode){
	        	case 0: drawTexturedModalRect(this.xPosition, this.yPosition, 0, 0, this.width, this.height);
	        	case 1: drawTexturedModalRect(this.xPosition, this.yPosition, 0, 5, this.width, this.height);
	        	case 2: drawTexturedModalRect(this.xPosition, this.yPosition, 0, 10, this.width, this.height);
	        	case 3: drawTexturedModalRect(this.xPosition, this.yPosition, 0, 15, this.width, this.height);
	        	}
	        	
	        } else if(pressed){
	        	switch(mode){
	        	case 0: drawTexturedModalRect(this.xPosition, this.yPosition, 12, 0, this.width, this.height);
	        	case 1: drawTexturedModalRect(this.xPosition, this.yPosition, 12, 5, this.width, this.height);
	        	case 2: drawTexturedModalRect(this.xPosition, this.yPosition, 12, 10, this.width, this.height);
	        	case 3: drawTexturedModalRect(this.xPosition, this.yPosition, 12, 15, this.width, this.height);
	        	}
	        }
    	}
    }
   
    //0: Red Mode - Module not running
    //1: Orange button - Module attempting run, but not sufficient power
    //2: Yellow button - Module slot open
    //3: Green button - Module running successfully
    
    public int getMode(){
    	return mode;
    }
    
    public void setMode(int s){
    	mode = s;
    }
    
    public void togglePressed(){
    	pressed = !pressed;
    }
    
    public void setPressed(boolean s){
    	pressed = s;
    }
}