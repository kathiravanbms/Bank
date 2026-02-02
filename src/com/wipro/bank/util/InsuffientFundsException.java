package com.wipro.bank.util;
public class InsuffientFundsException extends Exception{
	@Override
	public String toString(){
		return "Insufficient Funds:";
	}
}
