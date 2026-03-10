package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void createAssignsIdWhenNull() {
        Car car = createCar(null, "Civic", "Blue", 2);

        Car created = carRepository.create(car);

        assertNotNull(created.getCarID());
        assertEquals("Civic", created.getCarName());
    }

    @Test
    void createKeepsProvidedId() {
        UUID id = UUID.randomUUID();
        Car car = createCar(id, "Jazz", "White", 1);

        Car created = carRepository.create(car);

        assertEquals(id, created.getCarID());
    }

    @Test
    void findAllReturnsAllCars() {
        Car first = carRepository.create(createCar(UUID.randomUUID(), "A", "Red", 1));
        Car second = carRepository.create(createCar(UUID.randomUUID(), "B", "Green", 2));

        List<Car> cars = carRepository.findAll();

        assertEquals(2, cars.size());
        assertEquals(first, cars.get(0));
        assertEquals(second, cars.get(1));
    }

    @Test
    void findByIdReturnsCarOrNull() {
        Car car = carRepository.create(createCar(UUID.randomUUID(), "Camry", "Gray", 3));

        Car found = carRepository.findById(car.getCarID());
        Car missing = carRepository.findById(UUID.randomUUID());

        assertEquals(car, found);
        assertNull(missing);
    }

    @Test
    void updateExistingCarChangesFields() {
        UUID id = UUID.randomUUID();
        carRepository.create(createCar(id, "Old", "Black", 1));

        Car updatedData = createCar(id, "New", "Silver", 7);
        Car updated = carRepository.update(id, updatedData);

        assertNotNull(updated);
        assertEquals("New", updated.getCarName());
        assertEquals("Silver", updated.getCarColor());
        assertEquals(7, updated.getCarQuantity());
    }

    @Test
    void updateMissingCarReturnsNull() {
        Car updated = carRepository.update(UUID.randomUUID(), createCar(null, "Ghost", "White", 1));
        assertNull(updated);
    }

    @Test
    void deleteRemovesCar() {
        Car car = carRepository.create(createCar(UUID.randomUUID(), "DeleteMe", "Orange", 2));

        carRepository.delete(car.getCarID());

        assertNull(carRepository.findById(car.getCarID()));
    }

    private Car createCar(UUID id, String name, String color, int quantity) {
        Car car = new Car();
        car.setCarID(id);
        car.setCarName(name);
        car.setCarColor(color);
        car.setCarQuantity(quantity);
        return car;
    }
}
