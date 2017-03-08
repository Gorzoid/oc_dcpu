package com.gorzoid.dcpu.maptriggers;

import com.gorzoid.dcpu.DCPU;
import com.gorzoid.dcpu.devices.Screen;

public class MemScreen implements IMemoryTrigger {
	
	DCPU mapped_cpu;
	Screen screen;
	int width,height;
	short address;
	
	public MemScreen(Screen scr, int width, int height)
	{
		screen = scr;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void init(DCPU cpu,short addr)
	{
		mapped_cpu = cpu;
		address = addr;
		
		System.out.printf("Memmap at addr: %d First = %X\n",addr,cpu.ramRead((short) (0)));
		
		int[][] fg = new int[height][width];
		int[][] bg = new int[height][width];
		char[][] text = new char[height][width];
		for(int x = 0; x < width;x++)
		{
			for(int y = 0; y < height;y++)
			{
				short pixel = cpu.ramRead((short) (addr + x + y*width));
				if(pixel != 0) System.out.printf("Pixel[%d,%d] = 0x%X\n",x,y,pixel);
				text[y][x] = (char) (pixel & 0x7f);
				fg[y][x] = pixel >> 8 & 0xf;
				bg[y][x] = pixel >> 12 & 0xf;
			}
		}
		
		
		screen.refreshScreen(text,fg,bg);
	}
	
	@Override
	public boolean isInRegion(DCPU cpu, short addr) {
		return cpu == mapped_cpu && addr >= address && addr < address + width*height;
	}

	@Override
	public short read(short addr) {
		addr -= address;
		int x = addr % width, y = addr / width;
		
		return screen.getPixel(x,y);
	}

	@Override
	public void write(short addr, short value) {
		addr -= address;
		int x = addr % width, y = addr / width;
		System.out.printf("Set Pixel[%d,%d] = 0x%X\n",x,y,value);
		screen.setPixel(x,y,(char) (value & 0x7f),value >> 8 & 0xf,value >> 12 & 0xf);
	}

}
