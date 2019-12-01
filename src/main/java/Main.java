import sun.plugin2.message.Conversation;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Anton Sustavov
 * @since 2019/10/09
 */
public class Main {

}

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NotNullValidator.class)
public @interface NotNull {
    String message() default "{javax.validation.constraints.NotNull.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

public class NotNullValidator implements ConstraintValidator<NotNull, Object>
{
    public void initialize(NotNull parameters) {
    }
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        return object != null;
    }
}

public class SizeValidatorForString implements<Size, String> {...}
public class SizeValidatorForBigDecimal implements<Size, BigDecimal> {...}
public class SizeValidatorForCollection implements<Size, Collection<?>> {...}

@ChronologicalDates
public class Order {
    @NotNull @Pattern(regexp = "[C,D,M][A-Z][0-9]*")
    private String orderId;
    private Date creationDate;
    @Min(1)
    private Double totalAmount;
    private Date paymentDate;
    private Date deliveryDate;
    private List<OrderLine> orderLines;
    public Order() {
    }
    public Order(@Past Date creationDate) {
        this.creationDate = creationDate;
    }
    public @NotNull Double calculateTotalAmount(@GreaterThanZero Double
                                                        changeRate) {
// ...
    }
// Геттеры и сеттеры
}

@NotNull
@Size(min = 7)
@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+
(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
        + "@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*
        [a-z0-9])?")
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
    String message() default "Неверный электронный адрес";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

public class ItemServerConnection {
    @URL
    private String resourceURL;
    @NotNull @URL(protocol = "http", host = "www.cdbookstore.com")
    private String itemURL;
    @URL(protocol = "ftp", port = 21)
    private String ftpServerURL;
    private Date lastConnectionDate;
// Конструкторы, геттеры, сеттеры
}

@Constraint(validatedBy = {URLValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface URL {
    String message() default "Malformed URL";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String protocol() default "";
    String host() default "";
    int port() default -1;
}

public class URLValidator implements ConstraintValidator<URL, String> {
    private String protocol;
    private String host;
    private int port;
    public void initialize(URL url) {
        this.protocol = url.protocol();
        this.host = url.host();
        this.port = url.port();
    }
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }
        java.net.URL url;
        try {
// Преобразуем URL в java.net.URL для проверки того,
// имеет ли URL допустимый формат
            url = new java.net.URL(value);
        } catch (MalformedURLException e) {
            return false;
        }
// Проверяет, имеет ли атрибут протокола допустимое значение
        if (protocol != null && protocol.length() > 0 &&
                !url.getProtocol().equals(protocol)) {
            return false;
        }
        if (host != null && host.length() > 0 && !url.getHost().startsWith(host)) {
            return false;
        }
        if (port != -1 && url.getPort() != port) {
            return false;
        }
        return true;
    }
}

public class Order {
    @Pattern.List({
            @Pattern(regexp = "[C,D,M][A-Z][0-9]*"),
            @Pattern(regexp = ".[A-Z].*?")
    })
    private String orderId;
    private Date creationDate;
    private Double totalAmount;
    private Date paymentDate;
    private Date deliveryDate;
    private List<OrderLine> orderLines;
// Конструкторы, геттеры, сеттеры
}

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = PatternValidator.class)
public @interface Pattern {
    String regexp();
    String message() default "{javax.validation.constraints.Pattern.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    // Определяет несколько аннотаций @Pattern, применяемых к одному элементу
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @interface List {
        Pattern[] value();
    }
}

@ChronologicalDates
public class Order {
    private String orderId;
    private Double totalAmount;
    private Date creationDate;
    private Date paymentDate;
    private Date deliveryDate;
    private List<OrderLine> orderLines;
// Конструкторы, геттеры, сеттеры
}

public class ChronologicalDatesValidator implements
        ConstraintValidator<ChronologicalDates, Order> {
    @Override
    public void initialize(ChronologicalDates constraintAnnotation) {
    }
    @Override
    public boolean isValid(Order order, ConstraintValidatorContext context) {
        return order.getCreationDate().getTime() <
                order.getPaymentDate().getTime() &&
                order.getPaymentDate().getTime() <
                        order.getDeliveryDate().getTime();
    }
}

public class CardValidator {
    private ValidationAlgorithm validationAlgorithm;
    public CardValidator(@NotNull ValidationAlgorithm validationAlgorithm) {
        this.validationAlgorithm = validationAlgorithm;
    }
    @AssertTrue
    public Boolean validate(@NotNull CreditCard creditCard) {
        return validationAlgorithm.validate(creditCard.getNumber(),
                creditCard.getCtrlNumber());
    }
    @AssertTrue
    public Boolean validate(@NotNull String number, @Future Date expiryDate,
                            Integer controlNumber, String type) {
        return validationAlgorithm.validate(number, controlNumber);
    }
}

