package core.framework.impl.validate;

import core.framework.api.util.Exceptions;
import core.framework.api.util.Lists;
import core.framework.api.util.Maps;
import core.framework.api.validate.Length;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.ValueNotEmpty;
import core.framework.api.validate.ValueNotNull;
import core.framework.impl.reflect.Fields;
import core.framework.impl.reflect.GenericTypes;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author neo
 */
public class ValidatorBuilder {
    private final Type instanceType;
    private final Function<Field, String> fieldNameProvider;

    public ValidatorBuilder(Type instanceType, Function<Field, String> fieldNameProvider) {
        this.instanceType = instanceType;
        this.fieldNameProvider = fieldNameProvider;
    }

    public Validator build() {
        Class<?> targetClass;
        if (GenericTypes.isList(instanceType)) { // type validator ensured list can only be generic type in advance
            targetClass = GenericTypes.listValueClass(instanceType);
            if (isValueClass(targetClass)) {
                return new Validator(null); // not validate top level value List since no place to put annotation
            }
        } else {
            targetClass = GenericTypes.rawClass(instanceType);
        }

        ObjectValidator objectValidator = createObjectValidator(targetClass, null);
        if (objectValidator == null) {
            return new Validator(null);
        }
        if (GenericTypes.isList(instanceType)) {
            return new Validator(new ListValidator(Lists.newArrayList(objectValidator)));
        }

        return new Validator(objectValidator);
    }

    private ObjectValidator createObjectValidator(Class<?> instanceClass, String parentPath) {
        Map<Field, List<FieldValidator>> validators = Maps.newLinkedHashMap();
        for (Field field : instanceClass.getFields()) {
            List<FieldValidator> fieldValidators = Lists.newArrayList();
            createValidators(fieldValidators, field, parentPath);
            if (!fieldValidators.isEmpty()) validators.put(field, fieldValidators);
        }
        if (validators.isEmpty()) return null;
        return new ObjectValidator(validators);
    }

    private String fieldPath(String parentPath, Field field) {
        String fieldName = fieldNameProvider.apply(field);
        if (parentPath == null) return fieldName;
        return parentPath + "." + fieldName;
    }

    private void createValidators(List<FieldValidator> validators, Field field, String parentPath) {
        createNotNullValidator(validators, field, parentPath);
        createNotEmptyValidator(validators, field, parentPath);
        createLengthValidator(validators, field, parentPath);
        createMinValidator(validators, field, parentPath);
        createMaxValidator(validators, field, parentPath);

        validateCollectionAnnotations(field);

        Class<?> fieldClass = field.getType();
        if (List.class.equals(fieldClass) || Map.class.equals(fieldClass)) {
            FieldValidator collectionValidator = createCollectionValidator(field, parentPath);
            if (collectionValidator != null) validators.add(collectionValidator);
        } else if (!isValueClass(fieldClass)) {
            FieldValidator objectValidator = createObjectValidator(fieldClass, fieldPath(parentPath, field));
            if (objectValidator != null) validators.add(objectValidator);
        }
    }

    private FieldValidator createCollectionValidator(Field field, String parentPath) {
        Type fieldType = field.getGenericType();
        boolean isList = GenericTypes.isList(fieldType);

        Class<?> targetClass = isList ? GenericTypes.listValueClass(fieldType) : GenericTypes.mapValueClass(fieldType);

        List<FieldValidator> valueValidators = Lists.newArrayList();

        createValueNotNullValidator(valueValidators, parentPath, field);
        createValueNotEmptyValidator(valueValidators, parentPath, field);

        if (!isValueClass(targetClass)) {
            ObjectValidator objectValidator = createObjectValidator(targetClass, fieldPath(parentPath, field));
            if (objectValidator != null) valueValidators.add(objectValidator);
        }

        if (valueValidators.isEmpty()) return null;

        if (isList) return new ListValidator(valueValidators);
        else return new MapValidator(valueValidators);
    }

    private void validateCollectionAnnotations(Field field) {
        Class<?> fieldClass = field.getType();
        Type fieldType = field.getGenericType();

        if (field.isAnnotationPresent(ValueNotNull.class) && !(List.class.equals(fieldClass) || Map.class.equals(fieldClass))) {
            throw Exceptions.error("@ValueNotNull must on List<T> or Map<String, T>, field={}, fieldClass={}", field, fieldClass.getCanonicalName());
        }

        if (field.isAnnotationPresent(ValueNotEmpty.class)) {
            if (!(List.class.equals(fieldClass) || Map.class.equals(fieldClass)))
                throw Exceptions.error("@ValueNotEmpty must on List<String> or Map<String, String>, field={}, fieldClass={}", field, fieldClass.getCanonicalName());

            if (List.class.equals(fieldClass) && !String.class.equals(GenericTypes.listValueClass(fieldType))) {
                throw Exceptions.error("@ValueNotEmpty must on List<String>, field={}, fieldType={}", field, fieldType.getTypeName());
            }

            if (Map.class.equals(fieldClass) && !String.class.equals(GenericTypes.mapValueClass(fieldType))) {
                throw Exceptions.error("@ValueNotEmpty must on Map<String, String>, field={}, fieldType={}", field, fieldType.getTypeName());
            }
        }
    }

