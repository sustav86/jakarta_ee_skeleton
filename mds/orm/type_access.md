#Тип доступа
До сих пор я показывал вам аннотированные классы (@Entity или @Table) и атри-
буты (@Basic, @Column, @Temporal и т. д.), однако аннотации, применяемые к атрибуту
(или доступ к полям), также могут быть применены к соответствующему методу-
геттеру (или доступ к свойствам). Например, аннотация @Id может быть примене-
на к атрибуту id или методу getId(). Поскольку это в основном вопрос личных
предпочтений, я склонен использовать доступ к свойствам (аннотировать геттеры),
так как, по моему мнению, код при этом получается более удобочитаемым. Это
позволяет быстро изучить атрибуты сущности, не утопая в аннотациях. Чтобы код
в этой книге было легко разобрать, я решил аннотировать атрибуты. Однако в не-
которых случаях (например, когда речь идет о наследовании) это не просто дело
личного вкуса, поскольку оно может повлиять на ваше отображение.

В Java поле определяется как атрибут экземпляра. Свойство — это любое поле с мето-
дами (геттерами или сеттерами) доступа, которые придерживаются шаблона JavaBean
(в начале идет getXXX, setXXX или isXXX в случае с Boolean).

При выборе между доступом к полям (атрибуты) или доступом к свойствам
(геттеры) необходимо определить тип доступа. По умолчанию для сущности при-
меняется один тип доступа: это либо доступ к полям, либо доступ к свойствам, но
не оба сразу (например, поставщик постоянства получает доступ к постоянному
состоянию либо посредством атрибутов, либо посредством методов-геттеров). Со-
гласно спецификации поведение приложения, в котором сочетается применение
аннотаций к полям и свойствам без указания типа доступа явным образом, явля-
ется неопределенным. При использовании доступа к полям (листинг 5.21) постав-
щик постоянства отображает атрибуты.
```xml
@Entity
public class Customer {
    @Id @GeneratedValue
    private Long id;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    private String email;
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
// Конструкторы, геттеры, сеттеры
}
```
При использовании доступа к свойствам, как показано в листинге 5.22, отобра-
жение базируется на геттерах, а не на атрибутах. Все геттеры, не снабженные ан-
нотацией @Transient, являются постоянными.
```xml
@Entity
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    // Конструкторы
    @Id @GeneratedValue
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "first_name", nullable = false, length = 50)
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name = "last_name", nullable = false, length = 50)
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "phone_number", length = 15)
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
```
Однако вместо использования типа доступа по умолчанию вы можете явным об-
разом указать тип с помощью аннотации 
* @javax.persistence.Access

Эта аннотация принимает два возможных значения — FIELD или PROPERTY, а так-
же может быть использована в отношении сущности как таковой и/или каждого
атрибута или геттера. Например, при применении @Access(AccessType.FIELD) к сущ-
ности поставщиком постоянства будут приниматься во внимание только аннотации
отображения, которыми снабжены атрибуты. Тогда можно будет выборочно обо-
значить отдельные геттеры для доступа к свойствам посредством @Access(AccessTy-
pe.PROPERTY).

Типы явного доступа могут быть очень полезны (например, при работе со
встраиваемыми объектами или наследованием), однако их смешение часто приво-
дит к ошибкам. В листинге 5.23 показан пример того, что может произойти при
смешении типов доступа.
```xml
@Entity
@Access(AccessType.FIELD)
public class Customer {
    @Id @GeneratedValue
    private Long id;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    private String email;
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    // Конструкторы, геттеры, сеттеры
    @Access(AccessType.PROPERTY)
    @Column(name = "phone_number", length = 555)
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
```

В примере, показанном в листинге 5.23, тип доступа явным образом определя-
ется как FIELD на уровне сущности. Это говорит интерфейсу PersistenceManager
о том, что ему следует обрабатывать только аннотации, которыми снабжены атри-
буты. Атрибут phoneNumber снабжен аннотацией @Column, ограничивающей значение
его length величиной 15. Читая этот код, вы ожидаете, что в базе данных в итоге
будет VARCHAR(15), однако этого не случится. Метод-геттер показывает, что тип
доступа для метода getPhoneNumber() был изменен явным образом, поэтому длина
равна значению length атрибута phoneNumber (до 555). В данном случае сущность
AccessType.FIELD перезаписывается AccessType.PROPERTY. Тогда в базе данных у вас
окажется VARCHAR(555).