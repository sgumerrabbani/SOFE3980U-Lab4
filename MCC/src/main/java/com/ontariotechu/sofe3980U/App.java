package com.ontariotechu.sofe3980U;

import com.opencsv.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        String filePath = "model.csv";  // File to read

        System.out.println("DEBUG: Starting multi-class classification evaluation...");

        List<String[]> allData;
        try {
            FileReader filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            allData = csvReader.readAll();
            csvReader.close();
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + filePath);
            e.printStackTrace();
            return;
        }

        System.out.println("DEBUG: Successfully read " + allData.size() + " rows from " + filePath);

        int numClasses = allData.get(0).length - 1; // Number of classes (excluding actual class column)
        int[][] confusionMatrix = new int[numClasses][numClasses]; // Confusion matrix
        double totalCE = 0.0;
        int count = 0;

        for (String[] row : allData) {
            if (row.length < numClasses + 1) continue;

            int actualClass = Integer.parseInt(row[0]) - 1; // Convert to 0-based index
            double[] predictedProbs = new double[numClasses];

            int predictedClass = 0;
            double maxProb = Double.NEGATIVE_INFINITY;

            for (int j = 0; j < numClasses; j++) {
                predictedProbs[j] = Double.parseDouble(row[j + 1]);

                // Find the class with the highest probability
                if (predictedProbs[j] > maxProb) {
                    maxProb = predictedProbs[j];
                    predictedClass = j;
                }
            }

            // Compute Cross-Entropy Loss
            totalCE += -Math.log(predictedProbs[actualClass] + 1e-10); // Add small value to prevent log(0)
            count++;

            // Update Confusion Matrix
            confusionMatrix[actualClass][predictedClass]++;
        }

        double crossEntropy = totalCE / count;

        // Print the computed metrics
        System.out.printf("\nCross-Entropy Loss (CE): %.5f\n", crossEntropy);
        System.out.println("\nConfusion Matrix:");

        // Print Confusion Matrix
        for (int i = 0; i < numClasses; i++) {
            for (int j = 0; j < numClasses; j++) {
                System.out.print(confusionMatrix[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("\nLower CE is better. Analyze the confusion matrix to see misclassification patterns.");
    }
}
