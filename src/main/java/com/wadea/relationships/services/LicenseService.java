package com.wadea.relationships.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.wadea.relationships.models.License;
import com.wadea.relationships.models.Person;
import com.wadea.relationships.repositories.LicenseRepository;

@Service
public class LicenseService {
	private final LicenseRepository licenseRepo;

	public LicenseService(LicenseRepository licenseRepo) {
		this.licenseRepo = licenseRepo;
	}
	
	public License createLicense(@RequestParam("person") Person person, License license) {
		license.setNumber("0000"+person.getId().toString());
		return licenseRepo.save(license);
	}
	
	public License findLicense(Long id) {
		Optional<License> optionalLicense = licenseRepo.findById(id);
        if(optionalLicense.isPresent()) {
            return optionalLicense.get();
        } else {
            return null;
        }
	}
}
