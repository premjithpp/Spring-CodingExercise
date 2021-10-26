package com.demo.tableReservation.dto;

/**
 * Class to normalize all the response of api requests
 * @author Premjith
 */
public class Payload<T> {
	private T data;
	private String msg;




	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


	public Payload<T> withMsg(String msg) {
		this.setMsg(msg);
		return this;
	}

	public Payload<T> withData(T data) {
		this.setData(data);
		return this;
	}
}
