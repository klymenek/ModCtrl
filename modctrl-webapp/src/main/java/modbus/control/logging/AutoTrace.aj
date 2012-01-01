package modbus.control.logging;

/**
 *
 * @author ares
 */
import org.apache.log4j.Logger;
import java.lang.reflect.Field;

public aspect AutoTrace {

    private static final Logger logger 
    	= Logger.getLogger(AutoTrace.class);
    
    pointcut publicMethods() : 
    	execution(public * modbus.control..*(..));

    pointcut loggableCalls() : publicMethods();
    
    /**
     * Inspect the class and find its logger object. If none is found, 
     * use the one defined here.
     */
    private Logger getLogger(org.aspectj.lang.JoinPoint joinPoint) {
        try {
            /*
             * Try to discover the logger object. 
             * The logger object must be a static field called logger.
             */
            Class declaringType 
            = joinPoint.getSignature().getDeclaringType();
            Field loggerField 
            = declaringType.getField("logger");
            loggerField.setAccessible(true);
            return (Logger)loggerField.get(null);
        } catch(NoSuchFieldException e) {
            /*
             * Cannot find a logger object, use the internal one.
             */
            return logger;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * An aspect to log method entry.
     */
    before() : loggableCalls(){
        getLogger(thisJoinPoint).debug("Entering.." 
        + thisJoinPoint.getSignature().toString());
    }
      
    /**
     * An aspect to log method exit.
     */
    after() : loggableCalls(){
        getLogger(thisJoinPoint).debug("Exiting.." 
        + thisJoinPoint.getSignature().toString());
    }
}
