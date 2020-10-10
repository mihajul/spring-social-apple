package org.springframework.social.apple.connect;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.security.PrivateKey;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;

import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AppleOAuth2Template extends OAuth2Template  {

	private static long EXPIRATION_TIME = 86400*180 ; /* 6 months */
	String clientCertificate;
	public static final String RESPONSE_MODE_PARAM = "response_mode";
	public static final String RESPONSE_MODE_VALUE = "form_post";
	
	public AppleOAuth2Template(String clientId, String teamId, String keyId, String clientCertificate) {
		super(clientId, generateJWT(clientId, teamId, keyId, clientCertificate), "https://appleid.apple.com/auth/authorize", "https://appleid.apple.com/auth/token");
		setUseParametersForClientAuthentication(true);
	}
	
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		AccessGrant grant = extractAccessGrantApple(getRestTemplate().postForObject(accessTokenUrl, parameters, Map.class));
		return grant;
	}

	private AccessGrant extractAccessGrantApple(Map<String, Object> result) {
		return createAccessGrant((String) result.get("id_token"), (String) result.get("scope"), (String) result.get("refresh_token"), getIntegerValue(result, "expires_in"), result);
	}
	
	private Long getIntegerValue(Map<String, Object> map, String key) {
		try {
			return Long.valueOf(String.valueOf(map.get(key))); // normalize to String before creating integer value;			
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	@Override
	public String buildAuthenticateUrl(OAuth2Parameters parameters) {
		return super.buildAuthenticateUrl(parameters);
	}
	
	
	
	private static String generateJWT(String clientId, String teamId, String keyId, String clientCertificate) {
        PrivateKey pKey;
		try {
			pKey = generatePrivateKey(clientCertificate);
		} catch (Exception e) {
			return null;
		}
        String token = Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, keyId)
                .setIssuer(teamId)
                .setAudience("https://appleid.apple.com")
                 .setSubject(clientId)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(pKey, SignatureAlgorithm.ES256)
                 .compact();
        return token;
    }

    private static PrivateKey generatePrivateKey(String certificate) throws Exception {
        final PEMParser pemParser = new PEMParser(new StringReader(certificate));
        final JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        final PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        final PrivateKey pKey = converter.getPrivateKey(object);
        pemParser.close();
        return pKey;
    }
    
    @Override
	public String buildAuthorizeUrl(OAuth2Parameters parameters) {
    	if(!parameters.containsKey(RESPONSE_MODE_PARAM)) {
    		parameters.add(RESPONSE_MODE_PARAM, RESPONSE_MODE_VALUE);
    	}
		return super.buildAuthorizeUrl(parameters);
	}

	public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
		if(!parameters.containsKey(RESPONSE_MODE_PARAM)) {
    		parameters.add(RESPONSE_MODE_PARAM, RESPONSE_MODE_VALUE);
    	}
		return super.buildAuthorizeUrl(grantType, parameters);
	}


}
