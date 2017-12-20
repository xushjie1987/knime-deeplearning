package org.knime.dl.core.data.bbbbuffer;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;
import java.util.function.IntUnaryOperator;

import org.junit.Test;

public class DLBBBIntBufferTest {

	@Test
	public void testPut() throws Exception {

		DLBBBIntBuffer buffer = new DLBBBIntBuffer(10);
		// test put boolean
		buffer.put(true);
		assertEquals(1.0, buffer.readNextDouble(), TestUtil.EPSILON);
		// test put byte
		buffer.put((byte)15);
		assertEquals(15, buffer.readNextLong(), TestUtil.EPSILON);
		// test put short
		buffer.put((short) 122);
		assertEquals(122, buffer.readNextInt(), TestUtil.EPSILON);
		// test put int
		buffer.put(1337);
		assertEquals(1337, buffer.readNextDouble(), TestUtil.EPSILON);
		
		assertEquals(4L, buffer.size());
		buffer.close();
	}

	@Test
	public void testPutAll() throws Exception {
		Random rand = new Random();
		int buffSize = 10;
		DLBBBIntBuffer buffer = new DLBBBIntBuffer(buffSize);
		// test boolean
		boolean[] expectedBooleans = TestUtil.createBooleanArray(rand, buffSize);
		buffer.putAll(expectedBooleans);
		assertBufferEquality(buffer, buffSize, i -> expectedBooleans[i] ? 1 : 0);
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
		buffer.close();
	}

	@Test
	public void testToArray() throws Exception {
		int buffSize = 10;
		DLBBBIntBuffer buffer = new DLBBBIntBuffer(buffSize);
		int[] expectedInts = TestUtil.createIntArray(new Random(), buffSize);
		buffer.putAll(expectedInts);
		// move read position
		assertEquals(expectedInts[0], buffer.readNextInt());
		// read entire buffer content as int array
		assertArrayEquals(expectedInts, buffer.toIntArray());
		// read entire buffer content as long array
		assertArrayEquals(Arrays.stream(expectedInts).mapToLong(i -> i).toArray(), buffer.toLongArray());
		// read entire buffer content as double
		assertArrayEquals(Arrays.stream(expectedInts).mapToDouble(i -> i).toArray(),
				buffer.toDoubleArray(), TestUtil.EPSILON);
		// check if the previous read position is still valid
		assertEquals(expectedInts[1], buffer.readNextInt(), TestUtil.EPSILON);
		buffer.close();
	}

	private void assertBufferEquality(DLBBBIntBuffer buffer, int numElements, IntUnaryOperator elementAccessor) {
		for (int i = 0; i < numElements; i++) {
			assertEquals(elementAccessor.applyAsInt(i), buffer.readNextInt());
		}
	}
}
