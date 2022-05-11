package com.safetynet.api.dto.endpoints;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonFileMedicalrecordsDto implements Serializable {

    private static final long serialVersionUID = -2452535577524427285L;

    @JsonProperty("medicalrecords")
    private Set<MedicalRecordDto> medicalRecordDtos = new HashSet<MedicalRecordDto>();

}
