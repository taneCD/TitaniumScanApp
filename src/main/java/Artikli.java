import javax.swing.*;
public class Artikli {
    private String sifra;
    private int kolicina;
    private Proizvod proizvod;
    private JLabel label;
    private int brojacKolicine;

    public Artikli(String sifra, int kolicina, Proizvod proizvod) {
        this.sifra = sifra;
        this.kolicina = kolicina;
        this.proizvod = proizvod;
    }
    public Artikli() {
    }
    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
    }

    public JLabel getLabel() {
        return label;
    }
    public void setLabel(JLabel label) {
        this.label = label;
    }
    public int getBrojacKolicine() {
        return brojacKolicine;
    }
    public void setBrojacKolicine(int brojacKolicine) {
        this.brojacKolicine = brojacKolicine;
    }
}

