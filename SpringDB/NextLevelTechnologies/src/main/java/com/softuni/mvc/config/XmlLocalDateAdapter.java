package com.softuni.mvc.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class XmlLocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String source) throws Exception {
        return LocalDate.parse(source);
    }

    @Override
    public String marshal(LocalDate source) throws Exception {
        return source.toString();
    }
}
