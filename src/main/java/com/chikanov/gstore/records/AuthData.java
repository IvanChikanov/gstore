package com.chikanov.gstore.records;

import org.springframework.http.HttpStatus;

public record AuthData(HttpStatus statusCode, String result) {
}
