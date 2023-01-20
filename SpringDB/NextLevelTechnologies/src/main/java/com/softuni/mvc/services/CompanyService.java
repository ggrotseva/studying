package com.softuni.mvc.services;

import com.softuni.mvc.models.company.Company;
import com.softuni.mvc.models.company.ImportCompaniesDTO;
import com.softuni.mvc.repositories.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, ModelMapper mapper) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    public String getXmlContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xmls/companies.xml"));
    }

    public void importCompanies() throws IOException, JAXBException {

        String xmlContent = getXmlContent();
        ByteArrayInputStream stream = new ByteArrayInputStream(xmlContent.getBytes());

        JAXBContext context = JAXBContext.newInstance(ImportCompaniesDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportCompaniesDTO wrapper = (ImportCompaniesDTO) unmarshaller.unmarshal(stream);

        List<Company> companies = wrapper.getCompanies()
                .stream()
                .map(dto -> this.mapper.map(dto, Company.class))
                .collect(Collectors.toList());

        this.companyRepository.saveAll(companies);
    }

    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }
}
