package com.chikanov.gstore;

import com.chikanov.gstore.functional.RowCounter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class OneHandGamesApplicationTests {

	@Test
	void contextLoads() {
		int count = 0;
		int randomNumber = 0;
		randomNumber= count < 2 ?
				new Random().nextInt(1, 3):
				randomNumber == 1 ? 2 : 1;
		System.out.println(randomNumber);
	}


}
