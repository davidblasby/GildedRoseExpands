package com.miw.gildedroseexpands.entity.item.controller;

import com.miw.gildedroseexpands.entity.item.ItemEntity;
import com.miw.gildedroseexpands.entity.item.ItemEntityBuilder;
import com.miw.gildedroseexpands.entity.item.support.ItemDB;
import com.miw.gildedroseexpands.entity.item.support.ItemDBRepository;
import com.miw.gildedroseexpands.entity.item.support.ItemEntityBuilderFactory;
import com.miw.gildedroseexpands.entity.item.support.ItemNotFoundException;
import com.miw.gildedroseexpands.entity.item.support.ItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * main controller for app
 * This handles the two main endpoints
 * a) /items and /items/{name} -- anyone can access
 * b) /buy -- must be logged in
 */
@Controller
public class ItemController {

        private final ItemDBRepository itemDBRepository;

        @Autowired
        private ItemEntityBuilderFactory itemEntityBuilderFactory;

        public ItemController(ItemDBRepository itemDBRepository,
                ItemEntityBuilderFactory itemEntityBuilderFactory) {
                this.itemDBRepository = itemDBRepository;
                this.itemEntityBuilderFactory = itemEntityBuilderFactory;
        }

        /**
         * finds an Item and returns it to user
         * We go from ItemDB -> ItemEntity (main object) -> ItemViewModel
         * @param name name of item
         */
        @GetMapping("/items/{name}")
        @ResponseBody
        @Transactional
        public ItemViewModel getItem(@PathVariable String name) {
                ItemDB item = this.itemDBRepository.findByName(name);
                if (item == null) {
                        throw new ItemNotFoundException(name);
                }
                ItemEntityBuilder itemEntityBuilder = itemEntityBuilderFactory.create();
                itemEntityBuilder.setItem(item);
                ItemEntity itemEntity = itemEntityBuilder.build();
                ItemViewModel itemViewModel = itemEntity.toViewModel();
                return itemViewModel;
        }

        /**
         * finds all Items and returns it to user
         * We go from ItemDB -> ItemEntity (main object) -> ItemViewModel for each item
         */
        @GetMapping("/items")
        @ResponseBody
        @Transactional
        public List<ItemViewModel> getItems() {
                Iterable<ItemDB> items = this.itemDBRepository.findAll();
                List<ItemViewModel> result = new ArrayList<ItemViewModel>();
                for (ItemDB item : items) {
                        ItemEntityBuilder itemEntityBuilder = itemEntityBuilderFactory.create();
                        itemEntityBuilder.setItem(item);
                        ItemEntity itemEntity = itemEntityBuilder.build();
                        ItemViewModel itemViewModel = itemEntity.toViewModel();
                        result.add(itemViewModel);
                }
                return result;
        }

        /**
         * buy an item
         * @param purchaseRequest - POST request (currently just the itemname)
         * @param principal - injected by Spring Security (user info)
         * @return true if success, otherwise exception
         */
        @Secured({ "ROLE_USER" })
        @Transactional
        @ResponseBody
        @PostMapping("/buy")
        public boolean buyItem(@RequestBody PurchaseRequest purchaseRequest, Principal principal) {
                String userName = principal.getName();
                String itemName = purchaseRequest.getItemName();
                ItemDB item = this.itemDBRepository.findByName(itemName);
                if (item == null) {
                        throw new ItemNotFoundException(itemName);
                }
                ItemEntityBuilder itemEntityBuilder = itemEntityBuilderFactory.create();
                itemEntityBuilder.setItem(item);
                ItemEntity itemEntity = itemEntityBuilder.build();

                itemEntity.purchase(userName, itemName);
                return true;
        }

}
