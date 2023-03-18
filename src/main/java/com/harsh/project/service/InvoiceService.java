package com.harsh.project.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.harsh.project.dtos.Dto;
import com.harsh.project.entities.Invoice;

public interface InvoiceService {
	List<Invoice> getAllInvoices();
	Invoice getInvoiceById(long id);
	Invoice getInvoiceByInvoiceNumber(long invoiceNumber);
	Invoice updateInvoice(Invoice invoice, long id);
	Invoice createInvoice(Invoice invoice);
	void deleteInvoice(long id);
	List<Invoice> getAllInvoicesByProjectId(long id);
	List<Object> searchInvoice(Dto dto);
	List<Invoice> findByCriteria(Dto dto, Pageable pageable);
}
