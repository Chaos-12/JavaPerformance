package com.teaching.core.performance;

import com.teaching.core.helpers.ResourceHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;

import static com.teaching.core.helpers.LogHelper.log;
import static com.teaching.core.helpers.PerformanceHelper.assertFaster;
import static com.teaching.core.helpers.PerformanceHelper.logNanoTime;
import static com.teaching.core.performance.CollectionPerf.populateAllSameTime;
import static com.teaching.core.performance.CollectionPerf.populateOneByOne;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionPerfTest {

    private static final String dataPath = "/data/HP1_CH1.txt";
    private static final String argPath = "/arguments/HP1_CH1-args.txt";

    private static String[] data = new String[0];
    private static final Collection<String> dataArrayList = new ArrayList<>();
    private static final Collection<String> dataHashSet = new HashSet<>();

    @BeforeAll
    public static void loadResources() {
        //Load String[] from file
        data = logNanoTime("load data from file", () -> ResourceHelper.getResourceAsArray(dataPath));
        log("File loaded : %s words found", data.length);

        //Load ArrayList from 'data'
        logNanoTime("load data into Collection" , () -> populateOneByOne(dataArrayList, data),
                "ArrayList");
        log("ArrayList contains %s words", dataArrayList.size());

        //Load HashSet from 'data'
        logNanoTime("load data into Collection", () -> populateOneByOne(dataHashSet, data),
                "HashSet");
        log("HashSet contains %s words", dataHashSet.size());
    }

    @Test
    void testPerfPopulateOneByOne() {
        //Given
        Collection<String> arrayList = new ArrayList<>();
        Collection<String> hashSet = new HashSet<>();
        //Then
        assertFaster( () -> populateOneByOne(arrayList, data),() -> populateOneByOne(hashSet, data));
    }

    @ParameterizedTest
    @CsvFileSource(resources = argPath)
    void testArrayListContainsWord(boolean expected, String word) {
        //When
        boolean result = logNanoTime("check ArrayList", () -> dataArrayList.contains(word),
                String.valueOf(expected), word);
        //Then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = argPath)
    void testHashSetContainsWord(boolean expected, String word) {
        //When
        boolean result = logNanoTime("check HashSet", () -> dataHashSet.contains(word),
                String.valueOf(expected), word);
        //Then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = argPath)
    void testPerfContainsWord(boolean expected, String word) {
        assertEquals(expected, dataHashSet.contains(word));
        assertEquals(expected, dataArrayList.contains(word));
        assertFaster( () -> dataHashSet.contains(word), () -> dataArrayList.contains(word));
    }

    @Test
    void testPerfAddOneByOne() {
        //Given
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        //Then
        assertFaster( () -> populateAllSameTime(list1, data), () -> populateOneByOne(list2, data));
    }
}
