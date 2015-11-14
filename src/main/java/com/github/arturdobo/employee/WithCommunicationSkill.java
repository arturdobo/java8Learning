package com.github.arturdobo.employee;

public interface WithCommunicationSkill {
	default String communicate() {
		return "I can communicate with other employees";
	}
}
