# Instrucciones:

### Opciones disponibles:

###### Algoritmo genético

* -c (--crossover) N : Probabilidad de cruce.
* -f (--file) VAL    : Ruta del fichero.
* -m (--mutation) N  : Probabilidad de mutación.
* -s (--seed) N      : Semilla para números pseudoaleatorios.

###### Enfriamiento simulado
* -f (--file) VAL    : Ruta del fichero.
* -a (--annealingFactor) N : Factor usado para el decaimiento de la temperatura.
* -s (--seed) N      : Semilla para números pseudoaleatorios.

### Cómo compilar

Para compilar los programas, se deben utilizar los siguientes comandos después de haber descomprimido el archivo comprimido:

###### Algoritmo genético
```
javac -classpath .:args4j-2.33.jar ga/ssGA/*.java
```
###### Enfriamiento simulado
```
javac -classpath .:args4j-2.33.jar sa/ssSA/*.java
```

### Cómo ejecutar

Para ejecutar los programas, ejecute los siguientes comandos:

###### Algoritmo genético
```
java -classpath .:args4j-2.33.jar ga.ssGA.Exe 
```
###### Enfriamiento simulado
```
java -classpath .:args4j-2.33.jar sa.ssSA.Exe 
```
###### Uso de parámetros

Los comandos anteriores utilizarán las configuraciones por defecto. A continuación, se muestran comandos que utilizan parámetros:

```
java -classpath .:args4j-2.33.jar ga.ssGA.Exe -f problema2.txt -s 100
```

```
java -classpath .:args4j-2.33.jar ga.ssGA.Exe -m 0.01 -c 0.99
```

```
java -classpath .:args4j-2.33.jar sa.ssSA.Exe -a 0.99 -s 300
```

```
java -classpath .:args4j-2.33.jar sa.ssSA.Exe -a 0.95 -s 100 -f problema2.txt
```
