package org.knime.dl.core.data.bbbbuffer;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.function.IntToDoubleFunction;

import org.junit.Test;

public class DLBBBDoubleBufferTest {
	
	
	@Test
	public void testPut() throws Exception {
		
		DLBBBDoubleBuffer buffer = new DLBBBDoubleBuffer(10, false);
		// test put boolean
		buffer.put(true);
		assertEquals(1.0, buffer.readNextDouble(), TestUtil.EPSILON);
		// test put byte
		buffer.put((byte)15);
		assertEquals(15, buffer.readNextDouble(), TestUtil.EPSILON);
		// test put short
		buffer.put((short) 122);
		assertEquals(122, buffer.readNextDouble(), TestUtil.EPSILON);
		// test put int
		buffer.put(1337);
		assertEquals(1337, buffer.readNextDouble(), TestUtil.EPSILON);
		// test put float
		buffer.put(3.4F);
		assertEquals(3.4, buffer.readNextDouble(), TestUtil.EPSILON);
		// test put double
		buffer.put(3.7);
		assertEquals(3.7, buffer.readNextDouble(), TestUtil.EPSILON);
		buffer.close();
	}
	
	@Test
	public void testPutAll() throws Exception {
		Random rand = new Random();
		int buffSize = 10;
		DLBBBDoubleBuffer buffer = new DLBBBDoubleBuffer(buffSize, false);
		// test boolean
		boolean[] expectedBooleans = TestUtil.createBooleanArray(rand, buffSize);
		buffer.putAll(expectedBooleans);
		assertBufferEquality(buffer, buffSize, i -> expectedBooleans[i] ? 1.0 : 0.0);
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
		// test float
		float[] expectedFloats = TestUtil.createFloatArray(rand, buffSize);
		buffer.putAll(expectedFloats);
		assertBufferEquality(buffer, buffSize, i -> expectedFloats[i]);
		buffer.reset();
		// test double
		double[] expectedDoubles = TestUtil.createDoubleArray(rand, buffSize);
		buffer.putAll(expectedDoubles);
		assertBufferEquality(buffer, buffSize, i -> expectedDoubles[i]);
		buffer.close();
	}
	
	@Test
	public void testToDoubleArray() throws Exception {
		int buffSize = 10;
		DLBBBDoubleBuffer buffer = new DLBBBDoubleBuffer(buffSize, false);
		double[] expectedDoubles = TestUtil.createDoubleArray(new Random(), buffSize);
		buffer.putAll(expectedDoubles);
		// move read position
		assertEquals(expectedDoubles[0], buffer.readNextDouble(), TestUtil.EPSILON);
		// read entire buffer content
		assertArrayEquals(expectedDoubles, buffer.toDoubleArray(), TestUtil.EPSILON);
		// check if the previous read position is stil valid
		assertEquals(expectedDoubles[1], buffer.readNextDouble(), TestUtil.EPSILON);
		buffer.close();
	}
	
	private void assertBufferEquality(DLBBBDoubleBuffer buffer, int numElements, IntToDoubleFunction elementAccessor) {
		for (int i = 0; i < numElements; i++) {
			assertEquals(elementAccessor.applyAsDouble(i), buffer.readNextDouble(), TestUtil.EPSILON);
		}
	}

}
