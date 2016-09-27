package wad.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private List<Notification> success;
    private List<Notification> alert;

    public NotificationService() {
        this.success = new ArrayList<>();
        this.alert = new ArrayList<>();
    }

    public void add(Notification not) {
        if (not.getType().equals("success")) {
            success.add(not);
        } else if (not.getType().equals("error")) {
            alert.add(not);
        }
    }

    public List<Notification> getSuccess() {
        List<Notification> notifications = success;
        success = new ArrayList<>();
        return notifications;
    }

    public List<Notification> getAlert() {
        List<Notification> errors = alert;
        alert = new ArrayList<>();
        return errors;
    }
}
