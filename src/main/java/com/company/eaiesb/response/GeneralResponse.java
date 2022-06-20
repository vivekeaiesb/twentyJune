package com.company.eaiesb.response;

import org.springframework.stereotype.Component;

@Component
public class GeneralResponse<T, tiket, obj> {
	
	private T response;
	private tiket token;
	private obj object;

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public tiket getToken() {
		return token;
	}

	public void setToken(tiket token) {
		this.token = token;
	}

	public obj getObject() {
		return object;
	}

	public void setObject(obj object) {
		this.object = object;
	}
	
	
}
