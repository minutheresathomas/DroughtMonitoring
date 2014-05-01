package com.sjsu.cmpe272.service;

import java.net.UnknownHostException;
import java.util.List;

import net.vz.mongodb.jackson.JacksonDBCollection;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.sjsu.cmpe272.api.ReservoirResource;
import com.sjsu.cmpe272.config.DroughtMonitoringConfiguration;
import com.sjsu.cmpe272.domain.Reservoir;
import com.sjsu.cmpe272.repository.ReservoirRepository;
import com.sjsu.cmpe272.repository.ReservoirRepositoryInterface;
import com.sjsu.cmpe272.repository.fetcher.ReservoirFetcher;
import com.sjsu.cmpe272.service.health.MongoHealthCheck;
import com.sjsu.cmpe272.ui.resources.UserHomeResource;
import com.sun.jersey.api.container.filter.LoggingFilter;
import com.sun.jersey.api.core.ResourceConfig;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

public class DroughtMonitoring extends Service<DroughtMonitoringConfiguration>{
	public static void main(String Args[]) throws Exception
	{
		new DroughtMonitoring().run(Args);
	}
	
	@Override
	public void initialize(Bootstrap<DroughtMonitoringConfiguration> bootstrap)
	{
		bootstrap.setName("monitoring-service");
		bootstrap.addBundle(new ViewBundle());
		bootstrap.addBundle(new AssetsBundle());
	}
	
	@Override
	public void run(DroughtMonitoringConfiguration config,
					Environment environment) throws UnknownHostException
	{
		environment.setJerseyProperty(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS, 
				LoggingFilter.class.getName());
    	environment.setJerseyProperty(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, 
    			LoggingFilter.class.getName());
    	
    	Mongo mongo = new Mongo(config.mongohost, config.mongoport);
        DB db = mongo.getDB(config.mongodb);
    	
    	/** Water monitoring APIs */
        
        
        JacksonDBCollection<Reservoir, String> reservoirs =
                JacksonDBCollection.wrap(db.getCollection("reservoirs"), Reservoir.class, String.class);
        MongoManaged mongoManaged = new MongoManaged(mongo);
        
        environment.manage(mongoManaged);
        environment.addHealthCheck(new MongoHealthCheck(mongo));

        environment.addResource(new ReservoirResource(reservoirs));
        ReservoirRepositoryInterface reservoirRepository = new ReservoirRepository();
    	
    	/** Sms-Voting UI APIs */
    	environment.addResource(new UserHomeResource(reservoirRepository));
	}
}
