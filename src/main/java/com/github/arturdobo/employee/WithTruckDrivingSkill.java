package com.github.arturdobo.employee;

public interface WithTruckDrivingSkill extends WithCarDrivingSkill {
	@Override
	default String drive() {
		return "I can drive truck and car of course";
	}
}
