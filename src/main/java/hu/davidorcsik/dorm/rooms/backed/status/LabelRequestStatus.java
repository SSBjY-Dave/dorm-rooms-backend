package hu.davidorcsik.dorm.rooms.backed.status;

public enum LabelRequestStatus {
    OK,
    ID_INVALID, ID_ALREADY_EXISTS, ID_DOES_NOT_EXISTS,
    NAME_INVALID, NAME_ALREADY_EXISTS, NAME_DOES_NOT_EXISTS,
}
