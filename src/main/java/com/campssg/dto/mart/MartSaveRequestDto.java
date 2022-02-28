package com.campssg.dto.mart;

import com.campssg.DB.entity.Mart;
import com.campssg.DB.entity.User;
import io.swagger.annotations.ApiModel;
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
    private String martName;

    @NotNull(message = "사용자 식별번호 필수입니다.")
    private Long userId;

    @NotNull(message = "마트 주소는 필수입니다.")
    private String martAddress;

    @NotNull(message = "마트 영업시작시간 필수입니다.")
    private String openTime;

    @NotNull(message = "마트 마감시간 필수입니다.")
    private String closeTime;

    @NotNull(message = "마트 번호 필수입니다.")
    private String number;

    @NotNull(message = "마트 물품 요청 필수입니다.")
    private Long requestYn;

    public Mart toEntity(Long userId) {
        return Mart.builder()
            .martName(martName)
            .martAddress(martAddress)
            .requestYn(requestYn)
            .user(new User(userId))
            .build();
    }
}
