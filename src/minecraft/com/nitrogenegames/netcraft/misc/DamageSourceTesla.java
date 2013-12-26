package com.nitrogenegames.netcraft.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.StatCollector;

public class DamageSourceTesla extends DamageSource {

    public DamageSourceTesla(String par1Str)
    {
        super(par1Str);
    }

    /**
     * Returns the message to be displayed on player death.
     */
    public String getDeathMessage(EntityLiving par1EntityLiving)
    {
        //ItemStack itemstack = this.damageSourceEntity instanceof EntityLiving ? ((EntityLiving)this.damageSourceEntity).getHeldItem() : null;
        //String s = "death.attack." + this.damageType;
        // s1 = s + ".item";
        return par1EntityLiving.getTranslatedEntityName() + " was killed in a Tesla Field";
    }



}
