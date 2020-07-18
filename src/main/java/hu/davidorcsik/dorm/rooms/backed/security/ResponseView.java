package hu.davidorcsik.dorm.rooms.backed.security;

public class ResponseView {
    public static class PublicView {}
    public static class AdminView extends PublicView {}
    public static class InternalView extends AdminView {}
}