    private void createMaxValidator(List<FieldValidator> validators, Field field, String parentPath) {
        Class<?> fieldClass = field.getType();
        Max max = field.getDeclaredAnnotation(Max.class);
        if (max != null) {
            if (!Number.class.isAssignableFrom(fieldClass)) {
                throw Exceptions.error("@Max must on numeric field, field={}, fieldClass={}", field, fieldClass.getCanonicalName());
            }
            validators.add(new MaxValidator(fieldPath(parentPath, field), max));
        }
    }

    private void createMinValidator(List<FieldValidator> validators, Field field, String parentPath) {
        Class<?> fieldClass = field.getType();
        Min min = field.getDeclaredAnnotation(Min.class);
        if (min != null) {
            if (!Number.class.isAssignableFrom(fieldClass)) {
                throw Exceptions.error("@Min must on numeric field, field={}, fieldClass={}", field, fieldClass.getCanonicalName());
            }
            validators.add(new MinValidator(fieldPath(parentPath, field), min));
        }
    }

    private void createLengthValidator(List<FieldValidator> validators, Field field, String parentPath) {
        Class<?> fieldClass = field.getType();
        Length length = field.getDeclaredAnnotation(Length.class);
        if (length != null) {
            if (!String.class.equals(fieldClass) && !List.class.equals(fieldClass) && !Map.class.equals(fieldClass)) {
                throw Exceptions.error("@Length must on String, List<T> or Map<String, T>, field={}, fieldClass={}", field, fieldClass.getCanonicalName());
            }
            validators.add(new LengthValidator(fieldPath(parentPath, field), length));
        }
    }

    private void createNotEmptyValidator(List<FieldValidator> validators, Field field, String parentPath) {
        Class<?> fieldClass = field.getType();
        NotEmpty notEmpty = field.getDeclaredAnnotation(NotEmpty.class);
        if (notEmpty != null) {
            if (!String.class.equals(fieldClass)) {
                throw Exceptions.error("@Length must on String, field={}, fieldClass={}", Fields.path(field), fieldClass.getCanonicalName());
            }
            validators.add(new NotEmptyValidator(fieldPath(parentPath, field), notEmpty.message()));
        }
    }

    private void createNotNullValidator(List<FieldValidator> validators, Field field, String parentPath) {
        NotNull notNull = field.getDeclaredAnnotation(NotNull.class);
        if (notNull != null) {
            validators.add(new NotNullValidator(fieldPath(parentPath, field), notNull.message(), true));
        }
    }

    private void createValueNotEmptyValidator(List<FieldValidator> valueValidators, String parentPath, Field field) {
        ValueNotEmpty valueNotEmpty = field.getDeclaredAnnotation(ValueNotEmpty.class);
        if (valueNotEmpty != null) {
            valueValidators.add(new NotEmptyValidator(fieldPath(parentPath, field), valueNotEmpty.message()));
        }
    }

    private void createValueNotNullValidator(List<FieldValidator> valueValidators, String parentPath, Field field) {
        ValueNotNull valueNotNull = field.getDeclaredAnnotation(ValueNotNull.class);
        if (valueNotNull != null) {
            valueValidators.add(new NotNullValidator(fieldPath(parentPath, field), valueNotNull.message(), false));
        }
    }

    private boolean isValueClass(Class<?> fieldClass) {
        return String.class.equals(fieldClass)
            || Integer.class.equals(fieldClass)
            || Boolean.class.equals(fieldClass)
            || Long.class.equals(fieldClass)
            || Double.class.equals(fieldClass)
            || BigDecimal.class.equals(fieldClass)
            || LocalDate.class.equals(fieldClass)
            || LocalDateTime.class.equals(fieldClass)
            || Instant.class.equals(fieldClass)
            || Enum.class.isAssignableFrom(fieldClass)
            || "org.bson.types.ObjectId".equals(fieldClass.getCanonicalName()); // not depends on mongo jar if application doesn't include mongo driver
    }
}