Public class Item {
    @NotNull
    protected Long id;
    @NotNull @Size(min = 4, max = 50)
    protected String title;
    protected Float price;
    protected String description;
    @NotNull
    public Float calculateVAT() {
        return price * 0.196f;
    }
    @NotNull
    public Float calculatePrice(@DecimalMin("1.2") Float rate) {
        return price * rate;
    }
}

public class CD extends Item {
    @Pattern(regexp = "[A-Z][a-z]{1,}")
    private String musicCompany;
    @Max(value = 5)
    private Integer numberOfCDs;
    private Float totalDuration;
    @MusicGenre
    private String genre;
    // ConstraintDeclarationException : не допускается при переопределении метода
    public Float calculatePrice(@DecimalMin("1.4") Float rate) {
        return price * rate;
    }
}

public class Customer {
    @Email
    private String userId;
    @NotNull @Size(min = 4, max = 50, message = "Имя должно быть размером от
            {min} до {max} символов")

            Написание ограничений 117

            private String firstName;
            private String lastName;
            @Email(message = "Восстановленный электронный адрес не является действительным")
            private String recoveryEmail;
            private String phoneNumber;
            @Min(value = 18, message = "Покупатель слишком молод. Ему должно быть больше
                    {value} лет")
                    Private Integer age;
// Конструкторы, геттеры, сеттеры
}

public class URLValidator implements ConstraintValidator<URL, String> {
    private String protocol;
    private String host;
    private int port;
    public void initialize(URL url) {
        this.protocol = url.protocol();
        this.host = url.host();
        this.port = url.port();
    }
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }
        java.net.URL url;
        try {
            url = new java.net.URL(value);
        } catch (MalformedURLException e) {
            return false;
        }
        if (protocol != null && protocol.length() > 0 &&
                !url.getProtocol().equals(protocol)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Неверный
                    протокол").addConstraintViolation();
            return false;
        }
        if (host != null && host.length() > 0 && !url.getHost().startsWith(host)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Неверный
                    хост").addConstraintViolation();
            return false;
        }
        if (port != -1 && url.getPort() != port) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Неверный
                    порт").addConstraintViolation();
            return false;

            Написание ограничений 119

        }
        return true;
    }
}

@ChronologicalDates(groups = Delivery.class)
public class Order {
    @NotNull
    private Long id;
    @NotNull @Past
    private Date creationDate;
    private Double totalAmount;
    @NotNull(groups = Payment.class) @Past(groups = Payment.class)
    private Date paymentDate;
    @NotNull(groups = Delivery.class) @Past(groups = Delivery.class)
    private Date deliveryDate;
    private List<OrderLine> orderLines;
// Конструкторы, геттеры, сеттеры
}

<?xml version="1.0" encoding="UTF-8"?>
<validation-config
        xmlns="http://jboss.org/xml/ns/javax/validation/configuration"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://jboss.org/xml/ns/javax/validation/configuration
        validation-configuration-1.1.xsd"
        version="1.1">
<constraint-mapping>META-INF/constraints.xml</constraint-mapping>
</validation-config>

<?xml version="1.0" encoding="UTF-8"?>
<constraint-mappings
        xmlns="http://jboss.org/xml/ns/javax/validation/mapping"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://jboss.org/xml/ns/javax/validation/mapping
        validation-mapping-1.1.xsd"
        version="1.1">
<bean class="org.agoncal.book.javaee7.chapter03.Book" ignore-annotations="false">
<field name="title">
<constraint annotation="javax.validation.constraints.NotNull">
<message>Title should not be null</message>
</constraint>
</field>
<field name="price">
<constraint annotation="javax.validation.constraints.NotNull"/>
<constraint annotation="javax.validation.constraints.Min">
<element name="value">2</element>
</constraint>
</field>
<field name="description">
<constraint annotation="javax.validation.constraints.Size">
<element name="max">2000</element>
</constraint>
</field>
</bean>
</constraint-mappings>

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

@Entity
public class Book {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private Float price;
    private String description;
    private String isbn;
    private Integer nbOfPage;
    private Boolean illustrations;
    public Book() {
    }
// Геттеры, сеттеры
}

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

