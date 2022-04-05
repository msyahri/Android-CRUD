package pmo.M_Syahri_19SA1239.CRUD;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;
import pmo.M_Syahri_19SA1239.CRUD.Model.Pengguna;

public class RegisterActivity extends AppCompatActivity {
    //Deklarasi Variable
    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;
    private EditText ed_email, ed_pass;
    private Button btn_Login, btn_Reg;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private String getEmail, getPassword;
    private ImageButton ib_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        ed_email = findViewById(R.id.ed_email);
        ed_pass = findViewById(R.id.ed_pass);
        btn_Login = findViewById(R.id.btn_Login);
        btn_Reg = findViewById(R.id.btn_Reg);
        ib_back = findViewById(R.id.ib_back);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

//Instance / Membuat Objek Firebase Authentication
        auth = FirebaseAuth.getInstance();
        btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekDataUser();
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        });
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Method ini digunakan untuk mengecek dan mendapatkan data yang dimasukan user
    private void cekDataUser() {

//Mendapatkan data yang diinputkan User
        getEmail = ed_email.getText().toString();
        getPassword = ed_pass.getText().toString();

//Mengecek apakah email dan sandi kosong atau tidak
        if (TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)) {
            Toasty.warning(RegisterActivity.this, "Email atau sandi tidak boleh kosong", Toast.LENGTH_SHORT, true).show();
           //Toast.makeText(this, "Email atau Sandi Tidak Boleh Kosong",Toast.LENGTH_SHORT).show();
        } else {

//Mengecek panjang karakter password baru yang akan didaftarkan
            if (getPassword.length() < 8) {
                Toasty.warning(RegisterActivity.this, "Sandi terlalu pendek. Minimal 8 karakter", Toast.LENGTH_SHORT, true).show();
               // Toast.makeText(this, "Sandi Terlalu Pendek, Minimal 6 Karakter",Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                createUserAccount();
            }
        }
    }

    //Method ini digunakan untuk membuat akun baru user
    private void createUserAccount() {
        auth.createUserWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Pengguna user = new Pengguna(getEmail, getPassword);
                        FirebaseDatabase.getInstance().getReference("Pengguna").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new
                                                                                                                                                                                         OnCompleteListener<Void>() {
                                                                                                                                                                                             @Override
                                                                                                                                                                                             public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                                                 //Mengecek status keberhasilan saat medaftarkan email dan sandi baru
                                                                                                                                                                                                 if (task.isSuccessful()) {
                                                                                                                                                                                                     auth.getCurrentUser().sendEmailVerification()
                                                                                                                                                                                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                                                         @Override
                                                                                                                                                                                                         public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                                                             if (task.isSuccessful()){
                                                                                                                                                                                                                 Toasty.success(RegisterActivity.this, "\"Registrasi Sukses!\\nSilahkan cek email Anda untuk verifikasi", Toast.LENGTH_SHORT, true).show();
                                                                                                                                                                                                                // Toast.makeText(RegisterActivity.this, "Registrasi Sukses!\nSilahkan cek Email Anda untuk verifikasi.",Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                                 Intent intent = new
                                                                                                                                                                                                                         Intent(RegisterActivity.this, LoginActivity.class);
                                                                                                                                                                                                                 startActivity(intent);
                                                                                                                                                                                                                 finish();
                                                                                                                                                                                                             }else{
                                                                                                                                                                                                                 Toasty.warning(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT, true).show();
                                                                                                                                                                                                                // Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                             }

                                                                                                                                                                                                         }
                                                                                                                                                                                                     });

                                                                                                                                                                                                 } else {
                                                                                                                                                                                                     Toasty.error(RegisterActivity.this, "Terjadi Kesalahan. Silakan coba lagi", Toast.LENGTH_SHORT, true).show();
                                                                                                                                                                                                   //  Toast.makeText(RegisterActivity.this, "Terjadi Kesalahan, Silakan Coba Lagi", Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                     progressBar.setVisibility(View.GONE);
                                                                                                                                                                                                 }
                                                                                                                                                                                             }
                                                                                                                                                                                         });
                    }
                });
    }
}