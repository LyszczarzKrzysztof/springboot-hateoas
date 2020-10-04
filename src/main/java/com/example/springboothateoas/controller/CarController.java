package com.example.springboothateoas.controller;

import com.example.springboothateoas.model.Car;
import com.example.springboothateoas.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/cars", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<EntityModel> getCars() {
        List<Car> allCars = carService.getAllCars();
        allCars.forEach(car -> {
            car.add(linkTo(methodOn(CarController.class).getCarById(car.getCarId())).withSelfRel());
        });
        allCars.forEach(car -> car.add(linkTo(methodOn(CarController.class).getCarsByColor(car.getColor().toString())).withSelfRel()));
        Link link = linkTo(CarController.class).withSelfRel();
        CollectionModel<Car> carEntityModels = CollectionModel.of(allCars,link);
        return new ResponseEntity(carEntityModels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> carById = carService.getCarById(id);
        Link link = linkTo(CarController.class).slash(id).withSelfRel();
        Link linktoAll = linkTo(CarController.class).withSelfRel();
        EntityModel<Optional<Car>> carEntityModel = EntityModel.of(carById, link, linktoAll);
        return new ResponseEntity(carEntityModel, HttpStatus.OK);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<EntityModel> getCarsByColor(@PathVariable String color) {
        List<Car> carList = carService.getCarByColor(color);
        carList.forEach(car -> {
            car.add(linkTo(methodOn(CarController.class).getCarById(car.getCarId())).withSelfRel());
        });
        carList.forEach(car -> car.add(linkTo(methodOn(CarController.class).getCarsByColor(car.getColor().toString())).withSelfRel()));
        Link link = linkTo(CarController.class).withSelfRel();
        CollectionModel<Car> carEntityModels = CollectionModel.of(carList,link);
        return new ResponseEntity(carEntityModels, HttpStatus.OK);
    }

}
