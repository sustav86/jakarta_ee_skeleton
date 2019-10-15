#Валидация групп
* Группа определяет подмножество ограничений. Вместо валидации всех ограни-
чений для данного компонента проверяется только нужное подмножество. При
объявлении каждого ограничения указывается список групп, в которые входит
это ограничение. Если явно не объявлена ни одна группа, то ограничение отно-
сится к группе Default. Что касается проверки, у всех валидационных методов есть
параметр с переменным количеством аргументов, указывающий, сколько групп
должно учитываться при выполнении валидации. Если этот параметр не задан,
будет использоваться указанная по умолчанию валидационная группа (javax.va-
lidation.groups.Default). Если вместо Default указана другая группа, то Default
не валидируется.
* В листинге 3.21 все ограничения, за исключением применяемых с атрибутом
description, относятся к группе Default. Описание (@NotNull @Size(min = 100, max =
5000)) требуется лишь в том случае, если диск должен быть упомянут в каталоге
(группа PrintingCatalog). Итак, если мы создаем CD без заголовка, цены и описания,
после чего проверяем лишь условия из группы Default, то будут нарушены всего
два ограничения @NotNull, касающиеся title и price.
```xml
CD cd = new CD();
cd.setDescription("Best Jazz CD ever");
Set<ConstraintViolation<CD>> violations = validator.validate(cd, Default.
class);
assertEquals(2, violations.size());
```
Обратите внимание: в предыдущем коде при валидации явно упоминается
группа Default, но это слово можно пропустить. Итак, следующий код идентичен
предыдущему:
```xml
Set<ConstraintViolation<CD>> violations = validator. validate(cd);
```
С другой стороны, если бы мы решили проверить CD только для группы
PrintingCatalog, то следующий код нарушал бы лишь ограничение, налагаемое
на description, так как предоставляемое значение было бы слишком коротким:
```xml
CD cd = new CD();
cd.setDescription("Too short");
Set<ConstraintViolation<CD>> violations = validator.validate(cd,
PrintingCatalog.class);
assertEquals(1, violations.size());
```
Если бы вы хотели проверить компонент на соответствие обеим группам —
Default и PrintingCatalog, то у вас было бы нарушено три ограничения (@NotNull для
title и price, а также очень краткое описание):
```xml
CD cd = new CD();
cd.setDescription("Too short");
Set<ConstraintViolation<CD>> violations = validator.validate(cd, Default.
class, PrintingCatalog.class);
assertEquals(3, violations.size());
```
