package PriorityQueue;

import java.io.*;
import java.net.URL;

public class PriorityQueueDriverCode {


    public static void main(String[] args) throws IOException {
        MaxHeap<Student> maxHeap = new MaxHeap<>();

        File file;
        if (args.length == 0) {
            URL url = PriorityQueueDriverCode.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. "+file.getAbsolutePath());
        }
        String st;
        while ((st = br.readLine()) != null) {
            String[] cmd = st.split(" ");
            if (cmd.length == 0) {
                System.err.println("Error parsing: " + st);
                return;
            }

            switch (cmd[0]) {
                case "INSERT":
                    String contact = br.readLine();
                    Student student = new Student(contact.split(",")[0].trim(), Integer.parseInt(contact.split(",")[1].trim()));
                    System.out.println("Inserting: " + student.getName());
                    maxHeap.insert(student);
                    break;
                case "EXTRACTMAX":
                    Student max_student = maxHeap.extractMax();
                    if (max_student != null)
                        System.out.println(max_student.toString());
                    else {
                        System.out.println("Heap is empty.");
                    }
                    break;
                default:
                    System.err.println("Unknown command: " + cmd[0]);
            }
        }

    }
}
