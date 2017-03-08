package com.gorzoid.dcpu;

import li.cil.oc.api.internal.TextBuffer;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;

import com.gorzoid.dcpu.devices.Debugger;
import com.gorzoid.dcpu.devices.IDevice;
import com.gorzoid.dcpu.devices.Screen;
import com.gorzoid.dcpu.maptriggers.IMemoryTrigger;

enum CPUResult
{
	SLEEP,
	ERROR,
	DONE
}

class FakeBuffer implements TextBuffer{

	@Override
	public boolean canUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node node() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onConnect(Node node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnect(Node node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnergyCostPerTick(double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getEnergyCostPerTick() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPowerState(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getPowerState() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMaximumResolution(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaximumWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximumHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAspectRatio(double width, double height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getAspectRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setResolution(int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setViewport(int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getViewportWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewportHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaximumColorDepth(ColorDepth depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ColorDepth getMaximumColorDepth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setColorDepth(ColorDepth depth) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ColorDepth getColorDepth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPaletteColor(int index, int color) {
		// TODO Auto-generated method stub
		System.out.printf("Palette[%d] set to #%X\n",index,color);
	}

	@Override
	public int getPaletteColor(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setForegroundColor(int color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setForegroundColor(int color, boolean isFromPalette) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getForegroundColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isForegroundFromPalette() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBackgroundColor(int color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBackgroundColor(int color, boolean isFromPalette) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBackgroundColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isBackgroundFromPalette() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void copy(int column, int row, int width, int height,
			int horizontalTranslation, int verticalTranslation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fill(int column, int row, int width, int height, char value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(int column, int row, String value, boolean vertical) {
		// TODO Auto-generated method stub
		System.out.printf("[%d,%d] set to \"%s\"\n",column,row,value);
	}

	@Override
	public char get(int column, int row) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getForegroundColor(int column, int row) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isForegroundFromPalette(int column, int row) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getBackgroundColor(int column, int row) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isBackgroundFromPalette(int column, int row) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void rawSetText(int column, int row, char[][] text) {
		// TODO Auto-generated method stub
		System.out.printf("Text printed %d,%d #1 = %d \n",column,row,(byte)text[0][0]);
	}

	@Override
	public void rawSetForeground(int column, int row, int[][] color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rawSetBackground(int column, int row, int[][] color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean renderText() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int renderWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int renderHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRenderingEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRenderingEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void keyDown(char character, int code, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyUp(char character, int code, EntityPlayer player) {
	}
	@Override
	public void clipboard(String value, EntityPlayer player) {
	}
	@Override
	public void mouseDown(double x, double y, int button, EntityPlayer player) {
	}
	@Override
	public void mouseDrag(double x, double y, int button, EntityPlayer player) {
	}
	@Override
	public void mouseUp(double x, double y, int button, EntityPlayer player) {
	}
	@Override
	public void mouseScroll(double x, double y, int delta, EntityPlayer player) {
	}
}

public class DCPU {
	
	public short gRegs[];
	public static final int A=0,B=1,C=2,X=3,Y=4,Z=5,I=6,J=7;
	public short pc = 0;
	public short sp = 0;
	public short ex = 0;
    public short ia = 0;
    
    private short ram[];
    
    public short ramRead(short addr){
        
    	for(IMemoryTrigger t : memoryTriggers)
    		if(t.isInRegion(this,addr))
    			return t.read(addr);
    	
    	return ram[(int)addr & 0xffff];
    }
    
    public void ramWrite(short addr,short value){
        
    	for(IMemoryTrigger t : memoryTriggers)
    		if(t.isInRegion(this,addr))
    			t.write(addr,value);
    	
    	ram[(int)addr & 0xffff] = value;
    }
    
    
    
    private LinkedList<IMemoryTrigger> memoryTriggers;
    
    public void addMemTrigger(IMemoryTrigger t,short addr)
    {
    	t.init(this, addr);
    	memoryTriggers.add(t);
    }
    
    public void removeMemTrigger(IMemoryTrigger t)
    {
    	memoryTriggers.remove(t);
    }
    
    public boolean interrupt_queuing;
    private LinkedList<Short> interrupts;
    
    int cycles;
    
    boolean skip;
    
    LinkedList<IDevice> devices;
    
    public DCPU(byte[] code)
    {
    	gRegs = new short[8];
    	ram = new short[0x10000];
    	
    	for(int i = 0; i+1 < code.length;i+=2)
    	{
    		ram[i/2] = (short) ((code[i] << 8) | (code[i+1] & 0xff));
    		System.out.printf("0x%x ",ram[i/2]);
    	}
    	
    	devices = new LinkedList<IDevice>();
    	devices.add(new Debugger());
    	//devices.add(new Screen(new FakeBuffer()));
    	interrupts = new LinkedList<Short>();
    	memoryTriggers = new LinkedList<IMemoryTrigger>();
    }
    
    public void interrupt(short msg)
    {
    	System.out.println("DCPU Interrupt: "+ msg);
		interrupts.add(msg);
    }
    
    public void writeState(NBTTagCompound nbt)
    {
    	NBTTagList list = new NBTTagList();
    	
    	//Registers
    	for(int i = 0; i < 8; i++)
    		list.appendTag(new NBTTagShort(gRegs[i]));
    	
    	for(short i : new short[]{pc,sp,ex,ia})
    		list.appendTag(new NBTTagShort(i));
    	
    	for(int i = 0; i < 0x10000; i++)
    		list.appendTag(new NBTTagShort(ram[i]));
    	
    	for(short msg : interrupts)
    		list.appendTag(new NBTTagShort(msg));
    	nbt.setTag("state", list);
    }
    
    public void readState(NBTTagCompound nbt)
    {
    	NBTTagList list = nbt.getTagList("state", 2);
    	for(int i = 0; i < 8; i++)
    		gRegs[i] = ((NBTTagShort)list.removeTag(0)).func_150289_e();
    	
    	pc = ((NBTTagShort)list.removeTag(0)).func_150289_e();
    	sp = ((NBTTagShort)list.removeTag(0)).func_150289_e();
    	ex = ((NBTTagShort)list.removeTag(0)).func_150289_e();
    	ia = ((NBTTagShort)list.removeTag(0)).func_150289_e();
    	
    	for(int i = 0; i < 0x10000; i++)
    		ram[i] = ((NBTTagShort)list.removeTag(0)).func_150289_e();
    	
    	interrupt_queuing = ((NBTTagShort)list.removeTag(0)).func_150289_e() != 0;
    	
    	while(list.tagCount() > 0)
    		interrupts.add(((NBTTagShort)list.removeTag(0)).func_150289_e());
    }
    
    public short nextWord(){
    	cycles--;
    	return ramRead(pc++);
    }
    
    public short pop()
    {
    	return ramRead(sp++);
    }
    
    public void push(short value)
    {
    	ramWrite(--sp,value);
    }
    
    
    short last;
    void step() throws Exception
    {
    	if(!interrupt_queuing && interrupts.size() > 0 && ia != 0)
    	{
    		System.out.println("Executing DCPU interrupt.");
    		push(pc);
    		push(gRegs[DCPU.A]);
    		gRegs[DCPU.A] = interrupts.poll();
    		pc = ia;
    		
    	}
    	short word = nextWord();
    	Instruction inst = new Instruction(word);
    	
    	inst.execute(this,last==word);
    	last = word;
    	
    }
    
    public CPUResult run(int allocatedCycles)
    {
    	
    	if(interrupts.size() > 256)
    	{
    		System.out.println("Interrupts overload");
    		return CPUResult.ERROR;
    	}
    	
    	cycles+=allocatedCycles;
    	try{
	    	while(cycles > 0)
	    	{
	    		step();
	    	}
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    		e.printStackTrace();
    		return CPUResult.ERROR;
    	}
    	return CPUResult.SLEEP;
    }
    
}
