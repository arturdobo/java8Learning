package com.github.arturdobo.optionals;

import com.github.arturdobo.shop.CreditCard;
import com.github.arturdobo.shop.Customer;
import com.github.arturdobo.shop.DiscountCard;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.assertThat;

public class OptionalTest {
	@Test
	public void getNullableCreditCardNumberTest() throws Exception {
		Customer customer = new Customer("John");

		assertThat(Optional.ofNullable(customer.getCreditCard())
		                   .map(CreditCard::getNumber)
		                   .isPresent(), is(false));
	}
	
	@Test
	public void getDiscountFromOptionalCustomerOptionalDiscountCardTest() throws Exception {
		Optional<Customer> customer = Optional.of(new Customer("John"));

		Double discount = customer.flatMap(Customer::getDiscountCard)
		                          .map(DiscountCard::getDiscount)
		                          .orElse(0.0);

		assertThat(discount, equalTo(0.0));
	}

	@Test
	public void setDiscountCardIfCustomerIsPresent() throws Exception {
		Customer customer = new Customer("John");
		Optional.ofNullable(customer)
		        .ifPresent(c -> c.setDiscountCard(Optional.of(new DiscountCard(20))));

		assertThat(customer.getDiscountCard()
		                   .get()
		                   .getDiscount(), equalTo(20.0));
	}
	
	@Test(expected = IllegalStateException.class)
	public void thrownAnExceptionIfDiscountCardIsNotPresent() throws Exception {
		new Customer("John").getDiscountCard()
		                    .orElseThrow(IllegalStateException::new);
	}
	
	@Test
	public void filterCustomerByName() throws Exception {
		assertThat(Optional.of(new Customer("John"))
		                   .filter(customer -> "Mark".equals(customer.getName()))
		                   .isPresent(), is(false));
	}
}
