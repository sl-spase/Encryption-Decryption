package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

interface CipherAlgorithm {
    String execute(String data, int key);
}

class EncryptUnicodeAlgorithm implements CipherAlgorithm {
    StringBuilder sb = new StringBuilder();

    public String execute(String data, int key) {
        for (Character symbol : data.toCharArray()) {
            sb.append((char) (symbol + key));
        }
        return sb.toString();
    }
}

class EncryptShiftAlgorithm implements CipherAlgorithm {
    public String execute(String data, int key) {
        StringBuilder sb = new StringBuilder();
        char startLetter;

        for (Character symbol : data.toCharArray()) {
            if (Character.isAlphabetic(symbol)) {
                startLetter = (Character.isUpperCase(symbol)) ? 'A' : 'a';
                sb.append((char) (startLetter + (symbol - startLetter + key) % 26));
            } else {
                sb.append((char) symbol);
            }
        }
        return sb.toString();
    }
}

class DecryptUnicodeAlgorithm implements CipherAlgorithm {
    public String execute(String data, int key) {
        StringBuilder sb = new StringBuilder();
        for (Character symbol : data.toCharArray()) {
            sb.append((char) (symbol - key));
        }
        return sb.toString();
    }
}

class DecryptShiftAlgorithm implements CipherAlgorithm {
    public String execute(String data, int key) {
        StringBuilder sb = new StringBuilder();
        char endLetter;

        for (Character symbol : data.toCharArray()) {
            if (Character.isAlphabetic(symbol)) {
                endLetter = (Character.isUpperCase(symbol)) ? 'Z' : 'z';
                sb.append((char) (endLetter - (endLetter - symbol + key) % 26));
            } else {
                sb.append((char) symbol);
            }
        }
        return sb.toString();
    }
}

class Cipher {
    private String mode = "enc";
    private int key = 0;
    private String data = "";
    private String in = "";
    private String out = "";
    private String alg = "shift";
    private CipherAlgorithm algorithm;

    private void loadData() {
        File file = new File(in);
        try (Scanner fileScanner = new Scanner(file)) {
            data = fileScanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    private void saveData(String dataToSave) {
        File file = new File(out);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(dataToSave);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    private void setParams(String[] args) {
        for (int i = 0; i < args.length - 1; i += 2) {
            if ("-mode".equals(args[i]) && ("enc".equals(args[i + 1]) || "dec".equals(args[i + 1]))) {
                mode = args[i + 1];
            } else if ("-key".equals(args[i]) && new Scanner(args[i + 1]).hasNextInt()) {
                key = Integer.parseInt(args[i + 1]);
            } else if ("-data".equals(args[i])) {
                data = args[i + 1];
            } else if ("-in".equals(args[i])) {
                in = args[i + 1];
            } else if ("-out".equals(args[i])) {
                out = args[i + 1];
            } else if ("-alg".equals(args[i]) && ("shift".equals(args[i + 1]) || "unicode".equals(args[i + 1]))) {
                alg = args[i + 1];
            }
        }
    }

    private void setAlgorithm() {
        if ("enc".equals(mode) && "unicode".equals(alg)) {
            algorithm = new EncryptUnicodeAlgorithm();
        } else if ("enc".equals(mode) && "shift".equals(alg)) {
            algorithm = new EncryptShiftAlgorithm();
        } else if ("dec".equals(mode) && "unicode".equals(alg)) {
            algorithm = new DecryptUnicodeAlgorithm();
        } else if ("dec".equals(mode) && "shift".equals(alg)) {
            algorithm = new DecryptShiftAlgorithm();
        }
    }

    public Cipher(String[] args) {
        setParams(args);
        if (data.isEmpty() && !in.isEmpty()) {
            loadData();
        }
        setAlgorithm();
    }

    public void crypt() {
        String result = algorithm.execute(data, key);
        if (out.isEmpty()) {
            System.out.println(result);
        } else {
            saveData(result);
        }
    }
}

public class Main {

    public static void main(String[] args) {
        Cipher cipher = new Cipher(args);
        cipher.crypt();
    }
}



