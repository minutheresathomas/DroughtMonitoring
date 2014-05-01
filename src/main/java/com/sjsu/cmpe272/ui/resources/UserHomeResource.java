package com.sjsu.cmpe272.ui.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sjsu.cmpe272.repository.ReservoirRepositoryInterface;
import com.sjsu.cmpe272.ui.views.UserView;

@Path("/ca")
@Produces(MediaType.TEXT_HTML)
public class UserHomeResource {
	private final ReservoirRepositoryInterface reservoirRepository;

	/**
	 * Reservoir resource
	 * @param reservoirRepository
	 */
	public UserHomeResource(ReservoirRepositoryInterface reservoirRepository) {
		this.reservoirRepository = reservoirRepository;
	}
	
	@GET
    public UserView getUserHome() {
		System.out.println("getting the polls!...");
//		getR
    	return new UserView();
    }
	
}
