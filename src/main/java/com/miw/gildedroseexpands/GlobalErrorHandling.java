package com.miw.gildedroseexpands;

import com.miw.gildedroseexpands.entity.inventory.support.NoInventoryException;
import com.miw.gildedroseexpands.entity.item.support.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * allows us to have some custom errors returned to user.
 */
@ControllerAdvice
@RequestMapping()
public class GlobalErrorHandling {

        @ExceptionHandler(ItemNotFoundException.class)
        public ResponseEntity<String> itemNotFoundException(final ItemNotFoundException e) {
                return new ResponseEntity<>(
                        String.format("Could not find item '%s'", e.getItemName()),
                        HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(NoInventoryException.class)
        public ResponseEntity<String> noInventoryException(final NoInventoryException e) {
                return new ResponseEntity<>(
                        String.format("There is no inventory available for '%s'", e.getItemName()),
                        HttpStatus.BAD_REQUEST);
        }

}
