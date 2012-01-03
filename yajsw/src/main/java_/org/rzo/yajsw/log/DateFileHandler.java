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
package org.rzo.yajsw.log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

// TODO: Auto-generated Javadoc
/**
 * The Class DateFileHandler.
 */
public class DateFileHandler extends Handler
{

	/** The _handler. */
	volatile FileHandler			_handler;

	/** The _end date. */
	volatile long				_endDate;

	/** The _pattern. */
	volatile String				_pattern;

	/** The _limit. */
	volatile int					_limit;

	/** The _count. */
	volatile int					_count;

	/** The _append. */
	volatile boolean				_append;

	/** The format. */
	final SimpleDateFormat	format	= new SimpleDateFormat("yyyyMMdd");

	/** The _init. */
	volatile boolean				_init	= false;
	
	volatile long _startDate = System.currentTimeMillis();

	/**
	 * Instantiates a new date file handler.
	 * 
	 * @param pattern
	 *            the pattern
	 * @param limit
	 *            the limit
	 * @param count
	 *            the count
	 * @param append
	 *            the append
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SecurityException
	 *             the security exception
	 */
	public DateFileHandler(String pattern, int limit, int count, boolean append) throws IOException, SecurityException
	{
		_pattern = pattern;
		_limit = limit;
		_count = count;
		_append = append;
		rotateDate();
		_init = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Handler#close()
	 */
	@Override
	public void close() throws SecurityException
	{
		_handler.close();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Handler#flush()
	 */
	@Override
	public void flush()
	{
		_handler.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
	 */
	@Override
	public void publish(LogRecord record)
	{
		if (_endDate < record.getMillis())
			rotateDate();
		if (System.currentTimeMillis() - _startDate > 25*60*60*1000)
		{
			String msg = record.getMessage();
			record.setMessage("missed file rolling at: "+new Date(_endDate)+"\n"+msg);
		}
		_handler.publish(record);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Handler#setFormatter(java.util.logging.Formatter)
	 */
	@Override
	public void setFormatter(Formatter newFormatter)
	{
		super.setFormatter(newFormatter);
		if (_handler != null)
			_handler.setFormatter(newFormatter);
	}

	/**
	 * Rotate date.
	 */
	private void rotateDate()
	{
		_startDate = System.currentTimeMillis();
		if (_handler != null)
			_handler.close();
		String pattern = _pattern.replace("%d", format.format(new Date()));
		Calendar next = Calendar.getInstance(); // current date
		// begin of next date
		next.set(Calendar.HOUR_OF_DAY, 0);
		next.set(Calendar.MINUTE, 0);
		next.set(Calendar.SECOND, 0);
		next.set(Calendar.MILLISECOND, 0);
		next.add(Calendar.DATE, 1);
		_endDate = next.getTimeInMillis();

		try
		{
			_handler = new FileHandler(pattern, _limit, _count, _append);
			if (_init)
			{
				_handler.setEncoding(this.getEncoding());
				_handler.setErrorManager(this.getErrorManager());
				_handler.setFilter(this.getFilter());
				_handler.setFormatter(this.getFormatter());
				_handler.setLevel(this.getLevel());
			}
		}
		catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
