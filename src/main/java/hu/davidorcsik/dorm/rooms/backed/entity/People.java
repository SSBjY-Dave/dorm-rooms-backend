package hu.davidorcsik.dorm.rooms.backed.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.davidorcsik.dorm.rooms.backed.security.ResponseView;
import hu.davidorcsik.dorm.rooms.backed.status.PeopleRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.Sex;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "people")
public class People {
    private static Pattern neptunIdPattern = Pattern.compile("^[A-Z0-9]{6}$");
    private static Pattern emailPattern = Pattern.compile("^([A-z0-9\\.\\-\\_]+)@([a-z0-9\\.\\-\\_]+)\\.([a-z]{2,})$");

    public static boolean isNeptunIdValid(String neptunId) {
        neptunId = neptunId.toUpperCase();
        return neptunIdPattern.matcher(neptunId).matches();
    }

    public static boolean isEmailValid(String email) {
        return emailPattern.matcher(email).matches();
    }

    public static boolean isTokenValid(String token) {
        return true; //TODO: implement
    }

    public static boolean isSexValid(Sex sex) {
        return sex == Sex.MALE || sex == Sex.FEMALE;
    }

    public static ArrayList<PeopleRequestStatus> isPeopleValid(People p) {
        ArrayList<PeopleRequestStatus> status = new ArrayList<>();
        if (!isNeptunIdValid(p.neptunId)) status.add(PeopleRequestStatus.NEPTUN_ID_INVALID);
        if (!isEmailValid(p.email)) status.add(PeopleRequestStatus.EMAIL_INVALID);
        if (!isTokenValid(p.token)) status.add(PeopleRequestStatus.TOKEN_INVALID);
        if (!isSexValid(p.sex)) status.add(PeopleRequestStatus.SEX_INVALID);
        return status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ReadOnlyProperty
    @JsonView(ResponseView.AdminView.class)
    private long id;
    @JsonView(ResponseView.PublicView.class)
    private String name;
    @JsonView(ResponseView.OwnerView.class)
    private String neptunId;
    @JsonView(ResponseView.OwnerView.class)
    private String email;
    @JsonView(ResponseView.PublicView.class)
    private boolean newbie;
    @JsonView(ResponseView.InternalView.class)
    private String token;

    @Enumerated(EnumType.ORDINAL)
    @JsonView(ResponseView.PublicView.class)
    private Sex sex;

    @OneToMany(mappedBy = "people", cascade = CascadeType.DETACH)
    @ReadOnlyProperty
    @ToString.Exclude
    @JsonView(ResponseView.AdminView.class)
    private List<LabelConnector> labelConnectors;

    @OneToOne(mappedBy = "people", cascade = CascadeType.DETACH)
    @ReadOnlyProperty
    @ToString.Exclude
    @JsonView(ResponseView.PublicView.class)
    private RoomConnector roomConnector;

    @OneToMany(mappedBy = "people", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @ReadOnlyProperty
    @ToString.Exclude
    @JsonView(ResponseView.OwnerView.class)
    private List<RoleConnector> roleConnectors;

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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public List<LabelConnector> getLabelConnectors() {
        return labelConnectors;
    }

    public void setLabelConnectors(List<LabelConnector> labelConnectors) {
        this.labelConnectors = labelConnectors;
    }

    public RoomConnector getRoomConnector() {
        return roomConnector;
    }

    public void setRoomConnector(RoomConnector roomConnector) {
        this.roomConnector = roomConnector;
    }

    public List<RoleConnector> getRoleConnectors() {
        return roleConnectors;
    }

    public void setRoleConnector(List<RoleConnector> roleConnectors) {
        this.roleConnectors = roleConnectors;
    }

    public void prepareSerialization() {
        labelConnectors.forEach(LabelConnector::prepareSerializationFromPeople);
        if (roomConnector != null) roomConnector.prepareSerializationFromPeople();
        roleConnectors.forEach(RoleConnector::prepareSerializationFromPeople);
    }

    void prepareSerializationFromLabelConnector() {
        labelConnectors = null;
        if (roomConnector != null) roomConnector.prepareSerializationFromPeople();
        roleConnectors.forEach(RoleConnector::prepareSerializationFromPeople);
    }

    void prepareSerializationFromRoomConnector() {
        roomConnector = null;
        roleConnectors.forEach(RoleConnector::prepareSerializationFromPeople);
        labelConnectors.forEach(LabelConnector::prepareSerializationFromPeople);
    }

    void prepareSerializationFromRoleConnector() {
        roleConnectors = null;
        roomConnector.prepareSerializationFromPeople();
        labelConnectors.forEach(LabelConnector::prepareSerializationFromPeople);
    }
}
