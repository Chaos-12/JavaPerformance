package com.teaching.core.performance;

import com.teaching.core.enums.HpEnum;
import com.teaching.core.helpers.ResourceHelper;
import org.apache.commons.lang3.EnumUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;

import static com.teaching.core.helpers.EnumHelper.fromEnumtoSet;
import static com.teaching.core.helpers.EnumHelper.fromStringToEnum;
import static com.teaching.core.helpers.LogHelper.log;
import static com.teaching.core.helpers.PerformanceHelper.assertFaster;
import static com.teaching.core.helpers.PerformanceHelper.logNanoTime;
import static com.teaching.core.performance.MapPerf.populateMapWithEnum;
import static com.teaching.core.performance.MapPerf.populateMapWithFilter;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapPerfTest {

    private static final String dataPath = "/data/HP1_CH1.txt";
    private static final String argPath = "/arguments/HP1_CH1-args.txt";

    private static String[] data = new String[0];
    private static HpEnum[] enumData = new HpEnum[0];
    private static Collection<String> whiteList;

    private static Map<String, List<Integer>> dataHashMap = new HashMap<>();
    private static Map<String, List<Integer>> dataTreeMap = new TreeMap<>();
    private static Map<HpEnum, List<Integer>> dataEnumMap = new EnumMap<>(HpEnum.class);

    @BeforeAll
    public static void loadRessources() {
        log("Test for MapPerf:");
        data = logNanoTime("load data from file", () -> ResourceHelper.getResourceAsArray(dataPath));
        log("File loaded : %s words found", data.length);

        enumData = logNanoTime("transform data to enum", () -> fromStringToEnum(HpEnum.class, data), "file");

        whiteList = logNanoTime("transform enum to string", () -> fromEnumtoSet(HpEnum.class), "whitelist");
        log("WhiteList loaded : %s words found", whiteList.size());

        whiteList = fromEnumtoSet(HpEnum.class);
        dataHashMap = logNanoTime("populate HashMap", () -> populateMapWithFilter(dataHashMap, whiteList, data));
        whiteList = fromEnumtoSet(HpEnum.class);
        dataTreeMap = logNanoTime("populate TreeMap", () -> populateMapWithFilter(dataTreeMap, whiteList, data));
        enumData = fromStringToEnum(HpEnum.class, data);
        dataEnumMap = logNanoTime("populate EnumMap", () -> populateMapWithEnum(dataEnumMap, enumData));
    }

    @Test
    void testPerfFillMap() {
        //Given
        Map<String, List<Integer>> treeMap = new TreeMap<>();
        Map<HpEnum, List<Integer>> enumMap = new EnumMap<>(HpEnum.class);
        //Then
        assertFaster( () -> populateMapWithEnum(enumMap, enumData),
                () -> populateMapWithFilter(treeMap, fromEnumtoSet(HpEnum.class), data));
    }

    @ParameterizedTest
    @CsvFileSource(resources = argPath)
    void testHashMapContains(boolean expected, String word) {
        //When
        boolean result = logNanoTime("check HashMap", () -> dataHashMap.containsKey(word),
                String.valueOf(expected), word);
        //Then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = argPath)
    void testTreeMapContains(boolean expected, String word) {
        //When
        boolean result = logNanoTime("check TreeMap", () -> dataTreeMap.containsKey(word),
                String.valueOf(expected), word);
        //Then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = argPath)
    void testEnumMapContains(boolean expected, String word) {
        //Given
        HpEnum enumWord = EnumUtils.getEnum(HpEnum.class, word);
        //When
        boolean result = logNanoTime("check EnumMap", () -> dataEnumMap.containsKey(enumWord),
                String.valueOf(expected), word);
        //Then
        assertEquals(expected, result);
    }
}
