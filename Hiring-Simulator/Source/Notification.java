public class Notification {
    String notif_text;

    public Notification(){
        this("Default notification");
    }

    public Notification(String notif_text){
        this.notif_text = notif_text;
    }

    public String toString(){
        return "New notification! " + notif_text;
    }
}
