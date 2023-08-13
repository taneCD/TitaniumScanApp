import java.util.ArrayList;
public class Faktura {
    private String kupac;
    private String fakturaBroj;
    private ArrayList<Artikli> artikli;

    public Faktura() {
    }

    public Faktura(String kupac, String fakturaBroj, ArrayList<Artikli> artikli) {
        this.kupac = kupac;
        this.fakturaBroj = fakturaBroj;
        this.artikli = artikli;
    }
    public String getKupac() {
        return kupac;
    }

    public void setKupac(String kupac) {
        this.kupac = kupac;
    }

    public String getFakturaBroj() {
        return fakturaBroj;
    }

    public void setFakturaBroj(String fakturaBroj) {
        this.fakturaBroj = fakturaBroj;
    }

    public ArrayList<Artikli> getArtikli() {
        return artikli;
    }

    public void setArtikli(ArrayList<Artikli> artikli) {
        this.artikli = artikli;
    }

    @Override
    public String toString() {
        return "InputExcel{" +
                "kupac='" + kupac + '\'' +
                ", fakturaBroj='" + fakturaBroj + '\'' +
                ", artikli=" + artikli +
                '}';
    }
}
