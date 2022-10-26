package models;

public class Order implements Comparable<Order>  {
    public String customerSurname;
    public String customerName;
    public String productID;
    public int productAmount;

    public Order(String customerSurname, String customerName, String productID, int productAmount) {
        this.customerSurname = customerSurname;
        this.customerName = customerName;
        this.productID = productID;
        this.productAmount = productAmount;
    }


    @Override
    public int compareTo(Order o) {
        if (productAmount > o.productAmount) {
            return 1;
        } else if (productAmount == o.productAmount) {
            int surnameCompare = customerSurname.compareTo(o.customerSurname);
            if (surnameCompare < 0) {
                return 1;
            } else if (surnameCompare == 0 && customerName.compareTo(o.customerName) < 0) {
                return 1;
            }
        }

        return equals(o) ? 0 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Order))
            return false;

        Order order = (Order)o;
        return customerName.equals(order.customerName) &&
                customerSurname.equals(order.customerSurname) &&
                productAmount == order.productAmount &&
                productID.equals(order.productID);
    }
}
