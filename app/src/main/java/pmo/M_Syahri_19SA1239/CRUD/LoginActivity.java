package pmo.M_Syahri_19SA1239.CRUD;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    //Deklarasi Variable

    private EditText ed_email, ed_pass;
    private Button btn_Login, btn_Reg;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;
    private String getEmail, getPassword;
    private ImageButton ib_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        //Inisialisasi Widget
        ed_email = findViewById(R.id.ed_email);
        ed_pass = findViewById(R.id.ed_pass);
        btn_Login = findViewById(R.id.btn_Login);
        btn_Reg = findViewById(R.id.btn_Reg);
        progressBar = findViewById(R.id.progressBar);
        ib_back = findViewById(R.id.ib_back);

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Instance / Membuat Objek Firebase Authentication
        auth = FirebaseAuth.getInstance();



        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mendapatkan data yang diinputkan User
                getEmail = ed_email.getText().toString();
                getPassword = ed_pass.getText().toString();

                //Mengecek apakah email dan sandi kosong atau tidak
                if(TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)){
                    Toasty.error(LoginActivity.this, "Email atau Sandi Tidak Boleh Kosong", Toast.LENGTH_SHORT, true).show();
                    //Toast.makeText(LoginActivity.this, "Email atau Sandi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    loginUserAccount();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }



    //Method ini digunakan untuk proses autentikasi user menggunakan email dan kata sandi
    private void loginUserAccount(){
        auth.signInWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //Mengecek status keberhasilan saat login
                        if(task.isSuccessful()){
                            if (auth.getCurrentUser().isEmailVerified()){
                                Toasty.success(LoginActivity.this, "Login Sukses", Toast.LENGTH_SHORT, true).show();
                               // Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }else{
                                Toasty.warning(LoginActivity.this, "Silahkan verifikasi email terlebih dahulu", Toast.LENGTH_SHORT, true).show();
                               // Toast.makeText(LoginActivity.this, "Silahkan verifikasi email terlebih dahulu.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }else {
                            Toasty.error(LoginActivity.this, "Tidak Dapat Masuk. Periksa email dan kata sandi", Toast.LENGTH_SHORT, true).show();
                            //.makeText(LoginActivity.this, "Tidak Dapat Masuk, Periksa email dan kata sandi.",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }


}