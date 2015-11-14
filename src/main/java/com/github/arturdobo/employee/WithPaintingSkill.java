package com.github.arturdobo.employee;

public interface WithPaintingSkill {
	default String paint() {
		return "I can paint a picture";
	}
}
