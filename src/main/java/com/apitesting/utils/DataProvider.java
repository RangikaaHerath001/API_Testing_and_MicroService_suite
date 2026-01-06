package com.apitesting.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();

        try (InputStream inputStream = DataProvider.class.getClassLoader()
                .getResourceAsStream(filePath);
             CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

            data = reader.readAll();
            data.remove(0); // Remove header row

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static Object[][] getTestData(String filePath) {
        List<String[]> csvData = readCSV(filePath);
        Object[][] data = new Object[csvData.size()][];

        for (int i = 0; i < csvData.size(); i++) {
            data[i] = csvData.get(i);
        }

        return data;
    }
}