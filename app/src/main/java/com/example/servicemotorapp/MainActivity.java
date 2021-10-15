package com.example.servicemotorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicemotorapp.model.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Service> listDaftarMontir = new ArrayList<Service>();
    private char category;
    private int slotService;
    private String fullCategory;
    private String namaMontir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. Menggantikan event onclick dengan kode program
        Button buttonTambahMontir = findViewById(R.id.button);
        buttonTambahMontir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahDataMontir();
            }
        });
        // 2. Menampilkan isi list view
        tampilkanView();
    }

    private void tambahDataMontir() {
        // 1. Mengaitkan Komponent GUI nama kamar dengan kode program
        EditText namaMontirEditText = findViewById(R.id.inputNamaMontir);
        // 2. Ambil data nama kamar dari komponent GUI
        namaMontir = namaMontirEditText.getText().toString();
        if(namaMontir.equals("")){
            return;
        }

        // 3. Tambah nama montir baru ke dalam array list
        // 3a. Memeriksa jika nama belum ada didalam array list
        boolean isAvaliable = false;

        for (Service s:listDaftarMontir) {
            if (s.getNamaMontir().toLowerCase().equals(namaMontir.toLowerCase())){
                isAvaliable = true;
                break;
            }
        }

        if (!isAvaliable){
            // 3b. Jika tidak ditemukan nama monitir, maka buat object baru dan tambahkan

            // 3c. Meminta data jenis kategori dengan alert dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Kategori : ");
            alert.setMessage("Ketikan Kategori jenisServis, 'M' untuk “Mesin’, 'A' untuk 'Aksesoris', 'B' untuk 'Body'\n");
    
            final EditText inputCategory = new EditText(this);
            alert.setView(inputCategory);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (inputCategory.getText().toString().equals("M")){
                        category = 'M';
                        fullCategory = "Mesin";
                    } else if(inputCategory.getText().toString().equals("A")){
                        category = 'A';
                        fullCategory = "Aksesoris";
                    } else if(inputCategory.getText().toString().equals("B")) {
                        category = 'B';
                        fullCategory = "Body";
                    }
                    // 3d. meminta data kapasitas atau sisa slot
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Sisa Slot  : ");
                    alert.setMessage("Ketikan sisa slot");

                    final EditText inputSlot = new EditText(MainActivity.this);
                    alert.setView(inputSlot);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            slotService = Integer.parseInt(inputSlot.getText().toString());
                            // 3e. membuat object baru
                            Service service  =  new Service(namaMontir, category, fullCategory, slotService, slotService);
                            // 3f. menambah object baru kedalam array list
                            listDaftarMontir.add(service);
                            // 3g.
                            rekamArrayListKeStorage();
                            // 3h. menampilkan isi array list
                            tampilkanView();
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.show();

                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();


            namaMontirEditText.setText("");
        } else {
            Toast.makeText(this, "Nama Montir sudah ada", Toast.LENGTH_LONG).show();
        }
    }

    private void rekamArrayListKeStorage() {
        try{
            FileOutputStream writeData = openFileOutput("datamontir.txt", Context.MODE_PRIVATE);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
            writeStream.writeObject(listDaftarMontir);
            writeStream.flush();
            writeStream.close();
        } catch(IOException e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Kesalahan saat merekam data ke storage ");
            alert.setMessage("Pesan kesalahan :" + e.getMessage());
            alert.show();
        }
    }

    public void handleClickMontirName(View view){
        // 1. Membaca nama montir dari view yang diklik
        String nameMonitir = ((TextView) view).getText().toString();
        // 2. Mencari object montir yang nama montirnya sama
        Service montirMatch = null;
        for(Service k:listDaftarMontir){
            // 2a. jika montir sesuai, maka ambil data Object montir
            if (k.getNamaMontir().equals(nameMonitir)) {
                montirMatch = k;
                break;
            }
        }
        // 3. Buat string untuk ditampilkan pada alert dialog
        String tampilan = String.format(
                " Nama Montir %s\n Kategori: %s\n Slot service: %d",
                montirMatch.getNamaMontir(),
                montirMatch.getFullCategory(),
                montirMatch.getSisaSlotService());
        // 4. Tampilkan data kamar yang disentuh
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Rincian Detail : ");
        alert.setMessage(tampilan);
        alert.show();
    }
    private void tampilkanView(){
        // Mengaitkan komponent GUI dengan kode program
        ListView ListDaftarMontir = findViewById(R.id.listMontir);

        // Mengumpulkan attribut nama dari semua object

        // 2a. Membaca array list dari storage
        bacaArrayListDariStorage();
        // 2b. Membuat berbagai nama montir yang dipisahkan koma
        String daftarMontir = "";
        String[] arrDaftarMontir = {};

        for(Service s:listDaftarMontir) {
            daftarMontir += s.getNamaMontir() + ',';
        }
        // 2c. Membuang koma terakhir dari string
        if(listDaftarMontir.size() > 0) {
            daftarMontir = daftarMontir.substring(0, daftarMontir.length() - 1);
            // 2d. membuat array statis nama kamar dengan metode split
            arrDaftarMontir = daftarMontir.split(",");
        }
        // 3. membuat adapter untuk list view, yaitu penghubung antara komponent GUI dan data berbentuk array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.montir_view, R.id.textListMontir, arrDaftarMontir);
        // 4. aktifkan adapter
        ListDaftarMontir.setAdapter(adapter);
    }

    private void bacaArrayListDariStorage() {
        FileInputStream readData = null;
        try {
            readData = this.openFileInput("datamontir.txt");
            ObjectInputStream readStream = new ObjectInputStream(readData);
            listDaftarMontir = (ArrayList<Service>) readStream.readObject();
            readStream.close();
        } catch (Exception e) {
            if (listDaftarMontir.size() != 0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Kesalahan saat membaca data dari storage ");
                alert.setMessage("Pesan kesalahan :" + e.getMessage());
                alert.show();
            }
        }
    }
}