@Entity
@SecondaryTables({
        @SecondaryTable(name = "city"),
        @SecondaryTable(name = "country")
})
public class Address {
    @Id
    private Long id;
    private String street1;
    private String street2;
    @Column(table = "city")
    private String city;
    @Column(table = "city")
    private String state;
    @Column(table = "city")
    private String zipcode;
    @Column(table = "country")
    private String country;
// Конструкторы, геттеры, сеттеры
}

@Entity
@Table(name = "t_address")
@SecondaryTables({
        @SecondaryTable(name = "t_city"),
        @SecondaryTable(name = "t_country")
})
public class Address {
// Атрибуты, конструктор, геттеры, сеттеры
}

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Float price;
    private String description;
    private String isbn;
    private Integer nbOfPage;
    private Boolean illustrations;
// Конструкторы, геттеры, сеттеры
}

@Embeddable
public class NewsId {
    private String title;
    private String language;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class News {
    @EmbeddedId
    private NewsId id;
    private String content;
// Конструкторы, геттеры, сеттеры
}

    NewsId pk = new NewsId("Richard Wright has died on September 2008", "EN")
    News news = em.find(News.class, pk);

public class NewsId {
    private String title;
    private String language;
// Конструкторы, геттеры, сеттеры
}

@Entity
@IdClass(NewsId.class)
public class News {
    @Id private String title;
    @Id private String language;
    private String content;
// Конструкторы, геттеры, сеттеры
}

    create table NEWS (
        CONTENT VARCHAR(255),
        TITLE VARCHAR(255) not null,
        LANGUAGE VARCHAR(255) not null,
        primary key (TITLE, LANGUAGE)
        );

@Target({METHOD, FIELD}) @Retention(RUNTIME)
public @interface Basic {
    FetchType fetch() default EAGER;
    boolean optional() default true;
}

@Entity
public class Track {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Float duration;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] wav;
    private String description;
// Конструкторы, геттеры, сеттеры
}

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

@Entity
public class Customer {
    @Id @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Transient
    private Integer age;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
// Конструкторы, геттеры, сеттеры
}

public enum CreditCardType {
    VISA,
    MASTER_CARD,
    AMERICAN_EXPRESS
}

@Entity
@Table(name = "credit_card")
public class CreditCard {
    @Id
    private String number;
    private String expiryDate;
    private Integer controlNumber;
    private CreditCardType creditCardType;
// Конструкторы, геттеры, сеттеры
}

@Entity
@Table(name = "credit_card")
public class CreditCard {
    @Id
    private String number;
    private String expiryDate;
    private Integer controlNumber;
    @Enumerated(EnumType.STRING)
    private CreditCardType creditCardType;
// Конструкторы, геттеры, сеттеры
}

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

@Entity
public class Book {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private Float price;
    private String description;
    private String isbn;
    private Integer nbOfPage;
    private Boolean illustrations;

    Элементарное отображение 181

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "Tag")
    @Column(name = "Value")
    private List<String> tags = new ArrayList<>();
// Конструкторы, геттеры, сеттеры
}

@Entity
public class CD {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private Float price;
    private String description;
    @Lob
    private byte[] cover;
    @ElementCollection
    @CollectionTable(name="track")
    @MapKeyColumn (name = "position")
    @Column(name = "title")
    private Map<Integer, String> tracks = new HashMap<>();
// Конструкторы, геттеры, сеттеры
}

@Embeddable
public class Address {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zipcode;
    private String country;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Customer {
    @Id @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @Embedded
    private Address address;
// Конструкторы, геттеры, сеттеры
}

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
    @Embedded
    private Address address;
// Конструкторы, геттеры, сеттеры
}

@Embeddable
@Access(AccessType.PROPERTY)
public class Address {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    // Конструкторы
    @Column(nullable = false)
    public String getStreet1() {
        return street1;
    }
    public void setStreet1(String street1) {
        this.street1 = street1;
    }
    public String getStreet2() {
        return street2;
    }
    public void setStreet2(String street2) {
        this.street2 = street2;
    }
    @Column(nullable = false, length = 50)
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    @Column(length = 3)
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    @Column(name = "zip_code", length = 10)
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}

