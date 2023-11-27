package com.ssafy.web.mail.service;

import com.ssafy.web.util.MyException;

public interface MailService {
	
	public String sendMail(String mail) throws MyException;
	
}
