#Интеграция с Bean Validation
Технология Bean Validation, которая была разъяснена в предыдущей главе, связа-
на с Java EE несколькими способами. Один из них выражается в ее интеграции
с JPA и жизненным циклом сущностей. Сущности могут включать ограничения
Bean Validation и автоматически подвергаться валидации. Фактически автомати-
ческая валидация обеспечивается благодаря тому, что JPA возлагает ее проведение
на реализацию Bean Validation при наступлении событий жизненного цикла сущности 
* pre-persis 
* pre-update 
* pre-remove 

Разумеется, при необходимости валида-
ция по-прежнему может проводиться вручную вызовом метода validate класса
Validator в отношении сущности.
* В листинге 4.6 показана сущность Book с двумя ограничениями Bean Validation
(@NotNull и @Size). Если значением атрибута title окажется null, а вы захотите обе-
спечить постоянство этой сущности (вызвав EntityManager.persist()), то во время
выполнения JPA будет выброшено исключение ConstraintViolation, а соответ-
ствующая информация не будет помещена в базу данных.
```xml
@Entity
public class Book {
    @Id @GeneratedValue
    private Long id;
    @NotNull
    private String title;
    private Float price;
    @Size(min = 10, max = 2000)
    private String description;
    private String isbn;
    private Integer nbOfPage;
    private Boolean illustrations;
// Конструкторы, геттеры, сеттеры
}
```

