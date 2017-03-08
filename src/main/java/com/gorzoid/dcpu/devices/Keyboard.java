package com.gorzoid.dcpu.devices;

import java.util.LinkedList;
import java.util.Queue;

import li.cil.oc.api.machine.Machine;

import com.gorzoid.dcpu.DCPU;

public class Keyboard implements IDevice {
	
	
	Machine machine;
	DCPU cpu;
	String keyboardAddress;
	Queue<Short> keyboardBuffer;
	
	boolean keyStates[];
	
	short int_msg;
	
	public Keyboard(Machine machine,String addr)
	{
		this.machine = machine;
		keyboardAddress = addr;
		keyboardBuffer = new LinkedList<Short>();
		keyStates = new boolean[0x92];
		
	}
	
	@Override
	public DeviceInfo getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void interrupt(DCPU cpu) {
		
		switch(cpu.gRegs[DCPU.A])
		{
		case 0:
			keyboardBuffer.clear();
			break;
		case 1:
			cpu.gRegs[DCPU.C] = keyboardBuffer.isEmpty() ? 0 : keyboardBuffer.poll();
			break;
		case 2:
			//TODO: Save keypresses and set C to 1 if key B is pressed else 0
			short b = cpu.gRegs[DCPU.B];
			cpu.gRegs[DCPU.C] = (short) ((b < keyStates.length && keyStates[b]) ? 1 : 0);
			break;
		case 3:
			int_msg = cpu.gRegs[DCPU.B];
			this.cpu = cpu;
			
		}
		
	}
	
	private static short getCode(char c,int code)
	{
		
		if (c >= 20 && c < 0x80)
			return (short)c;
		
		switch(code)
		{
		case 0x0E: // backspace
			return 0x10;
		case 0x1C: // enter
			return 0x11;
		case 0xD2: // insert
			return 0x12;
		case 0xD3: // delete
			return 0x13;
			
		case 0xC8: // up
			return 0x80;
		case 0xD0: // down
			return 0x81;
		case 0xCB: // left
			return 0x82;
		case 0xCD: // right
			return 0x83;
			
		case 0x2A: // shift
			return 0x90;
		case 0x1D: // control
			return 0x91;
		
		default:
			return 0;
		
		}
		
	}
	
	public void keyPressed(char c, int code,boolean down)
	{
		if(int_msg == 0) return;
		short key = getCode(c,code);
		if (key == 0) return;
		
		keyStates[key] = down;
		
		//if(down)
		{
			keyboardBuffer.add(key);
			cpu.interrupt(int_msg);
		}
		
		
	}

}
