package org.rzo.yajsw.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JCLParser
{
	List<String>	_classpath	= new ArrayList<String>();
	List<String>	_vmOptions	= new ArrayList<String>();
	List<String>	_args		= new ArrayList<String>();
	String			_java		= null;
	String			_mainClass	= null;
	String			_jar		= null;

	private JCLParser(String commandLine)
	{
		parseInternal(commandLine);
	}

	public static JCLParser parse(String commandLine)
	{
		JCLParser result = null;
		result = new JCLParser(commandLine);
		return result;
	}

	// TODO this should cover most cases but is not complete
	private void parseInternal(String commandLine)
	{
		Matcher mr;
		Pattern p;
		// last position of _java in commandLine
		int posJ = 0;
		// last position of _classpath in commandLine
		int posCp = 0;
		// last position of __vmOptions in commandLine
		int posOpts = 0;
		// last position of _mainClass
		int posclp = 0;
		// last position of _jar
		int posJar = 0;

		// parse java
		p = Pattern.compile("\\A(\"[^\"]+\")|(\\S+) ");
		mr = p.matcher(commandLine);
		if (mr.find())
		{
			_java = mr.group();
			_java = _java.replaceAll("\"", "");
			_java = _java.trim();
			posJ = mr.end() - 1;
		}
		else
			throw new RuntimeException("could not parse command line " + commandLine);

		// parse jar
		p = Pattern.compile(" -jar +((\"[^\"]+\")|(\\S+))");
		mr = p.matcher(commandLine);
		if (mr.find(posJ))
		{
			_jar = mr.group(1);
			_jar = _jar.replaceAll("\"", "");
			_jar = _jar.trim();
			posJar = mr.end() - 1;
		}

		// parse classpath
		p = Pattern.compile("(( -cp)|( -classpath)|( \"-classpath\")) +((\"[^\"]+\")|(\\S+)) ");
		mr = p.matcher(commandLine);
		if (mr.find(posJ))
		{
			String cp = mr.group().trim();
			posCp = mr.end() - 1;
			cp = cp.substring(cp.indexOf(' '));
			String[] cpArr = cp.split(File.pathSeparator);
			for (String cc : cpArr)
			{
				cc = cc.replaceAll("\"", "");
				_classpath.add(cc.trim());
			}
		}

		// parse main class
		if (_jar == null)
		{
			p = Pattern.compile(" ([^- ])+( |$)");
			mr = p.matcher(commandLine);
			int max = Math.max(posJ, posCp);
			if (mr.find(max))
			{
				_mainClass = mr.group();
				_mainClass = _mainClass.replaceAll("\"", "");
				_mainClass = _mainClass.trim();
				posclp = mr.end() - 1;
			}
		}

		// parse JVM options
		p = Pattern.compile("(( -\\S+)|( -\"[^\"]+\")|( \"-[^\"]+\")) ");
		mr = p.matcher(commandLine);
		int max = Math.max(posJar, posclp);
		int pos = 0;
		while (mr.find(pos))
		{
			String opt = mr.group().trim();
			pos = mr.end()-1;
			opt = opt.replaceAll("\"", "");
			if (!opt.startsWith("-jar") && !opt.startsWith("-cp") && !opt.startsWith("-classpath") && mr.end() < max)
			{
				_vmOptions.add(opt);
				posOpts = mr.end();
			}
		}

		// parse args
		p = Pattern.compile(" ((\"[^\"]+\")|(\\S+))( |$)");
		mr = p.matcher(commandLine);
		max = Math.max(posclp, posJar);
		max = Math.max(max, posOpts);
		if (mr.find(max))
		{
			String arg = mr.group();
			arg = arg.replaceAll("\"", "");
			_args.add(arg.trim());
			max = mr.end() - 1;
			while (mr.find(max))
			{
				arg = mr.group();
				arg = arg.replaceAll("\"", "");
				arg = arg.trim();
				if (arg.length() > 0)
					_args.add(arg.trim());
				max = mr.end() - 1;
			}
		}

		if (_java == null || "".equals(_java) || ((_mainClass == null || "".equals(_mainClass)) && ((_jar == null || "".equals(_jar)))))
			throw new RuntimeException("error parsing java command line ");

	}

	public List<String> getClasspath()
	{
		return _classpath;
	}

	public List<String> getVmOptions()
	{
		return _vmOptions;
	}

	public List<String> getArgs()
	{
		return _args;
	}

	public String getJava()
	{
		return _java;
	}

	public String getMainClass()
	{
		return _mainClass;
	}

	public String getJar()
	{
		return _jar;
	}

	public static void main(String[] args)
	{
		String[] cmds = new String[]{
				"\"java\" -cp \"C:\\Program Files\\yajsw-alpha-9.5\\bat\\/../wrapper.jar\" test.HelloWorld",
		"java -Xrs -jar \"Z:\\dev\\yajsw\\bat\\/..\\wrapper.jar\" -c conf/wrapper.conf       ",
		"java -cp wrapper.jar -Xrs x.Test -c conf/wrapper.conf       ",
		"\"java\" -cp \"C:\\Program Files\\yajsw-alpha-9.5\\bat\\/../wrapper.jar\" test.HelloWorld start \n ",
		"\"java\"  test.HelloWorld",
		"\"C:\\Program Files\\Java\\jre7\\bin\\javaw.exe\" -Xmx512m -jar \"C:\\automa tisation\\bin\\sendfile-server.jar\" abc ",
		"java -jar testJar.jar",
	    "java -jar LogConsolidation-1.0.one-jar.jar",	
		"java -Dlog4j.debug -Dlog4j.configuration=file:../conf/log4j.xml -jar myApp.jar start",
		"/usr/DAVIDweb/jdk1.6.0_18/bin/java -Dwrapper.teeName=6849389861148562201$1312438311981 -Dwrapper.config=/usr/DAVIDweb/Tomcat557_AAA-DHK_3/AAA-DHK_3/bin/conf/wrapper.conf -Dwrapper.key=6849389861148562201 -Dwrapper.visible=false -Dwrapper.pidfile=/var/run/wrapper.ApacheTomcatAAADHK3.pid -Dwrapper.port=15003 -Dwrapper.key=6849389861148562201 -Dwrapper.teeName=6849389861148562201$1312438311981 -Dwrapper.tmpPath=/tmp -classpath /usr/DAVIDweb/Tomcat557_AAA-DHK_3/AAA-DHK_3/bin/wrapper.jar:/usr/DAVIDweb/Tomcat557_AAA-DHK_3/bin/bootstrap.jar -server -Djava.endorsed.dirs=/DAVIDweb/Tomcat557_AAA-DHK_3/common/endorsed -Dcatalina.home=/DAVIDweb/Tomcat557_AAA-DHK_3 -Dcatalina.base=/DAVIDweb/Tomcat557_AAA-DHK_3/AAA-DHK_3 -Dcatalina.properties=/DAVIDweb/Tomcat557_AAA-DHK_3/AAA-DHK_3/conf/catalina.properties -Djava.io.tmpdir=/DAVIDweb/Tomcat557_AAA-DHK_3/AAA-DHK_3/temp -Dibr.debug=false -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/DAVIDweb/Tomcat557_AAA-DHK_3/AAA-DHK_3/webapps/AAA-DHK_3/logs -XX:+DisableExplicitGC -Xss1024k -Dibr.dhk.geoinfodok=gid600 -Dibr.dhk.lib=/DAVIDweb/Tomcat557_AAA-DHK_3/AAA-DHK_3/webapps/AAA-DHK_3/WEB-INF/lib/gid600 -Xrs -Dwrapper.service=true -Dwrapper.console.visible=false -Xms512m -Xmx512m org.rzo.yajsw.app.WrapperJVMMain"
		};
		for (String cmd : cmds)
		{
			System.out.println("---------------------");
			System.out.println(cmd);
			System.out.println("---------------------");
		JCLParser p = JCLParser.parse(cmd);
		System.out.println(" java:");
		System.out.println(p.getJava());
		System.out.println(" jar:");
		System.out.println(p.getJar());
		System.out.println(" main class:");
		System.out.println(p.getMainClass());
		System.out.println(" args:");
		System.out.println(p.getArgs());
		System.out.println(" classpath:");
		System.out.println(p.getClasspath());
		System.out.println(" options:");
		System.out.println(p.getVmOptions());
		}
	}

}
