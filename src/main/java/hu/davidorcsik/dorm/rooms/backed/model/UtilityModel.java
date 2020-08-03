package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.LabelRepo;
import hu.davidorcsik.dorm.rooms.backed.database.PeopleRepo;
import hu.davidorcsik.dorm.rooms.backed.database.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilityModel {
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
    public String export(){
        return "";
    }
}
