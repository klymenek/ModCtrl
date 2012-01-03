package org.rzo.yajsw.os.ms.win.w32;

import java.util.logging.Logger;

import org.rzo.yajsw.os.SystemInformation;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Kernel32;

public class WindowsXPSystemInformation implements SystemInformation
{

	public Logger	_logger;

	public interface MyKernel32 extends Kernel32
	{

		// Method declarations, constant and structure definitions go here

		/** The INSTANCE. */
		MyKernel32	INSTANCE	= (MyKernel32) Native.loadLibrary("kernel32", MyKernel32.class);

		/*
		 * typedef struct _MEMORYSTATUSEX { DWORD dwLength; DWORD dwMemoryLoad;
		 * DWORDLONG ullTotalPhys; DWORDLONG ullAvailPhys; DWORDLONG
		 * ullTotalPageFile; DWORDLONG ullAvailPageFile; DWORDLONG
		 * ullTotalVirtual; DWORDLONG ullAvailVirtual; DWORDLONG
		 * ullAvailExtendedVirtual; } MEMORYSTATUSEX,LPMEMORYSTATUSEX;
		 */
		static class MEMORYSTATUSEX extends Structure
		{
			public int			dwLength;
			public int			dwMemoryLoad;
			public NativeLong	ullTotalPhys;
			public NativeLong	ullAvailPhys;
			public NativeLong	ullTotalPageFile;
			public NativeLong	ullAvailPageFile;
			public NativeLong	ullTotalVirtual;
			public NativeLong	ullAvailVirtual;
			public NativeLong	ullAvailExtendedVirtual;
		}

		/*
		 * BOOL WINAPI GlobalMemoryStatusEx( __inout LPMEMORYSTATUSEX lpBuffer
		 * );
		 */
		boolean GlobalMemoryStatusEx(MEMORYSTATUSEX lpBuffer);

	}

	public long freeRAM()
	{
		MyKernel32.MEMORYSTATUSEX lpBuffer = new MyKernel32.MEMORYSTATUSEX();
		lpBuffer.size();
		lpBuffer.dwLength = lpBuffer.size();
		if (MyKernel32.INSTANCE.GlobalMemoryStatusEx(lpBuffer))
		{
			lpBuffer.read();
			return lpBuffer.ullAvailPhys.longValue();
		}
		return 0;
	}

	public long totalRAM()
	{
		MyKernel32.MEMORYSTATUSEX lpBuffer = new MyKernel32.MEMORYSTATUSEX();
		lpBuffer.size();
		lpBuffer.dwLength = lpBuffer.size();
		if (MyKernel32.INSTANCE.GlobalMemoryStatusEx(lpBuffer))
		{
			lpBuffer.read();
			return lpBuffer.ullTotalPhys.longValue();
		}
		return 0;
	}

	public void setLogger(Logger logger)
	{
		_logger = logger;
	}

}
