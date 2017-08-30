package br.com.caelum.agiletickets.acceptance;

import static org.openqa.selenium.support.ui.ExpectedConditions.not;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class EspetaculoTest {
	@Test
	public void espetaculoTeste(){
		System.setProperty("webdriver.gecko.driver", "geckodriver");
		FirefoxDriver driver = new FirefoxDriver();
		driver.get("http://localhost:8080/espetaculos");
		WebElement form = driver.findElement(By.id("addForm"));
		form.findElement(By.name("espetaculo.nome")).sendKeys("Rei Leão");
		form.findElement(By.name("espetaculo.descricao")).sendKeys("é um musical sobre...");
		form.findElement(By.name("espetaculo.tipo")).sendKeys("Teatro");
		form.findElement(By.name("espetaculo.estabelecimento.id")).sendKeys("Casa de shows");
		form.submit();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(not(ExpectedConditions.textToBePresentInElementValue(By.name("espetaculo.nome"), "Rei Leão")));
		WebElement linha = driver.findElement(By.cssSelector("table tbody tr:last-child"));
		List<WebElement> colunas = linha.findElements(By.cssSelector("td"));
		Assert.assertEquals("Rei Leão", colunas.get(1).getText());
		Assert.assertEquals("é um musical sobre...", colunas.get(2).getText());
		Assert.assertEquals("TEATRO", colunas.get(3).getText());
		driver.close();
		EntityManagerFactory efm = Persistence.createEntityManagerFactory("default");
		EntityManager manager = efm.createEntityManager();
		manager.getTransaction().begin();
		manager.createNativeQuery("delete from Espetaculo where nome = 'Rei Leão'").executeUpdate();
		manager.getTransaction().commit();	
		
	}

	@Test
	public void testeCamposEmBranco() {
		System.setProperty("webdriver.gecko.driver", "geckodriver");
		FirefoxDriver driver = new FirefoxDriver();
		driver.get("http://localhost:8080/espetaculos");
		WebElement form = driver.findElement(By.id("addForm"));
;
		form.findElement(By.name("espetaculo.tipo")).sendKeys("Teatro");
		form.findElement(By.name("espetaculo.estabelecimento.id")).sendKeys("Casa de shows");
		form.submit();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(not(ExpectedConditions.textToBePresentInElementValue(By.name("espetaculo.nome"), "Rei Leão")));
		WebElement lista = driver.findElement(By.id("errors"));
		List<WebElement> linhas = lista.findElements(By.cssSelector("li"));
		Assert.assertEquals("Nome do espetáculo não pode estar em branco", linhas.get(0).getText());
		Assert.assertEquals("Descrição do espetáculo não pode estar em branco", linhas.get(1).getText());
		driver.close();		
		
		
	}
	
}
