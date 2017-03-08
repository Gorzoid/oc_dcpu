package com.gorzoid.dcpu;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import li.cil.oc.api.Items;
import li.cil.oc.api.Machine;

@Mod(modid = DCPUMod.MODID, version = DCPUMod.VERSION)
public class DCPUMod
{
    public static final String MODID = "oc-dcpu";
    public static final String VERSION = "1.0";
    
    public static final byte[] EEPROM = {0x7c,0x20, 0x00,0x01, 0x7c,0x20, 0x00,0x06, 0x7e,0x40, 0x00,0x00, 0x00,0x48, 0x00,0x65, 0x00,0x6c, 0x00,0x6c, 0x00,0x6f, 0x00,0x20, 0x00,0x77, 0x00,0x6f, 0x00,0x72, 0x00,0x6c, 0x00,0x64, 0x00,0x00};
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		Items.registerEEPROM("Test", EEPROM, "Poop".getBytes(), true);
		Machine.add(CustomArchitecture.class);
    }
}
