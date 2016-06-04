package accenttutor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class MPlayerAudioInputStream extends AudioInputStream
{
	protected BufferedInputStream in=null;
	protected float sampleRate;
	protected File tempFile;
	protected static final boolean  DEBUG = false;

	protected static final File tempFolder = new File(".");

	public MPlayerAudioInputStream(File inputFile, float sampleRate)
	{
		//call super class constructor
		super(new ByteArrayInputStream(new byte[0]), new AudioFormat(sampleRate, 16, 1, true, false)  , 0);

		this.sampleRate = sampleRate;

        try
		{

        	// Create a temporary file for decoding via mpalyer
        	tempFile = File.createTempFile("comriva", ".tmp", tempFolder);

	        // Execute a command with an argument that contains a space
        	String args[];
        	if(DEBUG)
        	{
        		String tmp[] = { "mplayer", "-nolirc", "-vo", "null", "-vc", "null", "-noconsolecontrols", "-endpos", "00:04:00", "-ao", "pcm:file=" + tempFile.getName() + ":fast:nowaveheader", "-af", "resample=" + sampleRate + ",channels=1", inputFile.getCanonicalPath()};
        		args = tmp;
        	}
        	else
        	{
        		String tmp[] = { "mplayer", "-nolirc", "-vo", "null", "-vc", "null", "-noconsolecontrols", "-really-quiet", "-endpos", "00:04:00", "-ao", "pcm:file=" + tempFile.getName() + ":fast:nowaveheader", "-af", "resample=" + sampleRate + ",channels=1", "-msglevel", "all=-1", inputFile.getCanonicalPath()};
        		args = tmp;
        	}

        	//execute command
        	//Process child = Runtime.getRuntime().exec(args);
        	
        	
        	
            Timer timer = null;
            Process child = null;
            try
            {
                timer = new Timer(true);
                InterruptTimerTask interrupter = new InterruptTimerTask(Thread.currentThread());
                timer.schedule(interrupter, 60 /*seconds*/ * 1000 /*milliseconds per second*/);
                child = Runtime.getRuntime().exec(args);
                
            	// any error message?
                StreamGobbler errorGobbler = new 
                StreamGobbler(child.getErrorStream(), "ERROR");            
                
                // any output?
                StreamGobbler outputGobbler = new 
                StreamGobbler(child.getInputStream(), "OUTPUT");
                    
                // kick them off
                errorGobbler.start();
                outputGobbler.start();                
                
                child.waitFor();
            }
            catch(InterruptedException e)
            {
                // do something to handle the timeout here
                child.destroy();
                throw new IllegalArgumentException("mplayer: decoding failed; process timed out.");
            }
            finally
            {
                timer.cancel();     // If the process returns within the timeout period, we have to stop the interrupter
                                    // so that it does not unexpectedly interrupt some other code later.

                Thread.interrupted();   // We need to clear the interrupt flag on the current thread just in case
                                        // interrupter executed after waitFor had already returned but before timer.cancel
                                        // took effect.
                                        //
                                        // Oh, and there's also Sun bug 6420270 to worry about here.
            }        	
	        
	        //destroy the process
	        child.destroy();
	        
			//check the result value
			int retvalue = child.exitValue();
	        if(retvalue == 0)
	        {
	        	in = new BufferedInputStream(new FileInputStream(tempFile));
	        	if(in.available() == 0)
	        	{
	        		in.close();
	        		throw new IllegalArgumentException("mplayer: decoding failed; output file has zero byte length.");
	        	}
	        }
	        else
	        {
	        	throw new IllegalArgumentException("mplayer: decoding failed; return value=" + retvalue);
	        }

	    }
		catch (IOException e)
		{
			System.out.println("io excep: " + e.getMessage());
			throw new IllegalArgumentException("io error: could not create temporary file or could not execute mplayer;");
	    }
	}

/*	public static void setTemporaryFolder(File tempFolder)
	{
		if(tempFolder.isDirectory())
			MPlayerAudioInputStream.tempFolder = tempFolder;
	}
	*/

	public int available() throws IOException
	{
		return in.available();
	}

	public void close() throws IOException
	{
		if(in != null)
			in.close();
		tempFile.delete();
		super.close();
	}

	public AudioFormat getFormat()
	{
		return new AudioFormat(sampleRate, 16, 1, true, false);
	}

	public long getFrameLength()
	{
		return -1;
	}


	public void mark(int readlimit) {
		in.mark(readlimit);
	}

	public boolean markSupported()
	{
		return in.markSupported();
	}

	public int read() throws IOException
	{
		return in.read();
	}

	public int read(byte[] b, int off, int len) throws IOException
	{
		return in.read(b, off, len);
	}

	public int read(byte[] b) throws IOException
	{
		return in.read(b);
	}

	public void reset() throws IOException
	{
		in.reset();
	}

	public long skip(long n) throws IOException
	{
		return in.skip(n);
	}

	protected void finalize() throws Throwable
	{
	    try
	    {
	        close();        // close open files
	    }
	    finally
	    {
	        super.finalize();
	    }
	}
	
	/**
	 * Just a simple TimerTask that interrupts the specified thread when run.
	 */
	class InterruptTimerTask extends TimerTask
	{

	    private Thread thread;

	    public InterruptTimerTask(Thread t)
	    {
	        this.thread = t;
	    }

	    public void run()
	    {
	        thread.interrupt();
	    }

	}	
	
	
	class StreamGobbler extends Thread
	{
	    InputStream is;
	    String type;
	    
	    StreamGobbler(InputStream is, String type)
	    {
	        this.is = is;
	        this.type = type;
	    }
	    
	    public void run()
	    {
	        try
	        {
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            String line=null;
	            while ( (line = br.readLine()) != null)
	            {
	                if(DEBUG)
	                	System.out.println(type + ">" + line);
	            }
	        } 
	        catch (IOException ioe)
	        {
	        	ioe.printStackTrace();  
	        }
	    }
	}
}
