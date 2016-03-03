package com.primes.tech.domain;


/**
 * Enumeration detailing the Execution
 * There are 2 modes available: Sequential and Parallel
 *  
 * @author cyrille.kabadjeu
 */
public enum ExecutionModeEnum {
	
	SEQUENTIAL("sequential"),
	PARALLEL("parallel");
	
	private String value;

	ExecutionModeEnum(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	public static ExecutionModeEnum fromValue(String v) {
		for (ExecutionModeEnum executionMode : ExecutionModeEnum.values()) {
			if (executionMode.value.equalsIgnoreCase(v)) {
				return executionMode;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
