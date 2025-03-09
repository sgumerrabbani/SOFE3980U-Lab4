package com.ontariotechu.sofe3980U;

import com.opencsv.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // CSV files to process
        String[] files = {"model_1.csv", "model_2.csv", "model_3.csv"};

        String bestModel = "";
        double bestBCE = Double.MAX_VALUE;
        double bestAUC = 0;

        // Process each model
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
                        trueValues.add(Double.parseDouble(row[0])); // True label (0 or 1)
                        predictedValues.add(Double.parseDouble(row[1])); // Predicted probability (0 to 1)
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading the CSV file: " + filePath);
                e.printStackTrace();
                continue;
            }

            if (trueValues.size() != predictedValues.size()) {
                System.out.println("Data mismatch in file: " + filePath);
                continue;
            }

            // Compute metrics
            double bce = computeBCE(trueValues, predictedValues);
            int[] confusionMatrix = computeConfusionMatrix(trueValues, predictedValues, 0.5);
            double accuracy = computeAccuracy(confusionMatrix);
            double precision = computePrecision(confusionMatrix);
            double recall = computeRecall(confusionMatrix);
            double f1Score = computeF1Score(precision, recall);
            double auc = computeAUC(trueValues, predictedValues);

            System.out.printf("BCE: %.5f, Accuracy: %.5f, Precision: %.5f, Recall: %.5f, F1-Score: %.5f, AUC: %.5f%n",
                    bce, accuracy, precision, recall, f1Score, auc);

            // Update best model based on lowest BCE and highest AUC
            if (bce < bestBCE || (bce == bestBCE && auc > bestAUC)) {
                bestBCE = bce;
                bestAUC = auc;
                bestModel = filePath;
            }
        }

        // Print the best model
        System.out.println("\nBest Model: " + bestModel);
        System.out.printf("Lowest BCE: %.5f, Highest AUC: %.5f%n", bestBCE, bestAUC);
    }

    // Compute Binary Cross-Entropy (BCE)
    private static double computeBCE(List<Double> actual, List<Double> predicted) {
        double sum = 0.0;
        int n = actual.size();
        for (int i = 0; i < n; i++) {
            double y = actual.get(i);
            double yHat = Math.max(1e-10, Math.min(1 - 1e-10, predicted.get(i))); // Prevent log(0)
            sum += y * Math.log(yHat) + (1 - y) * Math.log(1 - yHat);
        }
        return -sum / n;
    }

    // Compute Confusion Matrix (TP, FP, TN, FN)
    private static int[] computeConfusionMatrix(List<Double> actual, List<Double> predicted, double threshold) {
        int TP = 0, FP = 0, TN = 0, FN = 0;
        for (int i = 0; i < actual.size(); i++) {
            int y = actual.get(i) >= 0.5 ? 1 : 0;
            int yHat = predicted.get(i) >= threshold ? 1 : 0;
            if (y == 1 && yHat == 1) TP++;
            else if (y == 1 && yHat == 0) FN++;
            else if (y == 0 && yHat == 1) FP++;
            else TN++;
        }
        return new int[]{TP, FP, TN, FN};
    }

    // Compute Accuracy
    private static double computeAccuracy(int[] cm) {
        int TP = cm[0], FP = cm[1], TN = cm[2], FN = cm[3];
        return (double) (TP + TN) / (TP + TN + FP + FN);
    }

    // Compute Precision
    private static double computePrecision(int[] cm) {
        int TP = cm[0], FP = cm[1];
        return TP + FP == 0 ? 0 : (double) TP / (TP + FP);
    }

    // Compute Recall
    private static double computeRecall(int[] cm) {
        int TP = cm[0], FN = cm[3];
        return TP + FN == 0 ? 0 : (double) TP / (TP + FN);
    }

    // Compute F1-Score
    private static double computeF1Score(double precision, double recall) {
        return (precision + recall) == 0 ? 0 : 2 * (precision * recall) / (precision + recall);
    }

    // Compute AUC-ROC
    private static double computeAUC(List<Double> actual, List<Double> predicted) {
        List<Double[]> scores = new ArrayList<>();
        for (int i = 0; i < actual.size(); i++) {
            scores.add(new Double[]{predicted.get(i), actual.get(i)});
        }
        scores.sort((a, b) -> Double.compare(b[0], a[0])); // Sort by predicted probability

        double auc = 0.0;
        int TP = 0, FP = 0, prevTP = 0, prevFP = 0;
        int nPositive = (int) actual.stream().filter(y -> y == 1).count();
        int nNegative = actual.size() - nPositive;

        for (Double[] pair : scores) {
            if (pair[1] == 1) TP++;
            else FP++;

            auc += (double) (TP + prevTP) * (FP - prevFP) / 2.0;
            prevTP = TP;
            prevFP = FP;
        }

        return auc / (nPositive * nNegative);
    }
}
