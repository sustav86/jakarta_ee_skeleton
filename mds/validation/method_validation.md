#Валидация методов
* Методы для валидации параметров и возвращаемых значений методов и конструк-
торов вы найдете в интерфейсе javax.validation.ExecutableValidator. Метод
Validator.forExecutables() возвращает этот ExecutableValidator, с которым вы мо-
жете вызывать validateParameters, validateReturnValue, validateConstructorParameters
или validateConstructorReturnValue.
* В следующем коде мы вызываем метод calculatePrice, передавая значение 1.2.
В результате возникает нарушение ограничения, налагаемого на параметр: не
выполняется условие @DecimalMin("1.4"). Для этого в коде сначала нужно создать
объект java.lang.reflect.Method, нацеленный на метод calculatePrice с параметром
типа Float. После этого он получает объект ExecutableValidator и вызывает
validateParameters, передавая компонент, метод для вызова и значение параметра
(здесь — 1.2). Затем метод удостоверяется в том, что никакие ограничения нару-
шены не были.
```xml
CD cd = new CD("Kind of Blue", 12.5f);
Method method = CD.class.getMethod("calculatePrice", Float.class);
ExecutableValidator methodValidator = validator. forExecutables();
Set<ConstraintViolation<CD>> violations = methodValidator.
validateParameters(cd, method,
new Object[]{new Float(1.2)});
assertEquals(1, violations.size());
```