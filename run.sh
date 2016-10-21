#/bin/bash

javac -d . -cp . com/buvi/Sortable.java
java -cp . com.buvi.Sortable $1
