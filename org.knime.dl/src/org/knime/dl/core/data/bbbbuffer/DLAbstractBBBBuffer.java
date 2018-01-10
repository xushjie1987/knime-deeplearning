package org.knime.dl.core.data.bbbbuffer;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

import org.knime.dl.core.data.DLBuffer;
import org.knime.dl.core.data.DLReadableBuffer;
import org.knime.dl.core.data.DLWritableBuffer;

/**
 * Abstract implementation of a ByteBufferBackedBuffer.
 * This class and all extending classes are not thread-safe because the underlying {@link ByteBuffer}
 * is not thread-safe.
 * 
 * @author Adrian Nembach, KNIME GmbH, Konstanz, Germany
 *
 */
abstract class DLAbstractBBBBuffer implements DLBuffer, DLReadableBuffer, DLWritableBuffer {
	
	protected static final int BYTE_SIZE = 1;
	protected static final int BOOL_SIZE = 1;
	protected static final int SHORT_SIZE = 2;
	protected static final int INT_SIZE = 4;
	protected static final int FLOAT_SIZE = 4;
	protected static final int LONG_SIZE = 8;
	protected static final int DOUBLE_SIZE = 8;
	
	
	private ByteBuffer m_storage;
	private boolean m_readMode;
	private final int m_elementSize;
	private int m_writeHead = 0;
	private int m_readHead = 0;
	
	protected DLAbstractBBBBuffer(final int capacity, final int elementSize, final boolean allocateDirect) {
		if (allocateDirect) {
			m_storage = ByteBuffer.allocateDirect(capacity * elementSize);
		} else {
			m_storage = ByteBuffer.allocate(capacity * elementSize);
		}
		m_elementSize = elementSize;
		
	}
	
	private void prepareBufferFor(boolean read) {
		if (read && !m_readMode) {
			// prepare read by flipping buffer and setting position to m_readHead
			m_storage.flip().position(m_readHead);
			m_readMode = true;
		} else if (!read && m_readMode) {
			// write mode
			// prepare write by clearing buffer and setting position to m_writeHead
			m_storage.clear().position(m_writeHead);
			m_readMode = false;
		}
	}
	
	private void incrementWriteHead(int elements) {
		m_writeHead += elements;
	}
	
	private void incrementReadHead(int elements) {
		m_readHead += elements;
	}
	
	
	// Internal putter
	
	
	protected void putIntInternal(int value) {
		prepareBufferFor(false);
		m_storage.putInt(value);
		incrementWriteHead(INT_SIZE);
	}
	
	protected void putIntInternal(int[] values) {
		prepareBufferFor(false);
		for (int val : values) {
			m_storage.putInt(val);
		}
		incrementWriteHead(values.length * INT_SIZE);
	}
	
	protected void putFloatInternal(float value) {
		prepareBufferFor(false);
		m_storage.putFloat(value);
		incrementWriteHead(FLOAT_SIZE);
	}
	
	protected void putFloatInternal(float[] values) {
		prepareBufferFor(false);
		for (float val : values) {
			m_storage.putFloat(val);
		}
		incrementWriteHead(values.length * FLOAT_SIZE);
	}
	
	protected void putLongInternal(long value) {
		prepareBufferFor(false);
		m_storage.putLong(value);
		incrementWriteHead(LONG_SIZE);
	}
	
	protected void putLongInternal(long[] values) {
		prepareBufferFor(false);
		for (long val : values) {
			m_storage.putLong(val);
		}
		incrementWriteHead(values.length * LONG_SIZE);
	}
	
	protected void putDoubleInternal(double value) {
		prepareBufferFor(false);
		m_storage.putDouble(value);
		incrementWriteHead(DOUBLE_SIZE);
	}
	
	protected void putDoubleInternal(double[] values) {
		prepareBufferFor(false);
		for (double val : values) {
			m_storage.putDouble(val);
		}
		incrementWriteHead(values.length * DOUBLE_SIZE);
	}
	
	// internal getter
	
	protected int getIntInternal() {
		prepareBufferFor(true);
		incrementReadHead(INT_SIZE);
		return m_storage.getInt();
	}
	
	protected float getFloatInternal() {
		prepareBufferFor(true);
		incrementReadHead(FLOAT_SIZE);
		return m_storage.getFloat();
	}
	
	protected long getLongInternal() {
		prepareBufferFor(true);
		incrementReadHead(LONG_SIZE);
		return m_storage.getLong();
	}
	
	protected double getDoubleInternal() {
		prepareBufferFor(true);
		incrementReadHead(DOUBLE_SIZE);
		return m_storage.getDouble();
	}
	
	protected void readArray(Consumer<ByteBuffer> accessor) {
		prepareBufferFor(true);
		// set limit position to 0 and limit to capacity
		m_storage.position(0).limit(m_storage.capacity());
		accessor.accept(m_storage);
		// set position to old read position
		m_storage.position(m_readHead).limit(m_writeHead);
	}
	
	
	

	@Override
	public void setSize(long size) throws BufferOverflowException, BufferUnderflowException {
		throw new UnsupportedOperationException();

	}
	
	@Override
	public long getCapacity() {
		return m_storage.capacity() / m_elementSize;
	}

	@Override
	public void resetWrite() {
		m_writeHead = 0;
	}

	@Override
	public void resetRead() {
		m_readHead = 0;
	}

	@Override
	public long size() {
		// the writeHead is measured in bytes
		return m_writeHead / m_elementSize;
	}

	@Override
	public void reset() {
		resetWrite();
		resetRead();

	}

	@Override
	public void close() {
		m_storage = null;
	}

}
