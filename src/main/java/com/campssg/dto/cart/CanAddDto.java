package com.campssg.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CanAddDto {
    private Long canAdd;

    public CanAddDto(Long canAdd) {
        this.canAdd = canAdd;
    }
}
