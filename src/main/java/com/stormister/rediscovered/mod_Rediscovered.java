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

import java.util.HashMap;

import cpw.mods.fml.common.Mod.EventHandler;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWood;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderVillager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSign;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.config.*;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.util.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.lwjgl.input.Keyboard;

import com.stormister.rediscovered.BlockCherryLeaves.LeafCategory;
import com.stormister.rediscovered.BlockCherrySlab.SlabCategory;
import com.stormister.rediscovered.BlockDirtSlab.SlabCategory2;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
 
@Mod(modid = mod_Rediscovered.modid, name = "Minecraft Rediscovered Mod", version = "1.0")



/*
 * TO-DO for next update
 * -Table Inventory
 * -Lectern Something
 * -Crying Obsidian Spawn point
 * -Fix Ruby and Undead horse spawning
 */

/*
 * TO-DO for future updates
 * -Better Red Dragon
 * -Brick Pyramids
 */


public class mod_Rediscovered
{
	public static final String modid = "Rediscovered";
	@Instance
	public static mod_Rediscovered instance;
    public static HashMap<String, Pos3> usernameLastPosMap = new HashMap<String, Pos3>();
	public Random ChunkGenRand;
    public int ChunkGenRandNum;
    public static final int guiIDLockedChest = 0;
    public static BlockGearBase GearFloor;
    public static BlockGearBase GearWall;
    public static Item ItemGear;
    public static BlockSlab DirtSlab;
    public static BlockSlab DirtDoubleSlab;
    public static BlockSlab CherrySlab;
    public static BlockSlab CherryDoubleSlab;
    public static Block woodenCherrySingleSlab1;
	public static Block woodenCherryDoubleSlab1;
	public static Block dirtSingleSlab1;
	public static Block dirtDoubleSlab1;
    public static Block CherryStairs;
    public static Item Quiver;
    public static Item LeatherQuiver;
    public static Item ChainQuiver;
    public static Item GoldQuiver;
    public static Item IronQuiver;
    public static Item DiamondQuiver;
    public static Item LeatherChainHelmet;
    public static Item LeatherChainChest;
    public static Item LeatherChainLegs;
    public static Item LeatherChainBoots;
    public static Item Nausea;
    public static Item Blindness;
    public static Item Mining;
    public static Item NauseaSplash;
    public static Item JumpSplash;
    public static Item BlindnessSplash;
    public static Item MiningSplash;
    public static Block Sponge;
    public static Block RubyOre;
    public static Block RubyBlock;
    public static Item  gemRuby;
    public static Block CryingObsidian;
    public static BlockCherryLog CherryLog;
    public static Block CherryPlank;
    public static BlockCherryLeaves CherryLeaves;
    public static Block CherrySapling;
    public static Item CherrySaplingItem;
    public static Block Rose;
    public static BlockEmptyRoseBush EmptyRoseBush;
    public static Block EmptyRoseBushTop;
    public static Block Peony;
    public static Block EmptyPeonyBush;
    public static Block EmptyPeonyBushTop;
    public static Block Spikes;
    public static Block SpikesSide;
    public static Item SpikesItem;
    public static Block DragonEggRed;
    public static Block LockedChest;
    public static BlockChair Chair;
    public static BlockTable Table;
    public static Block Lectern;
    public static Block LecternOpen;
    public static Item DreamPillow;
    public static Item DreamBedItem;
    public static Block DreamBed;
    public static Block Lantern;
    public static Block LanternPhys;
    public static Item ItemLantern;    
    public static Item Scarecrow;
    
    public static int DreamChance;
    public static int ZombieHorseSpawn;
    public static int SkeletonHorseSpawn;
    public static int RedDragonSpawn;
    public static int SkyChickenSpawn;
    public static int GiantSpawn;
    public static int FishSpawn;
    public static int DimID;
    public static int HeavenBiomeID;
    public static boolean addToDefault;
    public static boolean biomeSkyGen;
    public static boolean EnablePigmanVillages;
    public static boolean EnableSpongeGenerate;
    public static boolean EnableDungeonLoot;
    public static boolean EnableRubyOre;
    public static boolean anmen;
    public static boolean RanaSpawn;
    public static boolean SteveSpawn;
    public static boolean BlackSteveSpawn;
    public static boolean BeastBoySpawn;
    public static boolean GVillagerSpawn;
    public static boolean SteveHostile;
    public static boolean BlackSteveHostile;
    public static boolean BeastBoyHostile;
    public static boolean ScarecrowAttractsMobs;
    public static boolean DreamBedEnabled;
    public static boolean DreamPillowRecipe;
    public static boolean DaytimeBed;
    public static int ParrowID;
    public static int MountBlockID;
    public static int RanaID;
    public static int SteveID;
    public static int BlackSteveID;
    public static int BeastBoyID;
    public static int GiantID;
    public static int SkyChickenID;
    public static int FishID;
    public static int PigmanID;
    public static int GVillagerID;
    public static int ZombieHorseID;
    public static int SkeletonHorseID;
    public static int ScarecrowID;
    public static int RedDragonID;
    public static final String rana = new String(mod_Rediscovered.modid + ":" + "textures/models/rana.png");
    public static final String steve = new String(mod_Rediscovered.modid + ":" + "textures/models/Steve");
    public static final String blacksteve = new String(mod_Rediscovered.modid + ":" + "textures/models/BlackSteve");
    public static final String beastboy = new String(mod_Rediscovered.modid + ":" + "textures/models/BeastBoy");
    