@Entity
public class Customer {
    @Id @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Address address;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Address {
    @Id @GeneratedValue
    private Long id;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zipcode;
    private String country;
// Конструкторы, геттеры, сеттеры
}

@Target({METHOD, FIELD}) @Retention(RUNTIME)
public @interface OneToOne {
    Class targetEntity() default void.class;
    CascadeType[] cascade() default {};
    FetchType fetch() default EAGER;
    boolean optional() default true;
    String mappedBy() default "";
    boolean orphanRemoval() default false;
}

@Entity
public class Customer {
    @Id @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "add_fk", nullable = false)
    private Address address;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Order {
    @Id @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    private List<OrderLine> orderLines;
// Конструкторы, геттеры, сеттеры
}

@Entity
@Table(name = "order_line")
public class OrderLine {
    @Id @GeneratedValue
    private Long id;
    private String item;
    private Double unitPrice;
    private Integer quantity;
// Конструкторы, геттеры, сеттеры
}

@Target({METHOD, FIELD}) @Retention(RUNTIME)
public @interface JoinTable {
    String name() default "";
    String catalog() default "";
    String schema() default "";
    JoinColumn[] joinColumns() default {};
    JoinColumn[] inverseJoinColumns() default {};
    UniqueConstraint[] uniqueConstraints() default {};
    Index[] indexes() default {};
}

@Entity
public class Order {
    @Id @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @OneToMany
    @JoinTable(name = "jnd_ord_line",
            joinColumns = @JoinColumn(name = "order_fk"),
            inverseJoinColumns = @JoinColumn(name = "order_line_fk") )
    private List<OrderLine> orderLines;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Order {
    @Id @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_fk")
    private List<OrderLine> orderLines;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class CD {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private Float price;
    private String description;
    @ManyToMany(mappedBy = "appearsOnCDs")
    private List<Artist> createdByArtists;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Artist {
    @Id @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToMany
    @JoinTable(name = "jnd_art_cd",
            joinColumns = @JoinColumn(name = "artist_fk"),
            inverseJoinColumns = @JoinColumn(name = "cd_fk"))
    private List<CD>appearsOnCDs;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Order {
    @Id @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @OneToMany(fetch = FetchType.EAGER)
    private List<OrderLine> orderLines;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Comment {
    @Id @GeneratedValue
    private Long id;
    private String nickname;
    private String content;
    private Integer note;
    @Column(name = "posted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class News {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String content;
    @OneToMany(fetch = FetchType.EAGER)
    @OrderBy("postedDate DESC")
    private List<Comment> comments;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Comment {
    @Id @GeneratedValue
    private Long id;
    private String nickname;
    private String content;
    private Integer note;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class News {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String content;
    @OneToMany(fetch = FetchType.EAGER)
    @OrderColumn(name = "posted_index")
    private List<Comment> comments;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Item {
    @Id @GeneratedValue
    protected Long id;
    protected String title;
    protected Float price;
    protected String description;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Book extends Item {
    private String isbn;
    private String publisher;
    private Integer nbOfPage;
    private Boolean illustrations;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class CD extends Item {
    private String musicCompany;
    private Integer numberOfCDs;
    private Float totalDuration;
    private String genre;
// Конструкторы, геттеры, сеттеры
}

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name="disc",
        discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("I")
public class Item {
    @Id @GeneratedValue
    protected Long id;
    protected String title;
    protected Float price;
    protected String description;
// Конструкторы, геттеры, сеттеры
}

@DiscriminatorValue("B")
public class Book extends Item {
    private String isbn;
    private String publisher;
    private Integer nbOfPage;
    private Boolean illustrations;
// Конструкторы, геттеры, сеттеры
}

@DiscriminatorValue("C")
public class CD extends Item {
    private String musicCompany;
    private Integer numberOfCDs;
    private Float totalDuration;
    private String genre;
// Конструкторы, геттеры, сеттеры
}

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {
    @Id @GeneratedValue
    protected Long id;
    protected String title;
    protected Float price;
    protected String description;
// Конструкторы, геттеры, сеттеры
}

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item {
    @Id @GeneratedValue
    protected Long id;
    protected String title;
    protected Float price;
    protected String description;
// Конструкторы, геттеры, сеттеры
}

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

public class Item {
    protected String title;
    protected Float price;
    protected String description;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Book extends Item {
    @Id @GeneratedValue
    private Long id;
    private String isbn;
    private String publisher;
    private Integer nbOfPage;
    private Boolean illustrations;
// Конструкторы, геттеры, сеттеры
}

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {
    @Id @GeneratedValue
    protected Long id;
    @Column(length = 50, nullable = false)
    protected String title;
    protected Float price;
    @Column(length = 2000)
    protected String description;
// Конструкторы, геттеры, сеттеры
}

@Entity
public class Book extends Item {
    private String isbn;
    private String publisher;
    private Integer nbOfPage;
    private Boolean illustrations;
// Конструкторы, геттеры, сеттеры
}