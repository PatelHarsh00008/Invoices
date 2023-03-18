package com.spring.project.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.project.dtos.ApiResponse;
import com.spring.project.dtos.Dto;
import com.spring.project.entities.Invoice;
import com.spring.project.service.InvoiceService;
import com.spring.project.service.PdfService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
	
	@Autowired
	private PdfService pdfService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@GetMapping("/")
	public ResponseEntity<List<Invoice>> getAllInvoices(){
		return ResponseEntity.ok(this.invoiceService.getAllInvoices());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Invoice> getInvoiceById(@PathVariable long id){
		return ResponseEntity.ok(this.invoiceService.getInvoiceById(id));
	} 

	@PutMapping("/{id}")
	public ResponseEntity<Invoice> updateInvoice(@RequestBody Invoice invoice, @PathVariable long id){
		Invoice updatedInvoice = this.invoiceService.updateInvoice(invoice, id);
		return ResponseEntity.ok(updatedInvoice);
	}
	
	@PostMapping("/")
	public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice){
		Invoice createdInvoice = this.invoiceService.createInvoice(invoice);
		return ResponseEntity.ok(createdInvoice);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteInvoice(@PathVariable long id){
		this.invoiceService.deleteInvoice(id);
		return ResponseEntity.ok("Invoice with id : " + id + " successfully deleted");
	} 
	
	@GetMapping("projectId/{id}")
	public ResponseEntity<List<Invoice>> getAllInvoicesByProjectId(@PathVariable long id){
		return ResponseEntity.ok(this.invoiceService.getAllInvoicesByProjectId(id));
	}
	
	@GetMapping("/invoiceNumber/{invoiceNumber}")
	public ResponseEntity<Invoice> getInvoiceByInvoiceNumber(@PathVariable long invoiceNumber){
		return ResponseEntity.ok(this.invoiceService.getInvoiceByInvoiceNumber(invoiceNumber));
	}
	
	@GetMapping("/createPdf/{invoiceNumber}")
	public ResponseEntity<InputStreamResource> exportToPdf(HttpServletResponse response, @PathVariable long invoiceNumber) {
		
		byte[] pdfBytes = (byte[]) this.pdfService.createPdf(this.invoiceService.getInvoiceByInvoiceNumber(invoiceNumber));
		ByteArrayInputStream pdf = new ByteArrayInputStream(pdfBytes);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "attachment;filename = Invoice_" + invoiceNumber + ".pdf");
		
		return ResponseEntity
				.ok()
				.headers(httpHeaders)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdf));
	}
	
	@GetMapping("/byteArrayOfPdf/{invoiceNumber}")
	public ResponseEntity<ApiResponse> getByteArrayOfPdf(@PathVariable long invoiceNumber) {
		
		byte[] pdfBytes = (byte[]) this.pdfService.createPdf(this.invoiceService.getInvoiceByInvoiceNumber(invoiceNumber));
		String pdfInString = Base64.getEncoder().encodeToString(pdfBytes);
		return ResponseEntity.ok(new ApiResponse(pdfInString, true));
	}
	
	@PostMapping("/findByCriteria/{pageNumber}")
	public ResponseEntity<List<Invoice>> findByCriteria(@RequestBody Dto dto, @PathVariable int pageNumber){
		return ResponseEntity.ok(this.invoiceService.findByCriteria(dto, PageRequest.of(pageNumber, 5)));
	}
	
	
	
	
}
