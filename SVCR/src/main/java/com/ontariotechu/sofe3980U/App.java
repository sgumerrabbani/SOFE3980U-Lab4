package com.ontariotechu.sofe3980U;

import com.opencsv.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // CSV files to process
        String[] files = {"model_1.csv", "model_2.csv", "model_3.csv"};

        String bestModel = "";
        double bestMSE = Double.MAX_VALUE;
        double bestMAE = Double.MAX_VALUE;
        double bestMARE = Double.MAX_VALUE;

        // Process each model file
        for (String filePath : files) {
            System.out.println("\nProcessing file: " + filePath);

            List<Double> trueValues = new ArrayList<>();
            List<Double> predictedValues = new ArrayList<>();

            try {
                FileReader filereader = new FileReader(filePath);
                CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
                List<String[]> allData = csvReader.readAll();
                csvReader.close();

                for (String[] row : allData) {
                    if (row.length >= 2) {
                        trueValues.add(Double.parseDouble(row[0]));
                        predictedValues.add(Double.parseDouble(row[1]));
                    }
                }
            } catch (Exception e) {
                System.out.println("Error reading the CSV file: " + filePath);
                e.printStackTrace();
                continue;
            }

            if (trueValues.size() != predictedValues.size()) {
                System.out.println("Data mismatch in file: " + filePath);
                continue;
            }

            // Compute error metrics
            double mse = computeMSE(trueValues, predictedValues);
            double mae = computeMAE(trueValues, predictedValues);
            double mare = computeMARE(trueValues, predictedValues);

            // Display the first 10 lines of data
            System.out.println("First 10 rows of data:");
            for (int i = 0; i < Math.min(10, trueValues.size()); i++) {
                System.out.printf("%.6f \t %.6f%n", trueValues.get(i), predictedValues.get(i));
            }

            System.out.printf("MSE: %.5f, MAE: %.5f, MARE: %.5f%%%n", mse, mae, mare);

            // Determine the best model based on the lowest MSE
            if (mse < bestMSE) {
                bestMSE = mse;
                bestMAE = mae;
                bestMARE = mare;
                bestModel = filePath;
            }
        }

        // Print the best model
        System.out.println("\nBest Model: " + bestModel);
        System.out.printf("Lowest MSE: %.5f, MAE: %.5f, MARE: %.5f%%%n", bestMSE, bestMAE, bestMARE);
    }

    // Compute Mean Squared Error (MSE)
    private static double computeMSE(List<Double> actual, List<Double> predicted) {
        double sum = 0.0;
        int n = actual.size();
        for (int i = 0; i < n; i++) {
            double error = actual.get(i) - predicted.get(i);
            sum += error * error;
        }
        return sum / n;
    }

    // Compute Mean Absolute Error (MAE)
    private static double computeMAE(List<Double> actual, List<Double> predicted) {
        double sum = 0.0;
        int n = actual.size();
        for (int i = 0; i < n; i++) {
            sum += Math.abs(actual.get(i) - predicted.get(i));
        }
        return sum / n;
    }

    // Compute Mean Absolute Relative Error (MARE)
    private static double computeMARE(List<Double> actual, List<Double> predicted) {
        double sum = 0.0;
        int n = actual.size();
        double epsilon = 1e-10; // Small value to avoid division by zero

        for (int i = 0; i < n; i++) {
            sum += Math.abs((actual.get(i) - predicted.get(i)) / (actual.get(i) + epsilon));
        }
        return (sum / n) * 100; // Convert to percentage
    }
}
