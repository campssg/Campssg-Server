package com.campssg.dto.mart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MartEditRequestDto {
    private Long martId;
    private String martName;
    private String openTiem;
    private String closeTiem;
    private Long requestYn;
}
