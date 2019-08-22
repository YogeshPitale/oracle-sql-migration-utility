package com.avaya.mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class InlineMockTest {

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Mock
	Map<String,String> mockMap;
	
	@Test
	void test() {
		Map mapMock = mock(Map.class);
		assertEquals(0, mapMock.size());
	}
	
	@Test
	void test2() {
		assertEquals(0, mockMap.size());
	}

}
