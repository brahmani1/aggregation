package com.textrecruit.product;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class RestClient {
		
	public ClientResponse getResponse(String contentType) throws Throwable {
		
		Client client = Client.create();
		
		WebResource webResource = client.resource(Constants.AGGREGATIONURI.getURI());
		ClientResponse response = webResource.accept(contentType).get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
		return response;
	}
	
	public JsonNode getRootNode(ClientResponse response) {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = objectMapper.readTree(response.getEntity(String.class));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (ClientHandlerException e) {
			e.printStackTrace();
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return root;
	}
}
