package org.knime.dl.core.data.bbbbuffer;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

import org.knime.dl.core.data.DLDefaultDoubleBuffer;
import org.knime.dl.core.data.DLReadableDoubleBuffer;
import org.knime.dl.core.data.DLWritableDoubleBuffer;

/**
 * ByteBuffer backed alternative to {@link DLDefaultDoubleBuffer}.
 * This class is not thread-safe.
 * 
 * @author Adrian Nembach, KNIME GmbH, Konstanz, Germany
 *
 */
public class DLBBBDoubleBuffer extends DLAbstractBBBBuffer implements DLReadableDoubleBuffer, DLWritableDoubleBuffer {

	protected DLBBBDoubleBuffer(final int capacity, final boolean allocateDirect) {
		super(capacity, DOUBLE_SIZE, allocateDirect);
	}

	@Override
	public void put(float value) throws BufferOverflowException {
		putDoubleInternal(value);
	}

	@Override
	public void putAll(float[] values) throws BufferOverflowException {
		for (float val : values) {
			putDoubleInternal(val);
		}
	}

	@Override
	public void put(short value) throws BufferOverflowException {
		putDoubleInternal(value);
	}

	@Override
	public void putAll(short[] values) throws BufferOverflowException {
		for (short val : values) {
			putDoubleInternal(val);
		}
	}

	@Override
	public void put(byte value) throws BufferOverflowException {
		putDoubleInternal(value);
	}

	@Override
	public void putAll(byte[] values) throws BufferOverflowException {
		for (byte val : values) {
			putDoubleInternal(val);
		}
	}

	@Override
	public void put(boolean value) throws BufferOverflowException {
		putDoubleInternal(value ? 1.0 : 0.0);
	}

	@Override
	public void putAll(boolean[] values) throws BufferOverflowException {
		for (boolean val : values) {
			putDoubleInternal(val ? 1.0 : 0.0);
		}
	}

	@Override
	public void put(int value) throws BufferOverflowException {
		putDoubleInternal(value);
	}

	@Override
	public void putAll(int[] values) throws BufferOverflowException {
		for (int val : values) {
			putDoubleInternal(val);
		}
	}

	@Override
	public void put(double value) throws BufferOverflowException {
		putDoubleInternal(value);
	}

	@Override
	public void putAll(double[] values) throws BufferOverflowException {
		putDoubleInternal(values);
	}

	@Override
	public double readNextDouble() throws BufferUnderflowException {
		return getDoubleInternal();
	}

	@Override
	public double[] toDoubleArray() {
		double[] res = new double[(int)getCapacity()];
		readArray(b -> {
			for (int i = 0; i < res.length; i++) {
				res[i] = b.getDouble();
			}
		});
		return res;
	}

}
