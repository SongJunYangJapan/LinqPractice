package linq.domain;

public class Product
{
	public Product(int productID, String productName, String category, double unitPrice, int unitsInStock) {
		this.productID = productID;
		this.productName = productName;
		this.category = category;
		this.unitPrice = unitPrice;
		this.unitsInStock = unitsInStock;
	}

	public final int productID;
	public final String productName;
	public final String category;
	public final double unitPrice;
	public final int unitsInStock;


	public int getProductID() {
		return productID;
	}

	public String getProductName() {
		return productName;
	}

	public String getCategory() {
		return category;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public int getUnitsInStock() {
		return unitsInStock;
	}

	@Override
	public String toString() {
		return "{" +
				"productID:" + productID +
				", productName:" + productName  +
				", category:" + category  +
				", unitPrice:" + unitPrice +
				", unitsInStock:" + unitsInStock +
				'}';
	}
}
