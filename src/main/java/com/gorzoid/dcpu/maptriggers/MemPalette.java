package com.gorzoid.dcpu.maptriggers;

import com.gorzoid.dcpu.DCPU;
import com.gorzoid.dcpu.devices.Screen;

public class MemPalette implements IMemoryTrigger
{
	
	DCPU mapped_cpu;
	short address;
	Screen screen;
	
	public MemPalette(Screen scr)
	{
		screen = scr;
		screen.setPaletteColour(0, 0xffbf00);
		screen.setPaletteColour(1, 0x0ffb15);
	}
	
	@Override
	public boolean isInRegion(DCPU cpu, short addr) {
		return cpu == mapped_cpu && addr >= address && addr < address + 16;
	}

	@Override
	public short read(short addr) {
		return (short) screen.getPaletteColour(addr-address);
	}

	@Override
	public void write(short addr, short value) {
		screen.setPaletteColour(addr-address, value);
	}

	@Override
	public void init(DCPU cpu, short addr) {
		address = addr;
		mapped_cpu = cpu;
		for(int i = 0; i < 16; i++)
			screen.setPaletteColour(i, cpu.ramRead((short) (addr+i)));
	}
	
}