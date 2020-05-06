package highestLikedTweet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class HighestLikedTweet1 {

	// Declaring global variables:
	ChromeOptions options;
	WebDriver driver;
	JavascriptExecutor jse;
	Logger logger = LogManager.getLogger(HighestLikedTweet1.class);
	WebDriverWait wait;

	static HighestLikedTweet1 h;
	int highLikedTweetNo;

	// Configure Driver:
	public void configureDriver() throws IOException {
		// Kill previous Chrome processes:
		Runtime.getRuntime().exec("Taskkill /F /IM chromedriver_80.exe /T");
		System.setProperty("webdriver.chrome.driver", "F:\\SOFTWARES\\chromedriver_80.exe");

		// Set Chrome options:
		this.options = new ChromeOptions();
		this.options.addArguments("start-maximized");
		this.options.addArguments("disable-infobars");
		this.options.addArguments("--disable-extensions");
		this.options.addArguments("--disable-notifications");
		this.options.setExperimentalOption("useAutomationExtension", false);

		this.driver = new ChromeDriver(options);
		this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		this.driver.get("https://twitter.com/stepin_forum?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor");
		this.wait = new WebDriverWait(this.driver, 20);
	}

	@When("I navigate to the STeP-IN Forum page")
	public void i_navigate_to_the_STeP_IN_Forum_page() throws IOException {
		h = new HighestLikedTweet1();
		h.configureDriver();
	}

	@When("I scroll through the top {int} tweets and find the no. of likes for each tweet")
	public void i_scroll_through_the_top_tweets_and_find_the_no_of_likes_for_each_tweet(Integer nofTweets)
			throws InterruptedException {

		jse = (JavascriptExecutor) h.driver;
		int max = 0;
		int nofLikes;
		// Scrolling to the account name at the start of the tweet:
		for (int i = 1; i <= nofTweets; i++) {

			WebElement nextTweet = h.wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@aria-label=\"Timeline: STeP-IN Forum’s Tweets\"]/div/div[" + i
							+ "]/descendant::div[@class=\"css-1dbjc4n r-1iusvr4 r-16y2uox r-1777fci r-5f2r5o r-1mi0q7o\"]/div[1]")));
			jse.executeScript("arguments[0].scrollIntoView(true);", nextTweet);

			// Navigating through each tweet and it's no.of likes, selecting the index of
			// the highest liked tweet:
			WebElement tweet = h.driver
					.findElement(By.xpath("//div[@aria-label=\"Timeline: STeP-IN Forum’s Tweets\"]/div/child::div[" + i
							+ "]/descendant::div[@role=\"group\"]/child::div[3]/descendant::span[@class=\"css-901oao css-16my406 r-1qd0xha r-ad9z0x r-bcqeeo r-qvutc0\"]"));
			nofLikes = Integer.parseInt(tweet.getText());
			if (nofLikes > max) {
				max = nofLikes;
				h.highLikedTweetNo = i;
			}
			logger.info("tweet[" + i + "] likes: " + nofLikes);
			Thread.sleep(5000);
		}
		logger.info("Highest likes for a tweet" + max);
	}

	@Then("I should find and display the highest liked tweets msg.")
	public void i_should_find_and_display_the_highest_liked_tweets_msg() {
		WebElement tweetMsg;
		int i = 1;

		// Moving to each part of the msg in every span and printing it until there is
		// no msg left:
		do {
			tweetMsg = h.driver.findElement(By.xpath(
					"//div[@aria-label=\"Timeline: STeP-IN Forum’s Tweets\"]/div/child::div[" + h.highLikedTweetNo
							+ "]/descendant::div[@class=\"css-901oao r-hkyrab r-1qd0xha r-a023e6 r-16dba41 r-ad9z0x r-bcqeeo r-bnwqim r-qvutc0\"]/descendant::span["
							+ i + "]"));
			i++;
			logger.info(tweetMsg.getText());
		} while (tweetMsg.getText() != null);

		h.driver.quit();
	}

	@Test
	public void findHighestTweetThroughApi() {
		// The base URI to be used
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1/";

		// Define the specification of request. Server is specified by baseURI above.
		RequestSpecification httpRequest = RestAssured.given();

		// Makes calls to the server using Method type.
		Response response = httpRequest.request(Method.GET, "employees");

		// Checks the Status Code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

	}

}
