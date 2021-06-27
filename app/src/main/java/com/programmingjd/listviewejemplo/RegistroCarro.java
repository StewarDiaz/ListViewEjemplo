package com.programmingjd.listviewejemplo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.programmingjd.listviewejemplo.model.Carro;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class RegistroCarro extends AppCompatActivity implements View.OnClickListener {

    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 10 ;
    String img;
    private Button btnSend;
    private Button btnList;
    private TextView txtName;
    private TextView txtCCar;
    private TextView txtModel;
    private TextView txtValue;
    private TextView txtUrl;
    ImageView mImageView;
    Button btnChooseImg;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_carro);

        btnSend = findViewById(R.id.btnSaveNewCar);
        btnList = findViewById(R.id.btnViewList);
        txtName = findViewById(R.id.etNameCar);
        txtCCar = findViewById(R.id.etCcCar);
        txtModel = findViewById(R.id.etModelCar);
        txtValue = findViewById(R.id.etValueCar);
        txtUrl = findViewById(R.id.etUrlImage);
        btnSend.setOnClickListener(this);
        btnList.setOnClickListener(this);

        mImageView = findViewById(R.id.ivFormNewCar);
        btnChooseImg = findViewById(R.id.btnUpImg);
        btnChooseImg.setOnClickListener(this);




        ArrayList<Carro> cities =db.selectCar(db.getWritableDatabase());
        int i =1;
        for (Carro ciudadSelected: cities){
            System.out.println("el auto "+ ciudadSelected.getName() + " tiene un modelo"+ ciudadSelected.getModel());
            i++;
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSaveNewCar){
            if (txtName.getText().toString().isEmpty()|| txtCCar.getText().toString().isEmpty()||
                    txtModel.getText().toString().isEmpty()||txtValue.getText().toString().isEmpty()){
                Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show();

            }else{
                addCar();
                Toast.makeText(this, "Auto Guardado", Toast.LENGTH_SHORT).show();

            }
        }else if (v.getId()== R.id.btnViewList){
            Intent intentList = new Intent(this, MainActivity.class);
            startActivity(intentList);
        }else if (v.getId() == R.id.btnUpImg){
            mostrarDialogOpciones();
        }
    }

    private void mostrarDialogOpciones(){
        final CharSequence[] opciones = {"Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliger una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Elegir de Galeria")) {
                    //Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_FOTO);
                } else {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath = data.getData();
                img = miPath.toString();
                mImageView.setImageURI(miPath);
                break;
        }
    }

    private void addCar() {
        /*imageViewToByte(mImageView)*/
        Carro car = new Carro(txtName.getText().toString(),txtCCar.getText().toString(),txtModel.getText().toString(),
                txtValue.getText().toString(),txtUrl.getText().toString());
        db.insertCity(db.getWritableDatabase(),car);
        clearFields();
    }

    public void clearFields(){
        txtName.setText("");
        txtCCar.setText("");
        txtModel.setText("");
        txtValue.setText("");
        txtUrl.setText("");
    }

}