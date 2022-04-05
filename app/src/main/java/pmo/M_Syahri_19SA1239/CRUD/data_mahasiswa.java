package pmo.M_Syahri_19SA1239.CRUD;
public class data_mahasiswa {

    private String nim;
    private String nama;
    private String key;
    private String fakultas;
    private String prodi;
    private String tgllahir;
    private String nope;
    private String email;
    private String ipk;
    private String alamat;
    private String darah;
    private String jeniskelamin;
    private String golA;
    private String golB;
    private String golAB;
    private String golO;
    private String gambar;



    public String getJeniskelamin() {
        return jeniskelamin;
    }

    public void setJeniskelamin(String jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getTgllahir() {
        return tgllahir;
    }

    public void setTgllahir(String tgllahir) {
        this.tgllahir = tgllahir;
    }

    public String getNope() {
        return nope;
    }

    public void setNope(String nope) {
        this.nope = nope;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpk() {
        return ipk;
    }

    public void setIpk(String ipk) {
        this.ipk = ipk;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDarah() {
        return darah;
    }

    public void setDarah(String goldar) {
        this.darah = goldar;
    }
    public String getgolA() {
        return golA;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
    public String getGambar() {
        return gambar;
    }

    public void setgolA(String a) {
        golA = a;
    }

    public String getgolB() {
        return golB;
    }

    public void setgolB(String b) {
        golB = b;
    }

    public String getgolAB() {
        return golAB;
    }

    public void setgolAB(String AB) {
        this.golAB = AB;
    }

    public String getgolO() {
        return golO;
    }

    public void setgolO(String o) {
        golO = o;
    }

    public data_mahasiswa() {
    }

    public data_mahasiswa(String nim, String nama, String fakultas, String prodi, String tgllahir, String nope, String email, String ipk, String alamat, String darah, String jeniskelamin, String gambar) {
        this.nim = nim;
        this.nama = nama;
        this.fakultas = fakultas;
        this.prodi = prodi;
        this.tgllahir = tgllahir;
        this.nope = nope;
        this.email = email;
        this.ipk = ipk;
        this.alamat = alamat;
        this.darah = darah;
        this.jeniskelamin = jeniskelamin;
        this.gambar = gambar;
    }
}