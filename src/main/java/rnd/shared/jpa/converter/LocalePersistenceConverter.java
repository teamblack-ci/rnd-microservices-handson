package rnd.shared.jpa.converter;

import java.util.Locale;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalePersistenceConverter implements AttributeConverter<Locale, String> {

    @Override
    public String convertToDatabaseColumn(Locale entityValue) {
        return entityValue  == null ? null : entityValue.toLanguageTag();
    }

    @Override
    public Locale convertToEntityAttribute(String databaseValue) {
        return databaseValue == null ? null : Locale.forLanguageTag(databaseValue);
    }
}