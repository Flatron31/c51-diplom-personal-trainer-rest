package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.Mapper.SportsNutritionMapper;
import com.example.c51diplompersonaltrainerrest.dto.ListShopsDTO;
import com.example.c51diplompersonaltrainerrest.dto.SportsNutritionDTO;
import com.example.c51diplompersonaltrainerrest.entity.Shop;
import com.example.c51diplompersonaltrainerrest.entity.SportsNutrition;
import com.example.c51diplompersonaltrainerrest.exception.InvalidParametrException;
import com.example.c51diplompersonaltrainerrest.exception.NotFoundException;
import com.example.c51diplompersonaltrainerrest.repository.ShopRepository;
import com.example.c51diplompersonaltrainerrest.repository.SportsNutritionRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "Sports Nutrition", description = "Operations with the sports nutrition object")
@RequestMapping("/api/user/sports-nutrition")
public class SportsNutritionController {

    private SportsNutritionRepository sportsNutritionRepository;
    private SportsNutritionMapper sportsNutritionMapper;
    private ShopRepository shopRepository;

    public SportsNutritionController(SportsNutritionRepository sportsNutritionRepository,
                                     SportsNutritionMapper sportsNutritionMapper,
                                     ShopRepository shopRepository) {
        this.sportsNutritionRepository = sportsNutritionRepository;
        this.sportsNutritionMapper = sportsNutritionMapper;
        this.shopRepository = shopRepository;
    }

    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "405", description = "Invalid input")
    @ApiOperation(value = "Creation of a new sports nutrition facility", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping()
    public ResponseEntity<SportsNutrition> createSportsNutrition(@ApiParam(value = "New sports nutrition facility",
            name = "body sports nutrition")
                                                                 @Valid @RequestBody SportsNutritionDTO sportsNutritionDTO,
                                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidParametrException();
        }
        SportsNutrition sportsNutrition = sportsNutritionMapper.SportsNutritionDTOToSportsNutrition(sportsNutritionDTO);

        return ResponseEntity.ok(sportsNutritionRepository.save(sportsNutrition));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Removal of sports nutrition", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteSportsNutrition(@ApiParam(value = "The identifier is required to obtain a sports nutrition" +
            " object by this identifier", example = "1")
                                      @PathVariable("id") Long id) {
        if (id < 1 | sportsNutritionRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        sportsNutritionRepository.delete(sportsNutritionRepository.getById(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Change in the object of sports nutrition",
            notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SportsNutrition> updateSportsNutrition(@ApiParam(value = "The identifier is required to " +
            "obtain a sports nutrition object by this identifier for subsequent changes", example = "1")
                                                                 @PathVariable("id") Long id,
                                                                 @ApiParam(value = "Changing the object of sports nutrition",
                                                                         name = "body sports nutrition")
                                                                 @Valid @RequestBody SportsNutritionDTO sportsNutritionDTO,
                                                                 BindingResult bindingResult) {
        if (id < 1 | sportsNutritionRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidParametrException();
        }
        SportsNutrition sportsNutrition = sportsNutritionRepository.getById(id);
        List<Shop> shopList = sportsNutrition.getShopList();

        SportsNutrition updateSportsNutrition = sportsNutritionMapper.SportsNutritionDTOToSportsNutrition(sportsNutritionDTO);
        updateSportsNutrition.setId(id);
        updateSportsNutrition.setShopList(shopList);

        return ResponseEntity.ok(sportsNutritionRepository.save(updateSportsNutrition));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Obtaining a sports nutrition object by ID")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SportsNutrition> getSportsNutrition(@ApiParam(value = "The identifier is required to obtain a " +
            "sports nutrition object by this identifier", example = "1")
                                                              @PathVariable("id") Long id) {
        if (id < 0 | sportsNutritionRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        SportsNutrition sportsNutrition = sportsNutritionRepository.getById(id);

        return ResponseEntity.ok(sportsNutrition);
    }

    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "405", description = "Invalid input")
    @ApiOperation(value = "Adding stores to a sports nutrition facility", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping("/{id}")
    public ResponseEntity<SportsNutrition> addingStoreToProduct(@ApiParam(value = "The identifier is required to obtain " +
            "a sports nutrition" +
            " object by this identifier", example = "1")
                                                                @PathVariable("id") Long id,
                                                                @ApiParam(value = "List of ID stores", example = "[1,2]")
                                                                @RequestBody ListShopsDTO listShopsDTO) {
        if (id < 1 | sportsNutritionRepository.findById(id).isEmpty()) {
            throw new InvalidParametrException();
        }
        List<Long> idShops = listShopsDTO.getIdShops();

        for (Long idShop : idShops) {
            if (idShop < 1 | shopRepository.findById(idShop).isEmpty()) {
                throw new InvalidParametrException();
            }
        }
        SportsNutrition sportsNutrition = sportsNutritionRepository.getById(id);
        List<Shop> shopList = sportsNutrition.getShopList();

        for (Long idShop : idShops) {
            Shop shop = shopRepository.getById(idShop);
            shopList.add(shop);
        }
        sportsNutrition.setShopList(shopList);

        return ResponseEntity.ok(sportsNutritionRepository.save(sportsNutrition));
    }
}
