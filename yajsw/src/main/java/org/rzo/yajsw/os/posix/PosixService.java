package org.rzo.yajsw.os.posix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.rzo.yajsw.Constants;
import org.rzo.yajsw.boot.WrapperLoader;
import org.rzo.yajsw.os.AbstractService;
import org.rzo.yajsw.os.JavaHome;
import org.rzo.yajsw.os.OperatingSystem;
import org.rzo.yajsw.os.posix.PosixProcess.CLibrary;

public class PosixService extends AbstractService implements Constants
{
	protected String	_daemonDir;
	String				_daemonTemplate;
	String				_daemonScript;
	String				_wrapperPidFile;
	String				_appPidFile;
	String				_pidDir;
	String				_confFile;
	int					_stopTimeout;

	String[]			_startCmd;
	String[]			_execCmd;
	String[]			_stopCmd;
	String[]			_statusCmd;

	List<String>		_ksLinks	= new ArrayList<String>();
	UpdateRcParser		_updateRcParser;
	Utils				_utils		= new Utils();

	@Override
	public void setLogger(Logger logger)
	{
		super.setLogger(logger);
		_utils.setLog(logger);
	}

	public void init()
	{
		if (_name == null)
		{
			_logger.warning("no name for daemon -> abort");
			return;
		}
		_daemonDir = _config.getString("wrapper.daemon.dir", getDefaultDaemonDir());
		File daemonDir = new File(_daemonDir);
		if (!daemonDir.exists() || !daemonDir.isDirectory())
		{
			_logger.warning("Error " + _daemonDir + " : is not a directory");
			return;
		}
		_pidDir = _config.getString("wrapper.daemon.pid.dir", Constants.DEFAULT_DAEMON_PID_DIR);
		File pidDir = new File(_pidDir);
		if (!pidDir.exists() || !pidDir.isDirectory())
		{
			_logger.warning("Error " + _pidDir + " : is not a directory");
			return;
		}
		String wrapperJar = WrapperLoader.getWrapperJar().trim();
		String wrapperHome = ".";
		try
		{
			wrapperHome = new File(wrapperJar).getParentFile().getCanonicalPath();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}

		String confFile = _config.getString("wrapper.config");
		String confDir = null;
		if (confFile != null)
		{
			File f = new File(confFile);
			if (f.exists())
				try
				{
					confDir = f.getParentFile().getCanonicalPath();
				}
				catch (IOException e)
				{
				}
		}
		if (confDir == null)
			confDir = wrapperHome + "/conf";
		if (confFile == null)
		{
			_logger.warning("no conf file found -> abort");
			return;
		}
		try
		{
			_confFile = new File(confFile).getCanonicalPath();
		}
		catch (IOException e)
		{
			_logger.throwing(this.getClass().getName(), "init", e);
		}
		_daemonTemplate = _config.getString("wrapper.daemon.template", wrapperHome + "/templates/daemon.vm");
		File daemonTemplate = new File(_daemonTemplate);
		if (!daemonTemplate.exists() || !daemonTemplate.isFile())
		{
			if (_logger != null)
				_logger.warning("Error " + _daemonTemplate + " : template file not found");
			return;
		}
		File daemonScript = getDaemonScript();
		if (daemonScript.exists())
			if (_logger != null)
				_logger.info(daemonScript.getAbsolutePath() + " already exists");
		String pidName = null;
		try
		{
			pidName = _config.getString("wrapper.pidfile", new File(pidDir, "wrapper." + getName() + ".pid").getCanonicalPath());
		}
		catch (IOException e)
		{
			if (_logger != null)
				_logger.throwing(this.getClass().getName(), "init", e);
		}
		File pidFile = new File(pidName);
		String apidName = null;
		try
		{
			apidName = _config.getString("wrapper.java.pidfile", new File(pidDir, "wrapper.java." + getName() + ".pid").getCanonicalPath());
		}
		catch (IOException e)
		{
			if (_logger != null)
				_logger.throwing(this.getClass().getName(), "init", e);
		}
		File apidFile = new File(apidName);
		try
		{
			_daemonTemplate = daemonTemplate.getCanonicalPath();
			_wrapperPidFile = pidFile.getCanonicalPath();
			_appPidFile = apidFile.getCanonicalPath();
			_daemonScript = daemonScript.getCanonicalPath();
		}
		catch (Exception ex)
		{
			if (_logger != null)
				_logger.throwing(this.getClass().getName(), "init", ex);
		}
		JavaHome javaHome = OperatingSystem.instance().getJavaHome(_config);
		String java = javaHome.findJava();
		_startCmd = new String[10];
		_startCmd[0] = java;
		_startCmd[1] = "-Dwrapper.pidfile=" + _wrapperPidFile;
		_startCmd[2] = "-Dwrapper.service=true";
		_startCmd[3] = "-Dwrapper.visible=false";
		_startCmd[4] = null;
		_startCmd[5] = null;
		_startCmd[6] = "-jar";
		_startCmd[7] = wrapperJar;
		_startCmd[8] = "-tx";
		_startCmd[9] = _confFile;

		_execCmd = _startCmd.clone();
		//_execCmd[4] = "-Xrs";
		_execCmd[8] = "-c";
		if (_config.getBoolean("wrapper.ntservice.debug", false))
		{
			_execCmd[4] = "-Xdebug";
			_execCmd[5] = "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=1044";
		}

		_stopCmd = _startCmd.clone();
		_stopCmd[8] = "-px";

		_statusCmd = _startCmd.clone();
		_statusCmd[8] = "-qx";

		String defaultRunLevelDir = _daemonDir + "/" + DEFAULT_DAEMON_RUN_LEVEL_DIR;
		String runLevelDir = _config.getString("wrapper.daemon.run_level_dir", defaultRunLevelDir);

		_updateRcParser = new UpdateRcParser(_config.getString("wrapper.daemon.update_rc", null), runLevelDir, getName());
		_ksLinks.addAll(_updateRcParser.getStartLinks());
		_ksLinks.addAll(_updateRcParser.getStopLinks());
		_stopTimeout = _config.getInt("wrapper.shutdown.timeout", Constants.DEFAULT_SHUTDOWN_TIMEOUT) * 1000;

	}

