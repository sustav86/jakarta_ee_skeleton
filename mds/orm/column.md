#@Column
Аннотация 
* @javax.persistence.Column, 

показанная в листинге 5.12, определяет свойства
столбца. Вы можете изменить имя столбца (которое по умолчанию отображается
в совпадающее с ним имя атрибута), а также указать размер и разрешить (или запре-
тить) столбцу иметь значение null, быть уникальным или позволить его значению
быть обновляемым или вставляемым. В листинге 5.12 приведен API-интерфейс
аннотации @Column с элементами и их значениями по умолчанию.
```xml
@Target({METHOD, FIELD}) @Retention(RUNTIME)
public @interface Column {
    String name() default "";
    boolean unique() default false;
    boolean nullable() default true;
    boolean insertable() default true;
    boolean updatable() default true;
    String columnDefinition() default "";
    String table() default "";
    int length() default 255;
    int precision() default 0; // десятичная точность
    int scale() default 0; // десятичная система счисления
}
```
Для переопределения отображения по умолчанию оригинальной сущности Book
(см. листинг 5.1) вы можете использовать аннотацию @Column разными способами
(листинг 5.13). Например, вы можете изменить имя столбца title и nbOfPage либо
длину description и не разрешить значения null. Следует отметить, что допустимы
не все комбинации атрибутов для типов данных (например, length применим толь-
ко к столбцу со строковым значением, а scale и precision — только к десятичному
столбцу).
```xml
@Entity
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "book_title", nullable = false, updatable = false)
    private String title;
    private Float price;
    @Column(length = 2000)
    private String description;
    private String isbn;
    @Column(name = "nb_of_page", nullable = false)
    private Integer nbOfPage;
    private Boolean illustrations;
// Конструкторы, геттеры, сеттеры
}
```
Сущность Book из листинга 5.13 будет отображена в определение таблицы, по-
казанное в листинге 5.14.
```xml
create table BOOK (
ID BIGINT not null,
BOOK_TITLE VARCHAR(255) not null,
PRICE DOUBLE(52, 0),
DESCRIPTION VARCHAR(2000),
ISBN VARCHAR(255),
NB_OF_PAGE INTEGER not null,
ILLUSTRATIONS SMALLINT,
primary key (ID)
);
```
Большинство элементов аннотации @Column влияют на отображение. Если вы
измените значение длины description на 2000, то значение длины целевого столбца
тоже будет задано как равное 2000. Параметры updatable и insertable по умолчанию
имеют значение true, которое подразумевает, что любой атрибут может быть встав-
лен или обновлен в базе данных, а также оказывают влияние во время выполнения.
Вы сможете задать для них значение false, когда вам понадобится, чтобы поставщик
постоянства позаботился о том, что он не вставит или не обновит данные в табли-
це в ответ на изменения в сущности. Следует отметить, что это не подразумевает,
что атрибут сущности не изменится в памяти. Вы по-прежнему сможете изменить
соответствующее значение, однако оно не будет синхронизировано с базой данных.
Причина этого состоит в том, что сгенерированный SQL-оператор (INSERT или
UPDATE) не будет включать столбцы. Другими словами, эти элементы не влияют на
реляционное отображение, но влияют на динамическое поведение менеджера сущ-
ностей при доступе к реляционным данным.

Как можно было видеть в главе 3, Bean Validation определяет ограничения только в рам-
ках пространства Java. Поэтому @NotNull обеспечивает считывание фрагмента Java-кода,
который убеждается в том, что значением атрибута не является null. С другой стороны,
аннотация JPA 
* @Column(nullable = false) 

используется в пространстве базы данных для
генерирования схемы базы данных. Аннотации JPA и Bean Validation могут сосущество-
вать в случае с тем или иным атрибутом.