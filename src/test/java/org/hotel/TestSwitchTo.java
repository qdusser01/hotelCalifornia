package org.hotel;

import  org.junit.Assert.*;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.hotel.ENavigateur;
import org.hotel.OutilTechnique;

public class TestSwitchTo {

	
	WebDriver driver;
	
	@Before
	public void setup() throws Exception {
		BDDConnexion.deleteAllData("src\\test\\JDD\\nettoyage.xml");
		driver = OutilTechnique.choisirNavigateur(ENavigateur.firefox);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}
	
	@After
	public void tearDown() throws Exception
	{
		driver.quit();
		BDDConnexion.deleteAllData("src\\test\\JDD\\nettoyage.xml");
	}
	@Test
	public void test() throws SQLException, Exception {
		driver.get("http://localhost/TutorialHtml5HotelPhp/");
		assertEquals("HTML5 Hotel Room Booking (JavaScript/PHP)", driver.getTitle());
		
		PageBooking page_booking = PageFactory.initElements(driver, PageBooking.class);
		page_booking.cel1.click();		
		
		driver.switchTo().frame(0);
		assertEquals("La popup n'est pas apparue", "New Reservation", page_booking.titre_popup.getText());
		
		OutilTechnique.remplirChamp(page_booking.champ_nom, "Resa 1");
		
		page_booking.button_save.submit();
		
		driver.switchTo().defaultContent();
		
		assertTrue(page_booking.resaCell.getText().contains("Resa 1"));
		
		BDDConnexion.compareData("reservations", "src\\test\\JDD\\reservation_room1.xml", "id");

	}
	


}
