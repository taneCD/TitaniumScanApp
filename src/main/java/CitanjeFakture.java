import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
public class CitanjeFakture {
    public Faktura citanjeInputa(ArrayList<Proizvod>proizvodiIzFakture, String excFilePath){

        ArrayList<Artikli> artikliLista = new ArrayList<>();
        Faktura faktura = null;
        try (FileInputStream fileInputStream = new FileInputStream(excFilePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Choose the sheet you want to read (0-indexed)

            try {
                var red2 = sheet.getRow(1);
                var celijaSaImenom = red2.getCell(1);
                String Kupac = celijaSaImenom.getStringCellValue();

                var red4 = sheet.getRow(3);
                var celijaFaktura = red4.getCell(1);
                String FakturaBroj = celijaFaktura.getStringCellValue();

                int startFrom = 5;
                for (int i = startFrom; i < sheet.getLastRowNum() + 1; i++) {
                    Row row = sheet.getRow(i);
                    String sifra = row.getCell(2).getStringCellValue();
                    int kolicina = (int) row.getCell(3).getNumericCellValue();

                    for (var el1 : proizvodiIzFakture) {
                        if (el1.getKataloskiBroj().equals(sifra)) {
                            Artikli ar = new Artikli(sifra, kolicina, el1);
                            artikliLista.add(ar);
                            break;
                        }
                    }
                }
                faktura = new Faktura(Kupac, FakturaBroj, artikliLista);

            } catch (Exception e) {
//                e.printStackTrace();
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return faktura;
    }
}
