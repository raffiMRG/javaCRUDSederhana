package com.tutorial;

// import java library
import java.io.IOException;
import java.util.Scanner;

// import CRUD library
import CRUD.Operasi;
import CRUD.Utility;


public class Main {

    public static void main(String[] args) throws IOException {

        boolean isLanjutkan = true;
        String pilihanuser;
        Scanner terminalInput = new Scanner(System.in);

        while (isLanjutkan) {
            Utility.clearScrean();
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
                    Operasi.tampilkanData();
                    break;
                case "2":
                    System.out.println("CARI BUKU");
                    // CARI DATA
                    Operasi.cariData();
                    break;
                case "3":
                    System.out.println("TAMBAH DATA BUKU");
                    // TAMBAH DATA
                    Operasi.tambahData();
                    Operasi.tampilkanData();
                    break;
                case "4":
                    System.out.println("UBAH DATA BUKU");
                    // UBAH DATA
                    Operasi.updateData();
                    break;
                case "5":
                    System.out.println("HAPUS DATA BUKU");
                    // HAPUS DATA
                    Operasi.deleteData();
                    break;
                default:
                    System.out.println("\npilihan anda tidak di temukan silahkan masukan angka pilihan 1-5");
            }
            // getyesorno
            isLanjutkan = Utility.getYesOrNo("\nApakah anda ingin melanjutkan");
        }
    }
}
