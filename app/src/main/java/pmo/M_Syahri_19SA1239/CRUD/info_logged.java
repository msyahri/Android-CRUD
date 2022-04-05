package pmo.M_Syahri_19SA1239.CRUD;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class info_logged extends AppCompatActivity {
   /* private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;*/

    TextView user_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_logged);

       /* user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Pengguna");
        userID = user.getUid();

        final TextView emailTextView = (TextView) findViewById(R.id.user_email);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pengguna userProfile = snapshot.getValue(Pengguna.class);

                if(userProfile != null){
                    String email = userProfile.email;

                    emailTextView.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(info_logged.this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });*/

    }
}