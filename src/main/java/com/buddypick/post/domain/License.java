package com.buddypick.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum License {

    // 스쿠버다이빙 자격증
    OPEN_WATER_DRIVER("오픈워터 다이버"),
    ADVANCED_OPEN_WATER_DRIVER("어드밴스드 오픈워터 다이버"),
    RESCUE_DRIVER("레스큐 다이버"),
    DIVE_MASTER("다이브마스터"),
    SCUBA_INSTRUCTOR("스쿠버다이빙 강사"),

    //프리다이빙 자격증
    FREE_DIVER("프리다이버"),
    ADVANCED_FREE_DIVER("어드밴스드 프리다이버"),
    MASTER_FREE_DIVER("마스터 프리다이버"),
    FREE_DIVING_INSTRUCTOR("프리다이빙 강사");

    private final String value;
}
