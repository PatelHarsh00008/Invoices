package com.spring.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.spring.project.dtos.Dto;
import com.spring.project.entities.Invoice;
import com.spring.project.exceptions.ResourceNotFoundException;
import com.spring.project.respositories.InvoiceRepository;
import com.spring.project.service.InvoiceService;
import com.spring.project.service.ProjectService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class InvoiceServiceImpl implements InvoiceService{
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private ProjectService projectService;

	@Override
	public List<Invoice> getAllInvoices() {
		return this.invoiceRepository.findAll();
	}
	
	public static String str = "";
	
	@Override
	public Invoice getInvoiceById(long id) {
		return this.invoiceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
	}

	@Override
	public Invoice getInvoiceByInvoiceNumber(long invoiceNumber) {
		Invoice invoice =  this.invoiceRepository.getInvoiceByInvoiceNumber(invoiceNumber);
		if(invoice == null) throw new ResourceNotFoundException("Invoice", "invoice number", invoiceNumber);
		return invoice;
	}

	@Override
	public Invoice updateInvoice(Invoice invoice, long id) {
		Invoice i = this.invoiceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
		
		i.setInvoiceNumber(invoice.getInvoiceNumber());
		i.setProject(this.projectService.getProjectById(invoice.getProject().getId()));
		i.setProjectNumber(invoice.getProjectNumber());
		
		i.setInvoiceDate(invoice.getInvoiceDate());
		i.setInstallDate(invoice.getInstallDate());
		i.setInstallFrom(invoice.getInstallFrom());
		i.setInstallTo(invoice.getInstallTo());
		
		i.setStoreNumber(invoice.getStoreNumber());
		i.setStoreAddress(invoice.getStoreAddress());
		i.setCity(invoice.getCity());
		i.setProvince(invoice.getProvince());
		
		i.setHoursWorked(invoice.getHoursWorked());
		i.setDeliverables(invoice.getDeliverables());
		i.setTravelTime(invoice.getTravelTime());
		
		i.setMileage(invoice.getMileage());
		i.setFood(invoice.getFood());
		i.setLuggage(invoice.getLuggage());
		i.setParking(invoice.getParking());
		i.setMaterial(invoice.getMaterial());
		i.setGas(invoice.getGas());
		i.setFerry(invoice.getFerry());
		
		i.setSubTotal(i.getSubTotal());
		i.setTax(i.getTax());
		i.setTotal(i.getTotal());
		
		i.setNotesForCompany(invoice.getNotesForCompany());
		i.setNotesForNetech(invoice.getNotesForNetech());
		
		return this.invoiceRepository.save(i);
	}

	@Override
	public Invoice createInvoice(Invoice invoice) {
		Invoice i = this.invoiceRepository.save(invoice);
		i.setProject(this.projectService.getProjectById(i.getProject().getId()));
		i.setSubTotal(i.getSubTotal());
		i.setTax(i.getTax());
		i.setTotal(i.getTotal());
		return this.invoiceRepository.save(i);
	}

	@Override
	public void deleteInvoice(long id) {
		Invoice invoice = this.invoiceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
		this.invoiceRepository.delete(invoice);
	}

	@Override
	public List<Invoice> getAllInvoicesByProjectId(long id) {
		List<Invoice> invoices = this.invoiceRepository.getAllInvoicesByProjectId(id);
		if(invoices == null) throw  new ResourceNotFoundException("Invoice", "project id", id);
		return invoices;
	}

	@Override
	public List<Object> searchInvoice(Dto dto) {
		str = makeQuery(dto);
		return null;
	}
	
	public String makeQuery(Dto dto){
        String query = "select ";
        String condition = "where ";
        int count = 0;
        if(dto.getInvoiceDate() != null) {
            query += "CAST(invoice_date as date) as invoice_date, ";
            condition += "invoice_date=?" + ++count + " AND ";
        }
        if(dto.getInstallDate() != null){
            query += "CAST(install_date as date) as install_date, ";
            condition += "install_date=?" + ++count + " AND ";
        }
        if(dto.getInstallFrom() != null){
            query += "CAST(install_from as date) as install_from, ";
            condition += "install_from=?" + ++count + " AND ";
        }
        if(dto.getInstallTo() != null){
            query += "CAST(install_to as date) as install_to, ";
            condition += "install_to=?" + ++count + " AND ";
        }
        if(dto.getInvoiceNumber() != 0){
            query += "CAST(invoice_number as bigint) as invoice_number, ";
            condition += "invoice_number=?" + ++count + " AND ";
        }
        if(dto.getProvince() != null){
            query += "province, ";
            condition += "province=?" + ++count + " AND ";
        }
        if(dto.getProjectId() != 0){
            query += "CAST(project_id as bigint) as project_id ";
            condition += "project_id=?" + ++count;
        }

        if(condition.endsWith("AND "))
            condition = condition.substring(0, condition.length() - 4);
        if(query.endsWith(", "))
            query = query.substring(0, query.length() - 2);

        return query + "\n from invoices \n" + condition;
    }
	
	@Override
	public List<Invoice> findByCriteria(Dto dto, Pageable pageable){
		List<Invoice> list = this.invoiceRepository.findAll(new Specification<Invoice>() {

			@Override
			public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if(dto.getInstallDate() != null)
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("installDate"), dto.getInstallDate())));
				if(dto.getInstallFrom() != null)
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("installFrom"), dto.getInstallFrom())));
				if(dto.getInstallTo() != null)
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("installTo"), dto.getInstallTo())));
				if(dto.getInvoiceDate() != null)
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("invoiceDate"), dto.getInvoiceDate())));
				if(dto.getProvince() != null)
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("province"), dto.getProvince())));
				if(dto.getInvoiceNumber() != 0)
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("invoiceNumber"), dto.getInvoiceNumber())));
				if(dto.getProjectId() != 0)
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("project").get("id"), dto.getProjectId())));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
		return list;
	}

}
