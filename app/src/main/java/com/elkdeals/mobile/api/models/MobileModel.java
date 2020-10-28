
package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileModel {
    /*
    * {
"status": true,
"code": "",
"needcode": false,
"enter": true,
"msg": "CODE_SENT"
}
    * */

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("needcode")
    @Expose
    private boolean needcode;
    @SerializedName("enter")
    @Expose
    private boolean allowToEnter;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public boolean isNeedcode() {
        return needcode;
    }

    public void setNeedcode(boolean needcode) {
        this.needcode = needcode;
    }

    public boolean isAllowToEnter() {
        return allowToEnter;
    }

    public void setAllowToEnter(boolean allowToEnter) {
        this.allowToEnter = allowToEnter;
    }
}
