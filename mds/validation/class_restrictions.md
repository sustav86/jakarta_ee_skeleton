#Ограничения на уровне класса
* Выше мы рассмотрели различные способы разработки ограничения, которое при-
менялось бы к атрибуту (или геттеру). Но вы также можете создать ограничение
для целого класса. Идея заключается в том, чтобы выразить ограничение на осно-
ве нескольких свойств, которыми обладает заданный класс.
* В листинге 3.11 показан класс для оформления заказа. Этот заказ товара следу-
ет определенному жизненному циклу бизнес-логики: создается в системе, оплачи-
вается клиентом, а потом доставляется клиенту. Класс отслеживает все эти события,
оперируя соответствующими датами: creationDate, paymentDate и deliveryDate. Ан-
нотация @ChronologicalDates действует на уровне класса и проверяет, находятся ли
эти даты в правильном хронологическом порядке.
```xml
@ChronologicalDates
public class Order {
    private String orderId;
    private Double totalAmount;
    private Date creationDate;
    private Date paymentDate;
    private Date deliveryDate;
    private List<OrderLine> orderLines;
// Конструкторы, геттеры, сеттеры
}
```
В листинге 3.12 показана реализация ограничения @ChronologicalDates. Подобно
тем ограничениям, что были рассмотрены выше, оно реализует интерфейс Constraint-
Validator, обобщенный тип которого — Order. Метод isValid проверяет, находятся ли
три даты в правильном хронологическом порядке, и если это так — возвращает true.
```xml
public class ChronologicalDatesValidator implements
        ConstraintValidator<ChronologicalDates, Order> {
    @Override
    public void initialize(ChronologicalDates constraintAnnotation) {
    }
    @Override
    public boolean isValid(Order order, ConstraintValidatorContext context) {
        return order.getCreationDate().getTime() <
                order.getPaymentDate().getTime() &&
                order.getPaymentDate().getTime() <
                        order.getDeliveryDate().getTime();
    }
}
```