package com.github.arturdobo.newMethods;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.assertThat;

public class MapTest {
	private Map<String, String> map;

	@Before
	public void setUp() throws Exception {
		map = new HashMap<>();
		map.put("a", null);
		map.put("c", "someValue");
	}

	@Test
	public void defaultValueWhenGettingNoExistingKey() throws Exception {
		assertThat(map.getOrDefault("b", "none"), equalTo("none"));
		assertThat(map.getOrDefault("a", "none"), nullValue());
	}
	
	@Test
	public void valueIsComputedAndPutWhenIsAbsent() throws Exception {
		String newValue = map.computeIfAbsent("b", String::toUpperCase);

		assertThat(newValue, equalTo("B"));
		assertThat(map.get("b"), equalTo(newValue));
	}
	
	@Test
	public void valueIsComputedAndPutWhenIsPresent() throws Exception {
		String newValue = map.computeIfPresent("c", (key, oldValue) -> oldValue.replace("some", "new"));

		assertThat(newValue, equalTo("newValue"));
		assertThat(map.get("c"), equalTo(newValue));
	}
	
	@Test
	public void valueIsPutWhenIsAbsent() throws Exception {
		map.putIfAbsent("b", "bValue");
		map.putIfAbsent("c", "cValue");

		assertThat(map.get("b"), equalTo("bValue"));
		assertThat(map.get("c"), equalTo("someValue"));
	}
	
	@Test
	public void removeKeyIfKeyWithGivenValueExist() throws Exception {
		boolean removeC = map.remove("c", "someValue");
		boolean removeA = map.remove("a", "someOtherValue");

		assertThat(removeC, is(true));
		assertThat(map.containsKey("c"), is(false));

		assertThat(removeA, is(false));
		assertThat(map.containsKey("a"), is(true));
	}
	
	@Test
	public void replaceValueIfKeyExists() throws Exception {
		map.replace("a", "someValue"); // [a: null]
		map.replace("c", "someNewValue");

		assertThat(map.get("a"), equalTo("someValue"));
		assertThat(map.get("c"), equalTo("someNewValue"));
	}
	
	@Test
	public void replaceIfKeyWithGivenValueExist() throws Exception {
		boolean replaceA = map.replace("a", "someValue", "someNewValue");
		boolean replaceC = map.replace("c", "someValue", "someNewValue");

		assertThat(replaceA, is(false));
		assertThat(map.get("a"), nullValue());

		assertThat(replaceC, is(true));
		assertThat(map.get("c"), equalTo("someNewValue"));
	}
	
	@Test
	public void replaceAllValues() throws Exception {
		map.replaceAll((key, oldValue) -> Optional.ofNullable(oldValue).orElse("newValue").toUpperCase());

		assertThat(map.get("a"), equalTo("NEWVALUE"));
		assertThat(map.get("c"), equalTo("SOMEVALUE"));
	}
	
	@Test
	public void mergingPutKeyWithGivenValueWhenKeyDoesntExist() throws Exception {
		map.merge("d", "value", (oldValue, newValue) -> oldValue + newValue);

		assertThat(map.containsKey("d"), is(true));
		assertThat(map.get("d"), equalTo("value"));
	}

	@Test
	public void mergingValueIfFunctionReturnNullRemovesKey() throws Exception {
		map.merge("c", "someValue", (oldValue, newValue) -> null);

		assertThat(map.containsKey("c"), is(false));
	}
	
	@Test
	public void mergingExistingValue() throws Exception {
		map.merge("c", "AfterMerge", (oldValue, newValue) -> oldValue + newValue);

		assertThat(map.get("c"), equalTo("someValueAfterMerge"));
	}
}
