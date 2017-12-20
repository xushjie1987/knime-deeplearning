package org.knime.dl.core.data.bbbbuffer;

import java.util.Random;

/**
 * Contains utility functions for testing.
 * We might want to introduce a global testing utility in the future that will 
 * contain the functionality provided by this class.
 * @author Adrian Nembach, KNIME GmbH, Konstanz, Germany
 *
 */
final class TestUtil {
	
	static final double EPSILON = 1e-5;
	
	private TestUtil() {
		// utility class
	}

	static boolean[] createBooleanArray(Random rand, int size) {
		boolean[] arr = new boolean[size];
		for (int i = 0; i < size; i++) {
			arr[i] = rand.nextBoolean();
		}
		return arr;
	}
	
	static byte[] createByteArray(Random rand, int size) {
		byte[] arr = new byte[size];
		rand.nextBytes(arr);
		return arr;
	}
	
	static short[] createShortArray(Random rand, int size) {
		short[] arr = new short[size];
		for (int i = 0; i < size; i++) {
			arr[i] = (short) rand.nextInt(Short.MAX_VALUE);
		}
		return arr;
	}
	
	static int[] createIntArray(Random rand, int size) {
		return rand.ints(size).toArray();
	}
	
	static long[] createLongArray(Random rand, int size) {
		return rand.longs(size).toArray();
	}
	
	static float[] createFloatArray(Random rand, int size) {
		float[] arr = new float[size];
		for (int i = 0; i < size; i++) {
			arr[i] = rand.nextFloat();
		}
		return arr;
	}
	
	static double[] createDoubleArray(Random rand, int size) {
		return rand.doubles(size).toArray();
	}
	
	static double[] floatToDouble(final float[] arr) {
		double[] res = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}
}
