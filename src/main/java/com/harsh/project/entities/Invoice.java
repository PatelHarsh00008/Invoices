package com.harsh.project.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.harsh.project.exceptions.ResourceNotFoundException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice {
	
	private static final Map<String, Double> taxOfProvinces = initMap();
	private static Map<String, Double> initMap() {
		Map<String, Double> map = new HashMap<>();
		map.put("Alberta", 5.0);
		map.put("British Columbia", 12.0);
		map.put("Manitoba", 12.0);
		map.put("New Brunswick", 15.0);
		map.put("Newfoundland and labrador", 15.0);
		map.put("Northwest Territories", 5.0);
		map.put("Nova Scotia", 15.0);
		map.put("Nunavut", 5.0);
		map.put("Ontario", 13.0);
		map.put("Prince Edward Island", 15.0);
		map.put("Quebec",14.975);
		map.put("Saskatchewan", 11.0);
		map.put("Yukon", 5.0);
		return map;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(unique = true)
	private long invoiceNumber;
	
	@OneToOne(cascade = {CascadeType.MERGE})
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private Project project;
	
	private String projectNumber;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate invoiceDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate installDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate installFrom;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate installTo;
	
	private String storeNumber;
	private String storeAddress;
	private String city;
	private String province;
	
	private double hoursWorked;
	private double deliverables;
	private double travelTime;
	
	private double mileage;
	
	private double food;
	private double luggage;
	private double parking;
	private double material;
	private double gas;
	private double ferry;
	
	private String  notesForCompany;
	private String notesForNetech;
	
	// if we use @Column use wrapper class
	private Double subTotal;
	
	private Double tax;
	
	private Double total;
	
	public Invoice() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

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

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public double getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public double getDeliverables() {
		return deliverables;
	}

	public void setDeliverables(double deliverables) {
		this.deliverables = deliverables;
	}

	public double getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(double travelTime) {
		this.travelTime = travelTime;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	public double getFood() {
		return food;
	}

	public void setFood(double food) {
		this.food = food;
	}

	public double getLuggage() {
		return luggage;
	}

	public void setLuggage(double luggage) {
		this.luggage = luggage;
	}

	public double getParking() {
		return parking;
	}

	public void setParking(double parking) {
		this.parking = parking;
	}

	public double getMaterial() {
		return material;
	}

	public void setMaterial(double material) {
		this.material = material;
	}

	public double getGas() {
		return gas;
	}

	public void setGas(double gas) {
		this.gas = gas;
	}

	public double getFerry() {
		return ferry;
	}

	public void setFerry(double ferry) {
		this.ferry = ferry;
	}

	public String getNotesForCompany() {
		return notesForCompany;
	}

	public void setNotesForCompany(String notesForCompany) {
		this.notesForCompany = notesForCompany;
	}

	public String getNotesForNetech() {
		return notesForNetech;
	}

	public void setNotesForNetech(String notesForNetech) {
		this.notesForNetech = notesForNetech;
	}
	
	public double getSubTotal() {
		this.subTotal =  food + luggage + parking + material + gas + ferry +
				((hoursWorked + deliverables + travelTime) * this.getProject().getRatePerHour()) +
				(mileage * this.getProject().getRatePerKm());
		return Double.parseDouble(String.format("%.2f", subTotal));
	}
	
	public double getTax() {
		if(!this.taxOfProvinces.containsKey(this.getProvince())) throw new ResourceNotFoundException("Province", "", this.getProvince());
		this.tax =  subTotal * taxOfProvinces.get(this.getProvince()) * 0.01;
		return Double.parseDouble(String.format("%.2f", tax));
	}
	
	public double getTotal() {
		this.total = this.getTax() + this.getSubTotal();
		return Double.parseDouble(String.format("%.2f", this.total));
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
