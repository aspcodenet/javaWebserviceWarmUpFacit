package se.systementor.javawebservicewarmupfacit.models.xmlrecords;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Students {
    @JacksonXmlProperty(localName = "student")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Student> student;
}
