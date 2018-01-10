package org.knime.dl.core.data.bbbbuffer;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

import org.knime.dl.core.data.DLDefaultLongBuffer;
import org.knime.dl.core.data.DLReadableLongBuffer;
import org.knime.dl.core.data.DLWritableLongBuffer;

/**
 * ByteBuffer backed alternative to {@link DLDefaultLongBuffer}.
 * This class is not thread-safe.
 * @author Adrian Nembach, KNIME GmbH, Konstanz, Germany
 *
 */
public class DLBBBLongBuffer extends DLAbstractBBBBuffer implements DLReadableLongBuffer, DLWritableLongBuffer {

	protected DLBBBLongBuffer(final int capacity, final boolean allocateDirect) {
		super(capacity, LONG_SIZE, allocateDirect);
	}

	@Override
	public void put(int value) throws BufferOverflowException {
		putLongInternal(value);
	}

	@Override
	public void putAll(int[] values) throws BufferOverflowException {
		for (int val : values) {
			putLongInternal(val);
		}
	}

	@Override
	public void put(short value) throws BufferOverflowException {
		putLongInternal(value);
	}

	@Override
	public void putAll(short[] values) throws BufferOverflowException {
		for (short val : values) {
			putLongInternal(val);
		}
	}

	@Override
	public void put(byte value) throws BufferOverflowException {
		putLongInternal(value);
	}

	@Override
	public void putAll(byte[] values) throws BufferOverflowException {
		for (byte val : values) {
			putLongInternal(val);
		}
	}

	@Override
	public void put(boolean value) throws BufferOverflowException {
		putLongInternal(value ? 1L : 0L);
	}

	@Override
	public void putAll(boolean[] values) throws BufferOverflowException {
		for (boolean val : values) {
			putLongInternal(val ? 1L : 0L);
		}
	}

	@Override
	public void put(long value) throws BufferOverflowException {
		putLongInternal(value);
	}

	@Override
	public void putAll(long[] values) throws BufferOverflowException {
		putLongInternal(values);
	}

	@Override
	public long readNextLong() throws BufferUnderflowException {
		return getLongInternal();
	}

	@Override
	public long[] toLongArray() {
		long[] res = new long[(int)getCapacity()];
		readArray(b -> {
			for (int i = 0; i < res.length; i++) {
				res[i] = b.getLong();
			}
		});
		return res;
	}

}
