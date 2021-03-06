package core.framework.impl.mongo;

import core.framework.api.mongo.Id;
import core.framework.api.util.Exceptions;
import core.framework.impl.code.CodeBuilder;
import core.framework.impl.code.DynamicInstanceBuilder;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;

/**
 * @author neo
 */
final class EntityIdHandlerBuilder<T> {
    private final Class<T> entityClass;
    private final Field idField;

    public EntityIdHandlerBuilder(Class<T> entityClass) {
        this.entityClass = entityClass;
        idField = getIdField();
    }

    public EntityIdHandler<T> build() {
        DynamicInstanceBuilder<EntityIdHandler<T>> builder = new DynamicInstanceBuilder<>(EntityIdHandler.class, EntityIdHandler.class.getCanonicalName() + "$" + entityClass.getSimpleName());
        builder.addMethod(getMethod());
        builder.addMethod(setMethod());
        builder.addMethod(generateIdIfAbsentMethod());
        return builder.build();
    }

    private Field getIdField() {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) return field;
        }
        throw Exceptions.error("can not find id field, class={}", entityClass.getCanonicalName());
    }

    private String generateIdIfAbsentMethod() {
        CodeBuilder builder = new CodeBuilder();
        builder.append("public boolean generateIdIfAbsent() {\n")
            .indent(1).append("return {};", ObjectId.class.equals(idField.getType()) ? "true" : "false")
            .append("}");
        return builder.build();
    }

    private String getMethod() {
        CodeBuilder builder = new CodeBuilder();
        builder.append("public Object get(Object value) {\n")
            .indent(1).append("{} entity = ({}) value;\n", entityClass.getCanonicalName(), entityClass.getCanonicalName())
            .indent(1).append("return entity.{};\n", idField.getName())
            .append("}");
        return builder.build();
    }

    private String setMethod() {
        CodeBuilder builder = new CodeBuilder();
        builder.append("public void set(Object value, Object id) {\n")
            .indent(1).append("{} entity = ({}) value;\n", entityClass.getCanonicalName(), entityClass.getCanonicalName())
            .indent(1).append("entity.{} = ({}) id;\n", idField.getName(), idField.getType().getCanonicalName())
            .append("}");
        return builder.build();
    }
}
