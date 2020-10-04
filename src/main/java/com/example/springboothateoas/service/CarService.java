package com.example.springboothateoas.service;

import com.example.springboothateoas.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getAllCars();
    Optional<Car> getCarById(Long id);
    List<Car> getCarByColor(String color);
}
