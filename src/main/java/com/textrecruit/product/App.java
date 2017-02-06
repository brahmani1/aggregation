package com.textrecruit.product;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jersey.api.client.ClientResponse;
import com.textrecruit.util.Config;

public class App {
	public static void main(String[] args) {
		try {
			RestClient restClient = new RestClient();
			ClientResponse response = restClient
					.getResponse("application/json");
			JsonNode root = restClient.getRootNode(response);
			String retsultJson = getResultNodes(root);
			writeJsonToFile(retsultJson);
			System.out.println("Result are written in file : "+Config.getSharedProperties("outputfilepath"));
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param retsultJson
	 * @throws FileNotFoundException
	 */
	private static void writeJsonToFile(String retsultJson)
			throws FileNotFoundException {
		try (PrintWriter out = new PrintWriter(new FileOutputStream(
				Config.getSharedProperties("outputfilepath"), false))) {
			out.print(retsultJson);
		}
	}

	/**
	 * @param root
	 */
	@SuppressWarnings("deprecation")
	private static String getResultNodes(JsonNode root) {

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode jsonObject = objectMapper.createObjectNode();

		// iterate for root nodes
		Iterator<Entry<String, JsonNode>> itr = root.fields();

		while (itr.hasNext()) {
			Entry<String, JsonNode> map = itr.next();
			jsonObject.put(map.getKey(), getTotals(map));
		}
		// System.out.println("result"+jsonObject);
		return jsonObject.toString();
	}

	/**
	 * @param map is collection of c and it's child json nodes
	 */
	private static JsonNode getTotals(Entry<String, JsonNode> map) {

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode totalJson = objectMapper.createObjectNode();

		Iterator<JsonNode> data = map.getValue().iterator();
		int sendTotal = 0;
		int recvTotal = 0;
		while (data.hasNext()) {
			JsonNode mNode = data.next();
			sendTotal += mNode.get("sent").asInt();
			recvTotal += mNode.get("recv").asInt();
		}
		totalJson.put("sentTotal", sendTotal);
		totalJson.put("recvTotal", recvTotal);
		// System.out.println(totalJson);

		return totalJson;
	}
}
