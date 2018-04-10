package org.thrustcurve.api;


/**
 * This class stores the element name and value of the search criterion passed in the search-request.
 */
public class Criterion
{
	
	private String  element;
	private String value;

	public Criterion(String element) {
		this(element, null);
	}
	
	public Criterion(String element, String value) {
		this.element= element;
		setValue(value);
	}

	public int hashCode()
	{
		return element.hashCode();
	}

	public boolean equals(Object o)
	{
		if (o instanceof Criterion)
		{
			Criterion rhs;

			rhs = (Criterion) o;
			return element.equals(rhs.element);
		}
		else
			return false;
	}

	public String getName() {
		return element;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
};