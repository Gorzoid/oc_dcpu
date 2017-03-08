package com.gorzoid.dcpu.maptriggers;

import com.gorzoid.dcpu.DCPU;

public interface IMemoryTrigger {
	public boolean isInRegion(DCPU cpu,short addr);
	public short read(short addr);
	public void write(short addr,short value);
	public void init(DCPU cpu,short addr);
}
