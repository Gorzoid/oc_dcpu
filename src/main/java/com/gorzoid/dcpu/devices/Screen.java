package com.gorzoid.dcpu.devices;

import li.cil.oc.api.internal.TextBuffer;
import li.cil.oc.api.internal.TextBuffer.ColorDepth;

import com.gorzoid.dcpu.DCPU;
import com.gorzoid.dcpu.maptriggers.IMemoryTrigger;
import com.gorzoid.dcpu.maptriggers.MemScreen;

public class Screen implements IDevice {
	
	enum InterruptCodes{
		MEM_MAP_SCREEN,
		MEM_MAP_FONT,
		MEM_MAP_PALETTE,
		SET_BORDER_COLOR,
	}
	InterruptCodes[] codes = InterruptCodes.values();
	
	static final DeviceInfo INFO = new DeviceInfo(0x7349f615,0x1802,0x1c6c8b36);
	
	private TextBuffer screen_buffer;
	
	private MemScreen mem_screen;
	private IMemoryTrigger mem_font;
	private IMemoryTrigger mem_palette;
	
	public Screen(TextBuffer screen)
	{
		screen_buffer = screen;
		screen.setMaximumResolution(32, 12);
		screen.setResolution(32, 12);
		screen.setMaximumColorDepth(ColorDepth.FourBit);
		screen.setColorDepth(ColorDepth.FourBit);
		screen.setPaletteColor(1, 0xffbf00);
		screen.setViewport(32, 12);
		screen.setPowerState(true);
	}
	
	@Override
	public DeviceInfo getInfo() {
		return INFO;
	}

	@Override
	public void interrupt(DCPU cpu) {
		
		System.out.printf("Recieved Interrupt: A=%d\n",cpu.gRegs[DCPU.A]);
		
		switch(codes[cpu.gRegs[DCPU.A]]){
		case MEM_MAP_SCREEN:
			cpu.addMemTrigger(new MemScreen(this, 32, 12),cpu.gRegs[DCPU.B]);
			break;
		case MEM_MAP_FONT:
			// not implemented
			break;
		case MEM_MAP_PALETTE:
			break;
		case SET_BORDER_COLOR:
			break;
		default:
			break;
			
		}
	}
	
	private void colourOfPallette(int[][] col)
	{
		for(int i = 0; i < col.length;i++)
			for(int j = 0; j < col[i].length;j++)
				col[i][j] = screen_buffer.getPaletteColor(col[i][j]);
	}
	
	public void refreshScreen(char[][] text, int[][] fg, int[][] bg)
	{
		
		colourOfPallette(fg);
		colourOfPallette(bg);
		
		screen_buffer.rawSetText(0,0,text);
		screen_buffer.rawSetForeground(0,0,fg);
		screen_buffer.rawSetBackground(0,0,bg);
		
	}
	
	public void setPixel(int x, int y, char c, int fg, int bg)
	{
		screen_buffer.set(x,y,String.valueOf(c),false);
	}

	public short getPixel(int x, int y) {
		char c = screen_buffer.get(x,y);
		byte colour = (byte) ((screen_buffer.getBackgroundColor(x,y) & 0x7) | (screen_buffer.getForegroundColor(x, y) & 0x7) << 4);
		
		return (short) (c & 0x7 | colour << 8);
	}

	public int getPaletteColour(int i) {
		return screen_buffer.getPaletteColor(i);
	}
	
	public void setPaletteColour(int i, int colour) {
		screen_buffer.setPaletteColor(i,colour);
	}

}
