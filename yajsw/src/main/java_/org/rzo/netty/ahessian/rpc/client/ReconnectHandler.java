package org.rzo.netty.ahessian.rpc.client;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;
import org.rzo.netty.ahessian.Constants;

public class ReconnectHandler extends SimpleChannelUpstreamHandler
{
	private  Timer _timer;
	private  long RECONNECT_DELAY = 5000;
	private BootstrapProvider _bootstrap;
	private volatile boolean _stop = false;
	private volatile Timeout _timeout;
	
	public ReconnectHandler(BootstrapProvider bootstrap, long reconnectDelay, Timer timer)
	{
		RECONNECT_DELAY = reconnectDelay;
		_bootstrap = bootstrap;
		_timer = timer;
	}
	
	public ReconnectHandler(BootstrapProvider bootstrap)
	{
		_bootstrap = bootstrap;
	}
	
		@Override
	    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
			ctx.sendUpstream(e);
			if (_stop)
				return;
			if (_timeout != null)
				return;
			Constants.ahessianLogger.warn("channel closed wait to reconnect ...");
	        _timeout = _timer.newTimeout(new TimerTask() {
				public void run(Timeout timeout) throws Exception
				{
					_timeout = null;
	            	connect(_bootstrap.getBootstrap());
	               
				}
	        }, RECONNECT_DELAY, TimeUnit.MILLISECONDS);
	    }
		
		@Override
	    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
			// if we get an exception : close the channel
	        Throwable cause = e.getCause();
	        cause.printStackTrace();
	        if (cause instanceof ConnectException) 
	        {
	        	Constants.ahessianLogger.warn("conection lost");
	        }
	        try
	        {
	        ctx.getChannel().close();
	        }
	        catch (Exception ex)
	        {
	        	
	        }
	    }
		
		public void stop()
		{
			_stop = true;
			Timeout timeout = _timeout;
			_timeout = null;
			timeout.cancel();
		}
		
		protected void connect(ClientBootstrap bootstrap)
		{
			Constants.ahessianLogger.warn("reconnecting...");
            ChannelFuture f = _bootstrap.getBootstrap().connect();
            try
		{
			f.awaitUninterruptibly();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			Constants.ahessianLogger.warn("", e);
				}
        if (f.isSuccess())
      	  Constants.ahessianLogger.warn("connected");
        else
        {
      	  Constants.ahessianLogger.warn("not connected");
        }
			
		}
    
	
}
