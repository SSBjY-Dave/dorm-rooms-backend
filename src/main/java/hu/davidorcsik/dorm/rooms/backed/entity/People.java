package hu.davidorcsik.dorm.rooms.backed.entity;

import hu.davidorcsik.dorm.rooms.backed.status.PeopleDataStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor()
public class People {
    private static Pattern neptunIdPattern = Pattern.compile("^[a-z0-9]{6}$");
    private static Pattern emailPattern = Pattern.compile("^([A-z0-9\\.\\-\\_]+)@([a-z0-9\\.\\-\\_]+)\\.([a-z]{2,})$");

    public static boolean isNeptunIdValid(String neptunId) {
        neptunId = neptunId.toLowerCase();
        return neptunIdPattern.matcher(neptunId).matches();
    }

    public static boolean isEmailValid(String email) {
        return emailPattern.matcher(email).matches();
    }

    public static boolean isTokenValid(String token) {
        return true; //TODO: implement
    }

    public static boolean isSexValid(short sex) {
        return sex == 0 || sex == 1;
    }

    public static ArrayList<PeopleDataStatus> isPeopleValid(People p) {
        ArrayList<PeopleDataStatus> status = new ArrayList<>();
        if (!isNeptunIdValid(p.neptunId)) status.add(PeopleDataStatus.NEPTUN_ID_INVALID);
        if (!isEmailValid(p.email)) status.add(PeopleDataStatus.EMAIL_INVALID);
        if (!isTokenValid(p.token)) status.add(PeopleDataStatus.TOKEN_INVALID);
        if (!isSexValid(p.sex)) status.add(PeopleDataStatus.SEX_INVALID);
        return status;
    }

    private long id;
    private String name;
    private String neptunId;
    private String email;
    private boolean newbie;
    private String token;
    private short sex;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeptunId() {
        return neptunId;
    }

    public void setNeptunId(String neptunId) {
        this.neptunId = neptunId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNewbie() {
        return newbie;
    }

    public void setNewbie(boolean newbie) {
        this.newbie = newbie;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }
}
