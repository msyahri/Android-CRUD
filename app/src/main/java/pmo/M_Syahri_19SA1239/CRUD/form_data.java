package pmo.M_Syahri_19SA1239.CRUD;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

import static android.text.TextUtils.isEmpty;

public class form_data extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText NIM, Nama, Tgllahir, Nope, Email, Ipk, Alamat;
    private Button Simpan, ShowData, getFoto;
    private String getNIM, getNama, getTgllahir, getEmail, getIpk, getNope, getAlamat, getDarah, getJeniskelamin, getGambar;
    private ImageView profilPic;
    private RadioButton radioPria, radioWanita, jk;
    private RadioGroup rgJenisKelamin;
    private CheckBox golA, golB, golAB, golO;
    public Uri imageurl;
    data_mahasiswa data_Mahasiswa;


    FirebaseStorage storage;
    StorageReference storageReference;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    String getFakultas, getProdi;
    Spinner Fakultas, Prodi;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    DatabaseReference getReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data);

        data_Mahasiswa = new data_mahasiswa();
        //inisiasi checkbox
        golA = findViewById(R.id.golA);
        golB = findViewById(R.id.golB);
        golAB = findViewById(R.id.golAB);
        golO = findViewById(R.id.golO);

        radioPria = findViewById(R.id.radioPria);
        radioWanita = findViewById(R.id.radioWanita);
        rgJenisKelamin = findViewById(R.id.rgjeniskelamin);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //oinisiasi profil pic
        profilPic = findViewById(R.id.profilPic);

        //Init fto
        getFoto = findViewById(R.id.getfoto);


        //inisialisasi ID (progressbar)
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);


        //inisiasi id (button)
        Simpan = findViewById(R.id.save);
        ShowData = findViewById(R.id.showdata);

        //inisiasi id(spinner)
        Fakultas = findViewById(R.id.Fakultas);
        Prodi = findViewById(R.id.Prodi);


        //inisisasi id (edit text)
        NIM = findViewById(R.id.nim);
        Nama = findViewById(R.id.nama);
        Tgllahir = findViewById(R.id.tgllahir);

        Email = findViewById(R.id.Email);
        Alamat = findViewById(R.id.alamat);
        Ipk = findViewById(R.id.ipk);
        Nope = findViewById(R.id.nope);


        //mendapatkan instance dari database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //mendapatkan referensi dari database
        getReference = database.getReference();


        //membuat fungsi date picker
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        Tgllahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateDialog();
            }
        });

        //fungsi upload foto
        profilPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);


            }
        });


        //membuat fungsi tombol simpan

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int pilihjk = rgJenisKelamin.getCheckedRadioButtonId();
                jk = findViewById(pilihjk);


                getNIM = NIM.getText().toString();
                getNama = Nama.getText().toString();
                getFakultas = Fakultas.getSelectedItem().toString();
                getProdi = Prodi.getSelectedItem().toString();
                getEmail = Email.getText().toString();
                getNope = Nope.getText().toString();
                getAlamat = Alamat.getText().toString();
                getIpk = Ipk.getText().toString();
                getTgllahir = Tgllahir.getText().toString();
              //  getJeniskelamin = jk.getText().toString();
                if(radioPria.isChecked()){
                    getJeniskelamin = jk.getText().toString();
                }else{

                }
                if(radioWanita.isChecked()){
                    getJeniskelamin = jk.getText().toString();
                }else{

                }

                if (golA.isChecked()) {
                    getDarah = golA.getText().toString();
                } else {

                }
                if (golB.isChecked()) {
                    getDarah = golB.getText().toString();
                } else {

                }
                if (golAB.isChecked()) {
                    getDarah = golAB.getText().toString();
                } else {

                }
                if (golO.isChecked()) {
                    getDarah = golO.getText().toString();

                } else {

                }


                checkUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        ShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        getFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        golA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    golB.setChecked(false);
                    golAB.setChecked(false);
                    golO.setChecked(false);
                }
            }
        });

        golB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    golA.setChecked(false);
                    golAB.setChecked(false);
                    golO.setChecked(false);
                }
            }
        });

        golAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    golA.setChecked(false);
                    golB.setChecked(false);
                    golO.setChecked(false);
                }
            }
        });

        golO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    golA.setChecked(false);
                    golB.setChecked(false);
                    golAB.setChecked(false);
                }
            }
        });
    }

    private void getImage() {
        Intent imageIntenGallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(imageIntenGallery, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (resultCode == RESULT_OK) {
                    profilPic.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    profilPic.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK) {
                    profilPic.setVisibility(View.VISIBLE);
                    Uri uri = data.getData();
                    profilPic.setImageURI(uri);
                }
                break;
        }
    }

    private void uploadToFirebase(Uri uri) {

        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Toasty.success(form_data.this, "Berhasil Upload", Toast.LENGTH_SHORT, true).show();
                       // Toast.makeText(form_data.this, "Berhasil Upload", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.error(form_data.this, "Gagal Upload", Toast.LENGTH_SHORT, true).show();
              //  Toast.makeText(form_data.this, "Gagal Upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri) {

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }


    private void ShowDateDialog() {

        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                Tgllahir.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }

    private void checkUser() {

        if (isEmpty(getNIM) || isEmpty(getNama) || isEmpty(getAlamat) || isEmpty(getEmail) || isEmpty(getIpk) || isEmpty(getNope) || isEmpty(getTgllahir))   {
            Toasty.warning(form_data.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT, true).show();
           // Toast.makeText(form_data.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        } else {
            profilPic.setDrawingCacheEnabled(true);
            profilPic.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) profilPic.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            //mengkompres Bitmap menjadi JPG dengan kualitas gambar 100%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();

            //Lokasi lengkap dimana gambar akan disimpan
            String namaFile = UUID.randomUUID() + ".jpg";
            final String pathImage = "Gambar/" + namaFile;
            UploadTask uploadTask = storageReference.child(pathImage).putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                            getReference.child("Admin").child("Mahasiswa").push()
                                    .setValue(new data_mahasiswa(getNIM, getNama, getFakultas, getProdi, getTgllahir, getNope, getEmail, getIpk, getAlamat, getDarah, getJeniskelamin, uri.toString().trim()))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                           /* NIM.setText("");
                                            Nama.setText("");
                                            Fakultas.setSelected(Boolean.parseBoolean(""));
                                            Prodi.setSelected(Boolean.parseBoolean(""));
                                            Tgllahir.setText("");
                                            Nope.setText("");
                                            Email.setText("");
                                            Alamat.setText("");
                                            Ipk.setText("");
                                            if (golA.isChecked()) {
                                                golA.setChecked(false);
                                            } else if (golB.isChecked()) {
                                                golB.setChecked(false);
                                            } else if (golAB.isChecked()) {
                                                golAB.setChecked(false);
                                            } else if (golO.isChecked()) {
                                                golO.setChecked(false);
                                            }*/
                                            Toasty.success(form_data.this, "Data Tersimpan", Toast.LENGTH_SHORT, true).show();
                                            //Toast.makeText(form_data.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                            finish();

                                        }
                                    });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toasty.error(form_data.this, "Gagal upload", Toast.LENGTH_SHORT, true).show();
                   // Toast.makeText(form_data.this, "Gagal Upload", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progressBar.setProgress((int) progress);
                }
            });
        }
    }
}