package se.systementor.javawebservicewarmupfacit.models.xmlrecords;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Student {
    @JacksonXmlProperty(localName = "name")
    public String name;
    @JacksonXmlProperty(localName = "region")
    public String region;
    @JacksonXmlProperty(localName = "country")
    public String country;
    @JacksonXmlProperty(localName = "numberrange")
    public int numberrange;
}
