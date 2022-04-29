package com.safetynet.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.entity.Email;
import com.safetynet.api.repository.EmailRepository;

import lombok.Data;

@Data
@Service
public class CommunityEmailService {
    // http://localhost:8080/communityEmail?city=<city>
    @Autowired
    private EmailRepository emailRepository;

    public Iterable<String> getMailByCity(String city) {
	Set<String> returnList = new HashSet<String>();
	Iterable<Email> emailIterable = emailRepository.findAllByPersonsHomeCity(city);
	for (Email email : emailIterable) {
	    returnList.add(email.getEmailAddress());
	}
	return returnList;
    }

}
