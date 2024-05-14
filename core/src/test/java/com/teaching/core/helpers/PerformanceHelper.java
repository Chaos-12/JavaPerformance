package com.teaching.core.helpers;

import org.apache.commons.lang3.time.StopWatch;

import java.util.function.Supplier;

import static com.teaching.core.helpers.LogHelper.log;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PerformanceHelper {

    private static final StopWatch stopWatch = new StopWatch();

    private PerformanceHelper(){ }

    public static <T> T logNanoTime(String action, Supplier<T> function, String... arguments) {
        // Reset variables
        T result;
        stopWatch.reset();
        // Compute the time of function.get()
        stopWatch.start();
        result = function.get();
        stopWatch.stop();
        // Log the time
        String msg = arguments.length == 0 ? "" : "(".concat(String.join("/", arguments)).concat(") ");
        log(" - Time to %s %s-> %s %s", action, msg, stopWatch.getNanoTime(), "ns");
        return result;
    }

    public static long getNanoTime(Supplier<?> function) {
        stopWatch.reset();
        stopWatch.start();
        function.get();
        stopWatch.stop();
        return stopWatch.getNanoTime();
    }

    public static void assertFaster(Supplier<?> fasterFunction, Supplier<?> slowerFunction) {
        long fasterTime = getNanoTime(fasterFunction);
        long slowerTime = getNanoTime(slowerFunction);
        assertThat(fasterTime)
                .withFailMessage("First function should be faster, but time spent in each method is %s and %s ns, respectively.", fasterTime, slowerTime)
                .isLessThan(slowerTime);
    }

}
