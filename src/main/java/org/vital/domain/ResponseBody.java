package org.vital.domain;

public class ResponseBody {
    private ResponseCode responseCode;
    private String capital;

    public ResponseBody(ResponseCode responseCode, String capital) {
        this.responseCode = responseCode;
        this.capital = capital;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
