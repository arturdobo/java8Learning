package com.github.arturdobo.employee;

public class Employee extends CommunicativeEmployee
		implements WithCarDrivingSkill, WithTruckDrivingSkill, WithCommunicationSkill,
		WithHousePaintingSkill, WithPaintingSkill, WithManualSkill, WithDIYSkill, WithF16PilotingSkill,
		WithJumboJetPilotingSkill {

	@Override
	public String paint() {
		return "I can paint both pictures and houses";
	}

	@Override
	public String doSth() {
		return WithManualSkill.super.doSth();
	}
}
