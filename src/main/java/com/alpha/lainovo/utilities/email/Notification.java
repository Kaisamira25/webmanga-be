package com.alpha.lainovo.utilities.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String to;
    private String title;
    private String body;
}
