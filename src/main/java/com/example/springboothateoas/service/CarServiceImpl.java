package com.example.springboothateoas.service;

import com.example.springboothateoas.model.Car;
import com.example.springboothateoas.model.Color;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Override
    public List<Car> getAllCars() {
        return createListOfCars();
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        return createListOfCars()
                .stream()
                .filter(car -> car.getCarId() == id)
                .findFirst();
    }

    @Override
    public List<Car> getCarByColor(String color) {
        return createListOfCars()
                .stream()
                .filter(car -> car.getColor().name().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    private List<Car> createListOfCars() {
        List<Car> carList = new ArrayList<>();
        carList.add(new Car(1L,"Mercedes","GLC 500", Color.NAVY_BLUE));
        carList.add(new Car(2L,"Maserati","Quatroporte", Color.MARINE));
        carList.add(new Car(3L,"ALfa Romeo","Giulia", Color.RED));
        carList.add(new Car(4L,"Mercedes","EQC", Color.BLUE));
        carList.add(new Car(5L,"Ferrari","California", Color.BLACK));
        carList.add(new Car(6L,"Fiat","126p", Color.BLACK));
        carList.add(new Car(7L,"Bugatti","La Voiture Noire", Color.WHITE));
        return carList;
    }
}
