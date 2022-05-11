package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.mapper.ShopMapper;
import com.example.c51diplompersonaltrainerrest.dto.ShopDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "Shop", description = "Store object operations")
@RequestMapping("/api/user/shop")
public class ShopController {

    private ShopRepository shopRepository;
    private SportsNutritionRepository sportsNutritionRepository;
    private ShopMapper shopMapper;

    public ShopController(ShopRepository shopRepository, SportsNutritionRepository sportsNutritionRepository,
                          ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.sportsNutritionRepository = sportsNutritionRepository;
        this.shopMapper = shopMapper;
    }

    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "405", description = "Invalid input")
    @ApiOperation(value = "Creating a new shop", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping()
    public ResponseEntity<Shop> createShop(@ApiParam(value = "New object shop", example = "shopDTO")
                                           @Valid @RequestBody ShopDTO shopDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("New exercise not added");
            throw new InvalidParametrException();
        }
        Shop shop = shopMapper.shopDTOToShop(shopDTO);
        Shop saveShop = shopRepository.save(shop);

        log.info("New shop {} added", shopDTO.getName());

        return ResponseEntity.ok(saveShop);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Getting all stores with id", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Shop>> getAllShops() {
        List<Shop> shopList = shopRepository.findAll();

        return ResponseEntity.ok(shopList);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Getting a store object by id", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Shop> getShop(@ApiParam(value = "This id is required to get the store object " +
            "under the given id", example = "1")
                                        @PathVariable("id") Long id) {
        if (id < 0 | shopRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        Shop shop = shopRepository.getById(id);

        return ResponseEntity.ok(shop);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Getting a store object by id",
            notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteShop(@ApiParam(value = "This id is required to get the store object " +
            "under the given id", example = "1")
                           @PathVariable("id") Long id) {
        if (id < 0 | shopRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        Shop shop = shopRepository.getById(id);
        shopRepository.delete(shop);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Modify the shop object",
            notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Shop> updateShop(@ApiParam(value = "id is required to get the store object by this id for " +
            "subsequent changes", example = "1")
                                           @PathVariable("id") Long id,
                                           @ApiParam(value = "Modified shop object",
                                                   example = "shopDTO")
                                           @Valid @RequestBody ShopDTO shopDTO,
                                           BindingResult bindingResult) {
        if (id < 1 | shopRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidParametrException();
        }
        Shop shop = shopRepository.getById(id);
        List<SportsNutrition> sportsNutritionList = shop.getSportsNutritionList();

        Shop updateShop = shopMapper.shopDTOToShop(shopDTO);
        updateShop.setId(shop.getId());
        updateShop.setSportsNutritionList(sportsNutritionList);

        return ResponseEntity.ok(shopRepository.save(updateShop));
    }


}
