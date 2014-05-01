package com.sjsu.cmpe272.config;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class DroughtMonitoringConfiguration extends Configuration{
	@JsonProperty @NotEmpty
    public String mongohost = "localhost";

    @Min(1)
    @Max(65535)
    @JsonProperty
    public int mongoport = 27017;

    @JsonProperty @NotEmpty
    public String mongodb = "mydb";
}
