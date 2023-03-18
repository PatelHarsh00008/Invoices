package com.spring.project.dtos;

import java.time.LocalDate;

public class Dto {
	private LocalDate invoiceDate;
    private LocalDate installDate;
    private LocalDate installFrom;
    private LocalDate installTo;
    private long invoiceNumber;
    private String province;
    private long projectId;
    
    public Dto() {}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public LocalDate getInstallDate() {
		return installDate;
	}

	public void setInstallDate(LocalDate installDate) {
		this.installDate = installDate;
	}

	public LocalDate getInstallFrom() {
		return installFrom;
	}

	public void setInstallFrom(LocalDate installFrom) {
		this.installFrom = installFrom;
	}

	public LocalDate getInstallTo() {
		return installTo;
	}

	public void setInstallTo(LocalDate installTo) {
		this.installTo = installTo;
	}

	public long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
    
}
