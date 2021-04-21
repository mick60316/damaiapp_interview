package com.example.damaiapp_interview.DataStruct;

import java.io.Serializable;

public class Attraction  implements Serializable {

    private  int id;
    private String name ;


    public Attraction (int id,String name)
    {
        this.id=id;
        this.name=name;
    }

    public int getId ()
    {
        return id;
    }
    public String getName ()
    {
        return name;

    }

}
