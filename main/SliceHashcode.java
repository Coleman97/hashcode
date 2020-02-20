/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tobicole
 */
public class SliceHashcode {

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) throws IOException {
        String[] info = { "", "" };

        File directory = new File("outputs");
        if (!directory.mkdir()) {
            System.out.println("Couldn't create outputs folder");
            System.exit(3);
        }
        ;

        List<String> inputfiles = new ArrayList<>();
        File folder = new File("inputs");
        for (File f : folder.listFiles()) {
            inputfiles.add(f.getName());
        }

        LineNumberReader lineReader;
        for (String path : inputfiles) {
            lineReader = new LineNumberReader(new FileReader("./inputs\\" + path));
            int i = 0;
            while ((lineReader.ready())) {
                info[i] = lineReader.readLine();
                i++;
            }
            simulate(info, path);
            lineReader.close();
        }
    }

    // Read a single input file by line and place in an array
    static void simulate(String[] info, String path) {
        int[] maxAndNo = { 0, 0, 0 };
        int j = 0;
        // String[] ff = Uti
        for (String i : info[0].split(" ")) {
            maxAndNo[j] = Integer.parseInt(i);
            j++;
        }
        j = 0;
        int[] slice = new int[info[1].split(" ").length];
        for (String i : info[1].split(" ")) {
            slice[j] = Integer.parseInt(i);
            j++;
        }

        int total = 0;
        int tmp = 0;
        int index = 0;
        int[] pizzaTypes = new int[maxAndNo[1]];
        for (int i = slice.length - 1; i >= 0; i--) {
            tmp = total + slice[i];
            if (tmp <= maxAndNo[0]) {
                total = tmp;
                pizzaTypes[++index] = i;
            }
        }
        int[] finaltype = new int[index];
        for (int i=0;i<index;i++) {
            finaltype[i] = pizzaTypes[i];
        }
        Arrays.sort(finaltype);
        printToFile(finaltype, path, total);
    }

    static void printToFile(int[] types, String filename, int total) {
        filename = filename.replaceAll("in", "out");
        try {
            File file = new File("./outputs\\" + filename);
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file);
                String content = types.length + "\n";
                for (int i : types) {
                    content += (i+" ");
                }
                writer.write(content);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
