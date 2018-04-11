package com.worldfirst.featuretoggle;

import com.worldfirst.featuretoggle.http.contract.CreateFeatureRequest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-tests")
public class FeatureToggleApplicationIT {

	@LocalServerPort
	int port;

	String base;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/api/features").toString();
	}

	@Test
	public void createFeatureProperly() {
		// Given
		final String FEATURE_ID = "testId";
		final String FEATURE_DESCRIPTION = "test description";

		CreateFeatureRequest request = new CreateFeatureRequest(FEATURE_ID, FEATURE_DESCRIPTION);

		// When
		ResponseEntity<String> response = template.postForEntity(base, request, String.class);

		// Then
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

}
