package com.campssg.dto.camping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import java.util.List;

@Getter
@NoArgsConstructor
public class CampingResponseDto<CampingList> {
    private String totalCount;
    private String pageNo;
    private List<CampingList> campingLists;

    public CampingResponseDto(String totalCount, String pageNo, List<CampingList> campingLists) {
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.campingLists = campingLists;
    }
}
