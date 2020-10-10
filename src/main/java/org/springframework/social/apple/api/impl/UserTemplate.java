package org.springframework.social.apple.api.impl;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.List;
import java.util.Map;

import org.springframework.social.apple.api.AppleKey;
import org.springframework.social.apple.api.AppleKeys;
import org.springframework.social.apple.api.AppleProfile;
import org.springframework.social.apple.api.UserOperations;
import org.springframework.social.apple.api.impl.json.AppleUserInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

public class UserTemplate extends AbstractAppleOperations implements UserOperations {
	private final RestTemplate restTemplate;
	private final String accessToken;
	private String userJson = null;
	private ObjectMapper om = new ObjectMapper();
	
	public UserTemplate(AppleTemplate appleTemplate, RestTemplate restTemplate, boolean authorized,
			String accessToken) {
		super(authorized);
		this.restTemplate = restTemplate;
		this.accessToken = accessToken;
	}

	@Override
	public AppleProfile getUserProfile() {
		//System.out.println("GetUserProfile " + accessToken);
		AppleProfile profile = null;
		try {
			profile = decodeJwt(accessToken);
			if(profile!= null && !StringUtils.isEmpty(userJson)) {
				AppleUserInfo userInfo = om.readValue(userJson, AppleUserInfo.class);
				if(userInfo != null && userInfo.getName() != null) {
					profile.setFirstName(userInfo.getName().getFirstName());
					profile.setLastName(userInfo.getName().getLastName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return profile;
	}

	private AppleKeys getKeys() {
		return restTemplate.getForObject("https://appleid.apple.com/auth/keys", AppleKeys.class);
	}

	private PublicKey getPublicKey(AppleKey key) throws Exception {
		String publicKeyString = key.getN();
		String publicKeyExponent = key.getE();

		BigInteger n = new BigInteger(1, Decoders.BASE64URL.decode(publicKeyString));
		BigInteger e = new BigInteger(1, Decoders.BASE64URL.decode(publicKeyExponent));

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		KeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		return publicKey;
	}

	private AppleProfile decodeJwt(String jwt) throws Exception {
		AppleKeys keys = getKeys();
		for(AppleKey key : keys.getAppleKeys()) {
			//System.out.println("Decoding with key " + key.getN());
			PublicKey pubKey = getPublicKey(key);
	
			Jws<Claims> jws;
	
			try {
				jws = Jwts.parserBuilder().setSigningKey(pubKey).build().parseClaimsJws(jwt);
				//System.out.println(jws.getBody().toString());
				String email = (String) jws.getBody().get("email");
				String id =  jws.getBody().getSubject();
				AppleProfile profile = new AppleProfile(id, null, null, email);
				return profile;	
			} catch (JwtException ex) {
				//ex.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void setUserJson(String userJson) {
		this.userJson = userJson;
	}

}
