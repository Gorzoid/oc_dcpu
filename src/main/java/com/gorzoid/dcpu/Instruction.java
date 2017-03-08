package com.gorzoid.dcpu;

import com.gorzoid.dcpu.devices.IDevice;

public class Instruction {
	
	public enum Opcode {
		NOP0,
		SET,
		ADD,
		SUB,
		MUL,
		MLI,
		DIV,
		DVI,
		MOD,
		MDI,
		AND,
		BOR,
		XOR,
		SHR,
		ASR,
		SHL,
		IFB,
		IFC,
		IFE,
		IFN,
		IFG,
		IFA,
		IFL,
		IFU,
		NOP1,NOP2,
		ADX,
		SBX,
		NOP3,NOP4,
		STI,
		STD,
		NOP5,
		JSR,
		NOP6,NOP7,NOP8,NOP9,NOP10,NOP11,
		INT,
		IAG,
		IAS,
		RFI,
		IAQ,
		NOP12,NOP13,NOP14,
		HWN,
		HWQ,
		HWI
	}
	private static final Opcode[] OPCODES = Opcode.values();
	
	class Operand{
		
		public String operandStr;
		DCPU cpu;
		int mode = 0; // 0 register, 1 ram, 2 literal
		int value;
		
		public Operand(DCPU cpu, char data,boolean isArgB)
		{
			this.cpu = cpu;
			operandStr = isArgB ? ", " : "";
			switch1:
			switch(data >> 3)
			{
			case 0:
				value = data & 0x7;
				operandStr += "r"+value;
				break;
			case 1:
				value = cpu.gRegs[data & 0x7];
				mode = 1;
				operandStr += "["+Integer.toHexString(value)+"]";
				break;
			case 2:
				value = cpu.gRegs[data & 0x7] + cpu.nextWord();
				mode = 1;
				operandStr += "["+Integer.toHexString(value)+"]";
				break;
			case 3:
				switch(data & 0x7)
				{
				case 0:
					if(cpu.skip) return;
					mode = 1;
					if(isArgB)
						value = cpu.sp--;
					else
						value = cpu.sp++;
					operandStr += isArgB ? "PUSH" : "POP";
					break switch1;
				case 1:
					mode = 1;
					value = cpu.sp;
					operandStr += "PEEK";
					break switch1;
				case 2:
					mode = 1;
					value = cpu.sp + cpu.nextWord();
					operandStr += "PICK["+Integer.toHexString(value)+"]";
					break switch1;
				case 6:
					mode = 1;
					value = cpu.nextWord();
					operandStr += "["+Integer.toHexString(value) + "]";
					break switch1;
				case 7:
					mode = 2;
					value = cpu.nextWord();
					break switch1;
				default:
					mode = data & 0x7;
				}
				break;
			default: // Literal
				mode = 2;
				value = data - 33;
				operandStr += "#"+value;
			}
			
		}
		
		public short get(){

			switch(mode)
			{
			case 0:
				if(value < 8)
				{
					return cpu.gRegs[value];
				}
				return 0;
			case 1:
				return cpu.ramRead((short) value);
			case 2:
				return (short) value;
			case 3:
				return cpu.sp;
			case 4:
				return cpu.pc;
			case 5:
				return cpu.ex;
			}
			return 0;
		}
		
		public void set(short val)
		{
			switch(mode)
			{
			case 0:
				if(value < 8)
					cpu.gRegs[value] = val;
				return;
			case 1:
				cpu.ramWrite((short) value,val);
				return;
			case 3:
				cpu.sp = val;
				return;
			case 4:
				cpu.pc = val;
				return;
			case 5:
				cpu.ex = val;
			
			}
		}
		
	}
	
	private short instruction;
	
	private char opcode;
	private boolean special;
	
	private char operand_a;
	private char operand_b;
	
	public Instruction(short word)
	{
		instruction = word;
		opcode = (char) (word & 0x1f);
		operand_b = (char) ((word >> 5) & 0x1f);
		operand_a = (char) ((word >> 10) & 0x3f);
		
		if(opcode == 0)
		{
			special = true;
			opcode = operand_b;
			operand_b = 0;
		}
		
	}
	
	public Opcode getOpcode(){
		if(opcode >= OPCODES.length) return Opcode.NOP1;
		if(special)
			return OPCODES[opcode+32];
		return OPCODES[opcode];
	}
	
