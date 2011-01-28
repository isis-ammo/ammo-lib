package edu.vu.isis.ammo.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UTFDataFormatException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.util.Log;

/**
 * Taken from http://www.javafaq.nu/java-example-code-1078.html and modified to 
 * handle big as well as little endian.
 * Works in conjunction with {@link EndianOutputStream.java}
 * 
 * We would like to extend DataInputStream but we can't as it is 'final'.
 */

public class EndianInputStream extends FilterInputStream {

	protected int read;

	protected ByteOrder order;

	public EndianInputStream(InputStream in) {
		super(in);
		order = ByteOrder.LITTLE_ENDIAN;
		read = 0;
	}
	
	public boolean setOrder(ByteOrder order) {
		if (this.order == order) return false;
		this.order = order;
		return true;
	}
	public ByteOrder getOrder() {
		return this.order;
	}
	
	// The work methods
	
	
    @Override
	public final int read() throws IOException {
		int b = in.read();
		read++;
		return b;
	}
    
    @Override
	public final int read(byte[] b) throws IOException {
		int cnt = in.read(b);
		if (cnt < 0) return cnt;
		read += cnt;
		return cnt;
	}

    /**
     * Reads up to len bytes of data from the contained input stream 
     * into an array of bytes.
     */
    @Override
	public final int read(byte[] data, int offset, int length) throws IOException {
		int cnt = in.read(data, offset, length);
		if (cnt < 0) return cnt;
		read += cnt;
		return cnt;
	}

    public final void readFully(byte[] b) throws IOException {
    	if (b == null) throw new NullPointerException("byte array is missing");
    	if (b.length < 1) return;
    	
    	int cnt = in.read(b, 0, b.length);
    	if (cnt < 0) return;
		read += cnt;
		return;
    }
    
    public final void readFully(byte[] b, int off, int len) throws IOException {
    	if (b == null) 
    		throw new NullPointerException("byte array is missing");
    	if (off < 0) 
    		throw new IndexOutOfBoundsException("negative array offset");
    	if (len < 1) 
    		throw new IndexOutOfBoundsException("length is not whole number");
    	if (off+len > b.length) 
    		throw new IndexOutOfBoundsException("more bytes than array can hold were requested");
    	if (b.length < 1) return;
    	
    	int cnt = in.read(b, off, len);
    	if (cnt < 0) return;
		read += cnt;
		return;
    }
    public int skipBytes(int n) throws IOException {
    	int ix;
    	for (ix=0; ix < n; ++ix) in.read();
		return ix;
    }
    /**
     * See the general contract of the readBoolean method of DataInput.
     */
	public boolean readBoolean() throws IOException {
		return (in.read() > 0) ? true : false;
	}
    /**
     * See the general contract of the readByte method of DataInput.
     * 
     * @return the byte read
     * @throws IOException
     */
	public byte readByte() throws IOException {
		byte b = (byte) in.read();
		read++;
		return b;
	}
	
	public int readUnsignedByte() throws IOException {
		byte b = (byte) in.read();
		read++;
		return b;
	}

	public int readShort() throws IOException {
		byte[] b = new byte[2];
		int cnt = in.read(b, 0, 2);
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(order);
		read += cnt;
		return bb.getShort();
	}
	
	public int readUnsignedShort() throws IOException {
		byte[] b = new byte[2];
		int cnt = in.read(b, 0, 2);
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(order);
		read += cnt;
		return bb.getInt();
	}
	
	public char readChar() throws IOException {
		byte[] b = new byte[2];
		int cnt = in.read(b, 0, 2);
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(order);
		read += cnt;
		return bb.getChar();
	}

//	{
//		dis.readFully( sizeBuf );
//		ByteBuffer bb = ByteBuffer.wrap( sizeBuf );
//		bb.order( ByteOrder.LITTLE_ENDIAN );
//		bytesToRead = bb.getInt();
//	}
	public int readInt() throws IOException {
		byte[] b = new byte[4];
		int cnt = in.read(b, 0, 4);
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(order);
		read += cnt;
		return bb.getInt();
	}
	
