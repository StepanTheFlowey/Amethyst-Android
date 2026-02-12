package net.kdt.pojavlaunch.tasks;

/**
 * A simple class to calculate the average Internet speed using a simple moving average.
 */
public class SpeedCalculator {
    private long mLastMillis;
    private long mLastBytes;
    private int mIndex;
    private final double[] mPreviousInputs;
    private double mSum;

    public SpeedCalculator() {
        this(64);
    }

    public SpeedCalculator(int averageDepth) {
        mPreviousInputs = new double[averageDepth];
    }

    private double addToAverage(double speed) {
        mSum -= mPreviousInputs[mIndex];
        mSum += speed;
        mPreviousInputs[mIndex] = speed;
        if(++mIndex == mPreviousInputs.length) mIndex = 0;

        final double dLength = mPreviousInputs.length;
        return (mSum + (dLength / 2d)) / dLength;
    }

    /**
     * Update the current amount of bytes downloaded.
     * @param bytes the new amount of bytes downloaded
     * @return the current download speed in bytes per second
     */
    public double feed(long bytes) {
        final long millis = System.currentTimeMillis();
        final long deltaBytes = bytes - mLastBytes;
        final long deltaMillis = millis - mLastMillis;
        mLastBytes = bytes;
        mLastMillis = millis;

        final double speed = (double)deltaBytes / ((double)deltaMillis / 1000d);
        return addToAverage(speed);
    }
}
