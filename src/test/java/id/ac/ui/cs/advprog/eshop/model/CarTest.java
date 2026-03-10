package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {

    @Test
    void carAliasGettersAndSettersWorkAsExpected() {
        Car car = new Car();
        UUID carId = UUID.randomUUID();

        car.setCarID(carId);
        car.setCarName("Kijang");
        car.setCarColor("Black");
        car.setCarQuantity(4);

        assertEquals(carId, car.getCarID());
        assertEquals("Kijang", car.getCarName());
        assertEquals("Black", car.getCarColor());
        assertEquals(4, car.getCarQuantity());

        assertEquals(carId, car.getProductId());
        assertEquals("Kijang", car.getProductName());
        assertEquals(4, car.getProductQuantity());
    }
}
