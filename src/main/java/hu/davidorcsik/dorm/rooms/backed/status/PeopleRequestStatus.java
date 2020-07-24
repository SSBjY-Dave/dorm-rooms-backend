package hu.davidorcsik.dorm.rooms.backed.status;

public enum PeopleRequestStatus {
    OK,
    ID_INVALID, ID_ALREADY_EXISTS,
    NAME_INVALID,
    NEPTUN_ID_INVALID, NEPTUN_ID_ALREADY_EXISTS,
    EMAIL_INVALID, EMAIL_ALREADY_EXITS,
    TOKEN_INVALID, TOKEN_ALREADY_EXISTS,
    SEX_INVALID
}
