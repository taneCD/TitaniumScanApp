public class Proizvod {
    private int Sifra;
    private long Barcode;
    private String Naziv;
    private String KataloskiBroj;
    private double MPCena;

    public Proizvod() {
    }
    public Proizvod(int sifra, long barcode, String naziv, String kataloskiBroj, double MPCena) {
        Sifra = sifra;
        Barcode = barcode;
        Naziv = naziv;
        KataloskiBroj = kataloskiBroj;
        this.MPCena = MPCena;
    }

    public int getSifra() {
        return Sifra;
    }

    public void setSifra(int sifra) {
        Sifra = sifra;
    }

    public long getBarcode() {
        return Barcode;
    }

    public void setBarcode(long barcode) {
        Barcode = barcode;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    public String getKataloskiBroj() {
        return KataloskiBroj;
    }

    public void setKataloskiBroj(String kataloskiBroj) {
        KataloskiBroj = kataloskiBroj;
    }

    public double getMPCena() {
        return MPCena;
    }

    public void setMPCena(double MPCena) {
        this.MPCena = MPCena;
    }

    @Override
    public String toString() {
        return "Proizvod{" +
                "Sifra=" + Sifra +
                ", Barcode=" + Barcode +
                ", Naziv='" + Naziv + '\'' +
                ", KataloskiBroj='" + KataloskiBroj + '\'' +
                ", MPCena=" + MPCena +
                '}';
    }
}
