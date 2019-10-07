# Jakarta EE

## Стандартные контейнеры Java EE

![jee_container](img/jee_container.png)

## Контейнеры

* Контейнеры апплетов выполняются большинством браузеров. При разработке 
апплетов можно сконцентрироваться на визуальной стороне приложения, в то 
время как контейнер обеспечивает безопасную среду. Контейнер апплета ис-
пользует модель безопасности изолированной программной среды («песочни-цы»), 
где коду, выполняемому в «песочнице», не разрешается «играть» за еепределами. 
Это означает, что контейнер препятствует любому коду, загружен-
ному на ваш локальный компьютер, получать доступ к локальным ресурсам
системы (процессам либо файлам).

* Контейнер клиентского приложения (ACC) включает набор Java-классов, би-
блиотек и других файлов, необходимых для реализации в приложениях Java SE
таких возможностей, как внедрение, управление безопасностью и служба имено-
вания (в частности, Swing, пакетная обработка либо просто класс сметодом main()).
Контейнер ACC обращается к EJB-контейнеру, используя протокол RMI-IIOP,
а к веб-контейнеру — по протоколу HTTP (например, для веб-служб).

* Веб-контейнер предоставляет базовые службы для управления и исполнения веб-
компонентов (сервлетов, компонентов EJB Lite, страниц JSP, фильтров, слушате-
лей, страниц JSF и веб-служб). Он отвечает за создание экземпляров, инициали-
зацию и вызов сервлетов, а также поддержку протоколов HTTP и HTTPS. Этот
контейнер используется для подачи веб-страниц к клиент-браузерам.

* EJB-контейнер отвечает за управление и исполнение компонентов модели EJB  
(компонент-сеансы EJB и компоненты, управляемые сообщениями), содержа-
щих уровень бизнес-логики вашего приложения на Java EE. Он создает новые
сущности компонентов EJB, управляет их жизненным циклом и обеспечивает
реализацию таких сервисов, как транзакция, безопасность, параллельный доступ,
распределение, служба именования либо возможность асинхронного вызова.

## Сервисы

* Java API для транзакций — этот сервис предлагает интерфейс разграничения
транзакций, используемый контейнером и приложением. Он также предоставляет 
интерфейс между диспетчером транзакций и диспетчером ресурсов на
уровне интерфейса драйвера службы.

* Интерфейс сохраняемости Java — стандартный интерфейс для объектно-реляционного
отображения (ORM). С помощью встроенного языка запросов
JPQL вы можете обращаться к объектам, хранящимся в основной базе данных.

* Валидация — благодаря валидации компонентов объявляется ограничение це-
лостности на уровне класса и метода.

* Интерфейс Java для доступа к службам сообщений — позволяет компонентам
асинхронно обмениваться данными через сообщения. Он поддерживает надеж-
ный обмен сообщениями по принципу «от точки к точке» (P2P), а также модель
публикации-подписки (pub-sub).

* Java-интерфейс каталогов и служб именования (JNDI) — этот интерфейс, по-
явившийся в Java SE, используется как раз для доступа к системам служб 
именования и каталогов. Ваше приложение применяет его, чтобы ассоцииро-
вать (связывать) имена с объектами и затем находить их в каталогах. Вы мо-
жете задать поиск источников данных, фабрик классов JMS, компонентов EJB
и других ресурсов. Интерфейс JNDI, повсеместно присутствовавший в коде
до версии 1.4 J2EE, в настоящее время используется более прозрачным спо-
собом — через внедрение.

* Интерфейс JavaMail — многим приложениям требуется функция отправки со-
общений электронной почты, которая может быть реализована благодаря этому
интерфейсу.

* Фреймворк активизации компонентов JavaBeans (JAF) — интерфейс JAF, яв-
ляющийся составной частью платформы Java SE, предоставляет фреймворк для
обработки данных различных MIME-типов. Используется сервисом JavaMail.

* Обработка XML — большинство компонентов Java EE могут развертываться
с помощью опциональных дескрипторов развертывания XML, а приложениям
часто приходится манипулировать XML-документами. Интерфейс Java для
обработки XML (JAXP) поддерживает синтаксический анализ документов
с применением интерфейсов SAX и DOM, а также на языке XSLT.

* Обработка JSON (объектной нотации JavaScript) — появившийся только в Java
EE 7 Java-интерфейс для обработки JSON (JSON-P) позволяет приложениям
синтаксически анализировать, генерировать, трансформировать и запрашивать
JSON.

* Архитектура коннектора Java EE — коннекторы позволяют получить доступ
к корпоративным информационным системам (EIS) с компонента Java EE.
К таким компонентам относятся базы данных, мейнфреймы либо программы
для планирования и управления ресурсами предприятия (ERP).

* Службы безопасности — служба аутентификации и авторизации для платформы
Java (JAAS) позволяет сервисам аутентифицироваться и устанавливать права
доступа, обязательные для пользователей. Контракт поставщика сервиса авто-
ризации Java для контейнеров (JACC) определяет соглашение о взаимодействии 
между сервером приложений Java EE и поставщиком сервиса авторизации, по-
зволяя, таким образом, сторонним поставщикам такого сервиса подключаться
к любому продукту Java EE. Интерфейс поставщика сервисов аутентификации
Java для контейнеров (JASPIC) определяет стандартный интерфейс, с помощью
которого модули аутентификации могут быть интегрированы с контейнерами.
В результате модули могут установить идентификаторы подлинности, исполь-
зуемые контейнерами.

* Веб-службы — Java EE поддерживает веб-службы SOAP и RESTful. Интерфейс
Java для веб-служб на XML (JAX-WS), сменивший интерфейс Java с поддерж-
кой вызовов удаленных процедур на основе XML (JAX-RPC), обеспечивает
работу веб-служб, работающих по протоколу SOAP/HTTP. Интерфейс Java для
веб-служб RESTful (JAX-RS) поддерживает веб-службы, использующие стиль
REST.

* Внедрение зависимостей — начиная с Java EE 5, некоторые ресурсы могут
внедряться в управляемые компоненты. К таким ресурсам относятся источ-
ники данных, фабрики классов JMS, единицы сохраняемости, компоненты
EJB и т. д. Кроме того, для этих целей Java EE 7 использует спецификации по
контексту и внедрению зависимости (CDI), а также внедрение зависимости
для Java (DI).

* Управление — Java EE с помощью специального управляющего компонента
определяет API для операций с контейнерами и серверами. Интерфейс управ-
ляющих расширений Java (JMXAPI) также используется для поддержки управ-
ления.

* Развертывание — спецификация Java EE по развертыванию определяет согла-
шение о взаимодействии между средствами развертывания и продуктами Java
EE для стандартизации развертывания приложения.

[Сетевые протоколы](mds/network_protocols.md)

[Упаковка](mds/packaging.md)

[Дескриптор развертывания](mds/deployment_descriptors.md)

[Java EE 7 спецификации](mds/java_ee7_specifications.md)