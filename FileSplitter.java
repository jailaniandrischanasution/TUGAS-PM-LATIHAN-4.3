import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FileSplitter {
    private Queue<String> partsQueue;

    public FileSplitter() {
        this.partsQueue = new LinkedList<>();
    }

    public void readFileAndSplit(String filePath, int partSize) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder currentPart = new StringBuilder();
            String line;
            int currentLength = 0;

            while ((line = reader.readLine()) != null) {
                currentPart.append(line).append("\n");
                currentLength += line.length() + 1;

                if (currentLength >= partSize) {
                    partsQueue.add(currentPart.toString());
                    currentPart.setLength(0); 
                    currentLength = 0; 
                }
            }

            if (currentPart.length() > 0) {
                partsQueue.add(currentPart.toString());
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }

    public void writePartsToFiles() {
        int partNumber = 1;
        while (!partsQueue.isEmpty()) {
            String part = partsQueue.poll();
            try (FileWriter writer = new FileWriter("part" + partNumber + ".txt")) {
                writer.write(part);
                partNumber++;
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan saat menulis file: " + e.getMessage());
            }
        }
        System.out.println("Pemotongan file selesai. Semua bagian telah disimpan.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileSplitter fileSplitter = new FileSplitter();

        System.out.print("Masukkan nama file yang ingin dibaca: ");
        String filePath = scanner.nextLine();

        System.out.print("Masukkan ukuran bagian (dalam karakter): ");
        int partSize = scanner.nextInt();

        fileSplitter.readFileAndSplit(filePath, partSize);
        fileSplitter.writePartsToFiles();
    }
}