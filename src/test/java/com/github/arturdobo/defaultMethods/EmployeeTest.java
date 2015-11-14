package com.github.arturdobo.defaultMethods;

import com.github.arturdobo.employee.Employee;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.assertThat;

public class EmployeeTest {
	private static Employee employee = new Employee();

	@Test
	public void theMostSpecificInterfaceMethodShouldBeChosenTest() throws Exception {
		assertThat(employee.drive(), equalTo("I can drive truck and car of course"));
	}

	@Test
	public void methodFromClassShouldBeChosen() throws Exception {
		assertThat(employee.communicate(), equalTo("I have no problems with communication"));
	}

	@Test
	public void whenAmbiguousProblemOccursNewImplementationOfAmbiguousMethodCanBeGiven() throws Exception {
		assertThat(employee.paint(), equalTo("I can paint both pictures and houses"));
	}

	@Test
	public void whenAmbiguousProblemOccursMethodFromSuperCanBeCalled() throws Exception {
		assertThat(employee.doSth(), equalTo("I can do many things myself"));
	}

	@Test
	public void whenDiamondProblemOccursAnd2InterfacesDontProvideDefaultImplementationThereIsNoAmbiguous()
			throws Exception {
		assertThat(employee.pilot(), equalTo("I can pilot"));
	}

}