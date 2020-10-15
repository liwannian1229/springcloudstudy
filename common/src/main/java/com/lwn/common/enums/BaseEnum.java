package com.lwn.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface BaseEnum {

    @JsonValue
    String getName();
}
