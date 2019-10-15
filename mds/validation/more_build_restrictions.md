#Множественные ограничения для одной целевой сущности
Иногда полезно применять одинаковое ограничение к одной и той же цели, используя
при этом разные свойства или группы (подробнее об этом ниже). Распространенный
пример такого рода — ограничение @Pattern, проверяющее соответствие целевой сущ-
ности определенному регулярному выражению. В листинге 3.9 показано, как при-
менить два регулярных выражения к одному и тому же атрибуту. Множественные
ограничения используют оператор AND. Это означает, что для валидности атрибута
orderId необходимо, чтобы он удовлетворял двум регулярным выражениям.
```xml
public class Order {
    @Pattern.List({
            @Pattern(regexp = "[C,D,M][A-Z][0-9]*"),
            @Pattern(regexp = ".[A-Z].*?")
    })
    private String orderId;
    private Date creationDate;
    private Double totalAmount;
    private Date paymentDate;
    private Date deliveryDate;
    private List<OrderLine> orderLines;
// Конструкторы, геттеры, сеттеры
}
```
* Чтобы иметь возможность несколько раз применить одно и то же ограничение
к данной цели, ограничивающая аннотация должна определить массив на основе
самой себя. При валидации компонентов такие массивы ограничений обрабатыва-
ются по-особому: каждый элемент массива интерпретируется как обычное ограни-
чение. В листинге 3.10 показана ограничивающая аннотация @Pattern, определя-
ющая внутренний интерфейс (произвольно названный List) с элементом Pattern[].
* Внутренний интерфейс должен иметь правило хранения RUNTIME и использовать
в качестве исходного ограничения один и тот же набор целей (в данном случае
METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER).
```xml
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = PatternValidator.class)
public @interface Pattern {
    String regexp();
    String message() default "{javax.validation.constraints.Pattern.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    // Определяет несколько аннотаций @Pattern, применяемых к одному элементу
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @interface List {
        Pattern[] value();
    }
}
```
При разработке собственной ограничивающей аннотации следует добавить соответству-
ющую ей аннотацию с множеством значений. Спецификация Bean Validation не требует
этого строго, но настоятельно рекомендует определять внутренний интерфейс под на-
званием List.