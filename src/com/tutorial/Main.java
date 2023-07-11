package com.tutorial;

import java.io.IOException;
import java.util.Scanner;

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
        boolean isTambah = getYesOrNo("Apakah anda ingin menambah data");
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
