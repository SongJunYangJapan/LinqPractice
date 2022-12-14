package linq.domain;

import java.util.List;

public class Customer {
	public final String customerID;
	public final String companyName;
	public final String address;
	public final String city;
	public final String region;
	public final String postalCode;
	public final String country;
	public final String phone;
	public final String fax;
	public final List<Order> orders;

	public Customer(String customerID, String companyName, String address, String city, String region,
					String postalCode, String country, String phone, String fax, List<Order> orders) {
		this.customerID = customerID;
		this.companyName = companyName;
		this.address = address;
		this.city = city;
		this.region = region;
		this.postalCode = postalCode;
		this.country = country;
		this.phone = phone;
		this.fax = fax;
		this.orders = orders;
	}

	public String getCustomerID() {
		return customerID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getRegion() {
		return region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCountry() {
		return country;
	}

	public String getPhone() {
		return phone;
	}

	public String getFax() {
		return fax;
	}

	public List<Order> getOrders() {
		return orders;
	}

	@Override
	public String toString() {
		return "{" +
				"customerID:'" + customerID + '\'' +
				", companyName:'" + companyName + '\'' +
				", address:'" + address + '\'' +
				", city:'" + city + '\'' +
				", region:'" + region + '\'' +
				", postalCode:'" + postalCode + '\'' +
				", country:'" + country + '\'' +
				", phone:'" + phone + '\'' +
				", fax:'" + fax + '\'' +
				", orders:" + (orders == null ? 0 : orders.size()) +
				'}';
	}
}
