#Валидация значений
* Пользуясь методом Validator.validateValue(), можно проверять, удастся ли успеш-
но валидировать конкретное свойство указанного класса при наличии у свойства
заданного значения. Этот метод удобен при опережающей валидации, так как не
требует даже создавать экземпляр компонента, заполнять или обновлять его
значения.
* Следующий код не создает объект CD, а просто ссылается на атрибут numberOfCDs
класса CD. Он передает значение и проверяет, является ли свойство валидным (ко-
личество CD не должно превышать пяти):
```xml
Set<ConstraintViolation<CD>> constr = validator.validateValue(CD.class,
"numberOfCDs", 2);
assertEquals(0, constr.size());
Set<ConstraintViolation<CD>> constr = validator.validateValue(CD.class,
"numberOfCDs", 7);
assertEquals(1, constr.size());
```
