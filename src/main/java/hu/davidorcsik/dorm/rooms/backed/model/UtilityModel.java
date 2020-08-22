package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.controller.MainController;
import hu.davidorcsik.dorm.rooms.backed.database.LabelRepo;
import hu.davidorcsik.dorm.rooms.backed.database.PeopleRepo;
import hu.davidorcsik.dorm.rooms.backed.database.RoomRepo;
import hu.davidorcsik.dorm.rooms.backed.entity.Label;
import hu.davidorcsik.dorm.rooms.backed.entity.LabelConnector;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import hu.davidorcsik.dorm.rooms.backed.types.Sex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    public UtilityModel(PeopleRepo peopleRepo, RoomRepo roomRepo, LabelRepo labelRepo) {
        assert(instance == null);
        instance = this;
    }

    //TODO: function to construct multisheet excel table
    public byte[] export() throws IOException {
        Workbook workbook = new XSSFWorkbook();

        List<People> people = PeopleModel.getInstance().getAll();
        List<Room> rooms = RoomModel.getInstance().getAll();

        Map<Label, List<People>> peopleByLabel = new HashMap<>();
        LabelModel.getInstance().getAll().forEach(l -> peopleByLabel.put(l, new ArrayList<>()));

        for (People p : people) {
            for (LabelConnector lc : p.getLabelConnectors()) peopleByLabel.get(lc.getLabel()).add(p);
        }

        createPersonSheet(workbook, "Emberek", people);
        createRoomSheet(workbook, rooms);
        for (Label l : peopleByLabel.keySet()) createPersonSheet(workbook, l.getName(), peopleByLabel.get(l));

        MainController.ByteCollectorOutputStream os = new MainController.ByteCollectorOutputStream();
        workbook.write(os);
        return os.getBytes();
    }

    private void createPersonSheet(Workbook workbook, String sheetName, List<People> people) {
        Sheet sheet = workbook.createSheet(sheetName);

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Név");
        headerRow.createCell(1).setCellValue("Neptun kód");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Évfolyam");
        headerRow.createCell(4).setCellValue("Nem");
        headerRow.createCell(5).setCellValue("Szoba");
        headerRow.createCell(6).setCellValue("Cimkék");

        int i = 1;
        for (People p : people) {
            Row contentRow = sheet.createRow(i++);
            contentRow.createCell(0).setCellValue(p.getName());
            contentRow.createCell(1).setCellValue(p.getNeptunId());
            contentRow.createCell(2).setCellValue(p.getEmail());
            contentRow.createCell(3).setCellValue(p.isNewbie() ? "Elsős" : "Felsős");
            contentRow.createCell(4).setCellValue(p.getSex() == Sex.MALE ? "Férfi" : "Nő");
            contentRow.createCell(5).setCellValue(p.getRoomConnector() != null ? p.getRoomConnector().getRoom().getFormattedRoomNumber() : "-");
            contentRow.createCell(6).setCellValue(p.getLabelConnectors().stream().map(l -> l.getLabel().getName()).collect(Collectors.joining(", ")));
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);

    }

    private void createRoomSheet(Workbook workbook, List<Room> rooms) {
        rooms.sort((a, b) -> (int)(a.getId() - b.getId()));

        List<List<Room>> levels = new ArrayList<>();
        levels.add(new ArrayList<>());
        for (Room r : rooms) {
            if (r.getLevel() != levels.size() - 1) levels.add(new ArrayList<>());
            levels.get(levels.size() - 1).add(r);
        }

        Sheet sheet = workbook.createSheet("Épület");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Szint");
        headerRow.createCell(1).setCellValue("Szoba");
        headerRow.createCell(2).setCellValue("Szobaszám");
        headerRow.createCell(3).setCellValue("Nem");
        headerRow.createCell(4).setCellValue("Név");

        for (int i = 0; i < rooms.stream().mapToInt(Room::getCapacity).sum(); ++i) {
            sheet.createRow(i + 1);
        }

        int rowsSoFar = 1;
        for (int i = 0; i < levels.size(); ++i) {
            sheet.getRow(rowsSoFar).createCell(0).setCellValue(i);
            sheet.addMergedRegion(new CellRangeAddress(rowsSoFar, rowsSoFar + levels.get(i).stream().mapToInt(Room::getCapacity).sum() - 1, 0, 0));
            for (Room room : levels.get(i)) {
                sheet.getRow(rowsSoFar).createCell(1).setCellValue(room.getRoomNumber());
                sheet.addMergedRegion(new CellRangeAddress(rowsSoFar, rowsSoFar + room.getCapacity() - 1, 1, 1));

                sheet.getRow(rowsSoFar).createCell(2).setCellValue(room.getId());
                sheet.addMergedRegion(new CellRangeAddress(rowsSoFar, rowsSoFar + room.getCapacity() - 1, 2, 2));

                String sexString = "-";
                if (room.getSex().equals(Sex.MALE)) sexString = "Férfi";
                else if (room.getSex().equals(Sex.FEMALE)) sexString = "Nő";
                sheet.getRow(rowsSoFar).createCell(3).setCellValue(sexString);
                sheet.addMergedRegion(new CellRangeAddress(rowsSoFar, rowsSoFar + room.getCapacity() - 1, 3, 3));

                for (int j = 0; j < room.getRoomConnectors().size(); ++j) {
                    People resident = room.getRoomConnectors().get(j).getPeople();
                    sheet.getRow(rowsSoFar + j).createCell(4).setCellValue(resident.getName() + " (" + resident.getNeptunId() + ')');
                }
                rowsSoFar += room.getCapacity();
            }
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
    }
}
