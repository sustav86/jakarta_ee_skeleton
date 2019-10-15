#Валидация свойств
* В предыдущих примерах валидируются свойства целого компонента. Но существу-
ет метод Validator.validateProperty(), позволяющий проверять конкретное имено-
ванное свойство заданного объекта.
* В показанном ниже коде создается CD — объект, имеющий нулевой заголовок
и нулевую цену; соответственно, такой компонент невалиден. Но, поскольку мы
проверяем только свойство numberOfCDs, валидация проходит успешно и мы полу-
чаем пустое множество нарушений ограничений:
```xml
CD cd = new CD();
cd.setNumberOfCDs(2);
Set<ConstraintViolation<CD>> violations = validator.validateProperty(cd,
"numberOfCDs");
assertEquals(0, violations.size());
```
С другой стороны, в следующем коде возникает нарушение ограничения, так как
максимальное количество объектов CD должно равняться пяти, а не семи. Обрати-
те внимание: мы используем API ConstraintViolation для проверки количества
нарушений, интерполированного сообщения, возвращенного при нарушении, не-
валидного значения и шаблона сообщения:
```xml
CD cd = new CD();
cd.setNumberOfCDs(7);
Set<ConstraintViolation<CD>> violations = validator.validateProperty(cd,
"numberOfCDs");
assertEquals(1, violations.size());
assertEquals("must be less than or equal to 5", violations.iterator().next().
getMessage());
assertEquals(7, violations.iterator().next().getInvalidValue());
assertEquals("{javax.validation.constraints.Max.message}",
violations.iterator().next().getMessageTemplate());
assertEquals(0, violations.size());
```
