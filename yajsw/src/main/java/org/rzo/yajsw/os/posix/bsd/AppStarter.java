package org.rzo.yajsw.os.posix.bsd;

import java.util.ArrayList;
import java.util.List;

import org.rzo.yajsw.os.posix.PosixProcess.CLibrary;

import com.sun.jna.Pointer;

public class AppStarter
{
	public static void main(String[] args)
	{
		// get pid and send it to parent
		int pid = CLibrary.INSTANCE.getpid();
		System.out.println("PID:" + pid);
		System.out.flush();

		// detach from parent
		CLibrary.INSTANCE.umask(0);
		CLibrary.INSTANCE.setsid();

		// set priority
		if (CLibrary.INSTANCE.nice(1) == -1)
			System.out.println("could not set priority ");
		if (getUser() != null)
			switchUser(getUser(), getPassword());

		// close streams ?
		if (!isPipeStreams())
		{
			/*
			try
			{
				System.in.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			*/
			
			System.out.close();
			System.err.close();
		}
		
		String[] env = null;//getEnv();

		// start the subprocess
		try
		{
			if (env == null)
				CLibrary.INSTANCE.execvp(args[0], args);
			else
				CLibrary.INSTANCE.execve(args[0], args, env);
		}
		catch (Exception ex)
		{
		}

	}

	private static boolean isPipeStreams()
	{
		return System.getProperty("wrapperx.pipeStreams") != null;
	}

	private static String getPassword()
	{
		return System.getProperty("wrapperx.password");
	}

	private static String getUser()
	{
		return System.getProperty("wrapperx.user");
	}

	private static String[] getEnv()
	{
		List<String> result = new ArrayList<String>();
		for (String key : System.getenv().keySet())
		{
			result.add(key+"="+System.getenv(key));
		}
		if (result.isEmpty())
			return null;
		String[] arr = new String[result.size()];
		int i = 0;
		for (String x : result)
		{
			arr[i] = x;
			System.out.println(x);
			i++;
		}
		return arr;
	}

	public static String currentUser()
	{
		int euid = CLibrary.INSTANCE.geteuid();
		Pointer p = CLibrary.INSTANCE.getpwuid(euid);
		if (p == null)
			System.out.println("could not get current user");
		return new CLibrary.passwd(p).getName();

	}

	public static void switchUser(String name, String password)
	{
		System.out.println("setting to user " + name);
		if (name == null || "".equals(name))
			return;
		String current = currentUser();
		System.out.println("current user" + current);
		if (current != null && !current.equals(name))
		{
			Pointer p = CLibrary.INSTANCE.getpwnam(name);
			int newUid = new CLibrary.passwd(p).getUid();
			if (newUid == 0)
				System.out.println("could not get user " + name);
			int res = CLibrary.INSTANCE.setreuid(newUid, newUid);
			if (res != 0)
				System.out.println("could not change to user " + name);
			current = currentUser();
			if (!name.equals(current))
				System.out.println("could not set user");

		}
	}

}
