## Запуск проекта

### 1. Поменять настройки подключения к базе данных

Для работы с базой данных необходимо настроить подключение. Измените параметры подключения в файле `hibernate.cfg.xml`:

```xml
<hibernate-configuration>
    <session-factory>
      <property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/hibernate</property>
        <property name="hibernate.connection.username">username</property>
        <property name="hibernate.connection.password">password</property>
    </session-factory>
</hibernate-configuration>