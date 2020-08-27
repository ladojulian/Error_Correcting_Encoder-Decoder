package correcter;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static String inputFileName = "send.txt";
    private static String encodedFileName = "encoded.txt";
    private static String receivedFileName = "received.txt";
    private static String outputFileName = "decoded.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Write a mode:");
        String mode = scanner.nextLine();

        switch (mode) {
            case "encode":
                encode();
                break;
            case "send":
                send();
                break;
            case "decode":
                decode();
                break;
        }
    }

    private static byte[] readFile(String fileName) {
        try (FileInputStream inputFileReader = new FileInputStream(fileName)) {
            return inputFileReader.readAllBytes();
        } catch (Exception e) {
            System.out.println("Exception FileInputStream: " + e);
        }

        return new byte[0];
    }

    private static void writeFile(String fileName, byte[] outputData) {
        try (FileOutputStream outputFileWriter = new FileOutputStream(fileName)) {
            outputFileWriter.write(outputData);
        } catch (Exception e) {
            System.out.println("Exception FileOutputStream: " + e);
        }
    }

    private static byte[] corruptData(byte[] data) {
        Random random = new Random();

        for (int i = 0; i < data.length; i++) {
            data[i] ^= 1 << random.nextInt(8);
        }

        return data;
    }

    private static void encode() {
        byte[] text = readFile(inputFileName);
        byte[] outputTextBytes = hammingEncode(text);
        writeFile(encodedFileName, outputTextBytes);
    }

    private static void send() {
        byte[] text = readFile(encodedFileName);
        byte[] outputText = corruptData(text);
        writeFile(receivedFileName, outputText);
    }

    private static void decode() {
        byte[] text = readFile(receivedFileName);
        byte[] outputTextBytes = hammingDecode(text);
        writeFile(outputFileName, outputTextBytes);
    }

    private static byte[] hammingEncode(byte[] data) {
        StringBuilder output = new StringBuilder();

        for (byte byteData : data) {
            output.append(String.format("%8s", Integer.toBinaryString(byteData & 0xFF)).replace(' ', '0'));
        }

        String binaryString = output.toString();
        String outputText = "";
        String newByte;

        for (int i = 0; i < binaryString.length(); i += 4) {
            String bytesSubstring = binaryString.substring(i, i + 4);

            int parityBit1 = 0;
            int parityBit2 = 0;
            int parityBit4 = 0;

            if (bytesSubstring.charAt(0) == '1') {
                parityBit1++;
                parityBit2++;
            }

            if (bytesSubstring.charAt(1) == '1') {
                parityBit1++;
                parityBit4++;
            }

            if (bytesSubstring.charAt(2) == '1') {
                parityBit2++;
                parityBit4++;
            }

            if (bytesSubstring.charAt(3) == '1') {
                parityBit1++;
                parityBit2++;
                parityBit4++;
            }

            newByte = (parityBit1 % 2 == 1 ? "1" : "0")
                    + (parityBit2 % 2 == 1 ? "1" : "0")
                    + bytesSubstring.charAt(0)
                    + (parityBit4 % 2 == 1 ? "1" : "0")
                    + bytesSubstring.charAt(1)
                    + bytesSubstring.charAt(2)
                    + bytesSubstring.charAt(3)
                    + "0";

            outputText += newByte;
        }

        byte[] outputTextBytes = new byte[data.length * 2];

        for (int i = 0; i < outputTextBytes.length; i++) {
            outputTextBytes[i] = (byte) Integer.parseInt(outputText.substring(i * 8, i * 8 + 8), 2);
        }

        return outputTextBytes;
    }

    private static byte[] hammingDecode(byte[] data) {
        StringBuilder decodedStringBuilder = new StringBuilder();

        for (byte byteData : data) {
            String bytesString = String.format("%8s", Integer.toBinaryString(byteData & 0xFF)).replace(' ', '0');

            int parityBit1 = 0;
            int parityBit2 = 0;
            int parityBit4 = 0;
            int wrongBit = 0;

            if (bytesString.charAt(2) == '1') {
                parityBit1++;
                parityBit2++;
            }

            if (bytesString.charAt(4) == '1') {
                parityBit1++;
                parityBit4++;
            }

            if (bytesString.charAt(5) == '1') {
                parityBit2++;
                parityBit4++;
            }

            if (bytesString.charAt(6) == '1') {
                parityBit1++;
                parityBit2++;
                parityBit4++;
            }

            char parityBit1Checker = (parityBit1 % 2 == 1) ? '1' : '0';
            char parityBit2Checker = (parityBit2 % 2 == 1) ? '1' : '0';
            char parityBit4Checker = (parityBit4 % 2 == 1) ? '1' : '0';

            wrongBit += parityBit1Checker == bytesString.charAt(0) ? 0 : 1;
            wrongBit += parityBit2Checker == bytesString.charAt(1) ? 0 : 2;
            wrongBit += parityBit4Checker == bytesString.charAt(3) ? 0 : 4;


            if (wrongBit != 3) {
                decodedStringBuilder.append(bytesString.charAt(2));
            } else {
                decodedStringBuilder.append((bytesString.charAt(2) == '1' ? '0' : '1'));
            }

            if (wrongBit != 5) {
                decodedStringBuilder.append(bytesString.charAt(4));
            } else {
                decodedStringBuilder.append((bytesString.charAt(4) == '1' ? '0' : '1'));
            }

            if (wrongBit != 6) {
                decodedStringBuilder.append(bytesString.charAt(5));
            } else {
                decodedStringBuilder.append((bytesString.charAt(5) == '1' ? '0' : '1'));
            }

            if (wrongBit != 7) {
                decodedStringBuilder.append(bytesString.charAt(6));
            } else {
                decodedStringBuilder.append((bytesString.charAt(6) == '1' ? '0' : '1'));
            }
        }

        String decodedString = decodedStringBuilder.toString();
        byte[] outputString = new byte[decodedString.length() / 8];

        for (int i = 0; i < decodedString.length() / 8; i++) {
            outputString[i] = (byte) Integer.parseInt(decodedString.substring(i * 8, i * 8 + 8), 2);
        }

        return outputString;
    }
}