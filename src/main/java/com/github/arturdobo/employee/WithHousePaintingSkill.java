package com.github.arturdobo.employee;

public interface WithHousePaintingSkill {
	default String paint() {
		return "I can paint a House";
	}
}
