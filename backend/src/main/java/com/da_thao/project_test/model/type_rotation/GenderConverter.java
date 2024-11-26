package com.da_thao.project_test.model.type_rotation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<String, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(String attribute) {
        // Chuyển từ String sang Boolean
        if ("Male".equalsIgnoreCase(attribute)) {
            return true; // Nam
        } else if ("Female".equalsIgnoreCase(attribute)) {
            return false; // Nữ
        }
        return null; // Giá trị không xác định
    }

    @Override
    public String convertToEntityAttribute(Boolean dbData) {
        // Chuyển từ Boolean sang String
        if (Boolean.TRUE.equals(dbData)) {
            return "Male"; // Nam
        } else if (Boolean.FALSE.equals(dbData)) {
            return "Female"; // Nữ
        }
        return null; // Giá trị không xác định
    }
}
