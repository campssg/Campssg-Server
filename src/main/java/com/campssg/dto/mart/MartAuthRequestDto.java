package com.campssg.dto.mart;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MartAuthRequestDto {
    @NotNull(message = "마트이름 필수입니다.")
    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "마트이름")
    private String martName;

    @NotNull(message = "사업자등록번호(필수) 숫자로 이루어진 10자리 값만 가능 ('-' 등의 기호 반드시 제거 후 호출)")
    @ApiModelProperty(position = 2, required = true, dataType = "String", value = "사업자등록번호 숫자로 이루어진 10자리 값만 가능 ('-' 등의 기호 반드시 제거 후 호출)")
    private String bNo;

    @NotNull(message = "개업일 YYYYMMDD 필수입니다")
    @ApiModelProperty(position = 3, required = true, dataType = "String", value = "개업일 YYYYMMDD")
    private String startDt;
}
