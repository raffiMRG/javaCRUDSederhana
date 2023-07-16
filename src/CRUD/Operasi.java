package CRUD;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Operasi {

    public static void updateData() throws IOException {
        // ambil database original
        File database = new File("database.txt");
        FileReader fileReader = new FileReader(database);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileWriter = new FileWriter(tempDB);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // tampilkan data
        System.out.println("List buku");
        tampilkanData();

        // ambil user input atau pilihan data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan buku yang akan diupdate : ");
        int updateNum = terminalInput.nextInt();

        // tampilkan data yang ingin di update
        String data = bufferedReader.readLine();
        int entryCount = 0;

        while (data != null){
            entryCount++;

            StringTokenizer st = new StringTokenizer(data, ",");

            // tampilkan entery count jika == update numm
            if(updateNum == entryCount){
                System.out.println("\nData yang ingin anda update adalah : ");
                System.out.println("---------------------------------------");
                System.out.println("Refrensi            : " + st.nextToken());
                System.out.println("Tahun               : " + st.nextToken());
                System.out.println("Penulis             : " + st.nextToken());
                System.out.println("Penerbit            : " + st.nextToken());
                System.out.println("judul               : " + st.nextToken());

                // update data

                // mengambil input dari user
                String[] feldData = {"tahun", "penulis", "penerbit", "judul"};
                String[] tempData = new String[4];

                // refresh token
                st = new StringTokenizer(data, ",");
                String originalData = st.nextToken();

                for (int i = 0; i < feldData.length; i++){
                    boolean isUpdate = Utility.getYesOrNo("Apakah anda ingin mengubah nama " + feldData[i]);

                    originalData = st.nextToken();
                    if (isUpdate){
                        // user input
                        if (feldData[i].equalsIgnoreCase("tahun")){
                            System.out.println("masukan tahun terbit, format = (YYYY) : ");
                            tempData[i] = Utility.ambilTahun();
                        }else {
                            terminalInput = new Scanner(System.in);
                            System.out.print("\nMasukan " + feldData[i] + " baru : ");
                            tempData[i] = terminalInput.nextLine();
                        }
                    } else {
                        tempData[i] = originalData;
                    }

                }

                // tampilkan data baru ke layar
                st = new StringTokenizer(data, ",");
                st.nextToken();
                System.out.println("\nData barua adalah : ");
                System.out.println("---------------------------------------");
                System.out.println("Tahun               : " + st.nextToken() + " --> " + tempData[0]);
                System.out.println("Penulis             : " + st.nextToken() + " --> " + tempData[1]);
                System.out.println("Penerbit            : " + st.nextToken() + " --> " + tempData[2]);
                System.out.println("judul               : " + st.nextToken() + " --> " + tempData[3]);

                boolean isUpdate = Utility.getYesOrNo("apakah anda yakin ingin mengupdate data tersebut?");
                if (isUpdate){
                    // chek data baru di databse
                    boolean isExsist = Utility.cekBukuDiatabase(tempData, true);
                    if (isExsist){
                        System.err.println("data buku sudah ada di database,\nproses update di batalkan silahkan delet data");
                        bufferedWriter.write(data);
                    } else {
                        // format data baru kedalam data base
                        String tahun = tempData[0];
                        String penulis = tempData[1];
                        String penerbit = tempData[2];
                        String judul = tempData[3];

                        // kita buat primary key
                        long nomorEntry = Utility.ambilEntryPertahun(penulis, tahun) + 1;

                        String penulisTanpaSpasi = penulis.replaceAll("\\s+", "");
                        String PrimaryKey = penulisTanpaSpasi + "_" + tahun + "_" + nomorEntry;

                        // tulis kedalam database
                        bufferedWriter.write(PrimaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                    }

                } else {
                    // coppy data
                    bufferedWriter.write(data);
                }
            } else {
                // copy data
                bufferedWriter.write(data);
            }
            bufferedWriter.newLine();

            data = bufferedReader.readLine();
        }
        // menulis data ke file
        bufferedWriter.flush();

        bufferedReader.close();
        fileReader.close();
        bufferedWriter.close();
        fileWriter.close();

        // delete original data base
        database.delete();

        // ganti nama tempDB menjadi database
        tempDB.renameTo(database);

        System.out.println("data terupdate");
    }

    public static void deleteData () throws IOException {
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
        // pastikan method yang kita panggil sudah menutup pemanggilan file
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

                // pastikan method yang kita panggil sudah menutup pemanggilan file
                isDelete = Utility.getYesOrNo("Apakah anda yakin akan menghapus?");
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

        // pastikan method yang kita panggil sudah menutup pemanggilan file, agar file dapat di hapus atau di ubah namanya
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

    public static void tampilkanData() throws IOException{

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

    public static void cariData() throws IOException{
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
        Utility.cekBukuDiatabase(keyword, true);
    }

    public static void tambahData() throws IOException{
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
        tahun = Utility.ambilTahun();

        // chek buku di database

        String[] keyword = {tahun + "," + penulis + "," + penerbit + "," + judul};
        System.out.println(Arrays.toString(keyword));

        boolean isExsist = Utility.cekBukuDiatabase(keyword, false);

        // menulis buku di database
        if(!isExsist){
//            faqihza_2021_1,2019,faqihza,gramedia,Belajar java
            System.out.println(Utility.ambilEntryPertahun(penulis, tahun));
            long nomorEntry = Utility.ambilEntryPertahun(penulis, tahun) + 1;

            String penulisTanpaSpasi = penulis.replaceAll("\\s+", "");
            String PrimaryKey = penulisTanpaSpasi + "_" + tahun + "_" + nomorEntry;
            System.out.println("\ndata yang akan anda masukan adalah");
            System.out.println("----------------------------------");
            System.out.println("primary key  : " + PrimaryKey);
            System.out.println("tahun terbit : " + tahun);
            System.out.println("penulis      : " + penulis);
            System.out.println("judul buku   : " + judul);
            System.out.println("penerbit     : " + penerbit);

            boolean isTambah = Utility.getYesOrNo("apakah kalian ingin menambah data tersebut");

            if(isTambah){
                bufferedWriter.write(PrimaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        } else {
            System.out.println("buku yang anda akan masukan sudah tersedia di database dengan data berikut");
            Utility.cekBukuDiatabase(keyword, true);
        }

        bufferedWriter.close();
    }

}
