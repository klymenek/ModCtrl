package org.rzo.yajsw.util;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.provider.http.HttpFileSystemConfigBuilder;

public class VFSUtils
{
	static DefaultFileSystemManager	fsManager	= null;
	static FileSystemOptions		opts		= new FileSystemOptions();

	public static void init() throws FileSystemException
	{
		if (fsManager != null)
			return;

		fsManager = (DefaultFileSystemManager) VFS.getManager();
		String httpProxy = System.getProperty("http.proxyHost");
		String httpPort = System.getProperty("http.proxyPort");
		if (httpProxy != null)
		{
			HttpFileSystemConfigBuilder.getInstance().setProxyHost(opts, httpProxy);

			int port = 8080;
			if (httpPort != null)
				try
				{
					port = Integer.parseInt(httpPort);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			HttpFileSystemConfigBuilder.getInstance().setProxyPort(opts, port);
		}
	}

	public static FileObject resolveFile(String base, String file) throws FileSystemException
	{
		init();
		FileObject basef = null;
		if (base != null)
			basef = fsManager.resolveFile(new File("."), base);
		return resolveFile(basef, file);
	}

	public static FileObject resolveFile(FileObject basef, String file) throws FileSystemException
	{
		init();
		if (basef != null)
			return fsManager.resolveFile(basef, file, opts);
		else
			return fsManager.resolveFile(file, opts);
	}

}
