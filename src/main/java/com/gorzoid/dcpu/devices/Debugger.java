package com.gorzoid.dcpu.devices;

import com.gorzoid.dcpu.DCPU;

public class Debugger implements IDevice {
	
	static final DeviceInfo INFO = new DeviceInfo(0x0123,1,0xFFBF00);
	
	@Override
	public DeviceInfo getInfo() {
		
		return INFO;
	}

	@Override
	public void interrupt(DCPU cpu) {
		switch(cpu.gRegs[DCPU.A])
		{
		case 0:
			System.out.printf("DCPU Debug word: %d\n",cpu.gRegs[DCPU.B]);
			break;
		case 1:
			short ptr = cpu.gRegs[DCPU.B];
			String str = "";
			for(char c = (char) (cpu.ramRead(ptr) & 0x7f);c != 0;c = (char) (cpu.ramRead(++ptr) & 0x7f))
				str = str + c;
			System.out.printf("DCPU Debug string: %s\n",str);
		}
	}

}
