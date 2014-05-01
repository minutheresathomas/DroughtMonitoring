package com.sjsu.cmpe272.ui.views;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.cmpe272.domain.Reservoir;
import com.yammer.dropwizard.views.View;

public class UserView extends View{
	List<Reservoir> reservoirs = new ArrayList<Reservoir>();
	

	public UserView()
	{
		super("CaReservoirHome.mustache");
//		this.reservoirs = reservoirs;
	}

	public List<Reservoir> getReservoirs() {
//		for(int i= 0; i<reservoirs.size() ; i++)
//		{
//			String reservoirCurrentDate = reservoirs.get(i).getStorageData().
//		}
		return reservoirs;
	}
}
