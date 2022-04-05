package pmo.M_Syahri_19SA1239.CRUD;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class ActivityUpdate extends AppCompatActivity {

    //deklarasi variable
    private ProgressBar progressbar;
    private EditText nimBaru,namaBaru,alamatBaru,ipkBaru,nopeBaru,emailBaru,tgllahirBaru;
    private Spinner fakultasBaru,prodiBaru;
    private Button update, btn_updateImg;
    private TextView goldarbaru;
    private CheckBox a1, b1, ab1, o1;
    private DatabaseReference database;
    private ImageView updateImg;
    private String cekNIM, cekNama, cekFakultas,cekProdi,cekAlamat,cekIpk,cekNope,cekEmail,cekTgllahir,cekJeniskelamin, cekDarah;
    private RadioButton jkBaru, new_radioPria, new_radioWanita;
    private RadioGroup rgjeniskelaminBaru;
    FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;


    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        nimBaru = findViewById(R.id.new_nim);
        namaBaru = findViewById(R.id.new_nama);
        fakultasBaru= findViewById(R.id.new_fakultas);
        prodiBaru = findViewById(R.id.new_prodi);
        alamatBaru=findViewById(R.id.new_alamat);
        ipkBaru=findViewById(R.id.new_ipk);
        nopeBaru=findViewById(R.id.new_nope);
        emailBaru=findViewById(R.id.new_email);
        tgllahirBaru=findViewById(R.id.new_tgllahir);
        update = findViewById(R.id.update);
        goldarbaru = findViewById(R.id.new_goldar);
        new_radioPria = findViewById(R.id.new_radioPria);
        new_radioWanita = findViewById(R.id.new_radioWanita);
        a1 = findViewById(R.id.new_golA);
        b1 = findViewById(R.id.new_golB);
        ab1 = findViewById(R.id.new_golAB);
        o1 = findViewById(R.id.new_golO);
        rgjeniskelaminBaru=findViewById(R.id.new_rgjeniskelamin);
        updateImg = findViewById(R.id.updateImg);
        btn_updateImg = findViewById(R.id.btn_updateImg);
        progressbar = findViewById(R.id.progressBar);
        progressbar.setVisibility(View.GONE);

        storageReference = FirebaseStorage.getInstance().getReference();


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        tgllahirBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateDialog();
            }
        });
        final String getJk = getIntent().getExtras().getString("dataJeniskelamin");
        final String getGoldar = getIntent().getExtras().getString("dataDarah");
        /*Toast.makeText(this,getGoldar,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,getJk,Toast.LENGTH_SHORT).show();*/
        if (getGoldar.equals("A")){
            a1.setChecked(true);
        }else if (getGoldar.equals("B")){
            b1.setChecked(true);
        }else if (getGoldar.equals("AB")){
            ab1.setChecked(true);
        }else if (getGoldar.equals("O")){
            o1.setChecked(true);
        }

        if (getJk.equals("Pria")){
            new_radioPria.setChecked(true);
        }else if (getJk.equals("Wanita")){
            new_radioWanita.setChecked(true);
        }

        database= FirebaseDatabase.getInstance().getReference();
        getData();

        btn_updateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pilihjkBaru = rgjeniskelaminBaru.getCheckedRadioButtonId();
                jkBaru = findViewById(pilihjkBaru);

                cekNIM = nimBaru.getText().toString();
                cekNama = namaBaru.getText().toString();
                cekFakultas = fakultasBaru.getSelectedItem().toString();
                cekProdi = prodiBaru.getSelectedItem().toString();
                cekTgllahir= tgllahirBaru.getText().toString();
                cekNope=nopeBaru.getText().toString();
                cekAlamat=alamatBaru.getText().toString();
                cekIpk=ipkBaru.getText().toString();
                cekEmail=emailBaru.getText().toString();
                if (a1.isChecked()) {
                    cekDarah = a1.getText().toString();
                    goldarbaru.setText(cekDarah);
                    cekDarah = goldarbaru.getText().toString();
                } else {

                }
                if (b1.isChecked()) {
                    cekDarah = b1.getText().toString();
                    goldarbaru.setText(cekDarah);
                    cekDarah = goldarbaru.getText().toString();
                } else {

                }
                if (ab1.isChecked()) {
                    cekDarah = ab1.getText().toString();
                    goldarbaru.setText(cekDarah);
                } else {

                }
                if (o1.isChecked()) {
                    cekDarah = o1.getText().toString();
                    goldarbaru.setText(cekDarah);
                } else {

                }


