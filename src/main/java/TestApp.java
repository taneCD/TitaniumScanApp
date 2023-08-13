import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class TestApp extends JFrame {
    private static ArrayList<Proizvod> elementiIzRepositoriuma = null;
    private static Faktura faktura = null;
    private static JFrame frame = null;
    private static JLabel greskaLabel = null;
    private static JLabel labelKupac = null;
    private static JLabel labelFaktura = null;

    public static void main(String[] args)  {
        Repositorium repositorium = new Repositorium();
        elementiIzRepositoriuma = repositorium.citanjeIzExcela();

        frame = new JFrame("Titanium app");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new GridLayout(12, 2));

        JTextArea filesInputArea = new JTextArea();
        filesInputArea.setText(" Ovde prevuci fakture");
        Font fakturaFont = new Font("Arial Rounded MT", Font.ITALIC, 16);
        filesInputArea.setBounds(50, 50, 100, 50);
        filesInputArea.setFont(fakturaFont);
        filesInputArea.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                try {
                    List<File> droppedFiles = (List<File>)
                            evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    var swingWorker = new SwingWorker<Integer, Void>() {
                        @Override
                        protected Integer doInBackground() {
                            try {
                                for (File file : droppedFiles) {
                                    try {
                                        CitanjeIIspisivanjeFakture(file.getAbsolutePath());
                                    } catch (Exception ae) {
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            evt.dropComplete(true);
                            return 1;
                        }

                        @Override
                        public void done() {
                            System.out.println("Done!");
                        }
                    };
                    swingWorker.execute();
                } catch (Exception e) {
                }
            }
        });

        JPanel panel = new JPanel();
        JTextField textField = new JTextField();

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    greskaLabel.setText("");
                    long ovoMiTreba = 0;
                    try {
                        ovoMiTreba = (Long.parseLong(textField.getText()));
                    } catch (Exception ex) {
                    }
                    textField.setText("");
                    textField.requestFocus();

                    if (ovoMiTreba == 0) {
                        greskaLabel.setText("Bar kod nije dobro procitan.");
                        return;
                    }
//
                    for (var el : faktura.getArtikli()) {
                        var code = el.getProizvod().getBarcode();
                        if (code == ovoMiTreba) {
                            int kolicina = el.getBrojacKolicine();
                            kolicina++;
                            el.setBrojacKolicine(kolicina);
                            if (el.getKolicina() == kolicina) {
                                el.getLabel().setBackground(Color.green);
                            }
                            if (el.getKolicina() < kolicina) {
                                el.getLabel().setBackground(Color.magenta);
                            }
                            if (el.getKolicina() > kolicina) {
                                el.getLabel().setBackground(Color.yellow);
                            }

                            el.getLabel().setText(el.getProizvod().getNaziv() + ", Količina: " + kolicina + "/" + el.getKolicina() + "kom");
                            return;
                        }
                    }
                    greskaLabel.setText("Ovaj artikal nije na porudzbini!");
                }
            }
        });
        frame.getContentPane().add(panel);
        greskaLabel =new JLabel();
        greskaLabel.setBounds(50, 50, 50, 30);
        Font newFont = new Font("Arial", Font.BOLD, 28);
        greskaLabel.setOpaque(true);
        greskaLabel.setForeground(Color.red);
        greskaLabel.setFont(newFont);
        frame.getContentPane().add(greskaLabel);

        frame.getContentPane().add(textField);
        frame.getContentPane().add(filesInputArea);
        frame.setVisible(true);
    }
    private static void CitanjeIIspisivanjeFakture(String filePath) throws InterruptedException {
        sacekajDaSeCelaFakturaSkenira();
        CitanjeFakture citanjeFakture = new CitanjeFakture();
        faktura = citanjeFakture.citanjeInputa(elementiIzRepositoriuma, filePath);

        int brojac = 0;
        for (var artikal : faktura.getArtikli()) {
            JLabel label1;
            brojac++;
            label1 = new JLabel("  " + brojac + ". " + artikal.getProizvod().getNaziv() + ", Kolicina: " + artikal.getKolicina() + "kom");
            artikal.setLabel(label1);
            label1.setOpaque(true);
            label1.setBackground(Color.red);
            frame.getContentPane().add(label1);
        }
        //Ime kupca
        labelKupac = new JLabel();
        labelKupac.setBounds(50, 50, 50, 30);
        labelKupac.setText(" Kupac: " + faktura.getKupac());
        Font font = new Font("Arial", Font.BOLD, 28);
        labelKupac.setFont(font);
        //Faktura
        labelFaktura = new JLabel();
        labelFaktura.setBounds(50, 50, 50, 30);
        labelFaktura.setText(" Faktura broj: " + faktura.getFakturaBroj());
        Font fontFaktura = new Font("Arial", Font.BOLD, 28);
        labelFaktura.setFont(fontFaktura);

        frame.getContentPane().add(labelKupac);
        frame.getContentPane().add(labelFaktura);
        frame.revalidate();
        frame.repaint();

        sacekajDaSeCelaFakturaSkenira();
    }
    private static void sacekajDaSeCelaFakturaSkenira() throws InterruptedException {
        if(faktura==null)
            return;
        while (true) {
            Thread.sleep(100);
            boolean sveJeOcitano = true;
            for (var el : faktura.getArtikli()) {
                int kolicina = el.getBrojacKolicine();
                if (kolicina < el.getKolicina()) {
                    sveJeOcitano = false;
                    break;
                }
            }
            if(sveJeOcitano) {
                break;
            }
        }
        frame.getContentPane().remove(labelKupac);
        frame.getContentPane().remove(labelFaktura);
        for (var el : faktura.getArtikli()) {
            frame.getContentPane().remove(el.getLabel());
        }
        greskaLabel.setText("");
        frame.revalidate();
        frame.repaint();
    }
}