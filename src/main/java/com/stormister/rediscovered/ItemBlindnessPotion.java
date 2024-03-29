//	  Copyright 2012-2014 Matthew Karcz
//
//	  This file is part of The Rediscovered Mod.
//
//    The Rediscovered Mod is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    The Rediscovered Mod is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with The Rediscovered Mod.  If not, see <http://www.gnu.org/licenses/>.




package com.stormister.rediscovered;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemBlindnessPotion extends Item
{
	String texture;
	
    public ItemBlindnessPotion(int par2, float par3, boolean par4, String texture)
    {
        super();
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabBrewing);
        this.texture = texture;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }

    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
	    
	            
	    entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness.id, 30 * 20, 6));
	    
	    if (!entityPlayer.capabilities.isCreativeMode)
        {
	    	itemStack.stackSize--;
            if (itemStack.stackSize <= 0)
            {
                return new ItemStack(Items.glass_bottle);
            }

            entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }
	
	    return itemStack;
    }    
    
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
            return par1ItemStack;
        
    }
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
            par3List.add("\u00a77" + "Blindness (0:30)");                
        
    }
    
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IIconRegister iconRegister) 
//    {
//    	this.itemIcon = iconRegister.registerIcon(mod_Rediscovered.modid + ":" + texture);
//    }
}
