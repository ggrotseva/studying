package com.softuni.mvc.models.company;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportCompaniesDTO {

    @XmlElement(name = "company")
    private List<ImportCompanyDTO> companies;

    public ImportCompaniesDTO() {
    }

    public List<ImportCompanyDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(List<ImportCompanyDTO> companies) {
        this.companies = companies;
    }
}
