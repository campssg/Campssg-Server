package com.campssg.dto.mart;

import com.campssg.DB.entity.Mart;
import com.campssg.DB.entity.User;
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
public class MartSaveRequestDto {

    @NotNull(message = "마트이름 필수입니다.")
    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "마트이름")
    private String martName;
//
//    @NotNull(message = "사업자등록번호(필수) 숫자로 이루어진 10자리 값만 가능 ('-' 등의 기호 반드시 제거 후 호출)")
//    @ApiModelProperty(position = 2, required = true, dataType = "String", value = "사업자등록번호 숫자로 이루어진 10자리 값만 가능 ('-' 등의 기호 반드시 제거 후 호출)")
//    private String bNo;

    @NotNull(message = "마트 경도")
    @ApiModelProperty(position = 2, required = true, dataType = "Long", value = "마트 경도")
    private Double longitude;

    @NotNull(message = "마트 위도")
    @ApiModelProperty(position = 3, required = true, dataType = "Long", value = "마트 위도")
    private Double latitude;

//    @NotNull(message = "개업일 YYYYMMDD 필수입니다")
//    @ApiModelProperty(position = 5, required = true, dataType = "String", value = "개업일 YYYYMMDD")
//    private String startDt;

    @ApiModelProperty(position = 4, required = true, dataType = "Long", value = "사용자 식별번호")
    private Long userId;

    @NotNull(message = "마트 주소는 필수입니다.")
    @ApiModelProperty(position = 5, required = true, dataType = "String", value = "마트 주소")
    private String martAddress;

    @NotNull(message = "마트 영업시작시간 필수입니다.")
    @ApiModelProperty(position = 6, required = true, dataType = "String", value = "마트 영업시작시간")
    private String openTime;

    @NotNull(message = "마트 마감시간 필수입니다.")
    @ApiModelProperty(position = 7, required = true, dataType = "String", value = "마트 마감시간")
    private String closeTime;

    @NotNull(message = "마트 번호 필수입니다.")
    @ApiModelProperty(position = 8, required = true, dataType = "String", value = "마트 번호")
    private String number;

    @NotNull(message = "마트 물품 요청 필수입니다.")
    @ApiModelProperty(position = 9, required = true, dataType = "Long", value = "마트 물품 요청 ")
    private Long requestYn;

    public Mart toEntity(User user) {
        return Mart.builder()
            .martName(martName)
            .martAddress(martAddress)
            .openTime(openTime)
            .closeTime(closeTime)
            .latitude(latitude)
            .longitude(longitude)
            .requestYn(requestYn)
            .user(user)
            .build();
    }
}
