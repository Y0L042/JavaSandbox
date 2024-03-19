public class Timer {
    private long startTime;
    private long endTime;
    private long duration;

    public long start() {
        startTime = System.nanoTime();
        return startTime;
    }

    public long getInterval() {
        return System.nanoTime() - startTime;
    }

    public long stop() {
        endTime = System.nanoTime();
        duration = endTime - startTime;
        return endTime;
    }

    public long getNanoTime() {
        return duration;
    }

    public double getMilliTime() {
        return duration/1000000.0;
    }

    public double getSecondsTime() {
        return duration/1000000000.0;
    }
}
