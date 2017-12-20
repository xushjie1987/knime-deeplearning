package org.knime.dl.core.data.bbbbuffer;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

import org.knime.dl.core.data.DLDefaultFloatBuffer;
import org.knime.dl.core.data.DLReadableFloatBuffer;
import org.knime.dl.core.data.DLWritableFloatBuffer;

/**
 * ByteBuffer backed alternative to {@link DLDefaultFloatBuffer}.
 * This class is not thread-safe.
 * 
 * @author Adrian Nembach, KNIME GmbH, Konstanz, Germany
 *
 */
public class DLBBBFloatBuffer extends DLAbstractBBBBuffer implements DLReadableFloatBuffer, DLWritableFloatBuffer {

	protected DLBBBFloatBuffer(int capacity) {
		super(capacity, FLOAT_SIZE);
	}

	@Override
	public double readNextDouble() throws BufferUnderflowException {
		return getFloatInternal();
	}

	@Override
	public double[] toDoubleArray() {
		final double[] res = new double[(int)getCapacity()];
		readArray(b -> {
			for (int i = 0; i < res.length; i++) {
				res[i] = b.getFloat();
			}
		});
		return res;
	}

	@Override
	public void put(short value) throws BufferOverflowException {
		putFloatInternal(value);
	}

	@Override
	public void putAll(short[] values) throws BufferOverflowException {
		for (short val : values) {
			putFloatInternal(val);
		}
	}

	@Override
	public void put(byte value) throws BufferOverflowException {
		putFloatInternal(value);
	}

	@Override
	public void putAll(byte[] values) throws BufferOverflowException {
		for (byte val : values) {
			putFloatInternal(val);
		}
	}

	@Override
	public void put(boolean value) throws BufferOverflowException {
		putFloatInternal(value ? 1.0f : 0.0f);
	}

	@Override
	public void putAll(boolean[] values) throws BufferOverflowException {
		for (boolean val : values) {
			put(val);
		}
	}

	@Override
	public void put(float value) throws BufferOverflowException {
		putFloatInternal(value);
	}

	@Override
	public void putAll(float[] values) throws BufferOverflowException {
		putFloatInternal(values);
	}

	@Override
	public float readNextFloat() throws BufferUnderflowException {
		return getFloatInternal();
	}

	@Override
	public float[] toFloatArray() {
		float[] res = new float[(int)getCapacity()];
		readArray(b -> {
			for (int i = 0; i < res.length; i++) {
				res[i] = b.getFloat();
			}
		});
		return res;
	}

}