	protected String getDefaultDaemonDir()
	{
		return Constants.DEFAULT_DAEMON_DIR;
	}

	protected File getDaemonScript()
	{
		return new File(new File(_daemonDir), getName());
	}

	public boolean install()
	{
		if (_daemonScript == null)
		{
			if (_logger != null)
				_logger.warning("Error : not initialized -> abort");
			return false;
		}
		try
		{
			File daemonTemplate = new File(_daemonTemplate);
			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(VelocityEngine.RESOURCE_LOADER, "file");
			ve.setProperty("file.resource.loader.path", daemonTemplate.getParent());
			ve.setProperty("runtime.log.logsystem.class", VelocityLog.class.getCanonicalName());
			// TODO find a way to set a non static logger
			VelocityLog.setLogger(_logger);
			ve.init();
			Template t = ve.getTemplate(daemonTemplate.getName());
			VelocityContext context = new VelocityContext();
			context.put("w_name", _name);
			context.put("w_long_name", _displayName);
			context.put("w_start_cmd", toStrCommand(_startCmd));
			context.put("w_stop_cmd", toStrCommand(_stopCmd));
			context.put("w_status_cmd", toStrCommand(_statusCmd));
			context.put("w_description", _description);
			context.put("w_conf_file", _confFile);
			context.put("w_app_pid_file", _appPidFile);
			context.put("w_wrapper_pid_file", _wrapperPidFile);
			FileWriter writer = new FileWriter(_daemonScript);

			t.merge(context, writer);
			writer.flush();
			writer.close();
			File daemonScript = new File(_daemonScript);
			if (daemonScript.exists())
			{
				if (_logger != null)
					_logger.warning("created daemon script: " + _daemonScript);
			}
			else
			{
				if (_logger != null)
					_logger.warning("error creating daemon script: " + _daemonScript);
			}
			// only jdk 1.6 daemonScript.setExecutable(true);
			Runtime.getRuntime().exec("chmod 755 " + _daemonScript);
			if ("AUTO_START".equals(_config.getString("wrapper.ntservice.starttype", DEFAULT_SERVICE_START_TYPE))
					|| "DELAYED_AUTO_START".equals(_config.getString("wrapper.ntservice.starttype", DEFAULT_SERVICE_START_TYPE)))
			{

				for (String link : _ksLinks)
					if (CLibrary.INSTANCE.symlink(_daemonScript, link) != 0)
					{
						if (_logger != null)
							_logger.info("error on creating script link " + link);
					}
					else
					{
						if (new File(link).exists())
						{
							if (_logger != null)
								_logger.info("created link : " + link);
						}
						else
						{
							if (_logger != null)
								_logger.info("error on creating script link " + link);
						}
					}

				// Runtime.getRuntime().exec(String.format("ln -s %1$s %2$s",
				// _klink, _daemonScript));
				// Runtime.getRuntime().exec(String.format("ln -s %1$s %2$s",
				// _slink, _daemonScript));
			}

		}
		catch (Exception ex)
		{
			if (_logger != null)
				_logger.throwing(this.getClass().getName(), "install", ex);
			return false;
		}
		return true;
	}

