/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.  
 */
package org.rzo.yajsw.tray;

import org.rzo.yajsw.boot.WrapperLoader;
import org.rzo.yajsw.config.YajswConfigurationImpl;
import org.rzo.yajsw.os.OperatingSystem;
import org.rzo.yajsw.os.Process;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating WrapperTrayIcon objects.
 */
public class WrapperTrayIconFactory
{

	/**
	 * Creates a new WrapperTrayIcon object.
	 * 
	 * @param name
	 *            the name
	 * @param icon
	 *            the icon
	 * 
	 * @return the wrapper tray icon
	 */
	public static WrapperTrayIcon createTrayIcon(String name, String icon, YajswConfigurationImpl	config)
	{
		WrapperTrayIcon result = null;
		if (config == null)
			config = new YajswConfigurationImpl();
		try
		{
			result = new WrapperTrayIconImpl(name, icon, config);
		}
		catch (Throwable ex)
		{
			System.out.println("java version does not support SystemTray: " + ex.getMessage());
			ex.printStackTrace();
		}
		if (result == null || !result.isInit())
			result = new WrapperTrayIconDummy();
		return result;
	}

	/**
	 * Start tray icon process.
	 * 
	 * @param config
	 *            the config
	 * 
	 * @return the process
	 */
	public static Process startTrayIconProcess(YajswConfigurationImpl config)
	{
		if (config == null)
			return null;
		String wrapperConfFileName = config.getCachedPath(false);

		final Process _osProcess = OperatingSystem.instance().processManagerInstance().createProcess();

		try
		{
			_osProcess.setCommand(new String[]
			{ getJava(), "-cp", WrapperLoader.getWrapperHome() + "/wrapper.jar", TrayIconMainBooter.class.getName(), wrapperConfFileName });
			_osProcess.setPipeStreams(false, false);
			_osProcess.setVisible(false);
			_osProcess.start();
			Runtime.getRuntime().addShutdownHook(new Thread()
			{
				public void run()
				{
					if (_osProcess != null)
						_osProcess.kill(0);
				}
			});
			return _osProcess;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the java.
	 * 
	 * @return the java
	 */
	public static String getJava()
	{
		String result = System.getenv("java_exe");
		if (result == null)
		{
			result = System.getProperty("sun.boot.library.path");
			if (result != null)
				result = result + "/javaw";
			else
				result = "java";
		}
		return result;
	}

}
