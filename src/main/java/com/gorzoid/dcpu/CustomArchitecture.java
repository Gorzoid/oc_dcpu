package com.gorzoid.dcpu;

import java.util.Map;

import com.gorzoid.dcpu.devices.Keyboard;
import com.gorzoid.dcpu.devices.Screen;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import li.cil.oc.api.internal.TextBuffer;
import li.cil.oc.api.machine.Machine;
import li.cil.oc.api.machine.Architecture;
import li.cil.oc.api.machine.ExecutionResult;
import li.cil.oc.api.machine.Signal;
import li.cil.oc.server.network.Network;

@Architecture.Name("DCPU")
public class CustomArchitecture implements Architecture {
	
	DCPU cpu;
	Machine machine;
	
	Keyboard keyboard;
	
	public CustomArchitecture(Machine m)
	{
		machine = m;
	}
	
	@Override
	public boolean isInitialized() {
		// TODO Check if initialized
		return cpu != null;
	}

	@Override
	public boolean recomputeMemory(Iterable<ItemStack> components) {
		// TODO Should you check for memory?
		return true;
	}

	@Override
	public boolean initialize() {
		Map<String, String> m = this.machine.components();
		
		byte[] code = null;
		String screenAddr = null;
		
        for(String k: m.keySet()) {
            String v = m.get(k);
            switch(v)
            {
            case "eeprom":
        		try {
					code = (byte[])machine.invoke(k, "get", new Object[]{})[0];
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
        		if(code == null || code.length <= 1)
                	return false;
            	break;
            	
            case "screen":
            	screenAddr = k;//cpu.devices.add(new Screen((TextBuffer)machine.node().network().node(k).host()));
            	break;
            	
            case "keyboard":
            	keyboard = new Keyboard(machine,k);
            	break;
            
            }
        }
        
        cpu = new DCPU(code);
        if(screenAddr != null)
        {
        	cpu.devices.add(new Screen((TextBuffer)machine.node().network().node(screenAddr).host()));
        }
        if(keyboard != null)
        {
        	cpu.devices.add(keyboard);
        }
		return true;
	}

	@Override
	public void close() {
		
	}

	@Override
	public void runSynchronized() {
		// TODO Auto-generated method stub
		
	}
	
	
	final int STEPS = 50;
	@Override
	public ExecutionResult runThreaded(boolean isSynchronizedReturn) {
		CPUResult ret = cpu.run(STEPS);
		switch(ret)
		{
		case DONE:
			return new ExecutionResult.Shutdown(false);
		case ERROR:
			return new ExecutionResult.Error("Error check logs");
		case SLEEP:
			return new ExecutionResult.Sleep(5);
		default:
			return new ExecutionResult.Error("Unexpected result: "+ret);
		}
}

	@Override
	public void onConnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(NBTTagCompound nbt) {
		cpu.readState(nbt);
	}

	@Override
	public void save(NBTTagCompound nbt) {
		cpu.writeState(nbt);
	}

	@Override
	public void onSignal() {
		Signal signal = machine.popSignal();
		System.out.println(signal.name());
		Object[] args = signal.args();
		switch(signal.name())
		{
		case "key_down":
			
			double key = (Double) args[1];
			double code = (Double) args[2];
			
			keyboard.keyPressed((char)key,(int)code,true);
			break;
			
		case "key_up":
			double key2 = (Double) args[1];
			double code2 = (Double) args[2];
			
			keyboard.keyPressed((char)key2,(int)code2,false);
			break;
			
		}
	}

}
