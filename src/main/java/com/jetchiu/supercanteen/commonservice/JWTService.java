package com.jetchiu.supercanteen.commonservice;

public interface JWTService {
    public String GetClaims(String token,String ClaimKey);
    public String CreateTokens(String account);
}