    public static BiomeGenBase heaven;
    public static ArmorMaterial EnumArmorMaterialInvinc = EnumHelper.addArmorMaterial("Invincible", -1, new int[] {0, 0, 0, 0}, 0);
    public static ArmorMaterial EnumArmorMaterialLC = EnumHelper.addArmorMaterial("LC", 20, new int[] {3, 8, 6, 2}, 27);
    public static ArmorMaterial EnumArmorMaterialCloth = EnumHelper.addArmorMaterial("Leather", 5, new int[]{1, 3, 2, 1}, 15);
    @SidedProxy(clientSide="com.stormister.rediscovered.ClientProxyRediscovered", serverSide= "com.stormister.rediscovered.CommonProxyRediscovered")
	public static CommonProxyRediscovered proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
    	/*
    	 * CONFIG
    	 */
    	
        Configuration c = new Configuration(e.getSuggestedConfigurationFile());
        c.load();

        //IDs
        ParrowID = c.get("Entities", "Purple Arrow ID", 88).getInt();
        MountBlockID = c.get("Entities", "Mountable Block ID", 78).getInt();
        RanaID = c.get("Entities", "Rana ID", 67).getInt();
        SteveID = c.get("Entities", "Steve ID", 69).getInt();
        BlackSteveID = c.get("Entities", "Black Steve ID", 79).getInt();
        BeastBoyID = c.get("Entities", "Beast Boy ID", 80).getInt();
        GiantID = c.get("Entities", "Giant ID", 81).getInt();
        SkyChickenID = c.get("Entities", "Sky Chicken ID", 68).getInt();
        FishID = c.get("Entities", "Fish ID", 72).getInt();
        PigmanID = c.get("Entities", "Pigman ID", 82).getInt();
        GVillagerID = c.get("Entities", "Green Villager ID", 73).getInt();
        ZombieHorseID = c.get("Entities", "Zombie Horse ID", 76).getInt();
        SkeletonHorseID = c.get("Entities", "Skeleton Horse ID", 77).getInt();
        ScarecrowID = c.get("Entities", "Scarecrow Entity ID", 89).getInt();
        RedDragonID = c.get("Entities", "Red Dragon ID", 74).getInt();
        DimID = c.get("Dimension", "Sky Dimension ID", 23).getInt();
        HeavenBiomeID = c.get("Dimension", "Sky Biome ID", 26).getInt();
        
        //Booleans
        EnableSpongeGenerate = c.get("Options", "Enable Sponges Appear in Ocean", true).getBoolean(true);
        EnableDungeonLoot = c.get("Options", "Enable Lanterns appear in Dungeon Chests", true).getBoolean(true);
        EnableRubyOre = c.get("Options", "Enable Ruby Ore Generates Underground", true).getBoolean(true);
        EnablePigmanVillages = c.get("Options", "Enable Pigman Villages in the Sky Dimension", true).getBoolean(true);
        RanaSpawn = c.get("Options", "Enable Rana Spawn in Villages", true).getBoolean(true);
        SteveSpawn = c.get("Options", "Enable Steve Spawn in Villages", true).getBoolean(true);
        SteveHostile = c.get("Options", "Enable Steve is Hostile", true).getBoolean(true);
        BlackSteveSpawn = c.get("Options", "Enable Black Steve Spawn in Pigmen Villages", true).getBoolean(true);
        BlackSteveHostile = c.get("Options", "Enable Black Steve is Hostile", true).getBoolean(true);
        BeastBoySpawn = c.get("Options", "Enable Beast Boy Spawn in Pigmen Villages", true).getBoolean(true);
        BeastBoyHostile = c.get("Options", "Enable Beast Boy is Hostile", true).getBoolean(true);
        anmen = c.get("Options", "Enable Steve, Black Steve, and Beast Boy jogging", true).getBoolean(true);
        GVillagerSpawn = c.get("Options", "Enable Green Villager Spawn in Villages", true).getBoolean(true);
        ZombieHorseSpawn = c.get("Options", "Zombie Horse Spawn Rate", 25).getInt();
        SkeletonHorseSpawn = c.get("Options", "Skeleton Horse Spawn Rate", 25).getInt();
        RedDragonSpawn = c.get("Options", "Red Dragon Spawn Rate", 25).getInt();
        SkyChickenSpawn = c.get("Options", "Sky Chicken Spawn Rate", 150).getInt();
        GiantSpawn = c.get("Options", "Giant Spawn Rate", 150).getInt();
        FishSpawn = c.get("Options", "Fish Spawn Rate", 150).getInt();
        ScarecrowAttractsMobs = c.get("Options", "Scarecrow attracts zombies (True by default. Set to false to have zombies avoid scarecrows)", true).getBoolean(true);
        DreamBedEnabled = c.get("Options", "Use old Dream Bed method of getting to Sky Dimension", false).getBoolean(false);
        DreamPillowRecipe = c.get("Options", "Enable Dream Pillow recipe (Only applies if Dream Bed method is disabled)", false).getBoolean(false);
        DreamChance = c.get("Options", "Percent chance out of 100 of going to Sky Dimension on sleep. Only applies if Dream Bed Method is disabled", 12).getInt();
        DaytimeBed = c.get("Options", "Can go to Sky Dimension without Restrictions (Daytime, Monsters nearby).", false).getBoolean(false);
        biomeSkyGen = c.get("Options", "Enable Heaven (Don't mess with this unless I explicitely state it)", true).getBoolean(false);
        c.save();
        
