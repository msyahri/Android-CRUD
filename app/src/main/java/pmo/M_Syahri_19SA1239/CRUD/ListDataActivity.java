package pmo.M_Syahri_19SA1239.CRUD;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class  ListDataActivity extends AppCompatActivity implements RecyclerViewAdapter.dataListener {

    //Deklarasi Variable untuk RecyclerView
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //Deklarasi Variable Database Reference dan Araylist dengan parammeter class model kita
    private DatabaseReference reference;
    private ArrayList<data_mahasiswa> dataMahasiswa;
    private FloatingActionButton fab;

    //Deklarasi SearchView
    private EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        recyclerView = findViewById(R.id.datalist);
        fab = findViewById(R.id.fab);

        //Ubah
        GetData("");

        //Initial and function Searchview

        searchView=findViewById(R.id.etSearch);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                   GetData2(s.toString());


                }
                else {
                    adapter.getFilter().filter(s.toString());
                }

            }
        });

        MyRecyclerView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListDataActivity.this,form_data.class);
                startActivity(intent);
            }
        });

    }

    private void GetData(String data) {
        //Toast.makeText(getApplicationContext(),"Memuat data...", Toast.LENGTH_LONG).show();
        Toasty.info(getApplicationContext(), "Memuat data....", Toast.LENGTH_SHORT, true).show();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("Mahasiswa")/*.orderByChild("nama").startAt(data.toUpperCase()).endAt(data.toLowerCase()+"\uf8ff")*/
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {

                       //Inisialisasi Arraylist
                        dataMahasiswa = new ArrayList<>();

                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                            data_mahasiswa mahasiswa = snapshot.getValue(data_mahasiswa.class);

                            mahasiswa.setKey(snapshot.getKey());
                            dataMahasiswa.add(mahasiswa);
                        }

                        adapter = new RecyclerViewAdapter(dataMahasiswa,ListDataActivity.this);


                        recyclerView.setAdapter(adapter);
                        //Toast.makeText(getApplicationContext(),"Selesai Dimuat",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {


                       // Toast.makeText(getApplicationContext(),"Gagal Dimuat",Toast.LENGTH_LONG).show();
                        Toasty.error(getApplicationContext(), "Gagal dimuat", Toast.LENGTH_SHORT, true).show();
                        Log.e("MyListActivity", databaseError.getDetails()+""+databaseError.getMessage());

                    }
                });
    }

    private void GetData2(String data) {

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("Mahasiswa")/*.orderByChild("nama").startAt(data.toUpperCase()).endAt(data.toLowerCase()+"\uf8ff")*/
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {

                        //Inisialisasi Arraylist
                        dataMahasiswa = new ArrayList<>();

                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                            data_mahasiswa mahasiswa = snapshot.getValue(data_mahasiswa.class);

                            mahasiswa.setKey(snapshot.getKey());
                            dataMahasiswa.add(mahasiswa);
                        }

                        adapter = new RecyclerViewAdapter(dataMahasiswa,ListDataActivity.this);


                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {


                       // Toast.makeText(getApplicationContext(),"Gagal Dimuat",Toast.LENGTH_LONG).show();
                        Toasty.error(getApplicationContext(), "Gagal dimuat", Toast.LENGTH_SHORT, true).show();
                        Log.e("MyListActivity", databaseError.getDetails()+""+databaseError.getMessage());

                    }
                });
    }

    private void MyRecyclerView() {

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.underline));
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onDeleteData(data_mahasiswa data, int position) {

        if(reference !=null){
            reference.child("Admin")
                    .child("Mahasiswa")
                    .child(data.getKey())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                           // Toast.makeText(ListDataActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            Toasty.success(ListDataActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT, true).show();
                        }
                    });
        }
    }
}
