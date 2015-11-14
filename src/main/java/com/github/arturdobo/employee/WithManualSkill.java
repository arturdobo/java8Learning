package com.github.arturdobo.employee;

public interface WithManualSkill {
	default String doSth() {
		return "I can do many things myself";
	}
}
