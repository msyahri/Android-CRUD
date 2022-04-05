package pmo.M_Syahri_19SA1239.CRUD;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button btn_Login, btn_Reg;
    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;

    //Authentication Variable
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Instance / Membuat Objek Firebase Auth
        auth = FirebaseAuth.getInstance();

        //Cek keberadaan User
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //Mengecek keberadaan User login / belum logout
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null && user.isEmailVerified()){
                    //Jika ada, akan langsung ke Home
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }
            }
        };



        relativeLayout = findViewById(R.id.mainactivity);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();

        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }

    //Menerapkan Listener
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }


    //Melepas Listener
    @Override
    protected void onStop() {
        super.onStop();
        if(listener != null){
            auth.removeAuthStateListener(listener);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (animationDrawable != null && !animationDrawable.isRunning()){
            animationDrawable.start();
        }

        btn_Login = findViewById(R.id.btn_Login);
        btn_Reg = findViewById(R.id.btn_Reg);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new
                        Intent(MainActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}