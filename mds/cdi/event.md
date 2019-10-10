# События
* Внедрение зависимостей, перехватчики и декораторы гарантируют слабую связан-
ность, обеспечивая разнообразные варианты дополнительного поведения как во
время развертывания, так и во время выполнения. События, кроме того, позволяют
компонентам взаимодействовать вне зависимости от времени компиляции. Один
компонент может определить событие, другой — инициировать событие, а третий —
обработать его. Эта базовая схема следует шаблону проектирования «Наблюдатель»
(Observer), разработанному группой Gang of Four.

@javax.enterprise.event.

* Производители событий запускают события, используя интерфейс javax.enter-
prise.event. Производитель инициирует события вызовом метода fire(), передает
объект события и не зависит от наблюдателя. В листинге 2.36 BookService запускает
событие (bookAddedEvent) каждый раз при создании книги. Код bookAddedEvent.fire(book)
инициирует событие и оповещает любые методы наблюдателя, следящие
за этим конкретным событием. Содержание этого события — сам объект Book, который
будет передан от производителя потребителю.
```xml
    public class BookService {
        @Inject
        private NumberGenerator numberGenerator;
        @Inject
        private Event<Book> bookAddedEvent;

        public Book createBook(String title, Float price, String description) {
            Book book = new Book(title, price, description);
            book.setIsbn(numberGenerator.generateNumber());
            bookAddedEvent.fire(book);
            return book;
        }
    }
```
* События инициируются производителем событий и на них подписываются на-
блюдатели. Наблюдатель — это компонент с одним или несколькими «отслежива-
ющими» методами. Каждый из этих методов наблюдателя берет в качестве параметра
событие определенного типа, сопровождаемое аннотацией @Observers и опциональ-
ными квалификаторами. Метод наблюдателя оповещается о событии, если объект
события соответствует типу и всем квалификаторам. В листинге 2.37 показана
служба инвентаризации, задача которой — отслеживать новые книжные поступле-
ния, дополняя информацию о книжном фонде. Там используется метод addBook,
который наблюдает за каждым событием с типом Book. Аннотированный параметр
называется параметром события. Поэтому, как только событие инициируется ком-
понентом BookService, контейнер CDI приостанавливает выполнение и передает
событие любому зарегистрированному наблюдателю. Внашем случае в листинге 2.37
будет вызван метод addBook, который обновит список книг, а затем контейнер про-
должит выполнение кода с того места, где он остановился в компоненте BookService.
Это означает, что события в CDI не рассматриваются асинхронно.
```xml
    public class InventoryService {
        @Inject
        private Logger logger;
        List<Book> inventory = new ArrayList<>();

        public void addBook(@Observes Book book) {
            logger.info("Adding book " + book.getTitle() + "to inventory");
            inventory.add(book);
        }
    }
```
* Как и большинство CDI, производство события и подписка являются типо-
безопасными и позволяют квалификаторам определять, какие наблюдатели
событий будут использоваться. Событию может быть назначен один или не-
сколько квалификаторов (с членами либо без таковых), которые позволяют
наблюдателям отличить его от остальных событий такого же типа. Листинг 2.38
возвращается к компоненту BookService, добавив туда дополнительное событие.
При создании книги инициируется событие bookAddedEvent, а при удалении — со-
бытие bookRemovedEvent, оба с типом Book. Чтобы можно было отличать эти со-
бытия, каждое из них сопровождается аннотацией @Added или @Removed. Код этих
квалификаторов идентичен коду в листинге 2.7: аннотация без членов и анноти-
рованная @Qualifier.
```xml
    public class BookService {
        @Inject
        private NumberGenerator numberGenerator;
        @Inject
        @Added
        private Event<Book> bookAddedEvent;
        @Inject
        @Removed
        private Event<Book> bookRemovedEvent;

        public Book createBook(String title, Float price, String description) {
            Book book = new Book(title, price, description);
            book.setIsbn(numberGenerator.generateNumber());
            bookAddedEvent.fire(book);
            return book;
        }

        public void deleteBook(Book book) {
            bookRemovedEvent.fire(book);
        }
    }
```
* InventoryService в листинге 2.39 наблюдает за обоими событиями, объявив два от-
дельных метода, один из которых наблюдает за событием одобавлении книги (@Observes
@Added Book), а другой — за событием о ее удалении (@Observes @Removed Book).
```xml
    public class InventoryService {
        @Inject
        private Logger logger;
        List<Book> inventory = new ArrayList<>();

        public void addBook(@Observes @Added Book book) {
            logger.warning("Книга " + book.getTitle() + " добавлена в список");
            inventory.add(book);
        }

        public void removeBook(@Observes @Removed Book book) {
            logger.warning("Книга " + book.getTitle() + " удалена из списка");
            inventory.remove(book);
        }
    }
```
* Поскольку модель события использует квалификаторы, вам было бы целесо-
образно задавать поля квалификаторов или агрегировать их. Следующий код на-
блюдает за всеми добавленными книгами, цена которых превышает 100:
```xml
void addBook(@Observes @Added @Price(greaterThan=100) Book book)
```