	public void execute(DCPU cpu,boolean debug_print) throws Exception{
		
		Operand a = new Operand(cpu,operand_a,false), b = special ? null : new Operand(cpu,operand_b,true);
		
		if(cpu.skip)
		{
			if(opcode > 0xf && opcode < 0x18)
				cpu.cycles--;
			else
				cpu.skip = false;
			return;
		}
		
		int res = 0;
		
		Opcode op = getOpcode();
		/*if(debug_print)
		{	
			if(special)
				System.out.printf("Instruction[0x%x] %s %s\n",instruction, op.toString(), a.operandStr);
			else
				System.out.printf("Instruction[0x%x] %s %s, %s\n",instruction, op.toString(), b.operandStr, a.operandStr);
		}*/
			
		switch(op)
		{
		case SET:
			short i = a.get();
			System.out.printf("Setting %s to %d\n",b.operandStr,(int)i);
			b.set(i);
			return;
		case ADD:
			res = a.get() + b.get();
			break;
		case SUB:
			res = b.get() - a.get();
			break;
		case MUL:
		case MLI:
			res = b.get() * a.get();
			break;
		case DIV:
		case DVI:
			if (a.get() == 0)
				break;
			res = b.get() / a.get();
			cpu.ex = (short) ((b.get() << 16) / a.get());
			b.set((short)res);
		case MOD:
		case MDI:
			res = a.get() == 0 ? 0 : b.get() % a.get();
			b.set((short)res);
		case AND:
			res = b.get() & a.get();
			b.set((short)res);
		case BOR:
			res = b.get() | a.get();
			b.set((short)res);
		case XOR:
			res = b.get() ^ a.get();
			b.set((short)res);
		case SHR:
		case ASR:
			res = b.get() >> a.get();
			b.set((short)res);
		case SHL:
			res = b.get() <<  a.get();
			b.set((short)res);
		case IFB:
			if((b.get()&a.get()) == 0)
				return;
			cpu.skip = true;
		case IFC:
			if((b.get()&a.get()) != 0)
				return;
			cpu.skip = true;
		case IFE:
			if(b.get() == a.get())
				return;
			cpu.skip = true;
		case IFN:
			if(b.get() != a.get())
				return;
			cpu.skip = true;
		case IFG:
		case IFA:
			if( b.get() >  a.get())
				return;
			cpu.skip = true;
		case IFL:
		case IFU:
			if(b.get() < a.get())
				return;
			cpu.skip = true;
		case ADX:
			res = b.get()+a.get()+cpu.ex;
			break;
		case SBX:
			res = b.get()-a.get()+cpu.ex;
			break;
			
		case STI:
			b.set(a.get());
			cpu.gRegs[DCPU.I]++;
			cpu.gRegs[DCPU.J]++;
			return;
		case STD:
			b.set(a.get());
			cpu.gRegs[DCPU.I]--;
			cpu.gRegs[DCPU.J]--;
			return;
			
		case JSR:
			cpu.push(cpu.pc);
			cpu.pc = a.get();
			break;
			
		case INT:
			cpu.push(cpu.pc);
			cpu.push(cpu.gRegs[DCPU.A]);
			cpu.pc = cpu.ia;
			cpu.gRegs[DCPU.A] = a.get();
			break;
		case IAG:
			a.set(cpu.ia);
			break;
		case IAS:
			cpu.ia = a.get();
			return;
		case RFI:
			cpu.interrupt_queuing = false;
			cpu.gRegs[DCPU.A] = cpu.pop();
			cpu.pc = cpu.pop();
			return;
		case IAQ:
			cpu.interrupt_queuing = a.get() != 0;
			return;
			
		case HWN:
			a.set((short) cpu.devices.size());
			return;
		case HWQ:
			int id = a.get();
			if(cpu.devices.size() > id)
			{
				IDevice.DeviceInfo info = cpu.devices.get(a.get()).getInfo();
				cpu.gRegs[DCPU.A] = (short)(info.HWID & 0xffff);
				cpu.gRegs[DCPU.B] = (short)(info.HWID >> 16);
				cpu.gRegs[DCPU.C] = (short)info.hwVersion;
				cpu.gRegs[DCPU.X] = (short)(info.manufacturer & 0xffff);
				cpu.gRegs[DCPU.Y] = (short)(info.manufacturer >> 16);
			}
			return;
		case HWI:
			id = a.get();
			System.out.printf("Hardware interrupt: %d\n",id);
			if(cpu.devices.size() > id)
				cpu.devices.get(id).interrupt(cpu);
			return;
		default:
			throw new Exception("Invalid Opcode: "+op.toString());
		}
		cpu.ex = (short) (res >> 16);
		b.set((short) res);
	
	}
	
}
