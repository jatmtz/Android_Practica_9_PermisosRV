package com.example.android_practica_9_permisosrv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 3;
    static final int REQUEST_STORAGE_PERMISSION = 4;
    private static final int REQUEST_CODE_SELECCIONAR_ARCHIVO = 5;

    List<ListElement> elements;
    private static final int REQUEST_CALL_PERMISSION = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {

        elements = new ArrayList<>();
        elements.add(new ListElement("Permiso para llamadas"));
        elements.add(new ListElement("Permiso para cámara"));
        elements.add(new ListElement("Permiso para almacenamiento"));

        Adapter Adapter = new Adapter(elements, this);
        RecyclerView rc = findViewById(R.id.rvPermisos);
        rc.setAdapter(Adapter);
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setHasFixedSize(true);
    }

    private void realizarLlamada() {

        String numero = "123456789";

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + numero));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Necesitas otorgar permiso para realizar llamadas.", Toast.LENGTH_SHORT).show();
        }
    }
    private void abrirCamara() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {

            Toast.makeText(this, "Necesitas otorgar permiso para acceder a la cámara.", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirSelectorDeArchivos() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(intent, REQUEST_CODE_SELECCIONAR_ARCHIVO);
        } finally {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (elements.equals("Permiso para llamadas")) {
                    realizarLlamada();
                }
            } else if (requestCode == REQUEST_CAMERA_PERMISSION) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (elements.equals("Permiso para llamadas")) {
                        abrirCamara();
                    }
                }
            }
              else  if (requestCode == REQUEST_STORAGE_PERMISSION) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        abrirSelectorDeArchivos();
                    }
            }
        }
    }
}
