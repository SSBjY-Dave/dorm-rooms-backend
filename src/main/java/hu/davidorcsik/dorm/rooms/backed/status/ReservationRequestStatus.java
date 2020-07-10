package hu.davidorcsik.dorm.rooms.backed.status;

public enum ReservationRequestStatus {
    OK,
    ROOM_ID_INVALID, PEOPLE_ID_INVALID,
    ROOM_ALREADY_FULL, ROOM_IS_EMPTY,
    RESERVATION_NOT_FOUND, RESERVATION_ALREADY_EXISTS,
    SEX_INVALID
}
