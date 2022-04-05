package pmo.M_Syahri_19SA1239.CRUD;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;
import pmo.M_Syahri_19SA1239.CRUD.Model.Pengguna;

public class HomeActivity extends AppCompatActivity {
    Context context = this;
    Button btn_Logout, btn_Data, btn_Info, btn_About;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    public String emailuser;

    //Deklarasi Variable
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Pengguna");
        userID = user.getUid();

        final TextView emailTextView = (TextView) findViewById(R.id.emailnya);
        //emailuser = ;
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pengguna userProfile = snapshot.getValue(Pengguna.class);

                if (userProfile != null) {
                    String emailnya = userProfile.email;

                    emailTextView.setText(emailnya);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toasty.error(HomeActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT, true).show();
               // Toast.makeText(HomeActivity.this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });




       /* if(getIntent().getExtras()!=null){

             // Jika Bundle ada, ambil data dari Bundle

            Bundle bundle = getIntent().getExtras();
            email = bundle.getString("emailnya");
        }else{

             // Apabila Bundle tidak ada, ambil dari Intent

            email = ("ngga ketemu emailnya");
        }*/

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.info_logged, null);
       // TextView customText = (TextView) customView.findViewById(R.id.user_email);
     //   customText.setText(emailuser);

//Instance Firebasee Auth
        auth = FirebaseAuth.getInstance();
//Menambahkan Listener untuk mengecek apakah user telah logout / keluar
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//Jika Iya atau Null, maka akan berpindah pada halaman Login
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Toasty.info(HomeActivity.this, "Anda telah logout", Toast.LENGTH_SHORT, true).show();
                   // Toast.makeText(HomeActivity.this, "Anda Logout", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        btn_Logout = findViewById(R.id.btn_Logout);
        btn_Data = findViewById(R.id.btn_Data);
        btn_Info = findViewById(R.id.btn_Info);
        btn_About = findViewById(R.id.btn_About);

        btn_Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,
                        ListDataActivity.class);
                startActivity(intent);
            }
        });
        btn_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialStyledDialog.Builder(HomeActivity.this)
                        .setHeaderColor(R.color.primaryTextColor)
                        .setCustomView(customView)
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveText("OK")
                        .setCancelable(false)
                        .withDialogAnimation(true)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Log.d("MaterialStyledDialogs", "Do something!");
                                ((ViewGroup) customView.getParent()).removeView(customView);
                            }
                        })
                        .show();
            }

        });
        btn_About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("About");
                builder.setMessage("Aplikasi ini dibuat untuk memenuhi tugas UTS Pemrograman Mobile.\n\n" +
                        "Purwokerto, 2021");

             /*   // Membuat tombol negativ
                builder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });*/
                //Membuat tombol positif
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Bila pilih ok, maka muncul toast
                        /* Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();*/
                    }
                });
                builder.show();
            }
        });

        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//Fungsi untuk logout
                auth.signOut();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}