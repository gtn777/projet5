
package com.safetynet.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.entity.Email;
import com.safetynet.api.repository.EmailRepository;
import com.safetynet.api.service.exception.UnknownCityException;


@Service
public class CommunityEmailService {

    @Autowired
    private EmailRepository emailRepository;

    public Iterable<String> getData(String city) {
	Set<String> returnList = new HashSet<String>();
	Iterable<Email> emailIterable = emailRepository.findAllByPersonsHomeCity(city);
	if (emailIterable != null && emailIterable.iterator().hasNext()) {
	    for (Email email : emailIterable) {
		returnList.add(email.getEmailAddress());
	    }
	    return returnList;
	} else {
	    throw new UnknownCityException(city);
	}
    }

}
