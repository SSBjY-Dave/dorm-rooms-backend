package hu.davidorcsik.dorm.rooms.backed.status;

public enum RoomRequestStatus {
    OK,
    ID_INVALID, ID_DOES_NOT_EXISTS,
    LEVEL_INVALID, LEVEL_DOES_NOT_EXISTS,
    ROOM_NUMBER_INVALID, ROOM_NUMBER_DOES_NOT_EXISTS,
    LOCK_STATE_INVALID
}
