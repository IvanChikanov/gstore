package com.chikanov.gstore;

import com.chikanov.gstore.functional.RowCounter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


class OneHandGamesApplicationTests {

	@Test
	void contextLoads() {
		int size = 3;
		List<int[]> lines = new ArrayList<>();
		int[] right = new int[size];
		int[] left = new int[size];
		for (int col = 0; col < size; col++) {
			int[] rows = new int[size];
			int[] cols = new int[size];
			for (int row = 0; row < size; row++) {
				rows[row] = col * size + row;
				cols[row] = row * size + col;
			}
			lines.add(rows);
			lines.add(cols);
			right[col] = col * size + col;
			left[col] = col * size + (size - 1 - col);
		}
		lines.add(right);
		lines.add(left);
	}


}
