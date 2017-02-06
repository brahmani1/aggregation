package com.textrecruit.product;

public enum Constants {
	AGGREGATIONURI(
			"https://gist.githubusercontent.com/jed204/92f90060d0fabf65792d6d479c45f31c/raw/346c44a23762749ede009623db37f4263e94ef2a/java2.json");

	private final String uri;

	Constants(final String uri) {
		this.uri = uri;
	}

	public String getURI() {
		return this.uri;
	}
}
