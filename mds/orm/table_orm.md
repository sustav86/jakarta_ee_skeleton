#@Table
Аннотация 
* @javax.persistence.Table 

дает возможность изменять значения по
умолчанию, связанные с определенной таблицей. Например, вы можете указать
имя таблицы, в которой будут храниться данные, каталог и схему базы данных.
Вы также можете определить уникальные ограничения для таблицы с помощью
аннотации 
* @UniqueConstraint 

в сочетании с @Table. Если пренебречь аннотацией
@Table, то имя таблицы будет соответствовать имени сущности. Если вы захо-
тите изменить имя с BOOK на T_BOOK, то поступите так, как показано в листин-
ге 5.1.
```xml
@Entity
@Table(name = "t_book")
public class Book {
    @Id
    private Long id;
    private String title;
    private Float price;
    private String description;
    private String isbn;
    private Integer nbOfPage;
    private Boolean illustrations;
// Конструкторы, геттеры, сеттеры
}
```