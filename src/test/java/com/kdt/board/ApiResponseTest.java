package com.kdt.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ApiResponseTest {

    @Test
    void ok_정상_테스트() {
        //given
        String data = "Data 입니다.";
        //when
        ApiResponse<String> ok = ApiResponse.ok(data);
        //then
        Assertions.assertThat(ok.getData()).isEqualTo(data);
        Assertions.assertThat(ok.getStatusCode()).isEqualTo(200);
    }

    @Test
    void fail_정상_테스트() {
        //given
        String data = "Fail입니다.";
        //when
        ApiResponse<String> fail = ApiResponse.fail(403,data);
        //then
        Assertions.assertThat(fail.getData()).isEqualTo(data);
        Assertions.assertThat(fail.getStatusCode()).isEqualTo(403);
    }
}