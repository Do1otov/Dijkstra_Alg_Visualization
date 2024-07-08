package research;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ArrayToFile {
    public static void main(String[] args) {
        double[] xData = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        double[] yData1 = {2, 4, 6, 8, 10, 8, 6, 4, 2, 0, -2, -4};
        double[] yData2 = {4, 6, 8, 10, 12, 10, 8, 6, 4, 2, 0, -2};

        writeToFile("research_data.txt", xData, yData1);
    }

    public static void writeToFile(String filename, double[] xData, double[] yData1) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            writeArray(writer, xData);

            writeArray(writer, yData1);

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private static void writeArray(BufferedWriter writer, double[] array) throws IOException {
        for (double value : array) {
            writer.write(value + " ");
        }
        writer.newLine();
    }
}
