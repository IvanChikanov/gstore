package com.chikanov.gstore;

import com.chikanov.gstore.functional.RowCounter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

class OneHandGamesApplicationTests {

	@Test
	void contextLoads() {
		int size = 3;
		BiFunction<Integer, Integer, Integer[]> call = (c, s)->{
			Integer[] rows = new Integer[s];
			for (int row = 0; row < s; row++) {
				rows[row] = c * s + row;
			}
			return rows;
		};
		List<Integer[]> lines = new ArrayList<>();
		for (int col = 0; col < size; col++) {
			lines.add(call.apply(col, size));
		}
		for (int col = 0; col < size; col++) {
			int[] rows = new int[size];
			for (int row = 0; row < size; row++) {
				rows[row] = row * size + col;
			}
		}
		for(var r: lines){
			for(var o : r){
				System.out.print(o);
			}
			System.out.println();
		}
	}

	void counter(BiFunction<Integer, Integer, Integer[]> call){

	}

}
