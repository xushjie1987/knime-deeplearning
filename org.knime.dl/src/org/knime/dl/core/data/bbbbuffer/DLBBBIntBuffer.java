package org.knime.dl.core.data.bbbbuffer;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

import org.knime.dl.core.data.DLReadableIntBuffer;
import org.knime.dl.core.data.DLWritableIntBuffer;

public class DLBBBIntBuffer extends DLAbstractBBBBuffer implements DLReadableIntBuffer, DLWritableIntBuffer {

	protected DLBBBIntBuffer(int capacity) {
		super(capacity, INT_SIZE);
	}

	@Override
	public double readNextDouble() throws BufferUnderflowException {
		return getIntInternal();
	}

	@Override
	public double[] toDoubleArray() {
		final double[] res = new double[(int)getCapacity()];
		readArray(b -> {
			for (int i = 0; i < res.length; i++) {
				res[i] = b.getInt();
			}
		});
		return res;
	}

	@Override
	public long readNextLong() throws BufferUnderflowException {
		return getIntInternal();
	}

	@Override
	public long[] toLongArray() {
		final long[] res = new long[(int)getCapacity()];
		readArray(b -> {
			for (int i = 0; i < res.length; i++) {
				res[i] = b.getInt();
			}
		});
		return res;
	}

	@Override
	public void put(short value) throws BufferOverflowException {
		putIntInternal(value);
	}

	@Override
	public void putAll(short[] values) throws BufferOverflowException {
		for (short val : values) {
			putIntInternal(val);
		}
	}

	@Override
	public void put(byte value) throws BufferOverflowException {
		putIntInternal(value);
	}

	@Override
	public void putAll(byte[] values) throws BufferOverflowException {
		for (byte val : values) {
			putIntInternal(val);
		}
	}

	@Override
	public void put(boolean value) throws BufferOverflowException {
		putIntInternal(value ? 1 : 0);
	}

	@Override
	public void putAll(boolean[] values) throws BufferOverflowException {
		for (boolean val : values) {
			putIntInternal(val ? 1 : 0);
		}
	}

	@Override
	public void put(int value) throws BufferOverflowException {
		putIntInternal(value);
	}

	@Override
	public void putAll(int[] values) throws BufferOverflowException {
		for (int val : values) {
			putIntInternal(val);
		}
	}

	@Override
	public int readNextInt() throws BufferUnderflowException {
		return getIntInternal();
	}

	@Override
	public int[] toIntArray() {
		int[] res = new int[(int)getCapacity()];
		readArray(b -> {
			for (int i = 0; i < res.length; i++) {
				res[i] = b.getInt();
			}
		});
		return res;
	}

}
