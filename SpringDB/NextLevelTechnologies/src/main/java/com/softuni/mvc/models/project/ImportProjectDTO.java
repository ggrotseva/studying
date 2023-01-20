package com.softuni.mvc.models.project;

import com.softuni.mvc.config.XmlLocalDateAdapter;
import com.softuni.mvc.models.company.ImportCompanyDTO;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportProjectDTO {

    @XmlElement
    @NotNull
    private String name;

    @XmlElement
    @NotNull
    private String description;

    @XmlElement(name = "is-finished")
    private Boolean isFinished;

    @XmlElement
    @NotNull
    private BigDecimal payment;

    @XmlElement(name = "start-date")
    @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
    private LocalDate startDate;

    @XmlElement
    @NotNull
    private ImportCompanyDTO company;

    public ImportProjectDTO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setCompany(ImportCompanyDTO company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ImportCompanyDTO getCompany() {
        return company;
    }
}
