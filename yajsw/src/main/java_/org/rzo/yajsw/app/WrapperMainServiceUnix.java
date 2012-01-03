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
package org.rzo.yajsw.app;

import java.io.File;

import org.rzo.yajsw.boot.WrapperLoader;
import org.rzo.yajsw.config.YajswConfigurationImpl;
import org.rzo.yajsw.os.OperatingSystem;
import org.rzo.yajsw.os.StopableService;
import org.rzo.yajsw.wrapper.WrappedProcess;
import org.rzo.yajsw.wrapper.WrappedProcessFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class WrapperMainService.
 */
public class WrapperMainServiceUnix implements StopableService
{

	/** The w. */
	static WrappedProcess	w;

	/**
	 * Instantiates a new wrapper main service.
	 */
	public WrapperMainServiceUnix()
	{
	}

	// this is the wrapper for services
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{
		String wrapperJar = WrapperLoader.getWrapperJar();
		String homeDir = new File(wrapperJar).getParent();
		OperatingSystem.instance().setWorkingDir(homeDir);

		StopableService service = new WrapperMainServiceUnix();
		YajswConfigurationImpl _config = new YajswConfigurationImpl(false);
		w = WrappedProcessFactory.createProcess(_config);
		// start the application
		//w.setDebug(true);
		w.init();
		w.setService(service);
		
		/* use wrapper.control
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				w.stop();
				w.shutdown();
				// give scripts time to terminate
				try
				{
					Thread.sleep(5000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});
		*/
		w.start();
	}

	public void onStop()
	{
		// give any running scripts time to terminate
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void waitOnStop()
	{
		// TODO Auto-generated method stub
		
	}

	public void signalStopping(long waitHint)
	{
		// TODO Auto-generated method stub
		
	}

}
