package com.github.arturdobo.employee;

public interface WithCarDrivingSkill {
	default String drive() {
		return "I can drive car";
	}
}
