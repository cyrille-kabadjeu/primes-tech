package com.primes.tech.domain;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This is a wrapper class to hold 
 * a list of Prime Numbers up to an Initial Number  
 * 
 * @author cyrille.kabadjeu
 */
@XmlRootElement(name = "primeNumbers")
@XmlType(propOrder = {"initial", "primes"})
public class PrimeNumbers implements Serializable{
	
	private static final long serialVersionUID = 2381367706197185158L;

	/**
	 * The initial number provided 
	 */
	private int initial;
	
	/**
	 * The set of prime numbers up to the number provided 
	 */
	private Set<Integer> primes = new TreeSet<Integer>();

	public PrimeNumbers() {
		super();
	}

	public PrimeNumbers(int initial, Set<Integer> primes) {
		super();
		this.initial = initial;
		this.primes = primes;
	}

	@XmlElement(name="initial")
	public int getInitial() {
		return initial;
	}

	public void setInitial(int initial) {
		this.initial = initial;
	}

	public Set<Integer> getPrimes() {
		return primes;
	}

	@XmlElement(name="primes")
	public void setPrimes(Set<Integer> primes) {
		this.primes = primes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + initial;
		result = prime * result
				+ ((primes == null) ? 0 : primes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrimeNumbers other = (PrimeNumbers) obj;
		if (initial != other.initial)
			return false;
		if (primes == null) {
			if (other.primes != null)
				return false;
		} else if (!primes.equals(other.primes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PrimeNumbers [initial=" + initial + ", primes=" + primes
				+ "]";
	}

	

}
