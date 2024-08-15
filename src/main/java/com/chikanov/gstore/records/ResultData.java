package com.chikanov.gstore.records;

import com.chikanov.gstore.entity.User;

public record ResultData(User user, int points, boolean winner) {
}
