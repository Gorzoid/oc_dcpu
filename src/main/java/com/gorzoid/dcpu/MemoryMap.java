package com.gorzoid.dcpu;

public class MemoryMap {
	
	short index;
	DCPU mappedCPU;
	
	public MemoryMap(DCPU cpu, short offset)
	{
		mappedCPU = cpu;
		index = offset;
		
	}
	
	public void write(short addr,short value)
	{
		mappedCPU.ramWrite((short) (addr+index), value);
	}
	public short read(short addr)
	{
		
		return mappedCPU.ramRead((short) (addr+index));
	}
}
