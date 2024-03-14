package com.alpha.lainovo.model;

import com.alpha.lainovo.utilities.email.Notification;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class Email extends Notification {
    private String from;
    private String[] bcc;
    private String[] cc;
    private List<File> files;

    public Email(String to, String subject, String body, String from){
        super(to,subject,body);
        this.from = from;
    }
}
