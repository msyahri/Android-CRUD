package pmo.M_Syahri_19SA1239.CRUD;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {


    private ArrayList<data_mahasiswa> listMahasiswa;
    private ArrayList<data_mahasiswa> listMahasiswaSearch;
    private Context context;

    public RecyclerViewAdapter(ArrayList<data_mahasiswa> listMahasiswa, Context context) {
        this.listMahasiswa = listMahasiswa;
        this.listMahasiswaSearch = listMahasiswa;
        this.context = context;
        listener = (ListDataActivity)context;
    }

    @Override
    public Filter getFilter() {
        return pencarian;
    }

    private Filter pencarian = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            ArrayList<data_mahasiswa> FilteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                FilteredList.addAll(listMahasiswaSearch);
            }else{
                String FilterPattern = constraint.toString().toLowerCase().trim();
                for(data_mahasiswa item : listMahasiswaSearch){
                    if (item.getNama().toLowerCase().contains(FilterPattern)){
                        FilteredList.add(item);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = FilteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listMahasiswa.clear();
            listMahasiswa.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface dataListener{
        void onDeleteData(data_mahasiswa data,int position);
    }
    dataListener listener;

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design,parent,false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        //mengambil nilai/value yang terdapat pdada recycleview berdasarkan posisi tertentu
        final String NIM = listMahasiswa.get(position).getNim();
        final String Nama = listMahasiswa.get(position).getNama();
        final String Fakultas = listMahasiswa.get(position).getFakultas();
        final String Prodi = listMahasiswa.get(position).getProdi();
        final String Alamat = listMahasiswa.get(position).getAlamat();
        final String Nope = listMahasiswa.get(position).getNope();
        final String Email =listMahasiswa.get(position).getEmail();
        final String Tgllahir = listMahasiswa.get(position).getTgllahir();
        final String Ipk = listMahasiswa.get(position).getIpk();
        final String Darah = listMahasiswa.get(position).getDarah();
        final String JenisKelamin = listMahasiswa.get(position).getJeniskelamin();
        final String Gambar = listMahasiswa.get(position).getGambar();

        //memasukan nilai kedalam view
        holder.NIM.setText      (": "+NIM);
        holder.Nama.setText     (": "+Nama);
        holder.Fakultas.setText (": "+ Fakultas);
        holder.Prodi.setText    (": " + Prodi);
        holder.Tgllahir.setText (": "+Tgllahir);
        holder.Alamat.setText   (": "+Alamat);
        holder.Email.setText    (": "+Email);
        holder.Nope.setText     (": "+ Nope);
        holder.Ipk.setText      (": "+Ipk);
        holder.Darah.setText(": "+Darah);
        holder.jk.setText (": "+JenisKelamin);
        if(Gambar==null) {
            holder.Gambar.setImageResource(R.drawable.ic_person);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(Gambar.trim())
                    .into(holder.Gambar);
        }



        holder.ListItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final  String[] action = {"Update Data","Hapus Data"};
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Pilih");
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                            /*
                            berpindah activity pada halaman layout Update Data
                            dan mengambil data pada listmahasiswa, berdasarkan posisinya
                            untuk dikirim pada activity selanjutnya
                             */

                                Bundle bundle = new Bundle();
                                bundle.putString("dataNIM", listMahasiswa.get(position).getNim());
                                bundle.putString("dataNama",listMahasiswa.get(position).getNama());
                                bundle.putString("dataFakultas",listMahasiswa.get(position).getFakultas());
                                bundle.putString("dataProdi",listMahasiswa.get(position).getProdi());
                                bundle.putString("dataTgllahir",listMahasiswa.get(position).getTgllahir());
                                bundle.putString("dataNope",listMahasiswa.get(position).getNope());
                                bundle.putString("dataAlamat",listMahasiswa.get(position).getAlamat());
                                bundle.putString("dataEmail",listMahasiswa.get(position).getEmail());
                                bundle.putString("dataIpk",listMahasiswa.get(position).getIpk());
                                bundle.putString("dataDarah", listMahasiswa.get(position).getDarah());
                                bundle.putString("dataJeniskelamin",listMahasiswa.get(position).getJeniskelamin());
                                bundle.putString("getPrimaryKey",listMahasiswa.get(position).getKey());
                                bundle.putString("dataGambar",listMahasiswa.get(position).getGambar());
                                Intent intent = new Intent(v.getContext(), ActivityUpdate.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);

                                break;

                            case 1:
                                AlertDialog.Builder konfirm = new AlertDialog.Builder(v.getContext());
                                konfirm.setTitle("Konfirmasi");
                                konfirm.setMessage("Anda yakin untuk menghapus?");

                                konfirm.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                konfirm.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        listener.onDeleteData(listMahasiswa.get(position),position);
                                    }
                                });
                                konfirm.show();
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMahasiswa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView NIM, Nama, Fakultas,Prodi,Nope,Email,Alamat,Tgllahir,Ipk,jk, Darah;
        private ImageView Gambar;
        private LinearLayout ListItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            NIM = itemView.findViewById(R.id.nim);
            Nama =itemView.findViewById(R.id.nama);
            Fakultas=itemView.findViewById(R.id.fakultas);
            Prodi=itemView.findViewById(R.id.prodi);
            Nope=itemView.findViewById(R.id.nope);
            Email=itemView.findViewById(R.id.email);
            Alamat=itemView.findViewById(R.id.alamat);
            Ipk=itemView.findViewById(R.id.ipk);
            Tgllahir=itemView.findViewById(R.id.tgllahir);
            Darah = itemView.findViewById(R.id.Darah);
            jk=itemView.findViewById(R.id.jk);
            Gambar = itemView.findViewById(R.id.Gambar);
          //  darah=itemView.findViewById(R.id.)

            ListItem = itemView.findViewById(R.id.list_item);
        }
    }
}