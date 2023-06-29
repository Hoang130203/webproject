package com.example.project.BEAN;

import java.util.ArrayList;
import java.util.List;

public class Bank {
	private String bankName;
	private String parent;
	private List<Bank> children=new ArrayList<Bank>();
	public String getBankName() {
		return bankName;
	}
	public void addChild(Bank child) {
        children.add(child);
    }
	public List<Bank> getChildren() {
        return children;
    }
	public void setChildren(List<Bank> list) {
        this.children=list;
    }
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public Bank(String bankName, String parent) {
		super();
		this.bankName = bankName;
		this.parent = parent;
	}
	public Bank() {
		
	}
	
}
