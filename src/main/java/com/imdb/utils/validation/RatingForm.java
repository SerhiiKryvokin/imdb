package com.imdb.utils.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RatingForm {
    @NotNull
    @Size(min = 1, max = 10)
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
