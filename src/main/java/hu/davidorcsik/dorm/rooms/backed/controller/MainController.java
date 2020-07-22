package hu.davidorcsik.dorm.rooms.backed.controller;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.ArrayUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


//TODO: this controller must not be part of the release build
@RestController
@CrossOrigin("http://localhost:4200") //TODO: Modify for production server
public class MainController {
    @RequestMapping("/")
    public String home() {
        return "home page";
    }

    public static class ByteCollectorOutputStream extends OutputStream {
        private final List<Byte> bytes = new ArrayList<>();
        @Override
        public void write(int b) { bytes.add((byte)b); }

        public byte[] getBytes() { return (byte[]) ArrayUtils.toPrimitive(bytes.toArray(new Byte[0])); }
    }
    @GetMapping("/test")
    public ResponseEntity test() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet personSheet = workbook.createSheet("People");
        Row headerRow = personSheet.createRow(0);

        Cell cellHeaderName     =   headerRow.createCell(0);
        Cell cellHeaderNeptun   =   headerRow.createCell(1);
        Cell cellHeaderEmail    =   headerRow.createCell(2);
        Cell cellHeaderNewbie   =   headerRow.createCell(3);
        Cell cellHeaderSex      =   headerRow.createCell(4);
        Cell cellHeaderRoom     =   headerRow.createCell(5);
        Cell cellHeaderLabels   =   headerRow.createCell(6);

        cellHeaderName.setCellValue("Name");
        cellHeaderNeptun.setCellValue("Neptun");
        cellHeaderEmail.setCellValue("Email");
        cellHeaderNewbie.setCellValue("Newbie");
        cellHeaderSex.setCellValue("Sex");
        cellHeaderRoom.setCellValue("Room");
        cellHeaderLabels.setCellValue("Labels");

        ByteCollectorOutputStream os = new ByteCollectorOutputStream();
        workbook.write(os);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.xlsx")
                .body(os.getBytes());
    }
}
