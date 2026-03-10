package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarServiceImplTest {

    private CarRepositoryInterface carRepository;
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        carRepository = mock(CarRepositoryInterface.class);
        carService = new CarServiceImpl();
        ReflectionTestUtils.setField(carService, "carRepository", carRepository);
    }

    @Test
    void createDelegatesToRepository() {
        Car car = createCar("Ayla", "Blue", 2);

        Car result = carService.create(car);

        verify(carRepository).create(car);
        assertSame(car, result);
    }

    @Test
    void findAllDelegatesToRepository() {
        List<Car> expected = List.of(
                createCar("A", "Red", 1),
                createCar("B", "Green", 3)
        );
        when(carRepository.findAll()).thenReturn(expected);

        List<Car> actual = carService.findAll();

        verify(carRepository).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findByIdDelegatesToRepository() {
        UUID id = UUID.randomUUID();
        Car car = createCar("City", "White", 4);
        car.setCarID(id);
        when(carRepository.findById(id)).thenReturn(car);

        Car actual = carService.findById(id);

        verify(carRepository).findById(id);
        assertEquals(car, actual);
    }

    @Test
    void updateDelegatesToRepository() {
        UUID id = UUID.randomUUID();
        Car car = createCar("Brio", "Black", 1);
        when(carRepository.update(id, car)).thenReturn(car);

        Car actual = carService.Update(id, car);

        verify(carRepository).update(id, car);
        assertSame(car, actual);
    }

    @Test
    void deleteDelegatesToRepository() {
        UUID id = UUID.randomUUID();

        carService.deleteByCarId(id);

        verify(carRepository).delete(id);
    }

    private Car createCar(String name, String color, int quantity) {
        Car car = new Car();
        car.setCarID(UUID.randomUUID());
        car.setCarName(name);
        car.setCarColor(color);
        car.setCarQuantity(quantity);
        return car;
    }
}
