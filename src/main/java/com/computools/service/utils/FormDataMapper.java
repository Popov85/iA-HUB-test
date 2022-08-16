package com.computools.service.utils;

import java.net.http.HttpRequest;
import java.util.Map;

public interface FormDataMapper {
    HttpRequest.BodyPublisher mapToFormData(Map<Object, Object> data);
}