        /*
         * END OF CONFIG
         */
        
        
        
        
        /*
         * START OF BLOCK REGISTRATION STUFF
         */
        instance = this; 
        Sponge = (new BlockAbsorb()).setHardness(0.6F).setStepSound(Block.soundTypeGrass).setBlockName("sponge").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(modid + ":" + "sponge");
        Quiver = (new ItemQuiver( EnumArmorMaterialInvinc, 0, 1)).setUnlocalizedName("Quiver").setCreativeTab(CreativeTabs.tabCombat);
        LeatherQuiver = (new ItemQuiver( EnumArmorMaterialCloth, 0, 1)).setUnlocalizedName("LeatherQuiver").setContainerItem(Quiver).setCreativeTab(CreativeTabs.tabCombat);
        ChainQuiver = (new ItemQuiver( ArmorMaterial.CHAIN, 0, 1)).setUnlocalizedName("ChainQuiver").setContainerItem(Quiver).setCreativeTab(CreativeTabs.tabCombat);
        GoldQuiver = (new ItemQuiver( ArmorMaterial.GOLD, 0, 1)).setUnlocalizedName("GoldQuiver").setContainerItem(Quiver).setCreativeTab(CreativeTabs.tabCombat);
        IronQuiver = (new ItemQuiver( ArmorMaterial.IRON, 0, 1)).setUnlocalizedName("IronQuiver").setContainerItem(Quiver).setCreativeTab(CreativeTabs.tabCombat);
        DiamondQuiver = (new ItemQuiver( ArmorMaterial.DIAMOND, 0, 1)).setUnlocalizedName("DiamondQuiver").setContainerItem(Quiver).setCreativeTab(CreativeTabs.tabCombat);
        LeatherChainHelmet = (new ItemLC( EnumArmorMaterialLC, 0, 0)).setUnlocalizedName("LeatherChainHelmet").setCreativeTab(CreativeTabs.tabCombat);
        LeatherChainChest = (new ItemLC( EnumArmorMaterialLC, 0, 1)).setUnlocalizedName("LeatherChainChest").setCreativeTab(CreativeTabs.tabCombat);
        LeatherChainLegs = (new ItemLC( EnumArmorMaterialLC, 0, 2)).setUnlocalizedName("LeatherChainLegs").setCreativeTab(CreativeTabs.tabCombat);
        LeatherChainBoots = (new ItemLC( EnumArmorMaterialLC, 0, 3)).setUnlocalizedName("LeatherChainBoots").setCreativeTab(CreativeTabs.tabCombat);
        CryingObsidian = (new BlockCryingObsidian()).setHardness(50F).setResistance(2000F).setStepSound(Block.soundTypeStone).setBlockName("CryingObsidian").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(modid + ":" + "cryingobsidian");
        Spikes = (new BlockSpikes()).setHardness(1.5F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("Spikes").setBlockTextureName(modid + ":" + "wood_planks");
        SpikesSide = (new BlockSpikesSide()).setHardness(1.5F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("SpikesSide").setBlockTextureName(modid + ":" + "wood_planks");
        SpikesItem = (new ItemSpikes("SpikesItem")).setUnlocalizedName("ItemSpikes").setCreativeTab(CreativeTabs.tabDecorations).setTextureName(modid + ":SpikesItem");
        DragonEggRed = (new BlockDragonEggRed("dragonegg")).setHardness(3.0F).setResistance(15.0F).setStepSound(Block.soundTypeStone).setLightLevel(0.125F).setBlockName("DragonEggRed");//.setBlockTextureName(modid + ":" + "dragonegg");
        RubyOre = (new BlockRubyOre("rubyore")).setHardness(3F).setResistance(5F).setStepSound(Block.soundTypeStone).setBlockName("RubyOre").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(modid + ":" + "rubyore");
        gemRuby = (new ItemRuby("Ruby")).setUnlocalizedName("gemRuby").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(modid + ":Ruby");
        RubyBlock = (new BlockRuby("rubyblock")).setHardness(5F).setResistance(10F).setStepSound(Block.soundTypeStone).setBlockName("RubyBlock").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(modid + ":" + "rubyblock");
        Nausea = (new ItemNauseaPotion(0, 1.0F, false, "nausea")).setUnlocalizedName("nausea").setTextureName(modid + ":nausea");
        Mining = (new ItemMiningPotion(0, 1.0F, false, "mining")).setUnlocalizedName("mining").setTextureName(modid + ":mining");
        Blindness = (new ItemBlindnessPotion(0, 1.0F, false, "blindness")).setUnlocalizedName("blindness").setTextureName(modid + ":blindness");
        Lantern = new BlockLantern().setBlockName("blockLantern");
        LanternPhys = new BlockLanternPhys("lantern").setBlockName("blockLanternphys").setBlockTextureName(modid + ":" + "lantern");
        ItemLantern = new ItemLantern("Lantern").setUnlocalizedName("itemLantern").setTextureName(modid + ":Lantern");
        LockedChest = (new BlockLockedChest("lockedchest")).setHardness(0.0F).setLightLevel(1.0F).setStepSound(Block.soundTypeWood).setBlockName("lockedchest").setTickRandomly(true).setCreativeTab(CreativeTabs.tabDecorations).setBlockTextureName(modid + ":" + "wood_planks");
        Chair = (BlockChair)(new BlockChair()).setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("chair").setBlockTextureName(modid + ":" + "wood_planks");
        Table = (BlockTable)(new BlockTable(0)).setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("table").setBlockTextureName(modid + ":" + "wood_planks");
        Lectern = (new BlockLectern()).setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("lectern").setBlockTextureName(modid + ":" + "lecternitem");
        LecternOpen = (new BlockLecternOpen()).setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("lectern").setBlockTextureName(modid + ":" + "lecternitem");
        CherrySlab = (BlockCherrySlab)new BlockCherrySlab(false, Material.wood, SlabCategory.WOOD1).setBlockName("woodenCherrySingleSlab1");
		CherryDoubleSlab = (BlockCherrySlab)new BlockCherrySlab(true, Material.wood, SlabCategory.WOOD1).setBlockName("woodenCherryDoubleSlab1");
		woodenCherrySingleSlab1 = registerBlock(CherrySlab, ItemBlockCherrySlab.class, CherrySlab, CherryDoubleSlab);
		woodenCherryDoubleSlab1 = registerBlock(CherryDoubleSlab, ItemBlockCherrySlab.class, CherrySlab, CherryDoubleSlab);
		DirtSlab = (BlockDirtSlab)new BlockDirtSlab(false, Material.ground, SlabCategory2.DIRT).setBlockName("woodenDirtSingleSlab1");
		DirtDoubleSlab = (BlockDirtSlab)new BlockDirtSlab(true, Material.ground, SlabCategory2.DIRT).setBlockName("woodenDirtDoubleSlab1");
		dirtSingleSlab1 = registerBlock(DirtSlab, ItemBlockDirtSlab.class, DirtSlab, DirtDoubleSlab);
		dirtDoubleSlab1 = registerBlock(DirtDoubleSlab, ItemBlockDirtSlab.class, DirtSlab, DirtDoubleSlab);
		Scarecrow = (new ItemScarecrow("Scarecrow")).setCreativeTab(CreativeTabs.tabDecorations).setUnlocalizedName("Scarecrow").setTextureName(modid + ":Scarecrow");
		DreamPillow = (new ItemDreamPillow("DreamPillow")).setCreativeTab(CreativeTabs.tabMisc).setUnlocalizedName("DreamPillow").setTextureName(modid + ":DreamPillow");
		DreamBedItem = (new ItemDreamBed("DreamBedItem")).setCreativeTab(CreativeTabs.tabDecorations).setUnlocalizedName("DreamBedItem").setTextureName(modid + ":DreamBedItem");
        DreamBed = (new BlockDreamBed()).setHardness(0.2F).setStepSound(Block.soundTypeCloth).setBlockName("DreamBed").setBlockTextureName(modid + ":" + "cryingobsidian");
		Rose = (new BlockRose(0)).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("rose").setBlockTextureName(modid + ":" + "rose");
		EmptyRoseBush = (BlockEmptyRoseBush)(new BlockEmptyRoseBush(0)).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("empty_rose_bush").setBlockTextureName(modid + ":" + "empty_rose_bottom");
		EmptyRoseBushTop = (new BlockEmptyRoseBushTop()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("empty_rose_bush").setBlockTextureName(modid + ":" + "empty_rose_top");
		Peony = (new BlockPeony(0)).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("peony").setBlockTextureName(modid + ":" + "peony");
		EmptyPeonyBush = (new BlockEmptyPeonyBush(0)).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("empty_peony_bush").setBlockTextureName(modid + ":" + "empty_peony_bottom");
		EmptyPeonyBushTop = (new BlockEmptyPeonyBushTop()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("empty_peony_bush").setBlockTextureName(modid + ":" + "empty_peony_top");
        CherryLog = (BlockCherryLog)(new BlockCherryLog("log_cherry")).setHardness(2.0F).setStepSound(Block.soundTypeWood).setBlockName("cherrylog").setBlockTextureName(modid + ":" + "log_cherry");
        CherryLeaves = (BlockCherryLeaves)(new BlockCherryLeaves(LeafCategory.CAT1)).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("cherryleaves").setBlockTextureName(modid + ":" + "leaves_cherry");
        CherryPlank = (new BlockCherryWood("planks_cherry")).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("cherrywood").setBlockTextureName(modid + ":" + "planks_cherry");
        CherrySapling = (new BlockCherrySapling("sapling_cherry")).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("cherrysapling").setBlockTextureName(modid + ":" + "sapling_cherry");
        CherryStairs = (new BlockCherryStairs(CherryPlank, 0)).setBlockName("CherryStairs").setBlockTextureName(modid + ":" + "planks_cherry");
        ItemGear = (new ItemGear("Gear")).setUnlocalizedName("ItemGear").setCreativeTab(CreativeTabs.tabRedstone).setTextureName(modid + ":Gear");
        GearFloor = (BlockGearBase)(new BlockGearFloor()).setHardness(1.0F).setStepSound(Block.soundTypeMetal).setBlockName("GearFloor").setBlockTextureName(modid + ":gear");
        GearWall = (BlockGearBase)(new BlockGearWall()).setHardness(1.0F).setStepSound(Block.soundTypeMetal).setBlockName("GearWall").setBlockTextureName(modid + ":gear");
        
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new RediscoveredGuiHandler());
        
        heaven = (new BiomeGenSky(HeavenBiomeID)).setColor(16421912).setBiomeName("Heaven").setDisableRain();

        EntityRegistry.registerModEntity(EntityParrow.class, "Parrow", ParrowID, this, 64, 20, false);
        EntityRegistry.registerModEntity(EntityMountableBlock.class, "EntityMountableBlock", MountBlockID, this, 250, 5, false);
        EntityRegistry.registerGlobalEntityID(EntityRana.class, "Rana", RanaID, 0x4c7129, 0xf0a5a2);
        EntityRegistry.registerGlobalEntityID(EntitySteve.class, "Steve", SteveID, 44975, 0xf6b201);
        EntityRegistry.registerGlobalEntityID(EntityBlackSteve.class, "BlackSteve", BlackSteveID, 10489616, 894731);
        EntityRegistry.registerGlobalEntityID(EntityBeastBoy.class, "BeastBoy", BeastBoyID, 0x9932cc, 5349438);
        EntityRegistry.registerGlobalEntityID(EntityPigman.class, "Pigman", PigmanID, 0xf0a5a2, 0xa1a1a1);
        EntityRegistry.registerGlobalEntityID(EntityMeleePigman.class, "MeleePigman", 83, 0xf0a5a2, 0xa1a1a1);
        EntityRegistry.registerGlobalEntityID(EntityRangedPigman.class, "RangedPigman", 84, 0xf0a5a2, 0xa1a1a1);
        EntityRegistry.registerGlobalEntityID(EntityGreenVillager.class, "GreenVillager", GVillagerID, 5651507, 7969893);
        EntityRegistry.registerGlobalEntityID(EntitySkyChicken.class, "SkyChicken", SkyChickenID);
        EntityRegistry.registerGlobalEntityID(EntityGiant.class, "GiantZombie", GiantID, 2243405, 7969893);
        EntityRegistry.registerGlobalEntityID(EntityFish.class, "Fish", FishID, 44975, 2243405);
        EntityRegistry.registerGlobalEntityID(EntityZombieHorse.class, "ZombieHorse", ZombieHorseID, 0x4c7129, 15656192);
        EntityRegistry.registerGlobalEntityID(EntitySkeletonHorse.class, "SkeletonHorse", SkeletonHorseID, 12698049, 15656192);
        EntityRegistry.registerGlobalEntityID(EntityScarecrow.class, "Scarecrow", ScarecrowID);
        EntityRegistry.registerGlobalEntityID(EntityGoodDragon.class, "RedDragon", RedDragonID);
        
        MinecraftForge.EVENT_BUS.register(new com.stormister.rediscovered.RediscoveredEventHandler());
        proxy.registerRenderThings();
        
        EntityRegistry.addSpawn(EntityFish.class, FishSpawn, 5, 20, EnumCreatureType.waterCreature, BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.swampland, this.heaven);
        EntityRegistry.addSpawn(EntityZombieHorse.class, ZombieHorseSpawn, 1, 1, EnumCreatureType.monster, BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.jungleHills, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.icePlains, BiomeGenBase.desert, BiomeGenBase.megaTaiga, BiomeGenBase.roofedForest, BiomeGenBase.mesa, BiomeGenBase.savanna);
        EntityRegistry.addSpawn(EntitySkeletonHorse.class, SkeletonHorseSpawn, 1, 1, EnumCreatureType.monster, BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.jungleHills, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.icePlains, BiomeGenBase.desert, BiomeGenBase.megaTaiga, BiomeGenBase.roofedForest, BiomeGenBase.mesa, BiomeGenBase.savanna);
        EntityRegistry.addSpawn(EntityGiant.class, GiantSpawn, 2, 2, EnumCreatureType.creature, this.heaven);
        EntityRegistry.addSpawn(EntitySkyChicken.class, SkyChickenSpawn, 6, 6, EnumCreatureType.creature, this.heaven);
        
        GameRegistry.registerItem(Quiver, "rediscovered_quiver");
        GameRegistry.addShapedRecipe(new ItemStack(Quiver), "FLL", "SLL", "SLL", 'F', Items.feather, 'S', Items.string, 'L', Items.leather);
        
        GameRegistry.registerItem(LeatherQuiver, "rediscovered_leatherquiver");
        GameRegistry.addShapelessRecipe(new ItemStack(LeatherQuiver, 1), new Object[]{Quiver, Items.leather_chestplate});
        GameRegistry.addShapedRecipe(new ItemStack(Items.leather_chestplate), "Q", 'Q', LeatherQuiver);
        
        GameRegistry.registerItem(ChainQuiver, "rediscovered_chainquiver");
        GameRegistry.addShapelessRecipe(new ItemStack(ChainQuiver, 1), new Object[]{Quiver, Items.chainmail_chestplate});
        GameRegistry.addShapedRecipe(new ItemStack(Items.chainmail_chestplate), "Q", 'Q', ChainQuiver);
        
        GameRegistry.registerItem(GoldQuiver, "rediscovered_goldquiver");
        GameRegistry.addShapelessRecipe(new ItemStack(GoldQuiver, 1), new Object[]{Quiver, Items.golden_chestplate});
        GameRegistry.addShapedRecipe(new ItemStack(Items.golden_chestplate), "Q", 'Q', GoldQuiver);
        
        GameRegistry.registerItem(IronQuiver, "rediscovered_ironquiver");
        GameRegistry.addShapelessRecipe(new ItemStack(IronQuiver, 1), new Object[]{Quiver, Items.iron_chestplate});
        GameRegistry.addShapedRecipe(new ItemStack(Items.iron_chestplate), "Q", 'Q', IronQuiver);
        
        GameRegistry.registerItem(DiamondQuiver, "rediscovered_diamondquiver");
        GameRegistry.addShapelessRecipe(new ItemStack(DiamondQuiver, 1), new Object[]{Quiver, Items.diamond_chestplate});
        GameRegistry.addShapedRecipe(new ItemStack(Items.diamond_chestplate), "Q", 'Q', DiamondQuiver);
        
        GameRegistry.registerItem(LeatherChainHelmet, "rediscovered_leatherchainhelmet");
        GameRegistry.addShapedRecipe(new ItemStack(LeatherChainHelmet), "L", "C", 'L', Items.leather_helmet, 'C', Items.chainmail_helmet);
        
        GameRegistry.registerItem(LeatherChainChest, "rediscovered_leatherchainchest");
        GameRegistry.addShapedRecipe(new ItemStack(LeatherChainChest), "L", "C", 'L', Items.leather_chestplate, 'C', Items.chainmail_chestplate);
        
        GameRegistry.registerItem(LeatherChainLegs, "rediscovered_leatherchainlegs");
        GameRegistry.addShapedRecipe(new ItemStack(LeatherChainLegs), "L", "C", 'L', Items.leather_leggings, 'C', Items.chainmail_leggings);
        
        GameRegistry.registerItem(LeatherChainBoots, "rediscovered_leatherchainboots");
        GameRegistry.addShapedRecipe(new ItemStack(LeatherChainBoots), "L", "C", 'L', Items.leather_boots, 'C', Items.chainmail_boots);
        
        GameRegistry.registerBlock(Sponge, "Fake Sponge");
        
        GameRegistry.registerBlock(Rose, "Rose");
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 1), new Object[]{new ItemStack(Rose)});
        GameRegistry.registerBlock(EmptyRoseBush, "EmptyRoseBush");
        GameRegistry.registerBlock(EmptyRoseBushTop, "EmptyRoseBushTop");
        GameRegistry.registerBlock(Peony, "Peony");
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 9), new Object[]{new ItemStack(Peony)});
        GameRegistry.registerBlock(EmptyPeonyBush, "EmptyPeonyBush");
        GameRegistry.registerBlock(EmptyPeonyBushTop, "EmptyPeonyBushTop");
        
        GameRegistry.registerBlock(CherryLog, "CherryLog");
        OreDictionary.registerOre("woodLog", CherryLog);
        OreDictionary.registerOre("logWood", CherryLog);
        
        GameRegistry.registerBlock(CherryPlank, "CherryPlank");
        OreDictionary.registerOre("plankWood", CherryPlank);
        GameRegistry.addShapelessRecipe(new ItemStack(CherryPlank, 4), new Object[]
                {
                    CherryLog
                });
        
        OreDictionary.registerOre("woodSlab", CherrySlab);
        OreDictionary.registerOre("slabWood", CherrySlab);
        GameRegistry.addRecipe(new ItemStack(CherrySlab, 6), new Object[]
                {
                    "SSS", 'S', CherryPlank
                });
        OreDictionary.registerOre("woodSlab", CherryDoubleSlab);
        OreDictionary.registerOre("slabWood", CherryDoubleSlab);
        
        GameRegistry.registerBlock(CherryLeaves, "CherryLeaves");
        OreDictionary.registerOre("leavesTree", CherryLeaves);
        
        GameRegistry.registerBlock(CherrySapling, "CherrySapling");
        OreDictionary.registerOre("saplingTree", CherrySapling);
        
        GameRegistry.registerBlock(CherryStairs, "CherryStairs");
        OreDictionary.registerOre("stairWood", CherryStairs);
        OreDictionary.registerOre("woodStair", CherryStairs);
        GameRegistry.addRecipe(new ItemStack(CherryStairs, 4), new Object[]
                {
                    "  S", " SS", "SSS", 'S', CherryPlank
                });
        
        GameRegistry.registerBlock(CryingObsidian, "CryingObsidian");
        GameRegistry.addRecipe(new ItemStack(CryingObsidian, 1), new Object[]
                {
                    " L ", "LOL", " L ", 'L', new ItemStack(Items.dye, 1, 4), 'O', Blocks.obsidian
                });
        
        GameRegistry.registerItem(SpikesItem, "rediscovered_spikesitem");
        GameRegistry.registerBlock(Spikes, "SpikesFloor");
        GameRegistry.registerBlock(SpikesSide, "SpikesSide");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(SpikesItem, 1), true, new Object[]
                {
                    "   ", "I I", "LLL", 'L', "plankWood", 'I', Items.iron_ingot
                }));
        
        GameRegistry.registerItem(Scarecrow, "rediscovered_scarecrow");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Scarecrow, 1), true, new Object[]
                {
                    " P ", "SHS", " S ", 'S', Items.stick, 'H', Blocks.hay_block, 'P', Blocks.pumpkin
                }));
        
        if(DreamBedEnabled){
        	GameRegistry.registerItem(DreamBedItem, "rediscovered_dreambeditem");
        	GameRegistry.registerBlock(DreamBed, "DreamBed");
        	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DreamBedItem, 1), true, new Object[]
                    {
                        "   ", "WWW", "OGO", 'G', Blocks.glowstone, 'W', new ItemStack(Blocks.wool, 1, 11), 'O', "plankWood"
                    }));
        }
        else{
        	GameRegistry.registerItem(DreamPillow, "rediscovered_dreampillow");
        	if(DreamPillowRecipe){
        		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DreamPillow, 1), true, new Object[]
                        {
                            "WWW", "WGW", "WWW", 'G', Items.glowstone_dust, 'W', new ItemStack(Blocks.wool, 1, 11)
                        }));
        	}
        }
        
        
        GameRegistry.registerBlock(DragonEggRed, "DragonEggRed");
        
        GameRegistry.registerBlock(RubyOre, "RubyOre");
        OreDictionary.registerOre("oreRuby", RubyOre);
        GameRegistry.registerBlock(RubyBlock, "RubyBlock");
        
        GameRegistry.registerItem(gemRuby, "rediscovered_gemruby");
        OreDictionary.registerOre("gemRuby", gemRuby);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RubyBlock, 1), true, new Object[]
                {
                    "LLL", "LLL", "LLL", 'L', "gemRuby"
                }));
        GameRegistry.addSmelting(RubyOre, new ItemStack (gemRuby), 1);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gemRuby, 9), true, new Object[]
                {
                    "L", 'L', RubyBlock
                }));
        
        GameRegistry.registerItem(Nausea, "rediscovered_nausea");
        GameRegistry.addShapelessRecipe(new ItemStack(Nausea, 1), new Object[]
                {
                    Items.rotten_flesh, new ItemStack(Items.potionitem, 1, 16)
                });
        
        GameRegistry.registerItem(Blindness, "rediscovered_blindness");
        GameRegistry.addShapelessRecipe(new ItemStack(Blindness, 1), new Object[]
                {
                    Items.poisonous_potato, new ItemStack(Items.potionitem, 1, 16)
                });
        
        GameRegistry.registerItem(Mining, "rediscovered_mining");
        GameRegistry.addShapelessRecipe(new ItemStack(Mining, 1), new Object[]
                {
                    Items.apple, new ItemStack(Items.potionitem, 1, 16)
                });
        
        GameRegistry.addRecipe(new ItemStack(DirtSlab, 6), new Object[]
                {
                    "DDD", 'D', Blocks.dirt
                });
        
        GameRegistry.registerBlock(LockedChest, "lockedchest");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LockedChest, 1), new Object[]
                {
                    "WWW", "WIW", "WWW", 'I', Items.iron_ingot, 'W', "plankWood"
                }));
        
        GameRegistry.registerBlock(Chair, "Chair");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Chair, 1), true, new Object[]
                {
                    "L  ", "LLL", "L L", 'L', "plankWood"
                }));
        
        GameRegistry.registerBlock(Table, "Table");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Table, 1), true, new Object[]
                {
                    "   ", "LLL", "L L", 'L', "plankWood"
                }));
        
        GameRegistry.registerBlock(Lectern, "Lectern");
        GameRegistry.registerBlock(LecternOpen, "LecternOpen");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Lectern, 1), true, new Object[]
                {
                    " B ", " L ", "LGL", 'L', "plankWood", 'G', Items.gold_ingot, 'B', Items.book
                }));
        
        GameRegistry.registerItem(ItemGear, "rediscovered_itemgear");
        GameRegistry.registerBlock(GearFloor, "GearFloor");
        GameRegistry.registerBlock(GearWall, "GearWall");
        GameRegistry.addRecipe(new ItemStack(ItemGear, 8), new Object[]
                {
                    " I ", "III", " I ", 'I', Items.iron_ingot
                });
        
        GameRegistry.registerItem(ItemLantern, "rediscovered_itemlantern");
        GameRegistry.registerBlock(Lantern, "LanternInvis");
        GameRegistry.registerBlock(LanternPhys, "Lantern");
        GameRegistry.addRecipe(new ItemStack(ItemLantern, 1), new Object[]
                {
                    " I ", " S ", " I ", 'I', Items.iron_ingot, 'S', Items.glowstone_dust
                });
        
        
        DimensionManager.registerProviderType(DimID, WorldProviderHeaven.class, true);
        DimensionManager.registerDimension(DimID, DimID);
        
        if(EnableRubyOre)
        {
        	GameRegistry.registerWorldGenerator(new WorldGeneratorRuby(), 0);
        }
        if(EnableSpongeGenerate)
        {
        	GameRegistry.registerWorldGenerator(new WorldGeneratorSponge(), 0);
        }
        if(EnablePigmanVillages)
        {
        	GameRegistry.registerWorldGenerator(new WorldGeneratorPigmanVillage(), 0);
        }
        if(EnableDungeonLoot)
        {
        	ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST ).addItem(new WeightedRandomChestContent(new ItemStack(ItemLantern), 1, 1, 15));
        	ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(ItemLantern), 1, 1, 15));
        }
    	
    	
    	GameRegistry.registerTileEntity(TileEntityChair.class, "tileentitychair");
    	GameRegistry.registerTileEntity(TileEntityTable.class, "tileentitytable");
    	GameRegistry.registerTileEntity(TileEntityLectern.class, "tileentitylectern");
    	GameRegistry.registerTileEntity(TileEntityLecternOpen.class, "tileentitylecternopen");
    	GameRegistry.registerTileEntity(TileEntityRedEgg.class, "tileentityegg");
    	GameRegistry.registerTileEntity(TileEntityLockedChest.class, "tileentitylockedchest");
    	GameRegistry.registerTileEntity(TileEntitySpikes.class, "tileentityspikes");
    	GameRegistry.registerTileEntity(TileEntitySpikesSide.class, "tileentityspikesside");    
    }
    
    public static Block registerBlock(Block block, Class<? extends ItemBlock> itemBlockClass, Object... constructorArgs)
    {
        GameRegistry.registerBlock(block, itemBlockClass, block.getUnlocalizedName(), block.getUnlocalizedName(), constructorArgs);
        
        return block;
    }
    
    public static boolean hasLitLanternOnHotbar(InventoryPlayer inv)
    {
        for (int i = 0; i < 9; i++)
        {
            ItemStack item = inv.mainInventory[i];

            if (item != null && item.getItem() == ItemLantern)
            {
                return true;
            }
        }

        return false;
    }
    
    public static DamageSource causeParrowDamage(EntityParrow par0EntityDarkArrow, Entity par1Entity)
    {
    	return (new EntityDamageSourceIndirect("parrow", par0EntityDarkArrow, par1Entity)).setProjectile();
    }   

    public String getVersion()
    {
        return "1.7.2";
    }

	
	public String getModName() 
	{
		return "rediscovered";
	}
 
}