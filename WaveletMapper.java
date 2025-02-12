import java.util.*;
import java.util.zip.*;
import java.lang.Math.*;
import java.math.*;

public class WaveletMapper
{
	public static int[] transpose(int []src, int xdim)
	{
		int    ydim = src.length / xdim;
		int [] dst  = new int[src.length];
		
		int k = 0;
		for (int i = 0; i < ydim; i++)
		    for (int j = 0; j < xdim; j++)
		    	dst[k++] = src[j * ydim + i];
		
		return dst;
	}
	
	public static int[] half_transpose(int []src, int xdim)
	{
		int    ydim = src.length / xdim;
		int [] dst  = new int[src.length];
		
		int k = 0;
		for (int i = 0; i < ydim /2; i++)
		    for (int j = 0; j < xdim; j++)
		    	dst[k++] = src[j * ydim + i];
		
		return dst;
	}
	
	// Replaces a row or part of a row in the source.
	public static void daub4(int [] src, int offset, int xdim, int min)
	{
		double [] c       = {0.4829629, 0.8365163, 0.2241438, -0.129409};
		int    [] buffer = new int[xdim];
			

		int i     = 0;
		int k     = offset;
		int m     = xdim / 2;
		
		for(int j = 0; j <= xdim - 4; j += 2)
		{
			buffer[i] = (int)(c[0] * buffer[j + k] + c[1] * buffer[j + k + 1] + c[2] * buffer[j + k + 2] + c[3] * buffer[j + k + 3]);
			buffer[i + m] = (int)(c[3] * buffer[j + k] + c[2] * buffer[j + k + 1] + c[1] * buffer[j + k + 2] + c[0] * buffer[j + k + 3]);	
		}
		
		buffer[i] = (int)(c[0] * buffer[k + xdim - 2] + c[1] * buffer[k + xdim - 1] + c[2] * buffer[k] + c[3] * buffer[k + 1]);
		buffer[i + m] = (int)(c[3] * buffer[k + xdim - 2] + c[2] * buffer[k + xdim - 1] + c[1] * buffer[k] + c[0] * buffer[k + 1]);
		    
		for(i = 0; i < xdim; i++)
		    src[i + k] = buffer[i];
	}
	
	public static int [] forward_transform(int [] src, int xdim)
	{
		int [] buffer = new int[src.length];
		for(int i = 0; i < src.length; i++)
		{
			buffer[i] = src[i];
		}
		
		// Assumes image xdim is even.
		int ydim = src.length / xdim;
		int min  = xdim;
		// Could get stuck in a loop if xdim is a power of 2.
		while(min % 2 == 0)
			min /= 2;
		return buffer;
	}
	
	public static int[] getPixel(int[] blue, int[] green, int[] red, int xdim)
	{
        int ydim = blue.length / xdim;
        int [] pixel = new int[blue.length];
       
		int blue_shift  = 16;		
		int green_shift = 8;	
		
		int k = 0;
		for(int i = 0; i < ydim; i++)
		{
			for(int j = 0; j < xdim; j++)
			{
				pixel[k] = (blue[k] << blue_shift) + (green[k] << green_shift) + red[k];
			    k++;
			}
		}
		return pixel;
	}
	
	
	
}