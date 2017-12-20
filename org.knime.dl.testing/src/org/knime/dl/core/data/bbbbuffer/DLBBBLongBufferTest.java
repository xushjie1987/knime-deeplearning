package org.knime.dl.core.data.bbbbuffer;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.function.IntToLongFunction;

import org.junit.Test;

public class DLBBBLongBufferTest {

	@Test
	public void testPut() throws Exception {

		DLBBBLongBuffer buffer = new DLBBBLongBuffer(10);
		// test put boolean
		buffer.put(true);
		assertEquals(1L, buffer.readNextLong());
		// test put byte
		buffer.put((byte)15);
		assertEquals(15, buffer.readNextLong());
		// test put short
		buffer.put((short) 122);
		assertEquals(122, buffer.readNextLong());
		// test put int
		buffer.put(1337);
		assertEquals(1337, buffer.readNextLong());
		// test put long
		buffer.put(1239394L);
		assertEquals(1239394, buffer.readNextLong());
		buffer.close();
	}

	@Test
	public void testPutAll() throws Exception {
		Random rand = new Random();
		int buffSize = 10;
		DLBBBLongBuffer buffer = new DLBBBLongBuffer(buffSize);
		// test boolean
		boolean[] expectedBooleans = TestUtil.createBooleanArray(rand, buffSize);
		buffer.putAll(expectedBooleans);
		assertBufferEquality(buffer, buffSize, i -> expectedBooleans[i] ? 1L : 0L);
		buffer.reset();
		// test byte
		byte[] expectedBytes = TestUtil.createByteArray(rand, buffSize);
		buffer.putAll(expectedBytes);
		assertBufferEquality(buffer, buffSize, i -> expectedBytes[i]);
		buffer.reset();
		// test short
		short[] expectedShorts = TestUtil.createShortArray(rand, buffSize);
		buffer.putAll(expectedShorts);
		assertBufferEquality(buffer, buffSize, i -> expectedShorts[i]);
		buffer.reset();
		// test int
		int[] expectedInts = TestUtil.createIntArray(rand, buffSize);
		buffer.putAll(expectedInts);
		assertBufferEquality(buffer, buffSize, i -> expectedInts[i]);
		buffer.reset();
		// test long
		long[] expectedLongs = TestUtil.createLongArray(rand, buffSize);
		buffer.putAll(expectedLongs);
		assertBufferEquality(buffer, buffSize, i -> expectedLongs[i]);
		buffer.reset();
		buffer.close();
	}

	@Test
	public void testToArray() throws Exception {
		int buffSize = 10;
		DLBBBLongBuffer buffer = new DLBBBLongBuffer(buffSize);
		long[] expectedLongs = TestUtil.createLongArray(new Random(), buffSize);
		buffer.putAll(expectedLongs);
		// move read position
		assertEquals(expectedLongs[0], buffer.readNextLong(), TestUtil.EPSILON);
		// read entire buffer content as long array
		assertArrayEquals(expectedLongs, buffer.toLongArray());
		// check if the previous read position is still valid
		assertEquals(expectedLongs[1], buffer.readNextLong(), TestUtil.EPSILON);
		buffer.close();
	}

	private void assertBufferEquality(DLBBBLongBuffer buffer, int numElements, IntToLongFunction elementAccessor) {
		for (int i = 0; i < numElements; i++) {
			assertEquals(elementAccessor.applyAsLong(i), buffer.readNextLong());
		}
	}
}
