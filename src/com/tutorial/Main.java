package com.tutorial;

import java.io.*;
import java.lang.reflect.Array;
import java.time.Year;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        boolean isLanjutkan = true;
        String pilihanuser;
        Scanner terminalInput = new Scanner(System.in);

        while (isLanjutkan) {
            clearScrean();
            System.out.println("ini adalah database perpustakaan\n");
            System.out.println("1.\tLihat seluruh buku");
            System.out.println("2.\tCari data buku");
            System.out.println("3.\tTambah data buku");
            System.out.println("4.\tUbah data buku");
            System.out.println("5.\tHapus buku");

            System.out.print("\n\nPilih pilihan anda : ");
            pilihanuser = terminalInput.next();

            switch (pilihanuser) {
                case "1":
                    System.out.println("LIST SELURUH BUKU");
                    // TAMPILKAN DATA
                    tampilkanData();
                    break;
                case "2":
                    System.out.println("CARI BUKU");
                    // CARI DATA
                    cariData();
                    break;
                case "3":
                    System.out.println("TAMBAH DATA BUKU");
                    // TAMBAH DATA
                    tambahData();
                    tampilkanData();
                    break;
                case "4":
                    System.out.println("UBAH DATA BUKU");
                    // UBAH DATA
                    break;
                case "5":
                    System.out.println("HAPUS DATA BUKU");
                    // HAPUS DATA
                    deleteData();
                    break;
                default:
                    System.out.println("\npilihan anda tidak di temukan silahkan masukan angka pilihan 1-5");
            }
            // getyesorno
            isLanjutkan = getYesOrNo("\nApakah anda ingin melanjutkan");
        }
    }

    private static void deleteData () throws IOException {
        // kita ambil databse orifginal
        File database = new File("database.txt");
        FileReader fileReader = new FileReader(database);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // kita buat databse sementara
        File tmpDB = new File("tempDB.txt");
        FileWriter fileWriter = new FileWriter(tmpDB);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // tampilkan data
        System.out.println("List buku");
        tampilkanData();

        // kita ambil user input untuk mendelete data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor buku yang akan di hapus : ");
        int deleteNum = terminalInput.nextInt();

        // looping untuk membaca tiap data baris dan skip data yang ingin di delete

        boolean isFound = false;
        int entryCounts = 0;
        String data = bufferedReader.readLine();

        while (data != null) {
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data, ",");

            // tampilkan data yang ingin di hapus
            if (deleteNum == entryCounts){
                System.out.println("data yang ingin anda hapus adalah : ");
                System.out.println("------------------------------------");
                System.out.println("Referensi        : " + st.nextToken());
                System.out.println("tahun            : " + st.nextToken());
                System.out.println("penulis          : " + st.nextToken());
                System.out.println("penerbit         : " + st.nextToken());
                System.out.println("judul            : " + st.nextToken());

                isDelete = getYesOrNo("Apakah anda yakin akan menghapus?");
                isFound = true;
            }

            if (isDelete){
                // skip pindahkan data dari original ke sementara
                System.out.println("data berhasil dihapus");
            }else {
                // kita pindahkan data dari original ke sementara
                bufferedWriter.write(data);
                bufferedWriter.newLine();
            }
            data = bufferedReader.readLine();
        }

        if (!isFound){
            System.err.println("Data tidak ditemukan!!");
        }

        // menulis data ke file
        bufferedWriter.flush();

        bufferedReader.close();
        fileReader.close();
        bufferedWriter.close();
        fileWriter.close();

        // menghapus database
        database.delete();

        // rename file tmp menjadi database
        tmpDB.renameTo(database);
        System.out.println("data dihapus");
    }

    private static void tampilkanData() throws IOException{

        FileReader fileInput;
        BufferedReader bufferedReader;

        try {
            fileInput = new FileReader("database.txt");
            bufferedReader = new BufferedReader(fileInput);
        } catch (Exception e){
            System.err.println("database tidak ditemuukan");
            System.err.println("silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }

        String data = bufferedReader.readLine();


        System.out.println("\n| No |\tTahun |\tPenlis             |\tPenerbit   |\tJudul Buku   ");
        System.out.println("----------------------------------------------------------------");

        int nomerData = 0;
        while(data != null){
            nomerData++;
            StringTokenizer stringTokenizer = new StringTokenizer(data,",");

            stringTokenizer.nextToken();
            System.out.printf("| %2d ",nomerData);
            System.out.printf("|\t%4s  ",stringTokenizer.nextToken());
            System.out.printf("|\t%-20s  ",stringTokenizer.nextToken());
            System.out.printf("|\t%-20s  ",stringTokenizer.nextToken());
            System.out.printf("|\t%s",stringTokenizer.nextToken());
            System.out.println();

            data = bufferedReader.readLine();
        }
        System.out.println("Akhir Dari Database");

        bufferedReader.close();
        fileInput.close();
    }

    private static void cariData() throws IOException{
        // membaca databasse ada atau tidak
        try {
            File file = new File("database.txt");
        } catch (Exception e){
            System.err.println("database tidak ditemuukan");
            System.err.println("silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }
        // ambil keyword dari user
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukan kata kunci untuk mencari buku : ");
        String cariString = terminalInput.nextLine();
        System.out.println(cariString);

        String[] keyword = cariString.split("\\s");

        // chek keyword di database
        cekBukuDiatabase(keyword, true);
    }

    private static void tambahData() throws IOException{
        FileWriter fileOutput = new FileWriter("database.txt", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileOutput);

        Scanner terminalInput = new Scanner(System.in);
        String penulis, judul, penerbit, tahun;

        System.out.print("masukan nama penulis : ");
        penulis = terminalInput.nextLine();
        System.out.print("masukan judul buku : ");
        judul = terminalInput.nextLine();
        System.out.print("masukan nama penerbit : ");
        penerbit = terminalInput.nextLine();
        System.out.print("masukan tahun terbit, format (YYYY) : ");
        tahun = ambilTahun();

        // chek buku di database

        String[] keyword = {tahun + "," + penulis + "," + penerbit + "," + judul};
        System.out.println(Arrays.toString(keyword));

        boolean isExsist = cekBukuDiatabase(keyword, false);

        // menulis buku di database
        if(!isExsist){
//            faqihza_2021_1,2019,faqihza,gramedia,Belajar java
            System.out.println(ambilEntryPertahun(penulis, tahun));
            long nomorEntry = ambilEntryPertahun(penulis, tahun) + 1;

            String penulisTanpaSpasi = penulis.replaceAll("\\s+", "");
            String PrimaryKey = penulisTanpaSpasi + "_" + tahun + "_" + nomorEntry;
            System.out.println("\ndata yang akan anda masukan adalah");
            System.out.println("----------------------------------");
            System.out.println("primary key  : " + PrimaryKey);
            System.out.println("tahun terbit : " + tahun);
            System.out.println("penulis      : " + penulis);
            System.out.println("judul buku   : " + judul);
            System.out.println("penerbit     : " + penerbit);

            boolean isTambah = getYesOrNo("apakah kalian ingin menambah data tersebut");

            if(isTambah){
                bufferedWriter.write(PrimaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        } else {
            System.out.println("buku yang anda akan masukan sudah tersedia di database dengan data berikut");
            cekBukuDiatabase(keyword, true);
        }

        bufferedWriter.close();
    }

    private static long ambilEntryPertahun(String penulis, String tahun) throws IOException{
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

        return entry;
    }

    private static boolean cekBukuDiatabase(String[] keywords, boolean isDisplay) throws IOException{

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

        return isExsist;

    }

    private static String ambilTahun() throws IOException{
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

    private static boolean getYesOrNo(String message) {
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

    private static void clearScrean(){
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
