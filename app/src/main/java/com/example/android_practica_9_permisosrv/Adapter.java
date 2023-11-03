package com.example.android_practica_9_permisosrv;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {


    private List<ListElement> mData;
    private LayoutInflater minflater;
    private Context context;
    private static final int REQUEST_CALL_PERMISSION = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;

    private static final int REQUEST_STORAGE_PERMISSION = 4;

    public Adapter(List<ListElement> itemList, Context context){
        this.minflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = minflater.inflate(R.layout.itempermiso,parent,false);
        return new Adapter.viewHolder(v);

    }



    public void onBindViewHolder(@NonNull Adapter.viewHolder holder, final int position) {
        final ListElement element = mData.get(position);
        holder.bindData(element);

        holder.sw_activo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // El usuario activó el switch, solicita el permiso correspondiente
                    if (element.permiso.equals("Permiso para llamadas")) {
                        if (ActivityCompat.checkSelfPermission(holder.sw_activo.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) holder.sw_activo.getContext(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
                        }
                    } else if (element.permiso.equals("Permiso para cámara")) {
                        if (ActivityCompat.checkSelfPermission(holder.sw_activo.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) holder.sw_activo.getContext(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                        }
                    }
                    else if (element.permiso.equals("Permiso para almacenamiento")) {
                        if (ActivityCompat.checkSelfPermission(holder.sw_activo.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) holder.sw_activo.getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
                        }
                    }
                }
            }
        });

        // Establece el estado del switch en función del valor almacenado en el modelo
        holder.sw_activo.setChecked(element.switchState);
    }




    public void setItems(List<ListElement> items) { mData = items;}

    public interface OnItemClickListener {
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        public CompoundButton sw_activo;
        TextView permiso;


        //Contructor
         viewHolder(@NonNull View itemView) {
            super(itemView);
             permiso = itemView.findViewById(R.id.permiso);
             sw_activo = itemView.findViewById(R.id.btnswitch);
        }

        public void bindData(final ListElement item) {
            permiso.setText(item.getPermiso());

        }
    }
}

