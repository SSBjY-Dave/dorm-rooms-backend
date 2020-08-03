package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.controller.MainController;
import hu.davidorcsik.dorm.rooms.backed.database.LabelRepo;
import hu.davidorcsik.dorm.rooms.backed.database.PeopleRepo;
import hu.davidorcsik.dorm.rooms.backed.database.RoomRepo;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UtilityModel {
    public static class ByteCollectorOutputStream extends OutputStream {
        private final List<Byte> bytes = new ArrayList<>();
        @Override
        public void write(int b) { bytes.add((byte)b); }
        public byte[] getBytes() { return (byte[]) ArrayUtils.toPrimitive(bytes.toArray(new Byte[0])); }
    }

    private static UtilityModel instance;

    public static UtilityModel getInstance() {
        return instance;
    }

    private final PeopleRepo peopleRepo;
    private final RoomRepo roomRepo;
    private final LabelRepo labelRepo;
    @Autowired
    public UtilityModel(PeopleRepo peopleRepo, RoomRepo roomRepo, LabelRepo labelRepo) {
        assert(instance == null);
        this.peopleRepo = peopleRepo;
        this.roomRepo = roomRepo;
        this.labelRepo = labelRepo;

        instance = this;
    }

    //TODO: function to construct multisheet excel table
    public byte[] export() throws IOException {
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

        MainController.ByteCollectorOutputStream os = new MainController.ByteCollectorOutputStream();
        workbook.write(os);
        return os.getBytes();
    }
}
