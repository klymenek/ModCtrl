package org.rzo.netty.ahessian.rpc.server;

import java.lang.reflect.Method;

import org.rzo.netty.ahessian.rpc.message.HessianRPCCallMessage;
import org.rzo.netty.ahessian.rpc.message.HessianRPCReplyMessage;

/**
 * The Class HessianSkeleton, extends the original HessianSkeleton, 
 * so that it can be used in a non-servlet environment
 * 
 * TODO method name overloading. currently only overloading by number of arguments is supported
 */
public abstract class HessianSkeleton extends com.caucho.hessian4.server.HessianSkeleton implements Service
{
	
	/** The _service. */
	Object _service;
	
	/** The _factory. */
	HessianRPCServiceHandler _factory;
	
	/**
	 * Instantiates a new hessian skeleton.
	 * 
	 * @param service the service
	 * @param apiClass the api class
	 * @param factory the factory
	 */
	public HessianSkeleton(Object service, Class apiClass, HessianRPCServiceHandler factory)
	{
	    super(apiClass);
		_service = service;
		_factory = factory;
	}
	
	
	/**
	 * Gets the method.
	 * 
	 * @param message the message
	 * 
	 * @return the method
	 */
	public Method getMethod(HessianRPCCallMessage message)
	{
		Object[] args = message.getArgs();
		String methodName = message.getMethod();
	    Method method = null;
	    if (args == null || args.length == 0)
	    	method = getMethod(mangleName(methodName, args));
	    if (method == null)
	      method = getMethod(message.getMethod());	    
	    return method;
	}
	
	  public static String mangleName(String method, Object[] args)
	  {
	    if (args != null && args.length > 0)
	    {
		StringBuffer sb = new StringBuffer();
		sb.append(method);
	    sb.append('_');
	    sb.append(args.length);
	    return sb.toString();
	    }
	    return method;
	  }

	/**
	 * Write result.
	 * 
	 * @param message the message
	 */
	public void writeResult(HessianRPCReplyMessage message)
	{
		_factory.writeResult(message);
	}
	
	/* (non-Javadoc)
	 * @see org.rzo.netty.ahessian.rpc.server.Service#messageReceived(org.rzo.netty.ahessian.rpc.HessianRPCCallMessage)
	 */
	abstract public void messageReceived(HessianRPCCallMessage message);

}
