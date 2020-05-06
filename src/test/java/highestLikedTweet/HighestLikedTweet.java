package highestLikedTweet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HighestLikedTweet {
	
	//Declaring global variables:
	ChromeOptions options;
	WebDriver driver;
	JavascriptExecutor jse;
	Logger logger = LogManager.getLogger(HighestLikedTweet.class);
	
	static HighestLikedTweet h;
	int highLikeTweetNo;
	
	//Configure Driver:
	public void configureDriver() throws IOException {
		
		//Kill previous Chrome processes:
		Runtime.getRuntime().exec("Taskkill /F /IM chromedriver_80.exe /T");
		System.setProperty("webdriver.chrome.driver", "F:\\SOFTWARES\\chromedriver_80.exe");
		
		//Set Chrome options:
		options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-notifications");
		options.setExperimentalOption("useAutomationExtension", false);
		
		this.driver = new ChromeDriver(options);
		this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		this.driver.get("https://twitter.com/stepin_forum?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor");
	}
	

	@When("I navigate to STeP-IN Forum twitter page")
	public void i_navigate_to_step_in_forum_twitter_page() throws IOException {
		h = new HighestLikedTweet();
	    h.configureDriver();
//	    for(int i=0; i<nofTweets; i++) {
//		    WebElement nextTweet = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div/div/div/div[2]/section/div/div/div[" +nofTweets + "]"));
//		    jse.executeScript("arguments[0].scrollIntoView(true);", nextTweet);
//	    }
	}

	@When("find the number of likes for each tweet")
	public void find_the_number_of_likes_for_each_tweet() throws InterruptedException {
		
		Thread.sleep(10000);
		List<WebElement> tweets = h.driver.findElements(By.xpath("//div[@class=\"css-1dbjc4n r-xoduu5 r-1udh08x\"]/span/span"));
		int max = 0;
		
	    for(int i=1; i< tweets.size(); i+=2) {
	    	WebElement like = tweets.get(i);
	    	int nofLikes = Integer.parseInt(like.getText());
	    	if(nofLikes > max) {
	    		max = nofLikes;
	    		h.highLikeTweetNo = i;
	    	}
	    }
	    
	    
	}

	@Then("I should print a message indicating the tweet that has highest likes.")
	public void i_should_print_a_message_indicating_the_tweet_that_has_highest_likes() {
		
		List<WebElement> tweets = h.driver.findElements(By.xpath("//div[@class=\"css-901oao r-hkyrab r-1qd0xha r-a023e6 r-16dba41 r-ad9z0x r-bcqeeo r-bnwqim r-qvutc0\"]/span[3]"));
		
		int index = (h.highLikeTweetNo/2);
		WebElement highLikeTweet = tweets.get(index);
		String highLikeTweetMsg = highLikeTweet.getText();
		logger.info(highLikeTweetMsg);
		h.driver.quit();
	}
}
