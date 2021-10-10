package com.kdt.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@JsonComponent
@Slf4j
public class ErrorsSerializer extends JsonSerializer<Errors> {

    @Override
    public void serialize(Errors errors, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();

        for (FieldError error : errors.getFieldErrors()) {
            try {
                jsonGenerator.writeStartObject();

                jsonGenerator.writeStringField("field", error.getField());
                jsonGenerator.writeStringField("objectName", error.getObjectName());
                jsonGenerator.writeStringField("code", error.getCode());
                jsonGenerator.writeStringField("defaultMessage", error.getDefaultMessage());

                jsonGenerator.writeEndObject();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                log.error("Json serialize error {}", ioException.getMessage());
            }
        }

        jsonGenerator.writeEndArray();
    }
}
