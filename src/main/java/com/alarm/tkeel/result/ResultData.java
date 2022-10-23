package com.alarm.tkeel.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/18/17:46
 */
@Data
public class ResultData<T> {
    @ApiModelProperty(value = "状态码")
    private String code;
    @ApiModelProperty(value = "-")
    private String msg;
    @ApiModelProperty(value = "-")
    private T data;
    @ApiModelProperty(value = "时间戳")
    private long timestamp ;


    public ResultData (){
        this.timestamp = System.currentTimeMillis()/1000;
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(ReturnCode.TKEEL200.getCode());
        resultData.setMsg(ReturnCode.TKEEL200.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static <T> ResultData<T> fail(String code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMsg(message);
        return resultData;
    }
}
