package com.miw.gildedroseexpands.utility;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.util.Date;

/**
 * we are using Instant times in the model, but the database only supports Date.
 * This is the auto-converter for it (configured via spring JPA)
 */
@Converter(autoApply = true)
public class InstantDateConverter implements AttributeConverter<Instant, Date> {
        @Override
        public Date convertToDatabaseColumn(Instant instant) {
                return Date.from(instant);
        }

        @Override
        public Instant convertToEntityAttribute(Date date) {
                return date.toInstant();
        }
}
