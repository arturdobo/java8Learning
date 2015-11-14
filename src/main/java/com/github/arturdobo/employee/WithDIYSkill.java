package com.github.arturdobo.employee;

public interface WithDIYSkill {
	default String doSth() {
		return "DIY is my hobby";
	}
}
