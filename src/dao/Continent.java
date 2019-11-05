package dao;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains details of the continent
 * 
 * @author Mitalee Naik
 * 
 */

public class Continent {
	/**
	 * To store the name of the continent
	 */
	private String name;
	/**
	 * To store the continent value
	 */
	private int continentValue;
	/**
	 * To store the name of the owner of the conitnent.
	 */
	private String owner;
	/**
	 * This method gives the owner
	 * @return
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * This variable stores whether to assign no of armies(which is equal to the continent value) to the player in reinforcement phase .
	 */
	private int assignArmy=0;
	/**
	 * This method is used to get the value of assignArmy
	 * @return
	 */
	public int getAssignArmy() {
		return assignArmy;
	}
	/**
	 * This method is used to set the value of assignArmy
	 * @param assignArmy
	 */
	public void setAssignArmy(int assignArmy) {
		this.assignArmy = assignArmy;
	}

	/**
	 *  This method sets the owner
	 * @param owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * TO store list of countries in the continent
	 */
	private List<String> countries;
	/**
	 * To store the bridges between the continents
	 */
	private List<Bridge> bridges;
	/**
	 * To store index of the continent in the list of continents in map
	 */
	private int continentIndexInListOfContinent;

	/**
	 * Constructor initializes list of bridges and countries
	 */
	public Continent() {
		this.countries = new ArrayList<String>();
		this.bridges = new ArrayList<Bridge>();
		this.setAssignArmy(0);
		this.setOwner("FREE CONTINENTS");
	}

	/**
	 * Copy Constructor
	 * 
	 * @param c Continent Object
	 */
	public Continent(Continent c) {
		this.name = c.name;
		this.continentValue = c.continentValue;
		this.countries = new ArrayList<String>(c.getCountries());
		this.bridges = new ArrayList<Bridge>();
		for (Bridge b : c.getBridges()) {
			Bridge newBridge = new Bridge(b);
			this.bridges.add(newBridge);
		}
	}

	/**
	 * This method gives the list of bridges
	 * 
	 * @return Bridges List
	 */
	public List<Bridge> getBridges() {
		return bridges;
	}

	/**
	 * This method sets the list of bridges
	 * 
	 * @param bridges Bridges List
	 */
	public void setBridges(List<Bridge> bridges) {
		this.bridges = bridges;
	}

	/**
	 * This method returns index of the continent in the Continent List
	 * 
	 * @return Continent Index
	 */
	public int getContinentIndexInListOfContinent() {
		return continentIndexInListOfContinent;
	}

	/**
	 * This method sets index of the continent in the Continent List
	 * 
	 * @param continentIndexInListOfContinent Continent Index
	 */
	public void setContinentIndexInListOfContinent(int continentIndexInListOfContinent) {
		this.continentIndexInListOfContinent = continentIndexInListOfContinent;
	}

	/**
	 * This method returns the name of the continent
	 * 
	 * @return Continent Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method sets the name of the continent
	 * 
	 * @param name Continent Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method returns the continent value of the continent
	 * 
	 * @return Continent Value
	 */
	public int getContinentValue() {
		return continentValue;
	}

	/**
	 * This method sets the continent value of the continent
	 * 
	 * @param continentValue Continent Value
	 */
	public void setContinentValue(int continentValue) {
		this.continentValue = continentValue;
	}

	/**
	 * This method returns the countries of the continent
	 * 
	 * @return List of Countries belonging to the Continent
	 */
	public List<String> getCountries() {
		return countries;
	}

	/**
	 * This method sets the countries of the continent
	 * 
	 * @param countries List of Countries belonging to the Continent
	 */
	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

}
