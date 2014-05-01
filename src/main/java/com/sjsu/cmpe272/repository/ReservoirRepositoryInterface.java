package com.sjsu.cmpe272.repository;

import java.util.List;

import com.sjsu.cmpe272.domain.Reservoir;

public interface ReservoirRepositoryInterface {
	public void insertReservoir(Reservoir reservoir);
	public List<Reservoir> getReservoirs();
}
