package edu.vu.isis.ammo.util;

/**
 * Taken from http://www.javafaq.nu/java-example-code-1078.html and modifed to 
 * handle big as well as little endian.
 * 
 */
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.nio.ByteOrder;

public class EndianOutputStream extends FilterOutputStream {

	protected int written;

	protected ByteOrder order;

	public EndianOutputStream(OutputStream out) {
		super(out);
		order = ByteOrder.LITTLE_ENDIAN;
	}
	
	public boolean setOrder(ByteOrder order) {
		if (this.order == order) return false;
		this.order = order;
		return true;
	}
	public ByteOrder getOrder() {
		return this.order;
	}

	public void write(int b) throws IOException {
		out.write(b);
		written++;
	}

	public void write(byte[] data, int offset, int length) throws IOException {
		out.write(data, offset, length);
		written += length;
	}

	public void writeBoolean(boolean b) throws IOException {
		if (b)
			this.write(1);
		else
			this.write(0);
	}

	public void writeByte(int b) throws IOException {
		out.write(b);
		written++;
	}

	public void writeShort(int s) throws IOException {
		if (order == ByteOrder.BIG_ENDIAN) {
			out.write((s >>> 8) & 0xFF);
			out.write(s & 0xFF);
	    } else if (order == ByteOrder.LITTLE_ENDIAN) {
			out.write(s & 0xFF);
			out.write((s >>> 8) & 0xFF);
		}
		written += 2;
	}

	public void writeChar(int c) throws IOException {
		if (order == ByteOrder.BIG_ENDIAN) {
			out.write((c >>> 8) & 0xFF);
			out.write(c & 0xFF);
		} else if (order == ByteOrder.LITTLE_ENDIAN) {
			out.write(c & 0xFF);
			out.write((c >>> 8) & 0xFF);
		}
		written += 2;
	}

	public void writeInt(int i) throws IOException {
		if (order == ByteOrder.BIG_ENDIAN) {
			out.write((i >>> 24) & 0xFF);
			out.write((i >>> 16) & 0xFF);
			out.write((i >>> 8) & 0xFF);
			out.write(i & 0xFF);
		} else if (order == ByteOrder.LITTLE_ENDIAN) {
			out.write(i & 0xFF);
			out.write((i >>> 8) & 0xFF);
			out.write((i >>> 16) & 0xFF);
			out.write((i >>> 24) & 0xFF);
		}
		written += 4;

	}

	public void writeLong(long l) throws IOException {
		if (order == ByteOrder.BIG_ENDIAN) {
			out.write((int) (l >>> 56) & 0xFF);
			out.write((int) (l >>> 48) & 0xFF);
			out.write((int) (l >>> 40) & 0xFF);
			out.write((int) (l >>> 32) & 0xFF);
			out.write((int) (l >>> 24) & 0xFF);
			out.write((int) (l >>> 16) & 0xFF);
			out.write((int) (l >>> 8) & 0xFF);
			out.write((int) l & 0xFF);
		} else if (order == ByteOrder.LITTLE_ENDIAN) {
			out.write((int) l & 0xFF);
			out.write((int) (l >>> 8) & 0xFF);
			out.write((int) (l >>> 16) & 0xFF);
			out.write((int) (l >>> 24) & 0xFF);
			out.write((int) (l >>> 32) & 0xFF);
			out.write((int) (l >>> 40) & 0xFF);
			out.write((int) (l >>> 48) & 0xFF);
			out.write((int) (l >>> 56) & 0xFF);
		}
		written += 8;

	}

	public final void writeFloat(float f) throws IOException {
		this.writeInt(Float.floatToIntBits(f));
	}

	public final void writeDouble(double d) throws IOException {
		this.writeLong(Double.doubleToLongBits(d));
	}

	public void writeBytes(String s) throws IOException {
		int length = s.length();
		for (int i = 0; i < length; i++) {
			out.write((byte) s.charAt(i));
		}
		written += length;
	}

	public void writeChars(String s) throws IOException {
		int length = s.length();
		if (order == ByteOrder.BIG_ENDIAN) {
			for (int i = 0; i < length; i++) {
				int c = s.charAt(i);
				out.write((c >>> 8) & 0xFF);
				out.write(c & 0xFF);
			}
		} else if (order == ByteOrder.LITTLE_ENDIAN) {
			for (int i = 0; i < length; i++) {
				int c = s.charAt(i);
				out.write(c & 0xFF);
				out.write((c >>> 8) & 0xFF);
			}
		}
		written += length * 2;
	}

	public void writeUTF(String s) throws IOException {

		int numchars = s.length();
		int numbytes = 0;

		for (int i = 0; i < numchars; i++) {
			int c = s.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007F))
				numbytes++;
			else if (c > 0x07FF)
				numbytes += 3;
			else
				numbytes += 2;
		}

		if (numbytes > 65535)
			throw new UTFDataFormatException();
		if (order == ByteOrder.BIG_ENDIAN) {
			out.write(numbytes & 0xFF);
			out.write((numbytes >>> 8) & 0xFF);
			for (int i = 0; i < numchars; i++) {
				int c = s.charAt(i);
				if ((c >= 0x0001) && (c <= 0x007F)) {
					out.write(c);
				} else if (c > 0x07FF) {
					out.write(0x80 | (c & 0x3F));
					out.write(0x80 | ((c >> 6) & 0x3F));
					out.write(0xE0 | ((c >> 12) & 0x0F));
					written += 2;
				} else {
					out.write(0x80 | (c & 0x3F));
					out.write(0xC0 | ((c >> 6) & 0x1F));
					written += 1;
				}
			}
		} else if (order == ByteOrder.LITTLE_ENDIAN) {
			out.write((numbytes >>> 8) & 0xFF);
			out.write(numbytes & 0xFF);
			for (int i = 0; i < numchars; i++) {
				int c = s.charAt(i);
				if ((c >= 0x0001) && (c <= 0x007F)) {
					out.write(c);
				} else if (c > 0x07FF) {
					out.write(0xE0 | ((c >> 12) & 0x0F));
					out.write(0x80 | ((c >> 6) & 0x3F));
					out.write(0x80 | (c & 0x3F));
					written += 2;
				} else {
					out.write(0xC0 | ((c >> 6) & 0x1F));
					out.write(0x80 | (c & 0x3F));
					written += 1;
				}
			}
		}
		written += numchars + 2;
	}

	public int size() {
		return this.written;
	}
}