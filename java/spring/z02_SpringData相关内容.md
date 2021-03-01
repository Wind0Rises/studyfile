# Spring Data相关的

## 一、Spring的事务隔离级别

* required：如果当前有事务，则支持当前事务，如果当前没有事务，则创建一个事务。
* support：如果当前有事务，则支持当前事务，如果当前没有事务，则以无事务执行。
* mandatory：支持当前事务，如果没有事务，直接报错。
* require_new：新建一个事务，如果当前存在事务，这挂起事务。
* not_support：
* never：
* nested：