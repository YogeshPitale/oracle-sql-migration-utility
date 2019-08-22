package com.avaya.mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExtendsWith {

	@Mock
	Map<String,String> mockMap;
	
	@Test
	void test2() {
		assertEquals(0, mockMap.size());
	}

}