	private String toStrCommand(String[] cmd)
	{
		String tmp = "";
		for (String s : cmd)
		{
			if (s != null)
				tmp += "\"" + s + "\" ";
		}
		return tmp;
	}

	public boolean isInstalled()
	{
		return _daemonScript != null && new File(_daemonScript).exists();
	}

	public boolean isRunning()
	{
		int pid = getPid();
		if (pid < 0)
			return false;
		org.rzo.yajsw.os.Process p = OperatingSystem.instance().processManagerInstance().getProcess(pid);
		return p != null && p.isRunning();
	}

	public boolean start()
	{
		if (isRunning())
		{
			if (_logger != null)
				_logger.info("already running");
			return true;
		}
		File f = new File(_daemonScript);
		OperatingSystem.instance().setWorkingDir(f.getParent());
		String txt = _utils.osCommand(_daemonScript + " start", 45000);
		if (_logger != null)
			_logger.info(txt);
		return isRunning();
	}

	public boolean stop()
	{
		if (_logger != null)
			_logger.info(_utils.osCommand(_daemonScript + " stop", _stopTimeout));
		return !isRunning();
	}

	public boolean startProcess()
	{
		if (isRunning())
		{
			_logger.info("already running");
			return true;
		}
		/*
		 * org.rzo.yajsw.os.Process p =
		 * OperatingSystem.instance().processManagerInstance().createProcess();
		 * p.setCommand(cleanCmd(_execCmd)); p.setVisible(false);
		 * p.setPipeStreams(false, false);
		 * p.setDebug(_config.getBoolean("wrapper.debug", false)); p.start();
		 */
		try
		{
			if (_logger != null)
				_logger.info("calling " + toStrCommand(_execCmd));
			Runtime.getRuntime().exec(cleanCmd(_execCmd));
		}
		catch (Exception e)
		{
			if (_logger != null)
				_logger.throwing(this.getClass().getName(), "startProcess", e);

		}
		return true; // p.isRunning();
	}

	public boolean stopProcess()
	{
		int pid = getPid();
		if (_logger != null)
			_logger.info("stop daemon with pid " + pid);
		if (pid <= 0)
			return false;
		org.rzo.yajsw.os.Process p = OperatingSystem.instance().processManagerInstance().getProcess(pid);
		if (p == null)
		{
			if (_logger != null)
				_logger.info("process not running");
			return true;
		}

		p.stop(_stopTimeout, 0);
		int apid = getAppPid();
		if (_logger != null)
			_logger.info("stop daemon app with pid " + apid);
		if (apid <= 0)
			return false;
		org.rzo.yajsw.os.Process ap = OperatingSystem.instance().processManagerInstance().getProcess(apid);

		if (ap != null)
			ap.kill(999);
		return true;
	}

	public boolean uninstall()
	{
		if (isRunning())
			stop();
		new File(_daemonScript).delete();
		for (String link : _ksLinks)
			new File(link).delete();
		return true;
	}

	public int state()
	{
		int result = 0;
		if (new File(_daemonScript).exists())
			result |= STATE_INSTALLED;
		if (isRunning())
			result |= STATE_RUNNING;
		return result;
	}

	public int getPid()
	{
		_logger.info("wrapper pid file: " + _wrapperPidFile);
		if (_wrapperPidFile != null && new File(_wrapperPidFile).exists())
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(_wrapperPidFile));
				String pid = reader.readLine();
				reader.close();
				return Integer.parseInt(pid);
			}
			catch (Exception e)
			{
				if (_logger != null)
					_logger.throwing(this.getClass().getName(), "getPid", e);
			}
		return -1;

	}

	public int getAppPid()
	{
		_logger.info("app pid file: " + _appPidFile);
		if (_appPidFile != null && new File(_appPidFile).exists())
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(_appPidFile));
				String pid = reader.readLine();
				reader.close();
				return Integer.parseInt(pid);
			}
			catch (Exception e)
			{
				if (_logger != null)
					_logger.throwing(this.getClass().getName(), "getAppPid", e);
			}
		return -1;

	}

	public String[] cleanCmd(String[] cmd)
	{
		List<String> cmdl = new ArrayList<String>();
		for (String s : cmd)
		{
			if (s != null && !"".equals(s))
				cmdl.add(s);
		}
		String[] result = new String[cmdl.size()];
		int i = 0;
		for (String s : cmdl)
			result[i++] = s;
		return result;

	}

}
