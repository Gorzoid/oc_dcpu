package com.gorzoid.dcpu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.gorzoid.dcpu.devices.Screen;

public class Standalone {
	
	static final char[] regs = {'A','B','C','X','Y','Z','I','J'};
	
	public static void updateInfo(DCPU cpu)
	{
		//System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nRegisters:"); //cls
		for(int i = 0;i < 8; i++)
			System.out.printf("\t%c[%d]",regs[i],cpu.gRegs[i]);
		System.out.println();
	}
	
	public static void main(String[] args)
	{
    	DCPU cpu;
    	try {
			cpu = new DCPU(Files.readAllBytes(Paths.get("eeprom")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
    	cpu.devices.add(new Screen(new FakeBuffer()));
    	System.out.printf("[1234] = 0x%x\n",cpu.ramRead((short) 1234));
    	cpu.run(10);
    	updateInfo(cpu);
	}
}
