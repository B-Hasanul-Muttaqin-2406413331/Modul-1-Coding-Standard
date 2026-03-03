package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Car extends Product {
    private String carColor;

    public UUID getCarID() {
        return getProductId();
    }

    public void setCarID(UUID carID) {
        setProductId(carID);
    }

    public String getCarName() {
        return getProductName();
    }

    public void setCarName(String carName) {
        setProductName(carName);
    }

    public int getCarQuantity() {
        return getProductQuantity();
    }

    public void setCarQuantity(int carQuantity) {
        setProductQuantity(carQuantity);
    }
}
