package com.gorzoid.dcpu.devices;

import com.gorzoid.dcpu.DCPU;

public interface IDevice {
	
	public class DeviceInfo{
		public int HWID;
		public int hwVersion;
		public int manufacturer;
		
		public DeviceInfo(int hwid,int hwVers, int manufact){
			HWID = hwid;
			hwVersion = hwVers;
			manufacturer = manufact;
		}
		
	}
	
	public DeviceInfo getInfo(); 
	public void interrupt(DCPU cpu);
	
}
