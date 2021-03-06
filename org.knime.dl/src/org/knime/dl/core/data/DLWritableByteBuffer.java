package org.knime.dl.core.data;

import java.nio.BufferOverflowException;

/**
 * A {@link DLWritableBuffer writable} byte buffer.
 *
 * @author Marcel Wiedenmann, KNIME GmbH, Konstanz, Germany
 * @author Christian Dietz, KNIME GmbH, Konstanz, Germany
 */
public interface DLWritableByteBuffer extends DLWritableBitBuffer {

	/**
	 * Writes a value into the buffer.
	 *
	 * @param value the value
	 * @throws BufferOverflowException if the buffer's {@link #getCapacity() capacity} is exceeded.
	 */
	void put(byte value) throws BufferOverflowException;

	/**
	 * Copies an array into the buffer.
	 *
	 * @param values the array
	 * @throws BufferOverflowException if the buffer's {@link #getCapacity() capacity} is exceeded.
	 */
	void putAll(byte[] values) throws BufferOverflowException;
}
