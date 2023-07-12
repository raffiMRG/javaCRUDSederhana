package com.tutorial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
                    break;
                case "3":
                    System.out.println("TAMBAH DATA BUKU");
                    // TAMBAH DATA
                    break;
                case "4":
                    System.out.println("UBAH DATA BUKU");
                    // UBAH DATA
                    break;
                case "5":
                    System.out.println("HAPUS DATA BUKU");
                    // HAPUS DATA
                    break;
                default:
                    System.out.println("\npilihan anda tidak di temukan silahkan masukan angka pilihan 1-5");
            }
            // getyesorno
            isLanjutkan = getYesOrNo("\nApakah anda ingin melanjutkan");
        }
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
