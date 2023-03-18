package com.harsh.project.respositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.harsh.project.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
			
	@Query(value = "select * from invoices where project_id = ?1", nativeQuery = true)
	List<Invoice> getAllInvoicesByProjectId(long id);
	
	@Query(value = "select * from invoices where invoice_number = ?1", nativeQuery = true)
	Invoice getInvoiceByInvoiceNumber(long invoiceNumber);

	List<Invoice> findAll(Specification<Invoice> specification, Pageable pageable);
	

	 
}
