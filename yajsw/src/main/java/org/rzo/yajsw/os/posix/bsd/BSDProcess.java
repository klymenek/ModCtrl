package org.rzo.yajsw.os.posix.bsd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.rzo.yajsw.boot.WrapperLoader;
import org.rzo.yajsw.io.CyclicBufferFileInputStream;
import org.rzo.yajsw.io.CyclicBufferFilePrintStream;
import org.rzo.yajsw.os.OperatingSystem;
import org.rzo.yajsw.os.Process;
import org.rzo.yajsw.os.posix.PosixProcess;

import com.sun.jna.ptr.IntByReference;

public class BSDProcess extends PosixProcess
{
	java.lang.Process	_process;

	public boolean start()
	{
		_terminated = false;
		_exitCode = -1;
		ArrayList<String> cmdList = new ArrayList();
		cmdList.add(getCurrentJava());
		cmdList.add("-classpath");
		cmdList.add(getStartClasspath());
		if (_pipeStreams)
			cmdList.add("-Dwrapperx.pipeStreams=true");
		if (_user != null)
			cmdList.add("-Dwrapperx.user=" + _user);
		if (_password != null)
			cmdList.add("-Dwrapperx.password=" + _password);
		String[] xenv = getXEnv();
		cmdList.add(AppStarter.class.getName());
		for (int i = 0; i < _arrCmd.length; i++)
			cmdList.add(_arrCmd[i]);
		String[] cmd = new String[cmdList.size()];
		for (int i = 0; i < cmd.length; i++)
		{
			cmd[i] = cmdList.get(i);
			System.out.print(cmd[i] + " ");
		}
		System.out.println();
		System.out.flush();

		final java.lang.Process p;

		try
		{
			p = Runtime.getRuntime().exec(cmd, xenv, new File(_workingDir));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			_terminated = true;
			return false;
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		try
		{
			do
			{
				line = in.readLine();
				if (line != null && line.contains("PID:"))
				{
					setPid(Integer.parseInt(line.substring(4)));
					line = null;
				}
				else
					System.out.println(line);
			}
			while (line != null);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			_terminated = true;
			return false;
		}
		_process = p;
		executor.execute(new Runnable()
		{

			public void run()
			{
				try
				{
					p.waitFor();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
				_terminated = true;
				_exitCode = p.exitValue();
				System.out.println("exit code bsd process " + _exitCode);
				BSDProcess.this.setTerminated(true);
			}

		});

		if (_teeName != null && _tmpPath != null)
		{
			File f = new File(_tmpPath);
			try
			{
				if (!f.exists())
					f.mkdir();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			try
			{
				// System.out.println("opening tee streams out");
				_inputStream = new CyclicBufferFileInputStream(createRWfile(_tmpPath, "out_" + _teeName));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				// System.out.println("opening tee streams err");
				_errorStream = new CyclicBufferFileInputStream(createRWfile(_tmpPath, "err_" + _teeName));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				// System.out.println("opening tee streams in");
				_outputStream = new CyclicBufferFilePrintStream(createRWfile(_tmpPath, "in_" + _teeName));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (_pipeStreams && _teeName == null && _tmpPath == null)
		{

			_outputStream = _process.getOutputStream();
			_inputStream = _process.getInputStream();
			_errorStream = _process.getErrorStream();

		}
		if (_cpuAffinity != AFFINITY_UNDEFINED)
		{
			IntByReference affinity = new IntByReference();
			affinity.setValue(_cpuAffinity);
			if (CLibrary.INSTANCE.sched_setaffinity(_pid, 4, affinity) == -1)
				System.out.println("error setting affinity");
		}

		System.out.println("started process " + _pid);

		return true;
	}

	private String[] getXEnv()
	{
		List<String[]> env = getEnvironment();
		if (env != null && !env.isEmpty())
		{
			String [] result = new String[env.size()];
			int i = 0;
			for (String[] x : env)
			{
				result[i] = x[0]+"="+x[1];
				System.out.println("bsd env "+result[i]);
				i++;
			}
			return result;
		}
		return null;
	}

	private String getStartClasspath()
	{
		String wrapperJar = WrapperLoader.getWrapperJar();
		File wrapperHome = new File(wrapperJar).getParentFile();
		File jnaFile = new File(wrapperHome, "lib/core/jna/jna-3.3.0.jar");
		try
		{
			return wrapperJar + ":" + jnaFile.getCanonicalPath();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private String getCurrentJava()
	{
		int myPid = OperatingSystem.instance().processManagerInstance().currentProcessId();
		Process myProcess = OperatingSystem.instance().processManagerInstance().getProcess(myPid);
		String cmd = myProcess.getCommand();
		String jvm = null;
		if (cmd.startsWith("\""))
			jvm = cmd.substring(0, cmd.indexOf("\" ") + 1);
		else
			jvm = cmd.substring(0, cmd.indexOf(" "));

		return jvm;
	}

	public String getCommandInternal()
	{
		if (_pid < 0)
			return null;
		String cmd = String.format("ps -p %1$s -o command", _pid);
		String res = _utils.osCommand(cmd, 5000);
		if (res == null)
			return null;
		String[] resx = res.split(System.getProperty("line.separator"));
		return resx[1];
	}

	public String getUserInternal()
	{
		if (_pid < 0)
			return null;
		String cmd = String.format("ps -p %1$s -o user", _pid);
		String res = _utils.osCommand(cmd, 5000);
		if (res == null)
			return null;
		String[] resx = res.split(System.getProperty("line.separator"));
		return resx[1];
	}

	public String getWorkingDirInternal()
	{
		if (_pid < 0)
			return null;
		return null;
	}

	public static Process getProcess(int pid)
	{
		BSDProcess result = null;
		result = new BSDProcess();
		result.setPid(pid);
		result.setUser(result.getUserInternal());
		result.setCommand(result.getCommandInternal());
		result.setWorkingDir(result.getWorkingDirInternal());
		if (result.getCommand() == null)
			return null;
		return result;
	}

	public static void main(String[] args)
	{
		BSDProcess p = new BSDProcess();
		System.out.println(p.getCurrentJava());
		p.setCommand(new String[]
		{ "ping", "localhost" });
		p.setPipeStreams(true, false);
		p.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		try
		{
			do
			{
				line = in.readLine();
				System.out.println(line);
			}
			while (line != null);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
