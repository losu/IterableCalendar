package gft.ddba.calendar.controller;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileControllerTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturnHttpStatusOkForAddFileRequest() {
		ResponseEntity<String> response = this.restTemplate.postForEntity("/app/addFile", folder.getRoot().getAbsolutePath() + "/newFile", String.class);
		Assert.assertThat(response.getStatusCode(), Matchers.is(HttpStatus.OK));
	}

	@Test
	public void shouldCreateNewFile() {
		ResponseEntity<String> response = this.restTemplate.postForEntity("/app/addFile", folder.getRoot().getAbsolutePath() + "/newFile", String.class);
		Assert.assertTrue(response.getBody().contains("newFile created"));
	}

	@Test
	public void shouldNotCreateAlreadyExisedFile() throws IOException {
		folder.newFile("newFile");
		ResponseEntity<String> response = this.restTemplate.postForEntity("/app/addFile", folder.getRoot().getAbsolutePath() + "/newFile", String.class);
		Assert.assertTrue(response.getBody().contains("Can not be created file"));
	}

	@Test
	public void shouldCreateNewFileByGetRequest() throws IOException {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/app/addFile?path=" + folder.getRoot().getAbsolutePath() + "/newFile", String.class);
		Assert.assertTrue(response.getBody().contains("newFile created"));
	}

	@Test
	public void shouldNotCreateNewFileByGetRequest() throws IOException {
		folder.newFile("newFile");
		ResponseEntity<String> response = this.restTemplate.postForEntity("/app/addFile?path=", folder.getRoot().getAbsolutePath() + "/newFile", String.class);
		Assert.assertTrue(response.getBody().contains("Can not be created file"));
	}

	@Test
	public void start() {
		ResponseEntity<String> response = this.restTemplate.postForEntity("/gs-guide-websocket", folder.getRoot().getAbsolutePath(), String.class);
		ResponseEntity<String> response1 = this.restTemplate.postForEntity("/app/start", folder.getRoot().getAbsolutePath(), String.class);
	}
}
