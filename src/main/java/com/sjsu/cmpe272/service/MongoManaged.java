package com.sjsu.cmpe272.service;

import com.mongodb.Mongo;
import com.yammer.dropwizard.lifecycle.Managed;

public class MongoManaged implements Managed{
	private Mongo m;

    public MongoManaged(Mongo m) {
        this.m = m;
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
        m.close();
    }
}
