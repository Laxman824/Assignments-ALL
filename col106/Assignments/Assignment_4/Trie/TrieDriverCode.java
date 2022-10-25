package Trie;

import java.io.*;
import java.net.URL;

public class TrieDriverCode {
    public static void main(String[] args) {
        Trie trie = new Trie();
        File file;
        if (args.length == 0) {
            URL url = TrieDriverCode.class.getResource("INP");
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
        while (true) {
            try {
                if ((st = br.readLine()) == null) break;
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }

                switch (cmd[0]) {
                    case "INSERT":
                        String contact = br.readLine();
                        Person person = new Person(contact.split(",")[0].trim(), contact.split(",")[1].trim());
                        System.out.println("Inserting: " + person.getName());
                        trie.insert(person.getName(), person);
                        break;
                    case "SEARCH":
                        String search_term = br.readLine();
                        System.out.println("Searching: "+search_term);
                        Object search = trie.search(search_term);
                        if (search != null) {
                            System.out.println("FOUND");
                            System.out.println(((TrieNode) search).getValue());
                        } else
                            System.out.println("NOT FOUND");
                        break;
                    case "MATCH":
                        System.out.println("Matching: "+cmd[1]);
                        TrieNode trieNode = trie.startsWith(cmd[1]);
                        if (trieNode == null)
                            System.out.println("NOT FOUND");
                        else {
                            System.out.println("MATCHED:");
                            trie.printTrie(trieNode);
                        }
                        break;
                    case "DELETE":
                        search_term = br.readLine();
                        System.out.println("Deleting: "+search_term );
                        trie.delete(search_term);
                        break;
                    case "PRINTLEVEL":
                        trie.printLevel(Integer.parseInt(cmd[1]));
                        break;
                    case "PRINT":
                        trie.print();
                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }
            } catch (IOException e) {
                System.err.println("File Not Found");
            }
        }
    }

}
