package org.knime.dl.core.data.bbbbuffer;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.function.IntToDoubleFunction;

import org.junit.Test;

public class DLBBBFloatBufferTest {

	@Test
	public void testPut() throws Exception {

		DLBBBFloatBuffer buffer = new DLBBBFloatBuffer(10);
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
		buffer.close();
	}

	@Test
	public void testPutAll() throws Exception {
		Random rand = new Random();
		int buffSize = 10;
		DLBBBFloatBuffer buffer = new DLBBBFloatBuffer(buffSize);
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
		// test float
		float[] expectedFloats = TestUtil.createFloatArray(rand, buffSize);
		buffer.putAll(expectedFloats);
		assertBufferEquality(buffer, buffSize, i -> expectedFloats[i]);
		buffer.reset();
		buffer.close();
	}

	@Test
	public void testToArray() throws Exception {
		int buffSize = 10;
		DLBBBFloatBuffer buffer = new DLBBBFloatBuffer(buffSize);
		float[] expectedFloats = TestUtil.createFloatArray(new Random(), buffSize);
		buffer.putAll(expectedFloats);
		// move read position
		assertEquals(expectedFloats[0], buffer.readNextFloat(), TestUtil.EPSILON);
		// read entire buffer content as float
		assertArrayEquals(expectedFloats, buffer.toFloatArray(), (float)TestUtil.EPSILON);
		// read entire buffer content as double
		assertArrayEquals(TestUtil.floatToDouble(expectedFloats), buffer.toDoubleArray(), TestUtil.EPSILON);
		// check if the previous read position is still valid
		assertEquals(expectedFloats[1], buffer.readNextDouble(), TestUtil.EPSILON);
		buffer.close();
	}

	private void assertBufferEquality(DLBBBFloatBuffer buffer, int numElements, IntToDoubleFunction elementAccessor) {
		for (int i = 0; i < numElements; i++) {
			assertEquals(elementAccessor.applyAsDouble(i), buffer.readNextFloat(), TestUtil.EPSILON);
		}
	}
}
