package hu.davidorcsik.dorm.rooms.backed.status;

public enum PeopleDataStatus {
    OK,
    NAME_INVALID,
    NEPTUN_ID_INVALID, NEPTUN_ID_ALREADY_EXISTS,
    EMAIL_INVALID, EMAIL_ALREADY_EXSITS,
    TOKEN_INVALID, TOKEN_ALREADY_EXISTS,
    SEX_INVALID
}
