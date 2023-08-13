import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
public class Repositorium {
    public ArrayList<Proizvod> citanjeIzExcela(){
        String excelFilePath = "Spisak Titanium proizvoda.xlsx";

        ArrayList<Proizvod> listaProizvoda = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(excelFilePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Choose the sheet you want to read (0-indexed)
            // Iterate through each row in the sheet

            int brojac=0;
            for (Row row : sheet) {
                brojac++;
                try {
                    if (brojac > 2) {
                        int sifra = (int) row.getCell(0).getNumericCellValue();
                        long barcode = (long) row.getCell(1).getNumericCellValue();
                        var naziv = row.getCell(2).getStringCellValue();
                        String kataloskiBroj = row.getCell(3).getStringCellValue();
                        double cena = Double.parseDouble(String.valueOf(row.getCell(4).getNumericCellValue()));
                        Proizvod pr = new Proizvod(sifra, barcode, naziv, kataloskiBroj, cena);
                        listaProizvoda.add(pr);
                    }
                }catch (Exception e){
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaProizvoda;
    }
}
