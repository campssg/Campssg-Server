package com.campssg.dto.mart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "마트 등록 요청")
public class MartCertificationRequestDto {
    @NotNull(message = "사업자 등록 번호는 필수입니다.")
    @ApiModelProperty(position = 1, required = true, dataType = "int", value = "사업자 등록 번호")
    private String bNo;

    @NotNull(message = "개업일자는 필수입니다.")
    @ApiModelProperty(position = 2, required = true, dataType = "int", value = "개업일자(YYYYMMDD)")
    private String startDt;

    @NotNull(message = "대표자성명는 필수입니다.")
    @ApiModelProperty(position = 3, required = true, dataType = "int", value = "대표자성명(외국인 사업자의 경우에는 영문명 입력)")
    private String pNm;
}
