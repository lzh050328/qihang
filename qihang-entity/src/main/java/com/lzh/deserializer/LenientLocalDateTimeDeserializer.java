package com.lzh.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 宽松的 LocalDateTime 反序列化器。
 * <p>
 * 前端日期选择器传的是 ISO 格式（2026-10-22T00:00:00），
 * 而实体上的 @JsonFormat 只认 yyyy-MM-dd HH:mm:ss（空格分隔），
 * Jackson 会报 "could not be parsed at index 10"，所以这里两种都兼容。
 */
public class LenientLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        if (text == null || text.isBlank()) {
            return null;
        }
        // ISO_LOCAL_DATE_TIME 只认 T 分隔，把空格换成 T 后两种格式都能解析
        return LocalDateTime.parse(text.trim().replace(' ', 'T'));
    }
}
