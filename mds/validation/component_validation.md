#Валидация компонентов
Как только вы получите Validator программно или путем внедрения, можете исполь-
зовать его методы как для валидации целого компонента, так идля работы с отдельно
взятым свойством. В листинге 3.21 показан класс CD, в котором действуют ограниче-
ния, связанные со свойствами, параметрами методов и возвращаемым значением.
```xml
public class CD {
    @NotNull @Size(min = 4, max = 50)
    private String title;
    @NotNull
    private Float price;
    @NotNull(groups = PrintingCatalog.class)
    @Size(min = 100, max = 5000, groups = PrintingCatalog.class)
    private String description;
    @Pattern(regexp = "[A-Z][a-z]{1,}")
    private String musicCompany;
    @Max(value = 5)
    private Integer numberOfCDs;
    private Float totalDuration;
    @NotNull @DecimalMin("5.8")
    public Float calculatePrice(@DecimalMin("1.4") Float rate) {
        return price * rate;
    }
    @DecimalMin("9.99")
    public Float calculateVAT() {
        return price * 0.196f;
    }
}
```
Чтобы валидировать свойства целого компонента, мы просто должны создать
экземпляр CD и вызвать метод Validator.validate(). Если экземпляр валиден, то воз-
вращается пустое множество ConstraintViolation. В следующем коде показан валид-
ный экземпляр CD (с заголовком и ценой), который и проверяется. После этого код
удостоверяет, что множество ограничений действительно является пустым.
```xml
CD cd = new CD("Kind of Blue", 12.5f);
Set<ConstraintViolation<CD>> violations = validator.validate(cd);
assertEquals(0, violations.size());
```
С другой стороны, следующий код вернет два объекта ConstraintViolation — один
будет соответствовать заголовку, а другой — цене (оба они нарушают @NotNull):
```xml
CD cd = new CD();
Set<ConstraintViolation<CD>> violations = validator.validate(cd);
assertEquals(2, violations.size());
```