	public long readUInt() throws IOException {
		byte[] b = new byte[4];
		int cnt = in.read(b, 0, 4);
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(order);
		read += cnt;
		long l = (bb.getInt() & 0xffffffffL);
		Log.i("EndianInputStream", Long.toHexString(l) );
		return l;
	}

	public long readLong() throws IOException {
		byte[] b = new byte[8];
		int cnt = in.read(b, 0, 8);
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(order);
		read += cnt;
		return bb.getLong();
	}

	public final float readFloat() throws IOException {
		byte[] b = new byte[4];
		int cnt = in.read(b, 0, 4);
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(order);
		read += cnt;
		return bb.getFloat();
	}

	public final double readDouble() throws IOException {
		byte[] b = new byte[8];
		int cnt = in.read(b, 0, 8);
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(order);
		read += cnt;
		return bb.getDouble();
	}

	public String readLine() throws IOException {
		StringBuffer str = new StringBuffer();
		int ix = 0;
		for (; ix < Integer.MAX_VALUE; ++ix) {
			int first = in.read() & 0XFF;
			if (first == '\r') {
				first = in.read() & 0XFF;
				if (first == '\n') break;
			}
			if (first == '\n') break;
			final int second = in.read() & 0XFF;
			
			if (order == ByteOrder.BIG_ENDIAN) {
				str.insert(ix, ((first << 8) | second));
			} else if (order == ByteOrder.LITTLE_ENDIAN) {
				str.insert(ix, ((second << 8) | first));
				break;
			}
		}
		read += ix * 2;
		return str.toString();
	}
	
	public String readUTF(String s) throws IOException {
		int utfLength = this.readUnsignedShort();
        StringBuffer sb = new StringBuffer(utfLength);
		int numbytes = 0;

		for (int i = 0; i < utfLength; i++) {
			// get first group byte
			int ga = in.read() & 0xFF;
			// check to see if this is the only byte
			// of the form 0xxx xxx'b
			if ((ga & 0x80) == 0) {
				sb.insert(i, ga);
				read += 1;
				continue;
			}
			// check to see if there are two bytes
			// i.e. the first byte of the form 110x xxx'b
			if ((ga & 0xC0) == 0 && (ga | 0x20) > 0) {
				int gb = in.read() & 0xFF;
				int gc = ((ga & 0x1F) << 6) | (gb & 0x3F);
				sb.insert(i, gc);
				read += 2;
				continue;
			}
			// check to see if there are three bytes
			// i.e. the first byte of the form 1110 xxx'b
			if ((ga & 0xE0) == 0 && (ga | 0x10) > 0) {
				int gb = in.read() & 0xFF;
				int gc = in.read() & 0xFF;
				int gd = ((ga & 0x0F) << 12) | ((gb & 0x3F) << 6) | (gc & 0x3F);
				sb.insert(i, gd);
				read += 3;
				continue;
			}
		}
		if (numbytes > 65535)
			throw new UTFDataFormatException();
		return sb.toString();
	}
    
	// The following are not provided by DataInputStream
	// They take a length argument and are intended as 
	// complements to the write methods found in DataOutputStream.
	
	public String readBytes(int length) throws IOException {
		//int length = this.readInt();
		char[] str = new char[length];
		for (int i = 0; i < length; i++) {
			str[i] = (char) in.read();
		}
		read += length;
		return new String(str);
	}

	public String readChars(int length) throws IOException {
		// int length = this.readInt();
		StringBuffer str = new StringBuffer(length * 2);
		
		if (order == ByteOrder.BIG_ENDIAN) {
			for (int i = 0; i < length; i++) {
				final int first = in.read() & 0XFF;
				final int second = in.read() & 0XFF;
				str.insert(i, ((first << 8) | second) );
			}
		} else if (order == ByteOrder.LITTLE_ENDIAN) {
			for (int i = 0; i < length; i++) {
				final int first = in.read() & 0XFF;
				final int second = in.read() & 0XFF;
				str.insert(i, ((second << 8) | first) );
			}
		}
		read += length * 2;
		return new String(str);
	}

	public int size() {
		return this.read;
	}
}



