package org.example.exeptions;

public class ResourceNotFoundException extends RuntimeException {

    /**
     * Конструктор с указанием подробного сообщения.
     *
     * @param resourceName название ресурса.
     * @param id идентификатор ресурса.
     */
    public ResourceNotFoundException(String resourceName, Object id) {
        super(resourceName + " с идентификатором " + id + " не найден.");
    }
}
