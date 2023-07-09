# Задание ВКР по направлению Java

## Задание

В случае возникновения аварии в электроэнергетической системе
происходит запись значений электрических величин и состояния дискретных
сигналов различных устройств в ходе аварийного процесса. Запись
автоматически осуществляет регистратор аварийных событий (РАС) в виде
файлов формата COMTRADE. Подробно с форматом COMTRADE можно
ознакомиться по ссылке: https://sank6.ru/docs_comtrade_viewer/comtrade.html.

Предлагается разработать программу, которая осуществляет чтение
COMTRADE-файлов, определяет параметры аварийного режима и
предоставляет пользователю данные на веб-странице.

### Порядок выполнения работы:

* По ссылке на Яндекс-диск (https://disk.yandex.ru/d/EOmKEMPrurrPYA)
  расположены COMTRADE-файлы, пронумерованные по вариантам. Во
  время защиты ВКР вам будет необходимо продемонстрировать работу
  вашей программы с любым вариантом COMTRADE-файла по
  указанию комиссии;
* Для предварительного просмотра параметров аварийного режима
  следует воспользоваться программой чтения COMTRADE-файлов
  (https://www.mtrele.ru/texnicheskaya-podderzhka/programmnoe-
  obespechenie/fastview.html). В программе чтения следует визуально
  определить действующее значение токов в нормальном и аварийном
  режиме. На основе визуального определения далее вы можете
  проверить корректность работы вашего алгоритма по п.4;
* Составить алгоритм чтения COMTRADE-файла, его расшифровку и
  записи в массив программы;
* Составить алгоритм обработки данных: при помощи фильтра Фурье
  получение действующих значений токов, определение времени
  возникновения аварийного режима (по скорости изменения
  действующего значения), поврежденные фазы. Пример алгоритма
  чтения COMTRADE-файла и определения действующего значения
  располагается по ссылке (https://disk.yandex.ru/d/dbklof7ZVvK73A);
* Записать в базу данных время возникновения короткого замыкания и
  действующие значения токов аварийного режима по фазам;
* Вывести на веб-страницу график токов доаварийного режима (0,1 с) и
  аварийного режима, ниже привести в табличном виде время
  возникновения аварийного режима и действующие значения токов по
  фазам, выгрузив эту информацию из базы данных п.5.

### Состав отчета

* Во введении указать цель выпускной квалификационной работы;
* Описать порядок работы разработанной программы. Представить
  основные фрагменты программы с описанием принципа работы
  каждого фрагмента;
* Привести скриншот веб-страницы с результатом работы программы;
* В приложении привести полный код программы.

**_На защите вам необходимо выступить с презентацией о вашей работе
(доклад не более 5 минут).
Во время защиты ВКР вам будет необходимо продемонстрировать
работу вашей программы с любым вариантом COMTRADE-файла по
указанию комиссии._**

Даты проведения защит 15-19 мая. В день до 10 защит. Запись на
защиты по ссылке https://disk.yandex.ru/i/-CBmyxgmYdeXdg  Если на
определенный день записалось менее 10 студентов – будут объединены с
другим днем.

За 3 (три) рабочих дня до защиты оформленную ВКР необходимо
выслать на проверку (нормоконтроль) на почту _BiserovDM@mpei.ru_