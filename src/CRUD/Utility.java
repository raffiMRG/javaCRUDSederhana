package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utility {

    static long ambilEntryPertahun(String penulis, String tahun) throws IOException {
        FileReader fileReader = new FileReader("database.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        long entry = 0;
        String data = bufferedReader.readLine();
        Scanner dataScanner;
        String primaryKey;

        while(data != null){
            dataScanner = new Scanner(data);
            dataScanner.useDelimiter(",");
            primaryKey = dataScanner.next();
            dataScanner = new Scanner(primaryKey);
            dataScanner.useDelimiter("_");

            penulis = penulis.replaceAll("\\s+", "");

            if(penulis.equalsIgnoreCase(dataScanner.next()) && tahun.equalsIgnoreCase(dataScanner.next())){
                entry = dataScanner.nextInt();
            }

            data = bufferedReader.readLine();
        }

        bufferedReader.close();
        fileReader.close();

        return entry;
    }

    static boolean cekBukuDiatabase(String[] keywords, boolean isDisplay) throws IOException{

        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        int nomerData = 0;
        boolean isExsist = false;

        if (isDisplay) {
            System.out.println("\n| No |\tTahun |\tPenlis             |\tPenerbit   |\tJudul Buku   ");
            System.out.println("----------------------------------------------------------------");
        }

        while(data!=null){

            // chek keyword di dalam baris
            isExsist = true;

            for(String keyword:keywords){
                isExsist = isExsist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            // jika keywordnya cocok maka tampilkan
            if(isExsist){
                if (isDisplay) {
                    nomerData++;
                    StringTokenizer stringTokenizer = new StringTokenizer(data, ",");

                    stringTokenizer.nextToken();
                    System.out.printf("| %2d ", nomerData);
                    System.out.printf("|\t%4s  ", stringTokenizer.nextToken());
                    System.out.printf("|\t%-20s  ", stringTokenizer.nextToken());
                    System.out.printf("|\t%-20s  ", stringTokenizer.nextToken());
                    System.out.printf("|\t%s", stringTokenizer.nextToken());
                    System.out.println();
                } else {
                    break;
                }
            }

            data = bufferInput.readLine();
        }

        bufferInput.close();
        fileInput.close();

        return isExsist;

    }

    protected static String ambilTahun() throws IOException{
        boolean tahunValid = false;
        Scanner terminalInput = new Scanner(System.in);
        String tahunInput = terminalInput.nextLine();

        while (!tahunValid){
            try {
                Year.parse(tahunInput);
                tahunValid = true;
            }catch (Exception e){
                System.err.println("format tahun yang anda masukan salah, format = (YYYY)");
                System.out.println("masukan tahun terbit lagi");
                tahunValid = false;
                tahunInput = terminalInput.nextLine();
            }
        }
        return tahunInput;
    }

    public static boolean getYesOrNo(String message) {
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\n" + message + " (y/n)?");
        String pilihanuser = terminalInput.next();

        while(!pilihanuser.equalsIgnoreCase( "y") && !pilihanuser.equalsIgnoreCase("n")){
            System.err.println("Pilihan anda bukan y atau n");
            System.out.print("\n" + message + " (y/n)?");
            pilihanuser = terminalInput.next();
        }

        return pilihanuser.equalsIgnoreCase("y");
    }

    public static void clearScrean(){
        try {
            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        }catch (Exception ex){
            System.err.println("tidak bisa clear screen");
        }
    }

}
