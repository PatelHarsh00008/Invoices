package com.harsh.project.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.harsh.project.entities.Invoice;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfService {
	
	private Map<String, String> mapOfInvoice(Invoice invoice){
		Map<String, String> map = new LinkedHashMap<>();
		
		map.put("id", String.valueOf(invoice.getId()));
		map.put("invoice number", String.valueOf(invoice.getInvoiceNumber()));
		map.put("project id", String.valueOf(invoice.getProject().getId()));
		map.put("project number", String.valueOf(invoice.getProjectNumber()));
		map.put("invoice date" , String.valueOf(invoice.getInvoiceDate()));
		map.put("install date" , String.valueOf(invoice.getInstallDate()));
		map.put("install from" , String.valueOf(invoice.getInstallFrom()));
		map.put("install to" , String.valueOf(invoice.getInstallTo()));
		map.put("store number", invoice.getStoreNumber());
		map.put("store address", invoice.getStoreAddress());
		map.put("city", invoice.getCity());
		map.put("province", invoice.getProvince());
		map.put("hours worked", String.valueOf(invoice.getHoursWorked()));
		map.put("deliverables", String.valueOf(invoice.getDeliverables()));
		map.put("travel time", String.valueOf(invoice.getTravelTime()));
		map.put("mileage", String.valueOf(invoice.getMileage()));
		map.put("food", String.valueOf(invoice.getFood()));
		map.put("luggage", String.valueOf(invoice.getLuggage()));
		map.put("parking", String.valueOf(invoice.getParking()));
		map.put("material", String.valueOf(invoice.getMaterial()));
		map.put("gas", String.valueOf(invoice.getGas()));
		map.put("ferry", String.valueOf(invoice.getFerry()));
		map.put("notes for company", invoice.getNotesForCompany());
		map.put("notes for netech", invoice.getNotesForNetech());
		map.put("sub total", String.valueOf(String.format("%.2f", invoice.getSubTotal())));
		map.put("tax", String.valueOf(String.format("%.2f", invoice.getTax())));
		map.put("total", String.valueOf(String.format("%.2f", invoice.getTotal())));
		
		return map;
	}
	
	public byte[] createPdf(Invoice invoice) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, bos);
		String urlOfImage = "https://netechaccounting.ca/static/media/image.11ce533c.png";
		document.open();
		
		float[] width = {4f, 3f, 3f};
		PdfPTable header = new PdfPTable(width);
		makeHeader(header,urlOfImage, invoice);
		
		float[] storeDetailsWidth = {4f, 1f, 5f};
		PdfPTable storeDetails = new PdfPTable(storeDetailsWidth);
		enterStoreDetails(storeDetails, invoice);
		
		float[] dataWidth = {4f, 1f, 2f, 3f};
		PdfPTable table = new PdfPTable(dataWidth);
		writeData(table, invoice);
		
		PdfPTable trailer = new PdfPTable(1);
		makeTrailer(trailer, invoice);
		
		document.add(header);
		document.add(new Paragraph("\n\n"));
		document.add(storeDetails);
		document.add(new Paragraph("\n\n"));
		document.add(table);
		document.add(new Paragraph("\n\n"));
		document.add(trailer);
		document.close();
		return bos.toByteArray();
	}

	private void makeTrailer(PdfPTable trailer, Invoice invoice) {
		Font bold = new Font(Font.TIMES_ROMAN, 14, Font.BOLD);
		Font normal = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL);
		
		PdfPCell notesForCompany = new PdfPCell();
		notesForCompany.addElement(new Phrase(invoice.getNotesForCompany(), normal));
		trailer.addCell(notesForCompany);
		
		PdfPCell notesForNetech = new PdfPCell();
		notesForNetech.addElement(new Phrase(invoice.getNotesForNetech(), normal));
		trailer.addCell(notesForNetech);
		
		PdfPCell cell = new PdfPCell();
		cell.addElement(new Phrase("\n\n"));
		cell.setBorder(Rectangle.NO_BORDER);
		trailer.addCell(cell);
		
		PdfPCell closeUp = new PdfPCell();
		closeUp.setPhrase(new Phrase("Thank you for your Business!!!\n", bold));
		closeUp.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		trailer.addCell(closeUp);
		
	}

	private void enterStoreDetails(PdfPTable storeDetails, Invoice invoice) {
		Font normal = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL);
		
		PdfPCell cell = new PdfPCell();
		cell.addElement(new Phrase("To:-\n"+ invoice.getStoreNumber() + "\n" + invoice.getStoreAddress() + 
				"\n" + invoice.getCity() + ", " + invoice.getProvince(), normal));
		storeDetails.addCell(cell);
		
		PdfPCell blankCell = new PdfPCell();
		blankCell.setBorder(Rectangle.NO_BORDER);
		storeDetails.addCell(blankCell);
		
		PdfPCell cell2 = new PdfPCell();
		cell2.addElement(new Phrase("Project # " + invoice.getProjectNumber() + 
				"\nDate:- " + String.valueOf(invoice.getInvoiceDate()) + 
				"\nStore # "+ invoice.getStoreNumber(), normal));
		storeDetails.addCell(cell2);
	}

	private void makeHeader(PdfPTable header, String urlOfImage, Invoice invoice) {
		Font bold = new Font(Font.TIMES_ROMAN, 14, Font.BOLD);
		Font normal = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL);
		
		PdfPCell cell = new PdfPCell();
		cell.addElement(new Phrase("NETECH CABLING INC.", bold));
		cell.addElement(new Phrase("14 Automatic Road, Unit # 40\n"
				+ "Brampton Ontario L6S5N5\nPhone:- 905-863-3993", normal));
		header.addCell(cell);
		
		PdfPCell imageCell = new PdfPCell();
		imageCell.setBorder(Rectangle.NO_BORDER);
		Image image = null;
		try {
			image = Image.getInstance(new URL(urlOfImage));
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageCell.addElement(image);
		imageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		header.addCell(imageCell);
		
		PdfPCell cell1 = new PdfPCell();
		cell1.setBorder(Rectangle.NO_BORDER);
		cell1.addElement(new Phrase("Invoice",bold));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setRight(0);
		cell1.addElement(new Phrase("Invoice # " + String.valueOf(invoice.getInvoiceNumber()) + 
				"\n Date:- " + String.valueOf(invoice.getInvoiceDate()), normal));
		header.addCell(cell1);
	}

	private void writeData(PdfPTable table, Invoice invoice) {
		Font bold = new Font(Font.TIMES_ROMAN, 11, Font.BOLD);
		Font normal = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL);
		
		// attributes row
		PdfPCell cell1 = new PdfPCell();
		cell1.addElement(new Phrase("Description", bold));
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell();
		cell2.addElement(new Phrase("Hours", bold));
		table.addCell(cell2);
		
		PdfPCell cell3 = new PdfPCell();
		cell3.addElement(new Phrase("Rate per hour/KM(CAD)", bold));
		table.addCell(cell3);
		
		PdfPCell cell4 = new PdfPCell();
		cell4.addElement(new Phrase("AMOUNT", bold));
		table.addCell(cell4);
		
		// labour hours row
		PdfPCell hours = new PdfPCell();
		hours.addElement(new Phrase("labour hours", normal));
		table.addCell(hours);
		
		PdfPCell hoursWorked = new PdfPCell();
		hoursWorked.addElement(new Phrase(String.valueOf(invoice.getHoursWorked()), normal));
		table.addCell(hoursWorked);
		
		PdfPCell hoursOfLabour = new PdfPCell();
		hoursOfLabour.addElement(new Phrase(String.valueOf(invoice.getProject().getRatePerHour()), normal));
		hoursOfLabour.setBorder(Rectangle.TOP);
		table.addCell(hoursOfLabour);
		
		PdfPCell amountOfRow1 = new PdfPCell();
		amountOfRow1.addElement(new Phrase(String.valueOf(invoice.getHoursWorked() * invoice.getProject().getRatePerHour()), normal));
		table.addCell(amountOfRow1);
		
		// deliverables row
		PdfPCell deliverables = new PdfPCell();
		deliverables.addElement(new Phrase("Deliverables", normal));
		table.addCell(deliverables);
		
		PdfPCell hoursOfDeliverables = new PdfPCell();
		hoursOfDeliverables.addElement(new Phrase(String.valueOf(invoice.getDeliverables()), normal));
		table.addCell(hoursOfDeliverables);
		
		PdfPCell ratePerHourForDeliverables = new PdfPCell();
		ratePerHourForDeliverables.addElement(new Phrase(String.valueOf(invoice.getProject().getRatePerHour()), normal));
		ratePerHourForDeliverables.setBorder(Rectangle.BOTTOM);
		table.addCell(ratePerHourForDeliverables);
		
		PdfPCell amountOfRow2 = new PdfPCell();
		amountOfRow2.addElement(new Phrase(String.valueOf(invoice.getDeliverables() * invoice.getProject().getRatePerHour()), normal));
		table.addCell(amountOfRow2);
		
		// travel time row
		PdfPCell travelTime = new PdfPCell();
		travelTime.addElement(new Phrase("Travel Time", normal));
		table.addCell(travelTime);
		
		PdfPCell hoursOfTravelTime = new PdfPCell();
		hoursOfTravelTime.addElement(new Phrase(String.valueOf(invoice.getTravelTime()), normal));
		table.addCell(hoursOfTravelTime);
		
		PdfPCell ratePerHourForTravelTime = new PdfPCell();
		ratePerHourForTravelTime.addElement(new Phrase(String.valueOf(invoice.getProject().getRatePerHour()), normal));
		table.addCell(ratePerHourForTravelTime);
		
		PdfPCell amountOfRow3 = new PdfPCell();
		amountOfRow3.addElement(new Phrase(String.valueOf(invoice.getTravelTime() * invoice.getProject().getRatePerHour()), normal));
		table.addCell(amountOfRow3);
		
		// mileage row
		PdfPCell mileage = new PdfPCell();
		mileage.addElement(new Phrase("Mileage", normal));
		table.addCell(mileage);
		
		PdfPCell kmsOfMileage = new PdfPCell();
		kmsOfMileage.addElement(new Phrase(String.valueOf(invoice.getMileage()), normal));
		table.addCell(kmsOfMileage);
		
		PdfPCell ratePerKmForMileage = new PdfPCell();
		ratePerKmForMileage.addElement(new Phrase(String.valueOf(invoice.getProject().getRatePerKm()), normal));
		table.addCell(ratePerKmForMileage);
		
		PdfPCell amountOfRow4 = new PdfPCell();
		amountOfRow4.addElement(new Phrase(String.valueOf(invoice.getMileage() * invoice.getProject().getRatePerKm()), normal));
		table.addCell(amountOfRow4);
		
		
		//food
		PdfPCell food = new PdfPCell();
		food.addElement(new Phrase("Food", normal));
		table.addCell(food);
		
		PdfPCell food1 = new PdfPCell();
		table.addCell(food1);
		PdfPCell food2 = new PdfPCell();
		table.addCell(food2);
		
		PdfPCell amountOfFood = new PdfPCell();
		amountOfFood.addElement(new Phrase(String.valueOf(invoice.getFood()), normal));
		table.addCell(amountOfFood);
		
		// luggage
		PdfPCell luggage = new PdfPCell();
		luggage.addElement(new Phrase("Luggage", normal));
		table.addCell(luggage);
		
		PdfPCell luggage1 = new PdfPCell();
		table.addCell(luggage1);
		PdfPCell luggage2 = new PdfPCell();
		table.addCell(luggage2);
		
		PdfPCell amountOfLuggage = new PdfPCell();
		amountOfLuggage.addElement(new Phrase(String.valueOf(invoice.getLuggage()), normal));
		table.addCell(amountOfLuggage);
		
		//parking 
		PdfPCell parking = new PdfPCell();
		parking.addElement(new Phrase("Parking", normal));
		table.addCell(parking);
		
		PdfPCell parking1 = new PdfPCell();
		table.addCell(parking1);
		PdfPCell parking2 = new PdfPCell();
		table.addCell(parking2);
		
		PdfPCell amountOfParking = new PdfPCell();
		amountOfParking.addElement(new Phrase(String.valueOf(invoice.getParking()), normal));
		table.addCell(amountOfParking);
		
		//material
		PdfPCell material = new PdfPCell();
		material.addElement(new Phrase("Material", normal));
		table.addCell(material);
		
		PdfPCell material1 = new PdfPCell();
		table.addCell(material1);
		PdfPCell material2 = new PdfPCell();
		table.addCell(material2);
		
		PdfPCell amountOfMaterial = new PdfPCell();
		amountOfMaterial.addElement(new Phrase(String.valueOf(invoice.getMaterial()), normal));
		table.addCell(amountOfMaterial);
		
		// gas
		PdfPCell gas = new PdfPCell();
		gas.addElement(new Phrase("Gas", normal));
		table.addCell(gas);
		
		PdfPCell gas1 = new PdfPCell();
		table.addCell(gas1);
		PdfPCell gas2 = new PdfPCell();
		table.addCell(gas2);
		
		PdfPCell amountOfGas = new PdfPCell();
		amountOfGas.addElement(new Phrase(String.valueOf(invoice.getGas()), normal));
		table.addCell(amountOfGas);
		
		//ferry
		PdfPCell ferry = new PdfPCell();
		ferry.addElement(new Phrase("Ferry", normal));
		table.addCell(ferry);
		
		PdfPCell ferry1 = new PdfPCell();
		table.addCell(ferry1);
		PdfPCell ferry2 = new PdfPCell();
		table.addCell(ferry2);
		
		PdfPCell amountOfFerry = new PdfPCell();
		amountOfFerry.addElement(new Phrase(String.valueOf(invoice.getFerry()), normal));
		table.addCell(amountOfFerry);
		
		//sub total
		PdfPCell subtotal1 = new PdfPCell();
		subtotal1.setBorder(Rectangle.NO_BORDER);
		table.addCell(subtotal1);
		PdfPCell subtotal2 = new PdfPCell();
		subtotal2.setBorder(Rectangle.NO_BORDER);
		table.addCell(subtotal2);
		
		PdfPCell subtotal = new PdfPCell();
		subtotal.addElement(new Phrase("Sub Total", normal));
		table.addCell(subtotal);
		
		PdfPCell amountOfSubTotal = new PdfPCell();
		amountOfSubTotal.addElement(new Phrase(String.valueOf(invoice.getSubTotal()), normal));
		table.addCell(amountOfSubTotal);
		
		// tax
		PdfPCell tax1 = new PdfPCell();
		tax1.setBorder(Rectangle.NO_BORDER);
		table.addCell(tax1);
		PdfPCell tax2 = new PdfPCell();
		tax2.setBorder(Rectangle.NO_BORDER);
		table.addCell(tax2);
		
		PdfPCell tax = new PdfPCell();
		tax.addElement(new Phrase("HST", normal));
		table.addCell(tax);
		
		PdfPCell amountOfTax = new PdfPCell();
		amountOfTax.addElement(new Phrase(String.valueOf(invoice.getTax()), normal));
		table.addCell(amountOfTax);
		
		//Total
		PdfPCell total1 = new PdfPCell();
		total1.setBorder(Rectangle.NO_BORDER);
		table.addCell(total1);
		PdfPCell total2 = new PdfPCell();
		total2.setBorder(Rectangle.NO_BORDER);
		table.addCell(total2);
		
		PdfPCell total = new PdfPCell();
		total.addElement(new Phrase("Total", normal));
		table.addCell(total);
		
		PdfPCell amountOfTotal = new PdfPCell();
		amountOfTotal.addElement(new Phrase(String.valueOf(invoice.getTotal()), normal));
		table.addCell(amountOfTotal);
		
//		for(Map.Entry<String, String> entry : map.entrySet()) {
//			PdfPCell keyCell = new PdfPCell();
//			keyCell.setPhrase(new Phrase(entry.getKey()));
//			keyCell.setBorder(Rectangle.NO_BORDER);;
//			table.addCell(keyCell);
//			
//			PdfPCell valueCell = new PdfPCell();
//			valueCell.setPhrase(new Phrase(entry.getValue()));
//			valueCell.setBorder(Rectangle.NO_BORDER);
//			valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			table.addCell(valueCell);
//		}
	}
	
	
}
