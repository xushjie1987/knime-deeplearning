package org.knime.dl.core.data.bbbbuffer;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.knime.dl.core.data.DLDefaultFloatBuffer;
import org.knime.dl.core.data.DLReadableFloatBuffer;
import org.knime.dl.core.data.DLWritableFloatBuffer;


public class DLBBBBufferPerformanceTest {

	@Test
	public void testDirectVsNotDirect() throws Exception {
		int dataSize = 100000;
		int numIterations = 20000;
		DLBBBFloatBuffer directBuffer = new DLBBBFloatBuffer(dataSize, true);
		DLBBBFloatBuffer nonDirectBuffer = new DLBBBFloatBuffer(dataSize, false);
		
		System.out.println("Direct buffer:");
		runMicroBench(directBuffer, dataSize, numIterations);
		
		System.out.println("Not direct buffer:");
		runMicroBench(nonDirectBuffer, dataSize, numIterations);
		
	}
	
	@Test
	public void testBBBBBVsDefault() throws Exception {
		int dataSize = 100000;
		int numIterations = 20000;
		DLBBBFloatBuffer directBuffer = new DLBBBFloatBuffer(dataSize, true);
		DLDefaultFloatBuffer defaultBuffer = new DLDefaultFloatBuffer(dataSize);
		
		System.out.println("Direct buffer:");
		runMicroBench(directBuffer, dataSize, numIterations);
		
		System.out.println("Default buffer:");
		runMicroBench(defaultBuffer, dataSize, numIterations);
	}

	private <T extends DLReadableFloatBuffer & DLWritableFloatBuffer> void runMicroBench(T buffer, int dataSize, int numIterations) {
		Random rand = new Random();
		// create some data
		float[] data = TestUtil.createFloatArray(rand, dataSize);
		float overallSum = 0;
		// warmup
		for (int i = 0; i < 15000; i++) {
			overallSum += doWriteRead(data, buffer);
			buffer.reset();
		}
		System.out.println("Done with warmup, start benchmarking");
		// benchmarking
		long startTime = System.nanoTime();
		for (int i = 0; i < numIterations; i++) {
			overallSum += doWriteRead(data, buffer);
			buffer.reset();
		}
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		double avgTime = ((double)totalTime) / numIterations;
		
		// only print this to avoid dead code removal by the JIT
		System.out.println("Ignore this: " + overallSum);
		System.out.println("Total time: " + totalTime + " Avg time: " + avgTime);
	}
	
	private <T extends DLReadableFloatBuffer & DLWritableFloatBuffer> float doWriteRead(float[] data, T buffer) {
		for (float f : data) {
			buffer.put(f);
		}
		float sum = 0;
		for (int i = 0; i < buffer.getCapacity(); i++) {
			sum += buffer.readNextFloat();
		}
		// sum is only returned to prevent the JIT from removing unused code
		return sum;
	}
}
