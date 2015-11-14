package com.github.arturdobo.employee;

public interface WithPilotingSkill {
	default String pilot() {
		return "I can pilot";
	}
}
