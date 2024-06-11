package com.proj.willow.shared.utils;

import java.util.UUID;

public class IdGenerator {

    public  static String generateRandomID() {
        return UUID.randomUUID().toString();
    }
}
