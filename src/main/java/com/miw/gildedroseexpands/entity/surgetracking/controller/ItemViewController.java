package com.miw.gildedroseexpands.entity.surgetracking.controller;

import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewDB;
import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * ADMIN ONLY CONTROLLER
 * This is a nice-to-have controller to see the data for itemviews
 * "/admin/itemviews" -- views associated with all items
 * "/admin/itemviews/{itemName}" -- views associated with a single item
 *
 * NOTE: view information is purged occasionally
 */
@Controller
public class ItemViewController {

        @Autowired
        private final ItemViewRepository itemViewRepository;

        public ItemViewController(ItemViewRepository itemViewRepository) {
                this.itemViewRepository = itemViewRepository;
        }

        /**
         * views associated with all items
         */
        @Secured({ "ROLE_ADMIN" })
        @GetMapping("/admin/itemviews")
        @ResponseBody
        public Iterable<ItemViewDB> getAllItemView() {
                Iterable<ItemViewDB> itemViews = this.itemViewRepository.findAll();
                return itemViews;
        }

        /**
         * iews associated with a single item
         * @param itemName name of Item to look at view for
         */
        @Secured({ "ROLE_ADMIN" })
        @GetMapping("/admin/itemviews/{itemName}")
        @ResponseBody
        public List<ItemViewDB> getItemViews(@PathVariable String itemName) {
                List<ItemViewDB> itemViews = this.itemViewRepository.findByItemName(itemName);
                return itemViews;
        }

}
