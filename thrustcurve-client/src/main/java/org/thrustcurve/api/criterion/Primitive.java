package org.thrustcurve.api.criterion;


/**
 * This class stores the element name and value of the search criterion passed in the search-request.
 */
public class Primitive
{
	
	private String element;
	private String value;

	public Primitive(String element, String value) {
		this.element= element;
		setValue(value);
	}

	public int hashCode()
	{
		return element.hashCode();
	}

	public boolean equals(Object o)
	{
		if (o instanceof Primitive)
		{
			Primitive rhs;

			rhs = (Primitive) o;
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

	@Override
	public String toString() {
		return "("+ getName() +"="+ getValue() +")";
	}
};