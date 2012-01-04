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
package org.rzo.yajsw.script;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.codehaus.groovy.control.CompilationFailedException;
import org.jboss.netty.logging.InternalLogger;
import org.rzo.yajsw.boot.WrapperLoader;
import org.rzo.yajsw.util.VFSUtils;
import org.rzo.yajsw.wrapper.WrappedJavaProcess;
import org.rzo.yajsw.wrapper.WrappedProcess;

/**
 * The Class GroovyScript.
 */
public class GroovyScript extends AbstractScript
{

	public static Map		context	= Collections.synchronizedMap(new HashMap());
	/** The binding. */
	Binding			binding;

	WrappedProcess	process;
	
	InternalLogger _logger;

	GroovyObject	_script;


	/**
	 * Instantiates a new groovy script.
	 * 
	 * @param script
	 *            the script
	 * @param timeout 
	 * @throws IOException
	 * @throws CompilationFailedException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException 
	 */
	public GroovyScript(String script, String id, WrappedProcess process, String[] args, int timeout, InternalLogger logger) throws CompilationFailedException, IOException,
			InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		super(script, id, process, args, timeout);

		// let's call some method on an instance
		_script = getScriptInstance(script);
		binding = (Binding) _script.invokeMethod("getBinding", null);
		binding.setVariable("args", args);
		binding.setVariable("callCount", 0);
		binding.setVariable("context", context);
		if (process != null && logger == null)
		_logger = process.getInternalWrapperLogger();
		else 
			_logger = logger;
		binding.setVariable("logger", _logger);
	}

	private void setGroovyClasspath(GroovyClassLoader loader)
	{
		ArrayList cp = WrapperLoader.getGroovyClasspath();
		for (Iterator it = cp.listIterator(); it.hasNext(); )
			loader.addURL((URL)it.next());
	}

  static GroovyClassLoader groovyClassLoader;

  private GroovyObject getScriptInstance(String scriptFileName) throws IOException, InstantiationException,
      IllegalAccessException, ClassNotFoundException
  {
    FileObject fileObject = VFSUtils.resolveFile(".", scriptFileName);
    FileName fileName = fileObject.getName();
    long lastModified = fileObject.getContent().getLastModifiedTime();
    String scriptName = StringUtils.removeEnd(fileName.getBaseName(), "." + fileName.getExtension()) + "_"
        + lastModified;

    synchronized (GroovyScript.class)
    {
      if (groovyClassLoader == null)
      {
        groovyClassLoader = new GroovyClassLoader(getClass().getClassLoader());        
        setGroovyClasspath(groovyClassLoader);
      }

      try
      {
        return (GroovyObject) Class.forName(scriptName, true, groovyClassLoader).newInstance();
      }
      catch (ClassNotFoundException e)
      {
        InputStream in = null;
        String scriptSrc = null;
        try
        {
          in = fileObject.getContent().getInputStream();
          scriptSrc = IOUtils.toString(in, "UTF-8");
        }
        finally
        {
          if (in != null)
            in.close();
        }
        return (GroovyObject) groovyClassLoader.parseClass(scriptSrc, scriptName + ".groovy").newInstance();         
      }
    }
  }

    /*
	 * (non-Javadoc)
	 * 
	 * @see org.rzo.yajsw.script.AbstractScript#execute(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.Object)
	 */
	public Object execute(String line)
	{
		Object result = null;
		
		if (_script == null)
		{
			System.out.println("cannot execute script " + _name);
			return null;
		}
		binding.setVariable("id", _id);
		if (_process != null)
		{
		binding.setVariable("state", _process.getStringState());
		binding.setVariable("count", _process.getRestartCount());
		binding.setVariable("pid", _process.getAppPid());
		binding.setVariable("exitCode", _process.getExitCode());
		binding.setVariable("line", line);
		binding.setVariable("process", _process);
		}
		try
		{
			result = _script.invokeMethod("run", new Object[]
			{});


		}
		catch (Throwable e)
		{
			if (_logger != null)
			_logger.info("execption in script "+this._name, e);
			else
				e.printStackTrace();
		}
		binding.setVariable("callCount", ((Integer) binding.getVariable("callCount")).intValue() + 1);
		return result;
	}

	public static void main(String[] args) throws Exception, IOException, InstantiationException, IllegalAccessException
	{
		WrappedJavaProcess w = new WrappedJavaProcess();
		w.getLocalConfiguration().setProperty("wrapper.config", "conf/wrapper.helloworld.conf");
		w.init();
		GroovyScript script = new GroovyScript("./scripts/timeCondition.gv", "id", w, new String[]
		{ "11", "12" }, 0, null);
		script.execute();
		script.execute();
		script = new GroovyScript("./scripts/fileCondition.gv", "id", w, new String[]
		{ "anchor.lck" }, 0, null);
		script.execute();
		script.execute();
		script = new GroovyScript("./scripts/snmpTrap.gv", "id", w, new String[]
		{ "192.168.0.1", "1", "msg" }, 0, null);
		script.execute();

	}

	public Object execute()
	{
		return execute("");
	}

	public Object executeWithTimeout()
	{
		return executeWithTimeout("");
	}

}
