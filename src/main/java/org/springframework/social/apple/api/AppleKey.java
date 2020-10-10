package org.springframework.social.apple.api;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "kty", "kid", "use", "alg", "n", "e" })
public class AppleKey {

	@JsonProperty("kty")
	private String kty;
	@JsonProperty("kid")
	private String kid;
	@JsonProperty("use")
	private String use;
	@JsonProperty("alg")
	private String alg;
	@JsonProperty("n")
	private String n;
	@JsonProperty("e")
	private String e;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("kty")
	public String getKty() {
		return kty;
	}

	@JsonProperty("kty")
	public void setKty(String kty) {
		this.kty = kty;
	}

	@JsonProperty("kid")
	public String getKid() {
		return kid;
	}

	@JsonProperty("kid")
	public void setKid(String kid) {
		this.kid = kid;
	}

	@JsonProperty("use")
	public String getUse() {
		return use;
	}

	@JsonProperty("use")
	public void setUse(String use) {
		this.use = use;
	}

	@JsonProperty("alg")
	public String getAlg() {
		return alg;
	}

	@JsonProperty("alg")
	public void setAlg(String alg) {
		this.alg = alg;
	}

	@JsonProperty("n")
	public String getN() {
		return n;
	}

	@JsonProperty("n")
	public void setN(String n) {
		this.n = n;
	}

	@JsonProperty("e")
	public String getE() {
		return e;
	}

	@JsonProperty("e")
	public void setE(String e) {
		this.e = e;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}