package org.rzo.yajsw.os.posix;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.rzo.yajsw.os.JavaHome;

public class PosixJavaHome implements JavaHome
{
	Configuration	_config;

	public PosixJavaHome(Configuration config)
	{
		if (config != null)
			_config = config;
		else
			_config = new BaseConfiguration();
	}

	public String findJava()
	{
		// find / -name java
		// TODO
		return _config.getString("wrapper.java.command", "java");
	}

}
