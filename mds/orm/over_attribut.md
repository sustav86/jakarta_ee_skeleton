#Переопределение атрибутов

При использовании стратегии «таблица на конкретный класс» столбцы таблицы
корневого класса дублируются в листовых таблицах. Они будут иметь одинаковые
имена. Но что, если применять унаследованную базу данных, а столбцы будут об-
ладать другими именами? JPA задействует аннотацию 
* @AttributeOverride 

для переопределения отображения одного столбца и @AttributeOverrides, если речь идет
о переопределении нескольких.

Чтобы переименовать столбцы ID, TITLE и DESCRIPTION в таблицах BOOK и CD, код
сущности Item не потребуется изменять, однако придется задействовать аннотацию
@AttributeOverride для сущностей Book (листинг 5.62) и CD (листинг 5.63).
```xml
@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id",
                column = @Column(name = "book_id")),
        @AttributeOverride(name = "title",
                column = @Column(name = "book_title")),
        @AttributeOverride(name = "description",
                column = @Column(name = "book_description"))
})
public class Book extends Item {
    private String isbn;
    private String publisher;
    private Integer nbOfPage;
    private Boolean illustrations;
// Конструкторы, геттеры, сеттеры
}
```
```xml
@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id",
                column = @Column(name = "cd_id")),
        @AttributeOverride(name = "title",
                column = @Column(name = "cd_title")),
        @AttributeOverride(name = "description",
                column = @Column(name = "cd_description"))
})
public class CD extends Item {
    private String musicCompany;
    private Integer numberOfCDs;
    private Float totalDuration;
    private String genre;
// Конструкторы, геттеры, сеттеры
}
```
Поскольку требуется переопределить несколько атрибутов, вам необходимо
использовать аннотацию @AttributeOverrides, которая принимает массив аннотаций
@AttributeOverride. После этого каждая аннотация указывает на атрибут сущности
Item и переопределяет отображение столбца с помощью аннотации @Column. Таким
образом, name = "title" ссылается на атрибут title сущности Item, а @Column(name =
"cd_title") информирует поставщика постоянства о том, что title надлежит ото-
бразить в столбец CD_TITLE. Результат показан на рис. 5.27.