//mengubah data databse menggunakan data yang baru di inputkan
                if(isEmpty(cekNIM)|| isEmpty(cekNama)|| isEmpty(cekNope)|| isEmpty(cekAlamat)|| isEmpty(cekEmail)|| isEmpty(cekIpk)|| isEmpty(cekTgllahir)) {
                    Toasty.warning(ActivityUpdate.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT, true).show();
                  //  Toast.makeText(ActivityUpdate.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                }else{
                    progressbar.setVisibility(View.VISIBLE);

                    //Get Image from imageView
                    updateImg.setDrawingCacheEnabled(true);
                    updateImg.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) updateImg.getDrawable()).getBitmap();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    //Proses bitmap -> JPEG
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bytes = stream.toByteArray();

                    //Where the Image saved
                    String namafile = UUID.randomUUID()+".jpg";
                    final String pathImage = "foto/"+namafile;
                    UploadTask uploadTask = storageReference.child(pathImage).putBytes(bytes);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    /*Processing Update Data
                                    Reload setter recomended to get new data from user*/

                                    data_mahasiswa setMahasiswa = new data_mahasiswa();
                                    setMahasiswa.setNim(nimBaru.getText().toString());
                                    setMahasiswa.setNama(namaBaru.getText().toString());
                                    setMahasiswa.setFakultas(fakultasBaru.getSelectedItem().toString());
                                    setMahasiswa.setProdi(prodiBaru.getSelectedItem().toString());
                                    setMahasiswa.setTgllahir(tgllahirBaru.getText().toString());
                                    setMahasiswa.setNope(nopeBaru.getText().toString());
                                    setMahasiswa.setAlamat(alamatBaru.getText().toString());
                                    setMahasiswa.setIpk(ipkBaru.getText().toString());
                                    setMahasiswa.setEmail(emailBaru.getText().toString());
                                    setMahasiswa.setJeniskelamin(jkBaru.getText().toString());
                                    setMahasiswa.setDarah(goldarbaru.getText().toString());
                                    setMahasiswa.setGambar(uri.toString().trim());
                                    updateMahasiswa(setMahasiswa);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toasty.error(ActivityUpdate.this, "Update Gagal", Toast.LENGTH_SHORT, true).show();
                           // Toast.makeText(ActivityUpdate.this, "Update Gagal",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            progressbar.setVisibility(View.VISIBLE);
                            double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                            progressbar.setProgress((int) progress);
                        }
                    });


                }
            }
        });

        a1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    b1.setChecked(false);
                    ab1.setChecked(false);
                    o1.setChecked(false);
                }
            }
        });

        b1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    a1.setChecked(false);
                    ab1.setChecked(false);
                    o1.setChecked(false);
                }
            }
        });

        ab1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    a1.setChecked(false);
                    b1.setChecked(false);
                    o1.setChecked(false);
                }
            }
        });

        o1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    a1.setChecked(false);
                    b1.setChecked(false);
                    ab1.setChecked(false);
                }
            }
        });

    }

    private void getImage() {
        Intent ImageIntentGallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(ImageIntentGallery, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                break;
            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK){
                    try {
                        updateImg.setVisibility(View.VISIBLE);
                        Uri uri = data.getData();
                        Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                        updateImg.setImageBitmap(bitmap);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    //mengambil data dari database ke form update
    private void getData() {
        final String getNIM = getIntent().getExtras().getString("dataNIM");
        final String getNama = getIntent().getExtras().getString("dataNama");
        final String getFakultas = getIntent().getExtras().getString("dataFakultas");
        final String getProdi = getIntent().getExtras().getString("dataProdi");
        final String getTgllahir = getIntent().getExtras().getString("dataTgllahir");
        final String getNope = getIntent().getExtras().getString("dataNope");
        final String getAlamat = getIntent().getExtras().getString("dataAlamat");
        final String getIpk = getIntent().getExtras().getString("dataIpk");
        final String getGoldar = getIntent().getExtras().getString("dataDarah");
        final String getEmail = getIntent().getExtras().getString("dataEmail");
        final String getJk = getIntent().getExtras().getString("dataJeniskelamin");
        final String getGambar = getIntent().getExtras().getString("dataGambar");

        //set Image View
        if (isEmpty(getGambar)){
            updateImg.setImageResource(R.drawable.ic_person);
        }else{
            Glide.with(ActivityUpdate.this)
                    .load(getGambar)
                    .into(updateImg);
        }


        nimBaru.setText(getNIM);
        namaBaru.setText(getNama);
      //fakultasBaru.setSelected(getFakultas);
      //prodiBaru.setText(getProdi);
        goldarbaru.setText(getGoldar);
        tgllahirBaru.setText(getTgllahir);
        nopeBaru.setText(getNope);
        alamatBaru.setText(getAlamat);
        ipkBaru.setText(getIpk);
        emailBaru.setText(getEmail);




    }
    private void updateMahasiswa(data_mahasiswa mahasiswa) {

        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        database.child("Admin")
                .child("Mahasiswa")
                .child(getKey)
                .setValue(mahasiswa)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        nimBaru.setText("");
                        namaBaru.setText("");
                        tgllahirBaru.setText("");
                        nopeBaru.setText("");
                        alamatBaru.setText("");
                        ipkBaru.setText("");
                        emailBaru.setText("");


                        Toasty.success(ActivityUpdate.this, "Data berhasil diubah", Toast.LENGTH_SHORT, true).show();
                       // Toast.makeText(ActivityUpdate.this, "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
    private void ShowDateDialog() {

        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                tgllahirBaru.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }
}