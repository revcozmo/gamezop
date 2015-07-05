package com.rishabhsethi.whoop2;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by rishabh on 26/6/15.
 */
@Table(name = "Contacts")
public class Contacts extends Model {
    // If name is omitted, then the field name is used.
    @Column(name = "Name")
    public String name;

    @Column(name = "PhoneNumber", index = true)
    public String phoneNumber;

    @Column(name = "OnGamezop")
    public boolean onGamezop;

    public Contacts() {
        super();
    }

    public Contacts(String name, String phoneNo, boolean ongamezop) {
        super();
        this.name = name;
        this.phoneNumber = phoneNo;
        this.onGamezop = ongamezop;
    }
}
