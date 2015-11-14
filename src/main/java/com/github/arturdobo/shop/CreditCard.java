package com.github.arturdobo.shop;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreditCard {
	@NonNull
	private String number;
	@NonNull
	private String expires;

}
