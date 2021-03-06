package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.mapper.SportsNutritionMapper;
import com.example.c51diplompersonaltrainerrest.dto.ListShopsDTO;
import com.example.c51diplompersonaltrainerrest.dto.SportsNutritionDTO;
import com.example.c51diplompersonaltrainerrest.entity.Shop;
import com.example.c51diplompersonaltrainerrest.entity.SportsNutrition;
import com.example.c51diplompersonaltrainerrest.exception.InvalidParametrException;
import com.example.c51diplompersonaltrainerrest.exception.NotFoundException;
import com.example.c51diplompersonaltrainerrest.repository.ShopRepository;
import com.example.c51diplompersonaltrainerrest.repository.SportsNutritionRepository;
import com.example.c51diplompersonaltrainerrest.validation.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "Sports Nutrition", description = "Operations with the sports nutrition object")
@RequestMapping("/api/user/sports-nutrition")
public class SportsNutritionController {

    private final SportsNutritionRepository sportsNutritionRepository;
    private final SportsNutritionMapper sportsNutritionMapper;
    private final ShopRepository shopRepository;
    private final Validator validator;

    public SportsNutritionController(SportsNutritionRepository sportsNutritionRepository,
                                     SportsNutritionMapper sportsNutritionMapper,
                                     ShopRepository shopRepository,
                                     Validator validator) {
        this.sportsNutritionRepository = sportsNutritionRepository;
        this.sportsNutritionMapper = sportsNutritionMapper;
        this.shopRepository = shopRepository;
        this.validator = validator;
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Creation of a new sports nutrition", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping()
    public ResponseEntity<SportsNutrition> createSportsNutrition(@ApiParam(value = "New sports nutrition facility",
            example = "sportsNutritionDTO")
                                                                 @Valid @RequestBody SportsNutritionDTO sportsNutritionDTO,
                                                                 BindingResult bindingResult) {
       validator.validate(bindingResult);

        SportsNutrition sportsNutrition = sportsNutritionMapper.SportsNutritionDTOToSportsNutrition(sportsNutritionDTO);

        log.info("New sportsNutrition {} added", sportsNutritionDTO.getName());

        return ResponseEntity.ok(sportsNutritionRepository.save(sportsNutrition));
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
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
                                                                 example = "sportsNutritionDTO")
                                                                 @Valid @RequestBody SportsNutritionDTO sportsNutritionDTO,
                                                                 BindingResult bindingResult) {
       validator.validateSportsNutritionId(id);
       validator.validate(bindingResult);

        SportsNutrition sportsNutrition = sportsNutritionRepository.getById(id);
        List<Shop> shopList = sportsNutrition.getShopList();

        SportsNutrition updateSportsNutrition = sportsNutritionMapper.SportsNutritionDTOToSportsNutrition(sportsNutritionDTO);
        updateSportsNutrition.setId(id);
        updateSportsNutrition.setShopList(shopList);

        return ResponseEntity.ok(sportsNutritionRepository.save(updateSportsNutrition));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Obtaining a sports nutrition object by ID",
            notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SportsNutrition> getSportsNutrition(@ApiParam(value = "The identifier is required to obtain a " +
            "sports nutrition object by this identifier", example = "1")
                                                              @PathVariable("id") Long id) {
        validator.validateSportsNutritionId(id);

        return ResponseEntity.ok(sportsNutritionRepository.getById(id));
    }


//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Adding stores to a sports nutrition facility", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping("/{id}")
    public ResponseEntity<SportsNutrition> addingStoreToProduct(@ApiParam(value = "The identifier is required to obtain " +
            "a sports nutrition" +
            " object by this identifier", example = "1")
                                                                @PathVariable("id") Long id,
                                                                @ApiParam(value = "List of ID stores", example = "[1,2]")
                                                                @RequestBody ListShopsDTO listShopsDTO) {
        validator.validateSportsNutritionId(id);

        List<Long> idShops = listShopsDTO.getIdShops();

        for (Long idShop : idShops) {
            validator.validateShopId(idShop);
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


    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Removal of sports nutrition", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteSportsNutrition(@ApiParam(value = "The identifier is required to obtain a sports nutrition" +
            " object by this identifier", example = "1")
                                      @PathVariable("id") Long id) {
        validator.validateSportsNutritionId(id);

        sportsNutritionRepository.delete(sportsNutritionRepository.getById(id));
    }
}
